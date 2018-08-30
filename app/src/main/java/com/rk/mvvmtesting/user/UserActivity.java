package com.rk.mvvmtesting.user;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.rk.mvvmtesting.R;
import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.databinding.ActivityUserBinding;

public class UserActivity extends AppCompatActivity {

    // RecyclerView
    private int mColumnCount = 1;
    //private RecyclerView mUserRecyclerView;
    private UserRecyclerViewAdapter mAdapter;
    // ViewModel
    private UserViewModel mViewModel;
    private ActivityUserBinding userBinding;

    /**
     * Activity lifecycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        initUI();
        setUpViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showInputDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Setup methods
     */
    private void setUpViewModel() {
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.getAllUsers().observe(this, users -> {
            if (users != null) {
                userBinding.setIsLoading(false);
                mAdapter.refreshList(users);
            } else {
                userBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            userBinding.executePendingBindings();
        });
    }

    private void initUI() {
        mColumnCount = getResources().getInteger(R.integer.user_list_columns);
        if (mColumnCount <= 1) {
            userBinding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            userBinding.userRecyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }
        mAdapter = new UserRecyclerViewAdapter();
        userBinding.userRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Add user
     */
    private void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_add_user, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editTextFirstName = promptView.findViewById(R.id.input_first_name);
        final EditText editTextLastName = promptView.findViewById(R.id.input_last_name);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    String fName = editTextFirstName.getText().toString();
                    String lName = editTextLastName.getText().toString();
                    if (isValidInputs(fName, lName)) {
                        addUser(fName, lName);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    dialog.cancel();
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    // Add user using view model
    private void addUser(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        mViewModel.addUSer(user);
    }

    // Validates input
    public boolean isValidInputs(String firstName, String lastName) {
        if(firstName.isEmpty() || lastName.isEmpty()) {
            return false;
        }
        return true;
    }
}
package com.rk.mvvmtesting.user;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.rk.mvvmtesting.R;
import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.utilities.InjectorUtils;

public class UserActivity extends AppCompatActivity {

    // RecyclerView
    private int mColumnCount = 1;
    private RecyclerView mUserRecyclerView;
    private UserRecyclerViewAdapter mAdapter;
    // ViewModel
    private UserViewModel mViewModel;

    /**
     * Activity lifecycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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
        UserViewModelFactory factory = InjectorUtils.
                provideUserViewModelFactory(getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        mViewModel.getAllUsers().observe(this, users -> {
            mAdapter.refreshList(users);
        });
    }

    private void initUI() {
        mUserRecyclerView = findViewById(R.id.user_recycler_view);
        mColumnCount = getResources().getInteger(R.integer.user_list_columns);
        if (mColumnCount <= 1) {
            mUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mUserRecyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }
        mAdapter = new UserRecyclerViewAdapter();
        mUserRecyclerView.setAdapter(mAdapter);
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
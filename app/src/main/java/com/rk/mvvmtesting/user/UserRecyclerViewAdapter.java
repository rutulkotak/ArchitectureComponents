package com.rk.mvvmtesting.user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rk.mvvmtesting.R;
import com.rk.mvvmtesting.data.localdb.User;

import java.util.List;

public class UserRecyclerViewAdapter
        extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private List<User> userList;

    public UserRecyclerViewAdapter() {
    }

    public UserRecyclerViewAdapter(List<User> items) {
        userList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = userList.get(position);
        holder.mFirstName.setText(holder.mItem.getFirstName());
        holder.mLastName.setText(holder.mItem.getLastName());

        holder.mView.setOnClickListener(v -> {
            
        });
    }

    @Override
    public int getItemCount() {
        if(userList == null) {
            return 0;
        }
        return userList.size();
    }

    public void refreshList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFirstName;
        public final TextView mLastName;
        private User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFirstName = view.findViewById(R.id.first_name);
            mLastName = view.findViewById(R.id.last_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLastName.getText() + "'";
        }
    }
}

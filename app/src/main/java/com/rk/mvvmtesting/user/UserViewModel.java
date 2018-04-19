package com.rk.mvvmtesting.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.data.repository.UserRepository;

import java.util.List;

/**
 * Created by Rutul Kotak
 *
 * ViewModel survives rotation & other configuration changes.
 * It keeps running while the activity is on the back stack.
 * It promotes simple and concise code.
 */

public class UserViewModel extends ViewModel {

    private LiveData<List<User>> userList;
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        if(userList == null) {
            userList = userRepository.getAllUsers();
        }
    }

    public LiveData<List<User>> getAllUsers() {
        return userList;
    }

    public void addUSer(User user) {
        userRepository.insert(user);
    }
}
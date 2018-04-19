package com.rk.mvvmtesting.user;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rk.mvvmtesting.data.repository.UserRepository;

/**
 * Created by Rutul Kotak
 *
 * ViewModelFactory provides ViewModel
 */

public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    UserRepository userRepository;

    public UserViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new UserViewModel(userRepository);
    }
}
package com.rk.mvvmtesting.utilities;

import android.content.Context;

import com.rk.mvvmtesting.data.LocalDatabase;
import com.rk.mvvmtesting.data.network.UserNetworkDataSource;
import com.rk.mvvmtesting.data.repository.UserRepository;
import com.rk.mvvmtesting.user.UserViewModelFactory;

/**
 * Created by Rutul Kotak
 *
 * Provides objects
 */

public class InjectorUtils {

    public static UserRepository provideUserRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        LocalDatabase database = LocalDatabase.getsInstance(
                context.getApplicationContext());
        UserNetworkDataSource networkDataSource =
                UserNetworkDataSource.getInstance(context.getApplicationContext());

        return UserRepository.getInstance(
                networkDataSource, database.userDao(), executors);
    }

    public static UserViewModelFactory provideUserViewModelFactory(Context context) {
        UserRepository repository = provideUserRepository(context.getApplicationContext());
        return new UserViewModelFactory(repository);
    }
}
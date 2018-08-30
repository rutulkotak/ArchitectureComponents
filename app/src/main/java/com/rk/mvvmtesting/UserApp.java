package com.rk.mvvmtesting;

import android.app.Application;

import com.rk.mvvmtesting.data.LocalDatabase;
import com.rk.mvvmtesting.data.repository.UserRepository;
import com.rk.mvvmtesting.utilities.AppExecutors;

public class UserApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public UserRepository getRepository() {
        return UserRepository.getInstance(getDatabase());
    }

    public LocalDatabase getDatabase() {
        return LocalDatabase.getInstance(this, mAppExecutors);
    }

    public AppExecutors getmAppExecutors() {
        return mAppExecutors;
    }
}
package com.rk.mvvmtesting.data.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.data.localdb.UserDao;
import com.rk.mvvmtesting.data.network.UserNetworkDataSource;
import com.rk.mvvmtesting.utilities.AppExecutors;

import java.util.List;

/**
 * Created by Rutul Kotak
 *
 * Repository will be hides the underlying source used to manage data
 * UI dose not know from where the data is coming from
 */

public class UserRepository {

    private static final String LOG_TAG = "UserRepository";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static UserRepository sInstance;

    private final UserNetworkDataSource webservice;
    private final UserDao userDao;
    private final AppExecutors executors;

    public UserRepository(UserNetworkDataSource webservice, UserDao userDao, AppExecutors executors) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executors = executors;
    }

    public synchronized static UserRepository getInstance(
            UserNetworkDataSource webService, UserDao userDao, AppExecutors executors) {

        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new UserRepository(webService, userDao, executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void insert(User user) {
        executors.diskIO().execute(() -> {
            userDao.insert(user);
        });
    }

    public void bulkInsert(User... users) {
        executors.diskIO().execute(() -> {
            userDao.bulkInsert(users);
        });
    }
}
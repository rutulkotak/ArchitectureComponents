package com.rk.mvvmtesting.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.rk.mvvmtesting.data.LocalDatabase;
import com.rk.mvvmtesting.data.localdb.User;
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

    private AppExecutors executors;
    private final LocalDatabase mDatabase;
    private MediatorLiveData<List<User>> mObservableUsers;

    public UserRepository(final LocalDatabase database) {
        mDatabase = database;

        mObservableUsers = new MediatorLiveData<>();
        mObservableUsers.addSource(mDatabase.userDao().getAllUsers(),
                productEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() != null) {
                mObservableUsers.postValue(productEntities);
            }
        });
    }

    public static UserRepository getInstance(final LocalDatabase database) {
        if (sInstance == null) {
            synchronized (UserRepository.class) {
                if (sInstance == null) {
                    sInstance = new UserRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<User>> getAllUsers() {
        return mObservableUsers;
    }

    public void insert(User user) {
        mDatabase.userDao().insert(user);
    }
}
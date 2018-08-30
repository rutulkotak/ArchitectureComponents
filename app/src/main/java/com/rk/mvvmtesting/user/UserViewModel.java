package com.rk.mvvmtesting.user;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.rk.mvvmtesting.UserApp;
import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.data.repository.UserRepository;
import com.rk.mvvmtesting.utilities.AppExecutors;

import java.util.List;

/**
 * Created by Rutul Kotak
 *
 * ViewModel survives rotation & other configuration changes.
 * It keeps running while the activity is on the back stack.
 * It promotes simple and concise code.
 */

public class UserViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<User>> mObservableUsers;
    private UserRepository userRepository;
    private AppExecutors executors;

    public UserViewModel(Application application) {
        super(application);

        mObservableUsers = new MediatorLiveData<>();
        mObservableUsers.setValue(null);

        userRepository = ((UserApp) application).getRepository();
        executors = ((UserApp) application).getmAppExecutors();

        LiveData<List<User>> users = userRepository.getAllUsers();
        mObservableUsers.addSource(users, mObservableUsers::setValue);
    }

    public LiveData<List<User>> getAllUsers() {
        return mObservableUsers;
    }

    public void addUSer(User user) {
        executors.diskIO().execute(() -> userRepository.insert(user));
    }

}
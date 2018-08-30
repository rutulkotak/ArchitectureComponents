package com.rk.mvvmtesting.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.data.localdb.UserDao;
import com.rk.mvvmtesting.utilities.AppExecutors;

import java.util.List;

/**
 * Created by Rutul Kotak
 *
 * Local database management
 */

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    // DAOs
    abstract public UserDao userDao();

    private static final String LOG_TAG = LocalDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "lead_db";

    // For Singleton instantiation
    private static LocalDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static LocalDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LocalDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public static LocalDatabase buildDatabase(final Context context, final AppExecutors executors) {
        return Room.databaseBuilder(context.getApplicationContext(),
                LocalDatabase.class,
                LocalDatabase.DATABASE_NAME)
                .addCallback(new Callback() {

                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        LocalDatabase database = LocalDatabase.getInstance(context, executors);
                        //insertData(database, userList);
                        database.setDatabaseCreated();
                    }
                }).build();
    }

    private static void insertData(final LocalDatabase database,
                                   final List<User> userList) {
        database.runInTransaction(() -> {
            //database.userDao().bulkInsert(userList);
        });
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
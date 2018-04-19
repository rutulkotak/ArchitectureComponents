package com.rk.mvvmtesting.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.data.localdb.UserDao;

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
    private static final Object LOCK = new Object();
    private static LocalDatabase sInstance;

    public static LocalDatabase getsInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if(sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        LocalDatabase.class,
                        LocalDatabase.DATABASE_NAME).build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }
}
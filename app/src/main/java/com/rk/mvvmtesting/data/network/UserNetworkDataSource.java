package com.rk.mvvmtesting.data.network;

import android.content.Context;
import android.util.Log;

/**
 * Created by Rutul Kotak
 *
 * Responsible for network request
 */

public class UserNetworkDataSource {

    private static final String LOG_TAG = UserNetworkDataSource.class.getSimpleName();
    private static UserNetworkDataSource sInstance;
    private static final Object LOCK = new Object();

    private final Context mContext;

    private UserNetworkDataSource(Context context) {
        mContext = context;
    }

    public static UserNetworkDataSource getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new UserNetworkDataSource(context.getApplicationContext());
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }
}
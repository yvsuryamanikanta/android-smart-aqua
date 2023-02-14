package com.odos.smartaqua.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {
    private static final String TAG = CheckNetwork.class.getSimpleName();


    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } else {
            return false;
        }
    }
}

package com.ongtonnesoup.permissions;

import android.util.Log;

public class PerMissionsFlow implements Runnable {

    private static final String TAG = "PerMissionsFlow";

    public void onGranted() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        try {
            onGranted();
        } catch (SecurityException e) {
            Log.d(TAG, "Security exception");
        }
    }

}

package com.ongtonnesoup.permissions.flow;

import android.util.Log;

public class PerMissionsDeniedFlow implements Runnable {

    private static final String TAG = "PerMissionsFlow";

    public void onDenied() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        try {
            onDenied();
        } catch (SecurityException e) {
            Log.d(TAG, "Security exception");
        }
    }

}
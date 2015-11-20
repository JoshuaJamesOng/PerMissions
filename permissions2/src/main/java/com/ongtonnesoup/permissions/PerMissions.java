package com.ongtonnesoup.permissions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.HashMap;

public class PerMissions extends Fragment {

    public static final String TAG = "PerMissions";
    private static final int REQUEST_PERMISSIONS = 10;

    private Bus bus;
    private HashMap<Integer, Runnable> mFlows;
    private PermissionHandler handler;
    private PermissionResultHandler callback;

    public PerMissions() {
        // Required empty public constructor
    }

    /**
     * @param handler
     * @return
     */
    public PerMissions handler(PermissionHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * @param bus
     * @return
     */
    public PerMissions bus(Bus bus) {
        this.bus = bus;
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public PerMissions callback(PermissionResultHandler callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mFlows = new HashMap<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (handler instanceof PermissionHandlerImpl && bus != null) {
            bus.register(handler);
        }
    }

    @Override
    public void onPause() {
        if (handler instanceof PermissionHandlerImpl && bus != null) {
            bus.unregister(handler);
        }
        super.onPause();
    }

    /**
     * @param permissions Permission to request
     * @param flow        Code to execute if granted
     */
    public void getPermission(String[] permissions, Runnable flow) {

        if (PermissionUtil.hasSelfPermission(getActivity(), permissions)) {
            callback.onPermissionGranted(permissions, flow);
        } else {
            if (PermissionUtil.showExplanation(getActivity(), permissions) && !mFlows.containsKey(Arrays.hashCode(permissions))) {
                mFlows.put(Arrays.hashCode(permissions), flow);
                callback.onPermissionExplain(permissions, flow);
            } else {
                mFlows.put(Arrays.hashCode(permissions), flow);
                requestPermissions(permissions, REQUEST_PERMISSIONS);
            }

        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                Runnable flow = mFlows.get(Arrays.hashCode(permissions));
                removeFlow(Arrays.hashCode(permissions));
                callback.onPermissionGranted(permissions, flow);
            } else {
                Log.i("Permissions", "Permission was NOT granted.");
                callback.onPermissionDenied(PermissionUtil.deniedPermissions(permissions, grantResults));
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean removeFlow(int key) {
        if (mFlows.containsKey(key)) {
            mFlows.remove(key);
            return true;
        }

        return false;
    }

}
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
    private HashMap<Integer, Runnable> flows;
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
        flows = new HashMap<>();
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
    public void getPermission(String[] permissions, Runnable flow, boolean isAfterExplanation) {

        if (PermissionUtil.hasSelfPermission(getActivity(), permissions)) {
            Log.d(TAG, "Permission request already granted");
            callback.onPermissionGranted(permissions, flow);
        } else {
            String[] permissionsToRequest = PermissionUtil.deniedPermissions(getActivity(), permissions);
            if (PermissionUtil.showExplanation(getActivity(), permissions) && !isAfterExplanation) {
                Log.d(TAG, "Permission request should show explanation");
                callback.onPermissionExplain(permissionsToRequest, flow);
            } else {
                flows.put(Arrays.hashCode(permissionsToRequest), flow);
                requestPermissions(permissionsToRequest, REQUEST_PERMISSIONS);
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
                Log.d(TAG, "Permission request granted.");
                Runnable flow = flows.get(Arrays.hashCode(permissions));
                flows.remove(Arrays.hashCode(permissions));
                callback.onPermissionGranted(permissions, flow);
            } else {
                Log.d(TAG, "Permission request was NOT granted.");
                flows.remove(Arrays.hashCode(permissions));
                callback.onPermissionDenied(PermissionUtil.deniedPermissions(permissions, grantResults));
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
package com.ongtonnesoup.permissions.lib;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.HashMap;

public class UmaPermissions extends Fragment {

    private static final int REQUEST_PERMISSIONS = 10;
    public static final String TAG = "Perm";

    private PermissionCallback mCallback;
    private HashMap<String, Runnable> mFlows;

    public static UmaPermissions newInstance() {
        return new UmaPermissions();
    }

    public UmaPermissions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mFlows = new HashMap<>();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PermissionCallback) {
            mCallback = (PermissionCallback) activity;
        } else {
            throw new IllegalArgumentException("Activity must implement callbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void checkPermissions(String permission, Runnable flow) {
        mFlows.put(permission, flow);

        if (PermissionUtil.hasSelfPermission(getActivity(), new String[]{permission})) {
            mCallback.onPermissionGranted(permission, flow);
        } else {

            if (getActivity().shouldShowRequestPermissionRationale(permission)) {
                mCallback.onPermissionExplain(permission, flow);
            } else {
                requestPermissions(new String[]{permission}, REQUEST_PERMISSIONS);
            }
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (requestCode == REQUEST_PERMISSIONS) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                Runnable flow = mFlows.get(permissions[0]);
                mFlows.remove(flow);
                mCallback.onPermissionGranted(permissions[0], flow);
            } else {
                Log.i("Permissions", "Permission was NOT granted.");
                mCallback.onPermissionDenied(permissions[0]);
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static class PermissionEvent {
        public String permission;
        public Runnable flow;

        public PermissionEvent(String permission, Runnable flow) {
            this.permission = permission;
            this.flow = flow;
        }
    }

    public interface PermissionHandler {
        void handlePermissionRequest(String permission, Runnable flow);
    }

    public interface PermissionCallback {
        void onPermissionGranted(String permission, Runnable flow);

        void onPermissionDenied(String permission);

        void onPermissionExplain(String permission, Runnable flow);
    }
}
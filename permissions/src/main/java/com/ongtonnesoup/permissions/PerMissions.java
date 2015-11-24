/*
* Copyright 2015 Joshua Ong
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

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
    private PerMissionsHandler handler;
    private PerMissionsResultHandler callback;

    public PerMissions() {
        // Required empty public constructor
    }

    /**
     * Set a custom PermissionHandler to call PerMissions
     * <p/>
     * If not called, PerMissions will use a default implementation
     *
     * @param handler
     * @return builder
     */
    public PerMissions handler(PerMissionsHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * Set a bus for the default PermissionHandler to subscribe to PermissionEvents
     *
     * @param bus Bus PermissionEvents are posted to
     * @return builder
     */
    public PerMissions bus(Bus bus) {
        this.bus = bus;
        return this;
    }

    /**
     * Set a PermissionResultHandler to handle permission request results
     * <p/>
     * If not called, PerMissions will handle UI feedback
     *
     * @param callback
     * @return builder
     */
    public PerMissions callback(PerMissionsResultHandler callback) {
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
        if (handler instanceof PerMissionsHandlerImpl && bus != null) {
            bus.register(handler);
        }
    }

    @Override
    public void onPause() {
        if (handler instanceof PerMissionsHandlerImpl && bus != null) {
            bus.unregister(handler);
        }
        super.onPause();
    }

    /**
     * Request permission or show explanations, if necessary
     *
     * @param permissions Permissions to request
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
     * Callback received when user selects dialog option
     *
     * @param requestCode  Permissions request code
     * @param permissions  Permissions requested
     * @param grantResults Permissions granted
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
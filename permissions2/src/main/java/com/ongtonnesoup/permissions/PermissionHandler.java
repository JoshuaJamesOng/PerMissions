package com.ongtonnesoup.permissions;

public interface PermissionHandler {

    /**
     * Request permissions or show explanations, if necessary
     *
     * @param permissions Permissions to request
     * @param flow        Code to run if the permissions are granted
     */
    void handlePermissionRequest(String[] permissions, Runnable flow);

    /**
     * @param isAfterExplanation If true, will not bother showing explanation
     * @see PermissionHandler#handlePermissionRequest(String[], Runnable)
     */
    void handlePermissionRequest(String[] permissions, Runnable flow, boolean isAfterExplanation);

}

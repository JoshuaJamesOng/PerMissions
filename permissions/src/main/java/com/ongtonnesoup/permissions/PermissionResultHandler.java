package com.ongtonnesoup.permissions;


public interface PermissionResultHandler {

    /**
     * Continue with requested action
     *
     * @param permissions Permissions granted
     * @param flow        Code to run if the permissions are granted
     */
    void onPermissionGranted(String[] permissions, final Runnable flow);

    /**
     * Show a dialog explaining why the action was not carried out
     *
     * @param permissions Permissions that were not granted
     */
    void onPermissionDenied(String[] permissions);

    /**
     * Show a dialog explaining why the permission are required
     *
     * @param permissions Permission that need granting
     * @param flow        Code to run if the permissions are granted
     */
    void onPermissionExplain(String[] permissions, final Runnable flow);

    /**
     * Gets string resource for explanation dialog
     *
     * @param permission Permission that requires explanation
     * @return Message for explanation dialog
     */
    String getExplanationForPermission(String permission);

    /**
     * Gets string resource for denied dialog
     *
     * @param permission Permission that was denied
     * @return Message for denied dialog
     */
    String getDeniedForPermission(String permission);

}

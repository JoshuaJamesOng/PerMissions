package com.ongtonnesoup.permissions;

public class PerMissionsEvent {

    public final String[] permissions;
    public final Runnable flow;

    /**
     * PermissionsEvent subscribed by the default PermissionHandler implementation
     *
     * @param permissions Permissions to request
     * @param flow        Code to run if the permissions are granted
     */
    public PerMissionsEvent(String[] permissions, Runnable flow) {
        this.permissions = permissions;
        this.flow = flow;
    }

}
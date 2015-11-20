package com.ongtonnesoup.permissions;

public class PermissionEvent {

    public final String[] permissions;
    public final Runnable flow;

    public PermissionEvent(String[] permissions, Runnable flow) {
        this.permissions = permissions;
        this.flow = flow;
    }

}
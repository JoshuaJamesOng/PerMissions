package com.ongtonnesoup.permissions;

public class PermissionEvent {

    public String[] permissions;
    public Runnable flow;

    public PermissionEvent(String[] permissions, Runnable flow) {
        this.permissions = permissions;
        this.flow = flow;
    }

}
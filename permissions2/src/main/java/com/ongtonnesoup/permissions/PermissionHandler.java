package com.ongtonnesoup.permissions;

public interface PermissionHandler {

    void handlePermissionRequest(String[] permissions, Runnable flow);

    void denyRequest(String[] permissions);

}

package com.ongtonnesoup.permissions;

public interface PermissionHandler {

    void handlePermissionRequest(String[] permissions, Runnable flow);

    void handlePermissionRequest(String[] permissions, Runnable flow, boolean isAfterExplanation);

}

package com.ongtonnesoup.permissions;


public interface PermissionResultHandler {

    void onPermissionGranted(String[] permissions, final Runnable flow);

    void onPermissionDenied(String[] permissions);

    void onPermissionExplain(String[] permissions, final Runnable flow);

    String getExplanationForPermission(String permission);

    String getDeniedForPermission(String permission);

}

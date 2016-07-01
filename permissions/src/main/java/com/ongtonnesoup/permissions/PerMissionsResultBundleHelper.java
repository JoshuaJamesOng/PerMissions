package com.ongtonnesoup.permissions;

import android.os.Bundle;

public final class PerMissionsResultBundleHelper {

    public static final String KEY_PERMISSIONS_ARRAY = "KEY_PERMISSIONS";
    public static final String KEY_GRANT_RESULTS_ARRAY = "KEY_GRANT_RESULTS";

    private PerMissionsResultBundleHelper() {

    }

    public static Bundle makeBundle(String[] permissions, int[] grantResults) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(KEY_PERMISSIONS_ARRAY, permissions);
        bundle.putIntArray(KEY_GRANT_RESULTS_ARRAY, grantResults);
        return bundle;
    }

    public static String[] getPermissions(Bundle bundle) {
        return bundle.getStringArray(KEY_PERMISSIONS_ARRAY);
    }

    public static int[] getGrantResults(Bundle bundle) {
        return bundle.getIntArray(KEY_GRANT_RESULTS_ARRAY);
    }

}

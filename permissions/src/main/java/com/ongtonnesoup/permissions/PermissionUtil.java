/*
* Copyright 2015 The Android Open Source Project
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
*
* Modifications:
*  - Use ActivityCompat.checkSelfPermission
*  - Added deniedPermissions() to return a list of non-granted permissions
*  - Added deniedPermissions() to return denied permission from grant result
*  - Added showExplanation()
*/

package com.ongtonnesoup.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 */
public abstract class PermissionUtil {

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return grantResults.length > 0;
    }

    /**
     * Returns array of non-granted permissions
     */
    public static String[] deniedPermissions(Activity activity, String[] permissions) {
        // Verify that each required permission has been granted, otherwise return false.
        List<String> denied = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasSelfPermission(activity, permission)) {
                denied.add(permission);
            }
        }

        return denied.toArray(new String[denied.size()]);
    }

    /**
     * Returns array of denied permissions from grant results
     */
    public static String[] deniedPermissions(String[] permissions, int[] grantResults) {
        // Verify that each required permission has been granted, otherwise return false.
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                denied.add(permissions[i]);
            }
        }

        return denied.toArray(new String[denied.size()]);
    }

    /**
     * Returns true if the Activity has access to all given permissions.
     * Always returns true on platforms below M.
     *
     * @see Activity#checkSelfPermission(String)
     */
    public static boolean hasSelfPermission(Activity activity, String[] permissions) {
        // Below Android M all permissions are granted at install time and are already available.
        if (!isMNC()) {
            return true;
        }

        // Verify that all required permissions have been granted
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the Activity has access to a given permission.
     * Always returns true on platforms below M.
     *
     * @see Activity#checkSelfPermission(String)
     */
    public static boolean hasSelfPermission(Activity activity, String permission) {
        // Below Android M all permissions are granted at install time and are already available.
        return !isMNC() || ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Returns true if the user has denied any of the requested permissions
     */
    public static boolean showExplanation(Activity activity, String[] permissions) {
        boolean showExplanation = false;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showExplanation = true;
                break;
            }
        }
        return showExplanation;
    }

    public static boolean isMNC() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
/*
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
*/

package com.ongtonnesoup.permissions;


import com.ongtonnesoup.permissions.flow.PerMissionsContinueFlow;
import com.ongtonnesoup.permissions.flow.PerMissionsDeniedFlow;
import com.ongtonnesoup.permissions.flow.PerMissionsFlows;

public interface PerMissionsResultHandler {

    /**
     * Continue with requested action
     *
     * @param permissions Permissions granted
     * @param flow        Code to run if the permissions are granted
     */
    void onPermissionGranted(String[] permissions, final PerMissionsContinueFlow flow);

    /**
     * Show a dialog explaining why the action was not carried out
     *
     * @param permissions Permissions that were not granted
     * @param deniedFlow  Code to run after the dialog has dismissed
     */
    void onPermissionDenied(String[] permissions, final PerMissionsDeniedFlow deniedFlow);

    /**
     * Show a dialog explaining why the permission are required
     *
     * @param permissions Permission that need granting
     * @param flows       Code to run if the permissions are granted or denied
     */
    void onPermissionExplain(String[] permissions, final PerMissionsFlows flows);

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

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

public interface PerMissionsHandler {

    /**
     * Request permissions or show explanations, if necessary
     *
     * @param permissions Permissions to request
     * @param flow        Code to run if the permissions are granted
     */
    void handlePermissionRequest(String[] permissions, Runnable flow);

    /**
     * @param isAfterExplanation If true, will not bother showing explanation
     * @see PerMissionsHandler#handlePermissionRequest(String[], Runnable)
     */
    void handlePermissionRequest(String[] permissions, Runnable flow, boolean isAfterExplanation);

}

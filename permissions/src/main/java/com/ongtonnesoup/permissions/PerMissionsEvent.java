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

public class PerMissionsEvent {

    public final String[] permissions;
    public final PerMissionsFlows flows;

    /**
     * PermissionsEvent subscribed by the default PermissionHandler implementation
     *
     * @param permissions  Permissions to request
     * @param continueFlow Code to run if the permissions are granted
     * @param deniedFlow   Code to run if the permissions are denied
     */
    public PerMissionsEvent(String[] permissions, PerMissionsContinueFlow continueFlow, PerMissionsDeniedFlow deniedFlow) {
        this.permissions = permissions;
        this.flows = new PerMissionsFlows(continueFlow, deniedFlow);
    }

}
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

import com.squareup.otto.Subscribe;

public class PerMissionsHandlerImpl implements PerMissionsHandler {

    private final PerMissions perMissions;

    public PerMissionsHandlerImpl(PerMissions perMissions) {
        this.perMissions = perMissions;
    }

    @Subscribe
    public void onPerMissionsEvent(PerMissionsEvent event) {
        if (event != null) {
            handlePermissionRequest(event.permissions, event.flow);
        }
    }

    @Override
    public void handlePermissionRequest(String[] permissions, Runnable flow) {
        perMissions.getPermission(permissions, flow, false);
    }

    @Override
    public void handlePermissionRequest(String[] permissions, Runnable flow, boolean isAfterExplanation) {
        perMissions.getPermission(permissions, flow, isAfterExplanation);
    }

}

package com.ongtonnesoup.permissions;

import com.squareup.otto.Subscribe;

import java.util.Arrays;

public class PermissionHandlerImpl implements PermissionHandler {

    private PerMissions perMissions;

    public PermissionHandlerImpl(PerMissions perMissions) {
        this.perMissions = perMissions;
    }

    @Subscribe
    public void onPerMissionsEvent(PermissionEvent event) {
        if (event != null) {
            handlePermissionRequest(event.permissions, event.flow);
        }
    }

    @Override
    public void handlePermissionRequest(String[] permissions, Runnable flow) {
        perMissions.getPermission(permissions, flow);
    }

    @Override
    public void denyRequest(String[] permissions) {
        perMissions.removeFlow(Arrays.hashCode(permissions));
    }

}

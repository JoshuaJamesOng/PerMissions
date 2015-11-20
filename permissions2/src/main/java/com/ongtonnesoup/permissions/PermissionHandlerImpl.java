package com.ongtonnesoup.permissions;

import com.squareup.otto.Subscribe;

public class PermissionHandlerImpl implements PermissionHandler {

    private final PerMissions perMissions;

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
        perMissions.getPermission(permissions, flow, false);
    }

    @Override
    public void handlePermissionRequest(String[] permissions, Runnable flow, boolean isAfterExplanation) {
        perMissions.getPermission(permissions, flow, isAfterExplanation);
    }

}

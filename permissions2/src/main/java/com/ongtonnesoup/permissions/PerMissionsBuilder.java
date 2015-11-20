package com.ongtonnesoup.permissions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

public class PerMissionsBuilder {

    private Context context;
    private Bus bus;
    private PermissionHandler handler;
    private PermissionResultHandler callback;
    private PerMissions permissionFrag;

    /**
     * @param activity
     * @return
     */
    public PerMissionsBuilder init(Context context, AppCompatActivity activity) {
        return init(context, activity, null);
    }

    /**
     * @param activity
     * @param bus
     * @return
     */
    public PerMissionsBuilder init(Context context, AppCompatActivity activity, Bus bus) {
        PerMissions frag = (PerMissions) activity.getSupportFragmentManager().findFragmentByTag(PerMissions.TAG);
        if (frag == null) {
            frag = new PerMissions();
            activity.getSupportFragmentManager().beginTransaction().add(frag, PerMissions.TAG).commit();
        }

        this.context = context;
        this.permissionFrag = frag;
        this.bus = bus;
        return this;
    }

    /**
     * @param handler
     * @return
     */
    public PerMissionsBuilder handler(PermissionHandler handler) {
        return handler(handler, null);
    }

    /**
     * @param handler
     * @param bus
     * @return
     */
    private PerMissionsBuilder handler(PermissionHandler handler, Bus bus) {
        this.handler = handler;
        this.bus = bus;
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public PerMissionsBuilder callback(PermissionResultHandler callback) {
        this.callback = callback;
        return this;
    }

    /**
     * @return
     */
    public PerMissions build() {
        if (handler == null && bus == null) {
            throw new IllegalArgumentException("Default handler requires an event bus");
        }

        if (handler == null) {
            handler = new PermissionHandlerImpl(permissionFrag);
        }

        if (callback == null) {
            callback = new PermissionResultHandlerImpl(context);
        }

        return permissionFrag.bus(bus).handler(handler).callback(callback);
    }

}

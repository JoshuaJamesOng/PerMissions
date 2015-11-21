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
     * Set a custom PermissionHandler to call PerMissions
     * <p/>
     * If not called, PerMissions will use a default implementation
     *
     * @param handler
     * @return builder
     */
    public PerMissionsBuilder handler(PermissionHandler handler) {
        return handler(handler, null);
    }

    /**
     * Set default PermissionHandler and bus events will be posted on
     *
     * @param handler
     * @param bus
     * @return builder
     */
    private PerMissionsBuilder handler(PermissionHandler handler, Bus bus) {
        this.handler = handler;
        this.bus = bus;
        return this;
    }

    /**
     * Set a PermissionResultHandler to handle permission request results
     * <p/>
     * If not called, PerMissions will handle UI feedback
     *
     * @param callback
     * @return builder
     */
    public PerMissionsBuilder callback(PermissionResultHandler callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Build PerMissions fragment
     *
     * @return PerMissions
     */
    public PerMissions build() {
        if (handler == null && bus == null) {
            throw new IllegalArgumentException("Default handler requires an event bus");
        }

        if (handler == null) {
            handler = new PermissionHandlerImpl(permissionFrag);
        }

        if (callback == null) {
            callback = new PermissionResultHandlerImpl(context, context.getResources(), handler);
        }

        return permissionFrag.bus(bus).handler(handler).callback(callback);
    }

}

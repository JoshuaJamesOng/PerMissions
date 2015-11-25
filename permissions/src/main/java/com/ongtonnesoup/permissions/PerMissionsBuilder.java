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

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.ongtonnesoup.permissions.dialog.PerMissionsDialogBuilder;
import com.ongtonnesoup.permissions.dialog.PerMissionsDialogBuilderImpl;
import com.squareup.otto.Bus;

public class PerMissionsBuilder {

    private Context context;
    private FragmentManager fragmentManager;
    private Bus bus;
    private PerMissionsHandler handler;
    private PerMissionsResultHandler callback;
    private PerMissions permissionFrag;
    private PerMissionsDialogBuilder dialogBuilder;

    /**
     * Use this for custom PerMissions handler and callback implementations
     *
     * @param context
     * @param fragmentManager
     * @return
     */
    public PerMissionsBuilder init(Context context, FragmentManager fragmentManager) {
        return init(context, fragmentManager, null);
    }

    /**
     * Use this for default PerMissions handler and callback implementations
     *
     * @param context
     * @param fragmentManager
     * @param bus
     * @return
     */
    public PerMissionsBuilder init(Context context, FragmentManager fragmentManager, Bus bus) {
        PerMissions frag = (PerMissions) fragmentManager.findFragmentByTag(PerMissions.TAG);
        if (frag == null) {
            frag = new PerMissions();
            fragmentManager.beginTransaction().add(frag, PerMissions.TAG).commit();
        }
        this.context = context;
        this.fragmentManager = fragmentManager;
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
    public PerMissionsBuilder handler(PerMissionsHandler handler) {
        return handler(handler, null);
    }

    /**
     * Set default PermissionHandler and bus events will be posted on
     *
     * @param handler
     * @param bus
     * @return builder
     */
    private PerMissionsBuilder handler(PerMissionsHandler handler, Bus bus) {
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
    public PerMissionsBuilder callback(PerMissionsResultHandler callback) {
        this.callback = callback;
        return this;
    }

    public PerMissionsBuilder dialogBuilder(PerMissionsDialogBuilder dialogBuilder) {
        this.dialogBuilder = dialogBuilder;
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
            handler = new PerMissionsHandlerImpl(permissionFrag);
        }

        if (dialogBuilder == null) {
            dialogBuilder = new PerMissionsDialogBuilderImpl();
        }

        if (callback == null) {
            callback = new PerMissionsResultHandlerImpl(context.getResources(), fragmentManager, handler, dialogBuilder);
        }

        return permissionFrag.bus(bus).handler(handler).callback(callback);
    }

}

package com.ongtonnesoup.permissionsdemo.demo;

import android.app.Application;

public class DemoApplication extends Application {

    private DemoComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerDemoComponent.builder().demoModule(new DemoModule()).build();
    }

    public DemoComponent getComponent() {
        return mComponent;
    }
}

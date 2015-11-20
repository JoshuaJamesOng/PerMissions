package com.ongtonnesoup.permissionsdemo.demo;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DemoModule {

    @Singleton
    @Provides
    public Bus provideBus() {
        return new Bus();
    }

}

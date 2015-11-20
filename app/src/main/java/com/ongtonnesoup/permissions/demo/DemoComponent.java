package com.ongtonnesoup.permissions.demo;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DemoModule.class})
public interface DemoComponent {
    void inject(DemoActivity activity);
    void inject(DemoFragment fragment);
}

package com.ongtonnesoup.permissionsdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ongtonnesoup.permissions.PerMissions;
import com.ongtonnesoup.permissions.PerMissionsBuilder;
import com.ongtonnesoup.permissionsdemo.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class DemoActivity extends AppCompatActivity {

    @Inject
    Bus mBus;
    private PerMissions mPerMissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        ((DemoApplication) getApplication()).getComponent().inject(this);

        mPerMissions = new PerMissionsBuilder().init(this, getSupportFragmentManager(), mBus).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

}

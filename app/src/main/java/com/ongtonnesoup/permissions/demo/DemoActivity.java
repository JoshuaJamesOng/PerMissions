package com.ongtonnesoup.permissions.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ongtonnesoup.permissions.R;
import com.ongtonnesoup.permissions.lib.UmaPermissions;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class DemoActivity extends AppCompatActivity implements UmaPermissions.PermissionHandler, UmaPermissions.PermissionCallback {

    @Inject
    Bus mBus;
    private UmaPermissions mPermissionFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        ((DemoApplication) getApplication()).getComponent().inject(this);

        mPermissionFrag = (UmaPermissions) getSupportFragmentManager().findFragmentByTag(UmaPermissions.TAG);
        if (mPermissionFrag == null) {
            mPermissionFrag = UmaPermissions.newInstance();
            getSupportFragmentManager().beginTransaction().add(mPermissionFrag, UmaPermissions.TAG).commit();
        }
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

    @Override
    public void onPermissionGranted(String permission, Runnable flow) {
        flow.run();
    }

    @Override
    public void onPermissionDenied(String permission) {
        Log.d("JJO", "Permission denied");
        // Show dialog gracefully explaining why nothing is happening
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Permission Denied");
        dialog.setMessage("This action requires the " + permission + " permission");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onPermissionExplain(String permission, final Runnable flow) {
        Log.d("JJO", "Permission explanation required");
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Permission Required");
        dialog.setMessage("The " + permission + " permission is required for ...");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                flow.run();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    @Subscribe
    public void onPermissionEvent(UmaPermissions.PermissionEvent event) {
        Log.d("JJO", "Event received");
        if (event != null) {
            handlePermissionRequest(event.permission, event.flow);
        }
    }

    @Override
    public void handlePermissionRequest(String permission, Runnable flow) {
        mPermissionFrag.checkPermissions(permission, flow);
    }
}

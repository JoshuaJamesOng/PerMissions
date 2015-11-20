package com.ongtonnesoup.permissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class PermissionResultHandlerImpl implements PermissionResultHandler {

    private Context context;

    public PermissionResultHandlerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onPermissionGranted(String[] permissions, Runnable flow) {
        flow.run();
    }

    @Override
    public void onPermissionDenied(String[] permissions) {
        // Show dialog gracefully explaining why nothing is happening
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Permission Denied");
        dialog.setMessage("The following action can't complete without permissions");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onPermissionExplain(String[] permissions, final Runnable flow) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Permission Required");
        dialog.setMessage("The following action can't complete without permissions. Would you like to grant permission?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                flow.run();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

}

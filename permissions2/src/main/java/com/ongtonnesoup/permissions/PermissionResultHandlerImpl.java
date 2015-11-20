package com.ongtonnesoup.permissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class PermissionResultHandlerImpl implements PermissionResultHandler {

    private final Context context;
    private final PermissionHandler handler;

    public PermissionResultHandlerImpl(Context context, PermissionHandler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onPermissionGranted(String[] permissions, Runnable flow) {
        flow.run();
    }

    @Override
    public void onPermissionDenied(final String[] permissions) {
        // Show dialog gracefully explaining why nothing is happening
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Permission Denied");
        dialog.setMessage("The following action can't complete without permissions");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                handler.denyRequest(permissions);
            }
        });
        dialog.show();
    }

    @Override
    public void onPermissionExplain(final String[] permissions, final Runnable flow) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Permission Required");
        dialog.setMessage("The following action can't complete without permissions. Would you like to grant permission?");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                handler.handlePermissionRequest(permissions, flow);
            }
        });
        dialog.show();
    }

}

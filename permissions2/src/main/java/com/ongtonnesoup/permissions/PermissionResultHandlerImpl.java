package com.ongtonnesoup.permissions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

public class PermissionResultHandlerImpl implements PermissionResultHandler {

    private final Context context;
    private Resources resources;
    private final PermissionHandler handler;

    public PermissionResultHandlerImpl(Context context, Resources resources, PermissionHandler handler) {
        this.context = context;
        this.resources = resources;
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
        dialog.setMessage(getExplanationString(permissions));
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

    private String getExplanationString(String[] permissions) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String permission : permissions) {
            stringBuilder.append(getExplantionForPermission(permission));
        }
        return stringBuilder.toString();
    }

    @Override
    public String getExplantionForPermission(String permission) {
        String explanation;

        switch (permission) {
            case Manifest.permission.READ_CALENDAR:
            case Manifest.permission.WRITE_CALENDAR:
                explanation = resources.getString(R.string.permissions_explanation_calendar);
                break;
            case Manifest.permission.CAMERA:
                explanation = resources.getString(R.string.permissions_explanation_camera);
                break;
            case Manifest.permission.READ_CONTACTS:
            case Manifest.permission.WRITE_CONTACTS:
            case Manifest.permission.GET_ACCOUNTS:
                explanation = resources.getString(R.string.permissions_explanation_contacts);
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                explanation = resources.getString(R.string.permissions_explanation_location);
                break;
            case Manifest.permission.RECORD_AUDIO:
                explanation = resources.getString(R.string.permissions_explanation_microphone);
                break;
            case Manifest.permission.READ_PHONE_STATE:
            case Manifest.permission.CALL_PHONE:
            case Manifest.permission.READ_CALL_LOG:
            case Manifest.permission.WRITE_CALL_LOG:
            case Manifest.permission.ADD_VOICEMAIL:
            case Manifest.permission.USE_SIP:
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                explanation = resources.getString(R.string.permissions_explanation_phone);
                break;
            case Manifest.permission.BODY_SENSORS:
                explanation = resources.getString(R.string.permissions_explanation_sensors);
                break;
            case Manifest.permission.SEND_SMS:
            case Manifest.permission.RECEIVE_SMS:
            case Manifest.permission.READ_SMS:
            case Manifest.permission.RECEIVE_WAP_PUSH:
            case Manifest.permission.RECEIVE_MMS:
                explanation = resources.getString(R.string.permissions_explanation_sms);
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                explanation = resources.getString(R.string.permissions_explanation_storage);
                break;
            default:
                explanation = resources.getString(R.string.permissions_explanation_unknown);
                break;
        }

        return explanation;
    }

}

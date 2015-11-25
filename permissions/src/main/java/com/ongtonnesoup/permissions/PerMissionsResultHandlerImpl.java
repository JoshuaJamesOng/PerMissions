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

import android.Manifest;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.ongtonnesoup.permissions.dialog.PerMissionsDialogBuilder;
import com.ongtonnesoup.permissions.flow.PerMissionsContinueFlow;
import com.ongtonnesoup.permissions.flow.PerMissionsDeniedFlow;
import com.ongtonnesoup.permissions.flow.PerMissionsFlows;

public class PerMissionsResultHandlerImpl implements PerMissionsResultHandler {

    private static final String TAG_DENIED = "PERMISSIONS_TAG_DIALOG_DENIED";
    private static final String TAG_EXPLAIN = "PERMISSIONS_TAG_DIALOG_EXPLAIN";

    private final Resources resources;
    private final PerMissionsHandler handler;
    private final FragmentManager fragmentManager;
    private final PerMissionsDialogBuilder dialogBuilder;

    public PerMissionsResultHandlerImpl(Resources resources, FragmentManager fragmentManager, PerMissionsHandler handler, PerMissionsDialogBuilder dialogBuilder) {
        this.resources = resources;
        this.fragmentManager = fragmentManager;
        this.handler = handler;
        this.dialogBuilder = dialogBuilder;
    }

    @Override
    public void onPermissionGranted(String[] permissions, PerMissionsContinueFlow flow) {
        if (flow != null) {
            flow.run();
        }
    }

    @Override
    public void onPermissionDenied(final String[] permissions, final PerMissionsDeniedFlow flow) {
        String title = resources.getString(R.string.permission_title_denied);
        String message = getDeniedString(permissions);
        DialogFragment dialog = dialogBuilder.build(title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (flow != null) {
                    flow.run();
                }
            }
        });
        dialog.show(fragmentManager, TAG_DENIED);
    }

    @Override
    public void onPermissionExplain(final String[] permissions, final PerMissionsFlows flows) {
        String title = resources.getString(R.string.permissions_title_explanation);
        String message = getExplanationString(permissions);
        DialogFragment dialog = dialogBuilder.build(title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handler.handlePermissionRequest(permissions, flows, true);
            }
        });
        dialog.show(fragmentManager, TAG_EXPLAIN);
    }

    private String getDeniedString(String[] permissions) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            stringBuilder.append(getDeniedForPermission(permission));

            if (i < permissions.length - 1) {
                stringBuilder.append(". ");
            }
        }
        return stringBuilder.toString();
    }

    private String getExplanationString(String[] permissions) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            stringBuilder.append(getExplanationForPermission(permission));

            if (i < permissions.length - 1) {
                stringBuilder.append(". ");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String getExplanationForPermission(String permission) {
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

    @Override
    public String getDeniedForPermission(String permission) {
        String denied;

        switch (permission) {
            case Manifest.permission.READ_CALENDAR:
            case Manifest.permission.WRITE_CALENDAR:
                denied = resources.getString(R.string.permissions_denied_calendar);
                break;
            case Manifest.permission.CAMERA:
                denied = resources.getString(R.string.permissions_denied_camera);
                break;
            case Manifest.permission.READ_CONTACTS:
            case Manifest.permission.WRITE_CONTACTS:
            case Manifest.permission.GET_ACCOUNTS:
                denied = resources.getString(R.string.permissions_denied_contacts);
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                denied = resources.getString(R.string.permissions_denied_location);
                break;
            case Manifest.permission.RECORD_AUDIO:
                denied = resources.getString(R.string.permissions_denied_microphone);
                break;
            case Manifest.permission.READ_PHONE_STATE:
            case Manifest.permission.CALL_PHONE:
            case Manifest.permission.READ_CALL_LOG:
            case Manifest.permission.WRITE_CALL_LOG:
            case Manifest.permission.ADD_VOICEMAIL:
            case Manifest.permission.USE_SIP:
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                denied = resources.getString(R.string.permissions_denied_phone);
                break;
            case Manifest.permission.BODY_SENSORS:
                denied = resources.getString(R.string.permissions_denied_sensors);
                break;
            case Manifest.permission.SEND_SMS:
            case Manifest.permission.RECEIVE_SMS:
            case Manifest.permission.READ_SMS:
            case Manifest.permission.RECEIVE_WAP_PUSH:
            case Manifest.permission.RECEIVE_MMS:
                denied = resources.getString(R.string.permissions_denied_sms);
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                denied = resources.getString(R.string.permissions_denied_storage);
                break;
            default:
                denied = resources.getString(R.string.permissions_denied_unknown);
                break;
        }

        return denied;
    }

}

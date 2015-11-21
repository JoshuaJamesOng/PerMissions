package com.ongtonnesoup.permissionsdemo.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresPermission;
import android.util.Log;

public class DemoFragmentPresenter {

    private static final String TAG = "DemoFragmentPresenter";
    private static final int REQUEST_PICK_CONTACT = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    public void onViewContacts() {
        Log.d(TAG, "View contacts");
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, REQUEST_PICK_CONTACT);
        }
    }

    @RequiresPermission(Manifest.permission.WRITE_CONTACTS)
    public void onAddContact() {
        Log.d(TAG, "Adding contact");
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void onTakePhoto() {
        Log.d(TAG, "Taking photo");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

}

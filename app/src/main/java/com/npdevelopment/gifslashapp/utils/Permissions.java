package com.npdevelopment.gifslashapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permissions {

    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111;

    private Activity currentActivity;

    private boolean externalStorageAccess = false;

    public Permissions(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public boolean checkPermissionExternalStorage() {
        if (ContextCompat.checkSelfPermission(currentActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted, show explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(currentActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                externalStorageAccess = false;
            }

            ActivityCompat.requestPermissions(currentActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            // Permission has already been granted
            externalStorageAccess = true;
        }

        return externalStorageAccess;
    }
}

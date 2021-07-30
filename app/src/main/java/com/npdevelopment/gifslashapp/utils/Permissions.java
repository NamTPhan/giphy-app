package com.npdevelopment.gifslashapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111;

    private Activity mCurrentActivity;

    private boolean mExternalStorageAccess = false;

    public Permissions(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    public boolean checkPermissionExternalStorage() {
        if (ContextCompat.checkSelfPermission(mCurrentActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted, show explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(mCurrentActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                mExternalStorageAccess = false;
            }

            ActivityCompat.requestPermissions(mCurrentActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            // Permission has already been granted
            mExternalStorageAccess = true;
        }

        return mExternalStorageAccess;
    }
}

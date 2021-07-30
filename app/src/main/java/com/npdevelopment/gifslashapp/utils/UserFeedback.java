package com.npdevelopment.gifslashapp.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class UserFeedback {

    private Context mContext;

    public UserFeedback(Context context) {
        this.mContext = context;
    }

    public void showSnackBarLong(View view, String message, int colorId) {
        Snackbar mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View sbView = mSnackBar.getView();
        sbView.setBackgroundColor(mContext.getResources().getColor(colorId));
        mSnackBar.show();
    }

    public void showSnackBarShort(View view, String message, int colorId) {
        Snackbar mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View sbView = mSnackBar.getView();
        sbView.setBackgroundColor(mContext.getResources().getColor(colorId));
        mSnackBar.show();
    }

    public void showToastLong(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

}

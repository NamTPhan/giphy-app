package com.npdevelopment.gifslashapp.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class UserFeedback {

    private Context context;

    public UserFeedback(Context context) {
        this.context = context;
    }

    public void showSnackBarLong(View view, String message, int colorId) {
        Snackbar mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View sbView = mSnackBar.getView();
        sbView.setBackgroundColor(context.getResources().getColor(colorId));
        mSnackBar.show();
    }

    public void showSnackBarShort(View view, String message, int colorId) {
        Snackbar mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View sbView = mSnackBar.getView();
        sbView.setBackgroundColor(context.getResources().getColor(colorId));
        mSnackBar.show();
    }


}

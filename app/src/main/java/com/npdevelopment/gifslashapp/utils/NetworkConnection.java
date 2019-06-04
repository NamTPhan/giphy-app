package com.npdevelopment.gifslashapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {

    private Context mContext;

    public NetworkConnection(Context context) {
        this.mContext = context;
    }

    public boolean availableNetworkConnection() {
        boolean wifiConnection = false;
        boolean mobileNetworkConnection = false;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : netInfo) {
            if (networkInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (networkInfo.isConnected())
                    wifiConnection = true;
            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (networkInfo.isConnected())
                    mobileNetworkConnection = true;
        }
        return wifiConnection || mobileNetworkConnection;
    }
}

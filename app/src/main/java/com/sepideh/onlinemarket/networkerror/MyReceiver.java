package com.sepideh.onlinemarket.networkerror;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String statusV = NetworkUtil.getConnectivityStatusString(context);
        Hawk.put("network_status_H",statusV);
        if(statusV.isEmpty()) {
            statusV="No Internet Connection";
        }
      //  Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}

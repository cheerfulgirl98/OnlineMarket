package com.sepideh.onlinemarket.networkerror;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.data.NotifItem;
import com.sepideh.onlinemarket.navigationview.messages.MessageActivity;
import com.sepideh.onlinemarket.room.MyRoomDatabase;
import com.sepideh.onlinemarket.utils.NotifConstant;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            String statusV = NetworkUtil.getConnectivityStatusString(context);
            Hawk.put("network_status_H", statusV);
            if (statusV.isEmpty()) {
                statusV = "No Internet Connection";
            }
            //  Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        }

    }
}

package com.sepideh.onlinemarket.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.utils.NotifConstant;

public class MyBaseActivity extends AppCompatActivity {

    BroadcastReceiver pushNotinReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base);

        pushNotinReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(NotifConstant.PUSH_NOTIFICATION))
                {
                    String notifMsg=intent.getStringExtra("pushMessage");
                    Log.d("mynotif", "onReceive: "+ notifMsg);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter(NotifConstant.PUSH_NOTIFICATION);
        registerReceiver(pushNotinReceiver,intentFilter);
    }

    @Override
    protected void onPause() {

        unregisterReceiver(pushNotinReceiver);
        super.onPause();

    }
}

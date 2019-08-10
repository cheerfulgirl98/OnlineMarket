package com.sepideh.onlinemarket.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.NotifItem;
import com.sepideh.onlinemarket.navigationview.messages.MessageActivity;
import com.sepideh.onlinemarket.room.MyRoomDatabase;
import com.sepideh.onlinemarket.utils.NotifConstant;

public class TheBroadcastInActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReciever;
    MyRoomDatabase roomDatabase;
    NotifItem notifItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_broadcast_in);

        broadcastReciever=new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(NotifConstant.PUSH_NOTIFICATION))
                {

                    notifItem=new NotifItem(intent.getStringExtra("pushTitle"),intent.getStringExtra("pushMessage"));
                    roomDatabase= MyRoomDatabase.getAppDatabase(getApplicationContext());

                    roomDatabase.getItemDao().insert(notifItem);

                    Intent intent1=new Intent(getApplicationContext(),MessageActivity.class);
                    startActivity(intent1);



                }
            }
        };

    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter(NotifConstant.PUSH_NOTIFICATION);
        registerReceiver(broadcastReciever,intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReciever);
        super.onPause();

    }

  
}

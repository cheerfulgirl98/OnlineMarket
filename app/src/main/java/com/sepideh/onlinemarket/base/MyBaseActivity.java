package com.sepideh.onlinemarket.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.NotifItem;
import com.sepideh.onlinemarket.navigationview.messages.MessageActivity;
import com.sepideh.onlinemarket.room.MyRoomDatabase;
import com.sepideh.onlinemarket.utils.NotifConstant;

import java.io.Serializable;

public class MyBaseActivity extends AppCompatActivity {

    BroadcastReceiver pushNotinReceiver;
    MyRoomDatabase roomDatabase;
    NotifItem notifItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base);

        pushNotinReceiver=new BroadcastReceiver() {

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
    protected void onStart() {
        super.onStart();
        pushNotinReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(NotifConstant.PUSH_NOTIFICATION))
                {
                    notifItem=(NotifItem)intent.getSerializableExtra("notiItem");
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
        registerReceiver(pushNotinReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(pushNotinReceiver);
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}

package com.sepideh.onlinemarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        send=findViewById(R.id.sendnotif);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel("default", "message Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                    // Configure the notification channel.
                    notificationChannel.setDescription("Sample Channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                }



                NotificationCompat.Builder builder = new NotificationCompat.Builder(Main2Activity.this, "default")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("salaam")
                        .setContentText("my notifffffffff")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);



                notificationManager.notify((int) System.currentTimeMillis(), builder.build());


            }
        });
    }
}

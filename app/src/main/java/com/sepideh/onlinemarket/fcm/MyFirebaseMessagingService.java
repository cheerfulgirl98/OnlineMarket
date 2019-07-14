package com.sepideh.onlinemarket.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.main.activity.MainActivity;
import com.sepideh.onlinemarket.navigationview.messages.MessageActivity;
import com.sepideh.onlinemarket.userInfo.UserInfoActivity;
import com.sepideh.onlinemarket.utils.NotifConstant;
import com.sepideh.onlinemarket.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("mynotif", "onMessageReceived: ");


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.d("mynotif", "onMessageReceived: notif ");
            handleNotification(remoteMessage);
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e("mynotif", "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e("mynotif", "Exception: " + e.getMessage());
            }
        }

    }

    void handleNotification(RemoteMessage remoteMessage) {

        Intent pushnotifBr = new Intent(NotifConstant.PUSH_NOTIFICATION);
        pushnotifBr.putExtra("pushMessage", remoteMessage.getNotification().getBody());
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,pushnotifBr,0);


        Intent intent=new Intent(this, MessageActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);


        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("default", "message Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            // Configure the notification channel.
            notificationChannel.setDescription("message Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

    }


    private void handleDataMessage(JSONObject json) {


        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e("mynotif", "title: " + title);
            Log.e("mynotif", "message: " + message);
            Log.e("mynotif", "isBackground: " + isBackground);
            Log.e("mynotif", "payload: " + payload.toString());
            Log.e("mynotif", "imageUrl: " + imageUrl);
            Log.e("mynotif", "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(NotifConstant.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    //showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e("mynotif", "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e("mynotif", "Exception: " + e.getMessage());
        }
    }


    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotifMessageWithoutImage(title, message, timeStamp, intent);
    }
}

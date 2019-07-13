package com.sepideh.onlinemarket.fcm;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.main.activity.MainActivity;
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
            Log.d("mynotif", "onMessageReceived: " + remoteMessage.getNotification().getBody());

            handleNotification(remoteMessage.getNotification().getBody());
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

    void handleNotification(String message) {
        Log.d("mynotif", "handleNotification: ");
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

            // app is in foreground, broadcast the push message
            Log.d("mynotif", "foreground: ");


            Intent pushnotifBr = new Intent(NotifConstant.PUSH_NOTIFICATION);
            pushnotifBr.putExtra("pushMessage", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushnotifBr);

        } else {
            Log.d("mynotif", "background: ");
            // If the app is in background, firebase itself handles the notification
        }

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

package com.mamits.zini24user.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mamits.zini24user.R;
import com.mamits.zini24user.ui.activity.LoginActivity;

public class NotificationService extends FirebaseMessagingService {
    private static final String TAG = "NotificationService";
    String title, message, type;
    private String CHANNEL_ID;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            type = remoteMessage.getData().get("type");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            try {
                title = remoteMessage.getNotification().getTitle(); //get title
                message = remoteMessage.getNotification().getBody(); //get message
                if (title == null) {
                    title = "";
                }
                if (message == null) {
                    message = "";
                }
                if (type == null) {
                    type = "";
                }
                Log.d("title ", title);
                Log.d("message ", message);
                Log.d("type ", type);
                notificationManager(title, message, type);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void notificationManager(String title, String notificationMsg, String type) {
        int NOTIFICATION_ID = 234;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("log : ", "if");
            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.WHITE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);


            Intent resultIntent = new Intent(this, LoginActivity.class);
            resultIntent.putExtra("type", type);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.zini24_logo)
                    .setColor(ContextCompat.getColor(this, R.color.color_orange))
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMsg))
                    .setContentText(notificationMsg)
                    .setAutoCancel(true);
            builder.setContentIntent(resultPendingIntent);
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        } else {
            Intent resultIntent = new Intent(this, LoginActivity.class);
            resultIntent.putExtra("type", type);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
            Notification n = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setStyle(new Notification.BigTextStyle().bigText(notificationMsg))
                    .setContentText(notificationMsg)
                    .setAutoCancel(true)
                    .setContentIntent(resultPendingIntent)
                    .setSmallIcon(R.drawable.zini24_logo)
                    .build();
            NotificationManager notificationManager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager1.notify(NOTIFICATION_ID, n);

        }
    }
}

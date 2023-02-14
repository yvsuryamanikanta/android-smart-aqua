package com.odos.smartaqua.utils;

import android.app.LauncherActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.odos.smartaqua.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String ADMIN_CHANNEL_ID = "AQUA_NOTIFICATIONS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        getMessage(remoteMessage);
    }


    private void getMessage(RemoteMessage remoteMessage) {
        Intent notificationIntent = new Intent(this, LauncherActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int notificationId = new Random().nextInt(60000);
        Bitmap bitmap = getBitmapfromUrl(remoteMessage.getData().get("image"));
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                        .setLargeIcon(bitmap)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setColor(getResources().getColor(R.color.colorAccent))
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .setSummaryText(remoteMessage.getData().get("message"))
                                .bigPicture(bitmap))
                        .setContentText(remoteMessage.getData().get("message"))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification))
                        .setContentIntent(pendingIntent)
                        .setChannelId(ADMIN_CHANNEL_ID)
                        .setDefaults(NotificationCompat.BADGE_ICON_LARGE)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(ADMIN_CHANNEL_ID);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ADMIN_CHANNEL_ID,
                    ADMIN_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
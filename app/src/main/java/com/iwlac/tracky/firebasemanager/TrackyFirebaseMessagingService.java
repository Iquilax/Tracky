package com.iwlac.tracky.firebasemanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iwlac.tracky.activity.MainActivity;
import com.iwlac.tracky.R;
import com.iwlac.tracky.activity.PriceCompareActivity;

import static com.iwlac.tracky.utility.IntentConstant.EXTRA_PRODUCT_CODE;


/**
 * Created by Thien on 3/21/2017.
 */

public class TrackyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            String productId = remoteMessage.getData().get("productId");
            String message = remoteMessage.getData().get("message");
            sendNotification(productId, message);
//            sendNotification("-KgxCzdKcUMyZBXpJxdK", message);
            try {
                Badges.setBadge(this, Integer.valueOf(remoteMessage.getData().get("notyCount")) );
            } catch (BadgesNotSupportedException e) {
                Log.d(TAG, e.getMessage());
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            sendNotification("", remoteMessage.getNotification().getBody());

            sendNotification("-KgxCzdKcUMyZBXpJxdK", remoteMessage.getNotification().getBody());
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    private void sendNotification(String productId, String messageBody) {

        final Intent intent = new Intent(this, PriceCompareActivity.class);
        intent.putExtra(EXTRA_PRODUCT_CODE, productId);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.noti_icon)
                .setContentTitle("Tracky")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }





}

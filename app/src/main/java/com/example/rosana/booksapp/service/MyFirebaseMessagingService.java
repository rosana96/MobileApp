package com.example.rosana.booksapp.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.rosana.booksapp.ItemDetailActivity;
import com.example.rosana.booksapp.ItemDetailFragment;
import com.example.rosana.booksapp.R;
import com.example.rosana.booksapp.repository.NovelsRepo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixplicity.easyprefs.library.Prefs;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + (notification != null ? notification.getBody() : null));
        Log.d(TAG, "Notification Tag: " + (notification != null ? notification.getTag() : null));

        String authorName = NovelsRepo.findOne(notification.getTag()).getAuthor();
        if (notification != null && !authorName.equals(Prefs.getString("personName", "nobody")))
            if (notification.getBody() != null) {
                Log.e("FIREBASE", "Message Notification Body: " + notification.getBody());
                showNotification(remoteMessage);
            }

    }

    private void showNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, notification != null ? notification.getTag() : null);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_whatshot))
                .setSmallIcon(R.drawable.ic_stat_whatshot)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public static void sendMessageToAll(String newObjectId) {
        // create FCM and add as a payload the objectId
        String message = Prefs.getString("personName","Someone") + " has created a new novel.";

    }
}
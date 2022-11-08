package com.doctris.care.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.doctris.care.App;
import com.doctris.care.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        switch (remoteMessage.getFrom()) {
            case "/topics/blog":
                showNotification(App.BLOG_CHANNEL_ID, notification.getTitle(), notification.getBody());
                break;
            case "/topics/doctor":
                showNotification(App.DOCTOR_CHANNEL_ID, notification.getTitle(), notification.getBody());
                break;
            case "/topics/service":
                showNotification(App.SERVICE_CHANNEL_ID, notification.getTitle(), notification.getBody());
                break;
            default:
                break;
        }
    }

    // create topic "blog"
    public static void subscribeToBlog() {
        if(!FirebaseMessaging.getInstance().isAutoInitEnabled()) {
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("blog");
    }

    // create topic "doctor"
    public static void subscribeToDoctor() {
        FirebaseMessaging.getInstance().subscribeToTopic("doctor");
    }

    // create topic "service"
    public static void subscribeToService() {
        FirebaseMessaging.getInstance().subscribeToTopic("service");
    }

    private void showNotification(String channel, String title, String body) {
        Notification notification = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}

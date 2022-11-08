package com.doctris.care;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.appcompat.app.AppCompatDelegate;
import com.doctris.care.service.MessagingService;
import com.google.gson.Gson;

public class App extends Application {
    private static App mSelf;
    private Gson mGSon;
    public static final String DOCTOR_CHANNEL_ID = "doctor";
    private static final String DOCTOR_CHANNEL_NAME = "Doctor";
    public static final String BLOG_CHANNEL_ID = "blog";
    private static final String BLOG_CHANNEL_NAME = "Blog";
    public static final String SERVICE_CHANNEL_ID = "service";
    private static final String SERVICE_CHANNEL_NAME = "Service";


    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGSon = new Gson();
        createNotificationBlogChannel();
        createNotificationDoctorChannel();
        createNotificationServiceChannel();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        MessagingService.subscribeToBlog();
        MessagingService.subscribeToDoctor();
        MessagingService.subscribeToService();
    }

    private void createNotificationDoctorChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                DOCTOR_CHANNEL_ID,
                DOCTOR_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);
    }

    private void createNotificationServiceChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                SERVICE_CHANNEL_ID,
                SERVICE_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);
    }

    private void createNotificationBlogChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                BLOG_CHANNEL_ID,
                BLOG_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);
    }

    public Gson getGSon() {
        return mGSon;
    }
}
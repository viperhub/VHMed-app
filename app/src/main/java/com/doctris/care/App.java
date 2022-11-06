package com.doctris.care;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.appcompat.app.AppCompatDelegate;
import com.google.gson.Gson;

public class App extends Application {
    private static App mSelf;
    private Gson mGSon;
    public static final String BOOKING_CHANNEL_ID = "booking_reminder";
    private static final String BOOKING_CHANNEL_NAME = "Booking Reminder";
    private static final String BLOG_CHANNEL_ID = "blog";
    private static final String BLOG_CHANNEL_NAME = "Blog";


    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGSon = new Gson();
        createNotificationBlogChannel();
        createNotificationBookingChannel();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void createNotificationBookingChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                BOOKING_CHANNEL_ID,
                BOOKING_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);
    }

    private void createNotificationBlogChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                BLOG_CHANNEL_ID,
                BLOG_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);
    }

    public Gson getGSon() {
        return mGSon;
    }
}
package com.example.moneyaah.classes;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.moneyaah.MainActivity;
import com.example.moneyaah.NotiActivity;
import com.example.moneyaah.R;

public class MyNotification extends Notification {
    private String title;
    private String content;
    private Context mContext;
    public static final int ALERT_TOTAL_NOTI = 0;
    public static final int ALERT_OVER_EXPENSE = 1;

    public MyNotification(Context context) {
        this.title = "Title";
        this.content = "Content";
        this.mContext = context;
    }

    public MyNotification(Context context, String title, String content) {
        this.title = title;
        this.content = content;
        this.mContext = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Notification getInstance() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(mContext, NotiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        return builder.build();
    }

    public void show(int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(notificationId, getInstance());

        save();
    }

    public void save() {
        NotiData.getInstance().add(this);
    }
}

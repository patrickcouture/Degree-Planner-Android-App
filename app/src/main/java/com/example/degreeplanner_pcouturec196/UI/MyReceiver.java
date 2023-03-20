package com.example.degreeplanner_pcouturec196.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.degreeplanner_pcouturec196.R;

public class MyReceiver extends BroadcastReceiver {

    String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("CourseStartNotification")) {
            Toast.makeText(context, intent.getStringExtra("courseStart"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("courseStart"))
                    .setContentTitle("Course Alert").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        } else if (action.equals("CourseEndNotification")) {
            Toast.makeText(context, intent.getStringExtra("courseEnd"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("courseEnd"))
                    .setContentTitle("Course Alert").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        } else if (action.equals("TermStartNotification")) {
            Toast.makeText(context, intent.getStringExtra("termStart"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("termStart"))
                    .setContentTitle("Term Alert").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        } else if (action.equals("TermEndNotification")) {
            Toast.makeText(context, intent.getStringExtra("termEnd"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("termEnd"))
                    .setContentTitle("Term Alert").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        } else if (action.equals("AssessmentNotification")) {
            Toast.makeText(context, intent.getStringExtra("assessment"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("assessment"))
                    .setContentTitle("Assessment Alert").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        }
    }
    // TODO: This method is called when the BroadcastReceiver is receiving
    // an Intent broadcast.


    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
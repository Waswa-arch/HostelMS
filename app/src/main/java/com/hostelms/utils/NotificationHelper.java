package com.hostelms.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.hostelms.R;
import com.hostelms.activities.StudentDashboardActivity;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Announcement;

/**
 * Helper class for sending local push notifications in HostelMS.
 */
public class NotificationHelper {

    private static final String CHANNEL_ID   = "room_allocation_channel";
    private static final String CHANNEL_NAME = "Room Allocation";
    private static final String CHANNEL_DESC = "Notifications for room allocation events";
    private static final int    NOTIFICATION_ID = 1001;

    /**
     * Send a room-allocation notification and save it as an Announcement.
     */
    public static void sendRoomAllocationNotification(
            Context context,
            String studentName,
            String hostelName,
            String roomNumber,
            int bedNumber) {

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, StudentDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        String title = "Room Allocated 🏠";
        String body  = "Hello " + studentName + "! You have been assigned to "
                + hostelName + " Room " + roomNumber
                + ", Bed " + bedNumber + ". Welcome!";

        // SAVE AS ANNOUNCEMENT so it appears in the notices tab
        Announcement a = new Announcement();
        a.title = title;
        a.body = body;
        a.author = "System / Allocation";
        a.isUrgent = true;
        a.datePosted = System.currentTimeMillis();
        
        AppDatabase.getInstance(context).announcementDao().insert(a);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 300, 200, 300});

        manager.notify(NOTIFICATION_ID, builder.build());
    }
}

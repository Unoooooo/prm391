package com.example.fu.myapplication.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.fu.myapplication.R;

public class AlarmReceiver extends BroadcastReceiver {
    public static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("ngon roi ", "haha");

        //

        NOTIFICATION_ID = intent.getExtras().getInt("noti");
        Intent stopSoundIntent = new Intent(context,
                StopService.class)
                .setAction("CLOSE");

        PendingIntent stopSoundPendingIntent = PendingIntent.getService(context, 0,
                stopSoundIntent, PendingIntent.FLAG_ONE_SHOT);
        //create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "HAHA")
                .setSmallIcon(R.drawable.clock)
                .setContentTitle("ALARM")
                .setContentText("ARE YOU READY ?")
                .setAutoCancel(true).addAction(new NotificationCompat.Action(R.drawable.clock,
                        "CLOSE", stopSoundPendingIntent));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


        //run music
        context.startService(new Intent(context, AlarmService.class));

    }
}

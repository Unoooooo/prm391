package com.example.fu.myapplication.view;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.service.AlarmService;
import com.example.fu.myapplication.service.StopService;

public class AlarmReceiver extends BroadcastReceiver {
   public static int NOTIFICATION_ID =0 ;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ngon roi ","haha");
        Intent intent1= new Intent(context, AlarmService.class);
        context.startService(intent1);
        NOTIFICATION_ID = intent.getIntExtra("Alarm",0);
        Intent stopSoundIntent = new Intent(context,
                StopService.class)
                .setAction("StopSound");

        PendingIntent stopSoundPendingIntent = PendingIntent.getService(context, 0,
                stopSoundIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "HAHA")
                .setSmallIcon(R.drawable.clock)
                .setContentTitle("ALARM")
                .setContentText("ARE YOU READY ?")
                .setAutoCancel(true).addAction(new NotificationCompat.Action(R.drawable.clock,
                        "StopSound", stopSoundPendingIntent));
                ;
        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
        notificationManagerCompat.notify( intent.getIntExtra("Alarm",0), builder.build());

    }
}

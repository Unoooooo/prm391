package com.example.fu.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;


public class StopService extends IntentService {


    public StopService() {
        super(StopService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();

        if ("CLOSE".equals(action)) {
            // TODO: handle action StopSound.
            AlarmService.mediaPlayer.stop();
            // If you want to cancel the notification:
            NotificationManagerCompat.from(this).cancel(1);
        }
    }
}

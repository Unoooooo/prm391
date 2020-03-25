package com.example.fu.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.fu.myapplication.data.DataBaseHelper;
import com.example.fu.myapplication.model.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmService extends IntentService {
    public static final String ALARMS_EXTRA = "alarms_extra";

    public AlarmService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        List<Alarm> alarmList = DataBaseHelper.getInstance(this).getAlarmArray();
        final Intent i = new Intent();

        i.putParcelableArrayListExtra(ALARMS_EXTRA, new ArrayList<>(alarmList));
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }
}

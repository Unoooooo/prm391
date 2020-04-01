package com.example.fu.myapplication.view;

import android.app.PendingIntent;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import com.example.fu.myapplication.model.Alarm;

public class SendAlarmViewModel extends ViewModel {
    public MutableLiveData<Long> timeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Alarm> alarmMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> listchanged = new MutableLiveData<>();
    public MutableLiveData<Alarm> alarmMutableLiveData_DELETE = new MutableLiveData<>();
    public MutableLiveData<Alarm> alarmMutableLiveData_ADD = new MutableLiveData<>();



    public MutableLiveData<Long> getTimeMutableLiveData() {
        return timeMutableLiveData;
    }

    public MutableLiveData<Alarm> getAlarmMutableLiveData() {
        return alarmMutableLiveData;
    }

    public MutableLiveData<Boolean> getListchanged() {
        return listchanged;
    }

    public MutableLiveData<Alarm> getAlarmMutableLiveData_DELETE() {
        return alarmMutableLiveData_DELETE;
    }

    public MutableLiveData<Alarm> getAlarmMutableLiveData_ADD() {
        return alarmMutableLiveData_ADD;
    }
}

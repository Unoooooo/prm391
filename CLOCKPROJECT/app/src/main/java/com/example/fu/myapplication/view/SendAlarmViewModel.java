package com.example.fu.myapplication.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import com.example.fu.myapplication.model.Alarm;

public class SendAlarmViewModel extends ViewModel {
    public MutableLiveData<Long> timeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Alarm> alarmMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> listchanged = new MutableLiveData<>();

    public MutableLiveData<Long> getTimeMutableLiveData() {
        return timeMutableLiveData;
    }

    public MutableLiveData<Alarm> getAlarmMutableLiveData() {
        return alarmMutableLiveData;
    }

    public MutableLiveData<Boolean> getListchanged() {
        return listchanged;
    }
}

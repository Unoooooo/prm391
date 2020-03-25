package com.example.fu.myapplication.presenter;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.view.EditTimeDialog;

public class ViewAlarmHolder extends RecyclerView.ViewHolder {
    private TextView time, amPm, days;
    private Switch switchOnOffAlarm;

    ViewAlarmHolder(View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.lblTime);
        amPm = itemView.findViewById(R.id.lblAmPm);
        days = itemView.findViewById(R.id.lblDaysInWeek);
        switchOnOffAlarm = itemView.findViewById(R.id.switchOnOffAlarm);


    }



    public Switch getSwitchOnOffAlarm() {
        return switchOnOffAlarm;
    }

    public void setSwitchOnOffAlarm(Switch switchOnOffAlarm) {
        this.switchOnOffAlarm = switchOnOffAlarm;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public TextView getAmPm() {
        return amPm;
    }

    public void setAmPm(TextView amPm) {
        this.amPm = amPm;
    }

    public TextView getDays() {
        return days;
    }

    public void setDays(TextView days) {
        this.days = days;
    }
}

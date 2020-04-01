package com.example.fu.myapplication.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.fu.myapplication.util.AlarmUtils;

import java.util.Calendar;

public class EditTimeDialog extends DialogFragment {

    private SendAlarmViewModel sendAlarmViewModel;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //get alarm from bundle
        long time = 0;
        time = getArguments().getLong("timeEdit");
        int hour = 0;
        int minute = 0;
        if (time == 0) {
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } else {
            hour = Integer.parseInt(AlarmUtils.convertTimeToHour24(time));
            minute = Integer.parseInt(AlarmUtils.convertTimeToMinute(time));
        }
        return new TimePickerDialog(getActivity(), timeSetListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    //move to AddOrEditAlarm

                    final Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    long timeEdited = calendar.getTimeInMillis();

                    sendAlarmViewModel = ViewModelProviders.of(getActivity()).get(SendAlarmViewModel.class);
                    sendAlarmViewModel.getTimeMutableLiveData().postValue(timeEdited);


                }
            };


}

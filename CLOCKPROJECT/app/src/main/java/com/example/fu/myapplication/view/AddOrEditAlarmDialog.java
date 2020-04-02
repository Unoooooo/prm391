package com.example.fu.myapplication.view;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.data.DataBaseHelper;
import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.util.AlarmUtils;

import java.util.Calendar;

public class AddOrEditAlarmDialog extends DialogFragment {
    private Alarm alarmBundle;
    private Long timeForEdit;
    private TextView lblTime;
    private Button btnDel;


    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;


    public AddOrEditAlarmDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_or_edit_alarm, container, false);
        lblTime = view.findViewById(R.id.lblTime);
        btnDel = view.findViewById(R.id.btnDel);
        alarmBundle = getArguments().getParcelable("alarmEdit");
        getArguments().clear();
        int hour = 0;
        int minute = 0;
        if (alarmBundle != null) {
            btnDel.setVisibility(View.VISIBLE);
            lblTime.setText(AlarmUtils.convertTimeToHour12AmPm(alarmBundle.getTime()));
            timeForEdit = alarmBundle.getTime();
            SparseBooleanArray daysBolean = alarmBundle.getDaysInWeek();
            //ToggleButton view
            ((ToggleButton) view.findViewById(R.id.mon)).setChecked(daysBolean.get(1));
            ((ToggleButton) view.findViewById(R.id.tues)).setChecked(daysBolean.get(2));
            ((ToggleButton) view.findViewById(R.id.wed)).setChecked(daysBolean.get(3));
            ((ToggleButton) view.findViewById(R.id.thurs)).setChecked(daysBolean.get(4));
            ((ToggleButton) view.findViewById(R.id.fri)).setChecked(daysBolean.get(5));
            ((ToggleButton) view.findViewById(R.id.sat)).setChecked(daysBolean.get(6));
            ((ToggleButton) view.findViewById(R.id.sun)).setChecked(daysBolean.get(7));
        } else {
            btnDel.setVisibility(View.INVISIBLE);
            Calendar c = Calendar.getInstance();
            Long timeSysDate = c.getTimeInMillis();
            lblTime.setText(AlarmUtils.convertTimeToHour12AmPm(timeSysDate));
            timeForEdit = timeSysDate;
            String daySys = null;
            daySys = AlarmUtils.convertTimeToDay(timeSysDate);
            switch (daySys) {
                //ToggleButton view
                case "Mon":
                    ((ToggleButton) view.findViewById(R.id.mon)).setChecked(true);
                    break;
                case "Tue":
                    ((ToggleButton) view.findViewById(R.id.tues)).setChecked(true);
                    break;
                case "Wed":
                    ((ToggleButton) view.findViewById(R.id.wed)).setChecked(true);
                    break;
                case "Thu":
                    ((ToggleButton) view.findViewById(R.id.thurs)).setChecked(true);
                    break;
                case "Fri":
                    ((ToggleButton) view.findViewById(R.id.fri)).setChecked(true);
                    break;
                case "Sat":
                    ((ToggleButton) view.findViewById(R.id.sat)).setChecked(true);
                    break;
                case "Sun":
                    ((ToggleButton) view.findViewById(R.id.sun)).setChecked(true);
                    break;
            }

        }
        mon = ((ToggleButton) view.findViewById(R.id.mon)).isChecked();
        tue = ((ToggleButton) view.findViewById(R.id.tues)).isChecked();
        wed = ((ToggleButton) view.findViewById(R.id.wed)).isChecked();
        thu = ((ToggleButton) view.findViewById(R.id.thurs)).isChecked();
        fri = ((ToggleButton) view.findViewById(R.id.fri)).isChecked();
        sat = ((ToggleButton) view.findViewById(R.id.sat)).isChecked();
        sun = ((ToggleButton) view.findViewById(R.id.sun)).isChecked();


        lblTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.moveInfoAlarm(mAlarm);

                //open dialog fragement
                DialogFragment newFragment = new EditTimeDialog();
                //transfer Alarm to fragement
                Bundle bundle = new Bundle();
                if (alarmBundle != null) {
                    bundle.putLong("timeEdit", alarmBundle.getTime());
                } else {
                    bundle.putLong("timeEdit", timeForEdit);
                }

                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), null);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Click OK
        Button btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert Alarm to DB
                Alarm newAlarm;
                if (alarmBundle != null) {
                    if (!AlarmUtils.checkFalseDayAll(mon, tue, wed, thu, fri, sat, sun)) {
                        setDayNowToAlarm();

                    }
                        newAlarm = new Alarm(alarmBundle.getId(), timeForEdit, Alarm.addDaysInWeekToAlarm(mon, tue, wed, thu, fri, sat, sun), true);


                    DataBaseHelper.getInstance(getContext()).updateAlarm(newAlarm);

                } else {
                    if (!AlarmUtils.checkFalseDayAll(mon, tue, wed, thu, fri, sat, sun)) {
                        setDayNowToAlarm();
                    }

                        newAlarm = new Alarm(0, timeForEdit, Alarm.addDaysInWeekToAlarm(mon, tue, wed, thu, fri, sat, sun), true);

                    DataBaseHelper.getInstance(getContext()).insertAlarm(newAlarm);
                    // set change id for new alarm
                    int idNew = DataBaseHelper.getInstance(getContext()).selectMaxId();
                    newAlarm.setId(idNew);
                }
                //out
                MainActivity.sendAlarmViewModel.getListchanged().postValue(true);
                MainActivity.sendAlarmViewModel.getAlarmMutableLiveData_ADD().postValue(newAlarm);
                getDialog().dismiss();
            }
        });

        //Click Cancel
        Button btnCan = view.findViewById(R.id.btnCancel);
        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //out
                getDialog().dismiss();
            }
        });
        //CliCk Delete
        Button btnDel = view.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper.getInstance(getContext()).deleteAlarm(alarmBundle);
                //out

                MainActivity.sendAlarmViewModel.getListchanged().postValue(true);
                MainActivity.sendAlarmViewModel.getAlarmMutableLiveData_DELETE().postValue(alarmBundle);
                getDialog().dismiss();
            }
        });


        MainActivity.sendAlarmViewModel.getTimeMutableLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long timeLive) {
                if (timeLive != null) {
                    lblTime.setText(AlarmUtils.convertTimeToHour12AmPm(timeLive));
                    timeForEdit = timeLive;
                    if (alarmBundle != null) {
                        alarmBundle.setTime(timeLive);
                    }
                    //reset live data
                    MainActivity.sendAlarmViewModel.getTimeMutableLiveData().postValue(null);

                }
            }
        });
        // set listener for toggleButton
        CompoundButton.OnCheckedChangeListener listener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        switch (compoundButton.getId()) {
                            case R.id.mon:
                                mon = compoundButton.isChecked();
                                break;
                            case R.id.tues:
                                tue = compoundButton.isChecked();
                                break;
                            case R.id.wed:
                                wed = compoundButton.isChecked();
                                break;
                            case R.id.thurs:
                                thu = compoundButton.isChecked();
                                break;
                            case R.id.fri:
                                fri = compoundButton.isChecked();
                                break;
                            case R.id.sat:
                                sat = compoundButton.isChecked();
                                break;
                            case R.id.sun:
                                sun = compoundButton.isChecked();
                                break;
                        }
                    }
                };
        ((ToggleButton) view.findViewById(R.id.mon)).setOnCheckedChangeListener(listener);
        ((ToggleButton) view.findViewById(R.id.tues)).setOnCheckedChangeListener(listener);
        ((ToggleButton) view.findViewById(R.id.wed)).setOnCheckedChangeListener(listener);
        ((ToggleButton) view.findViewById(R.id.thurs)).setOnCheckedChangeListener(listener);
        ((ToggleButton) view.findViewById(R.id.fri)).setOnCheckedChangeListener(listener);
        ((ToggleButton) view.findViewById(R.id.sat)).setOnCheckedChangeListener(listener);
        ((ToggleButton) view.findViewById(R.id.sun)).setOnCheckedChangeListener(listener);
    }

    public void setDayNowToAlarm(){


        String dayName = AlarmUtils.convertTimeToDay(Calendar.getInstance().getTimeInMillis());
        switch (dayName) {

            case "Mon":
                mon=true;
                break;

            case "Tue":
                tue=true;
                break;

            case "Wed":
                wed=true;
                break;

            case "Thu":
                thu=true;
                break;

            case "Fri":
                fri=true;
                break;

            case "Sat":
                sat=true;
                break;


            case "Sun":
                sun=true;
                break;

        }

    }


}

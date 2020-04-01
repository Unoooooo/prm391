package com.example.fu.myapplication.presenter;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.data.DataBaseHelper;
import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.view.*;
import com.example.fu.myapplication.util.AlarmUtils;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<ViewAlarmHolder> {
    public static final int COLOR_NUMBER_DAY = 2;
    public static List<Alarm> mAlarmList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private Context context;


    public AlarmAdapter(List<Alarm> mAlarmList, FragmentManager fragmentManager, Context context) {
        this.mAlarmList = mAlarmList;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAlarmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_row, viewGroup, false);
        return new ViewAlarmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAlarmHolder viewHolder, final int position) {
        Alarm alarm = mAlarmList.get(position);
        viewHolder.getTime().setText(AlarmUtils.convertTimeToHour12(alarm.getTime()));
        viewHolder.getAmPm().setText(AlarmUtils.convertTimeToAmPm(alarm.getTime()));
        viewHolder.getDays().setText(AlarmUtils.convertSparseBooleanArrayToString(alarm.getDaysInWeek()));

        viewHolder.getSwitchOnOffAlarm().setChecked(alarm.isEnabled());
        viewHolder.getTime().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open dialog fragement
                AddOrEditAlarmDialog newFragment = new AddOrEditAlarmDialog();
                //transfer Alarm to fragement
                Bundle bundle = new Bundle();
                bundle.putParcelable("alarmEdit", mAlarmList.get(position));
                newFragment.setArguments(bundle);
                newFragment.show(fragmentManager, null);

            }
        });
        viewHolder.getSwitchOnOffAlarm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alarm editAlarm = mAlarmList.get(position);
                boolean status = viewHolder.getSwitchOnOffAlarm().isChecked();
                editAlarm.setEnabled(status);
                DataBaseHelper.getInstance(context).updateAlarm(editAlarm);

                if(status){
                    MainActivity.sendAlarmViewModel.getAlarmMutableLiveData_ADD().postValue(editAlarm);
                }else{
                    MainActivity.sendAlarmViewModel.getAlarmMutableLiveData_DELETE().postValue(editAlarm);
                }


            }
        });


    }


    @Override
    public int getItemCount() {

        return (mAlarmList == null) ? 0 : mAlarmList.size();
    }


}



package com.example.fu.myapplication.presenter;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.util.AlarmUtils;
import com.example.fu.myapplication.view.EditTimeDialog;

import java.util.List;


public class AlarmAdapter extends RecyclerView.Adapter<ViewAlarmHolder> {
    public static final int COLOR_NUMBER_DAY = 2;
    private List<Alarm> mAlarmList;
    private FragmentManager fragmentManager;


    public AlarmAdapter(List<Alarm> mAlarmList, FragmentManager fragmentManager) {
        this.mAlarmList = mAlarmList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewAlarmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_row, viewGroup, false);
        return new ViewAlarmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAlarmHolder viewHolder, int position) {
        Alarm alarm = mAlarmList.get(position);
        viewHolder.getTime().setText(AlarmUtils.convertTimeToHour12(alarm.getTime()));
        viewHolder.getAmPm().setText(AlarmUtils.convertTimeToAmPm(alarm.getTime()));
        viewHolder.getDays().setText(AlarmUtils.convertSparseBooleanArrayToString(alarm.getDaysInWeek()));
        viewHolder.getSwitchOnOffAlarm().setEnabled(alarm.isEnabled());
        viewHolder.getTime().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new EditTimeDialog();
                newFragment.show(fragmentManager, null);
            }
        });

        //action click on button switch on/off
        viewHolder.getSwitchOnOffAlarm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch onOffAlarm = view.findViewById(R.id.switchOnOffAlarm);

               

            }
        });

    }




    @Override
    public int getItemCount() {

        return (mAlarmList == null) ? 0 : mAlarmList.size();
    }

    public List<Alarm> getmAlarmList() {
        return mAlarmList;
    }

    public void setmAlarmList(List<Alarm> mAlarmList) {
        this.mAlarmList = mAlarmList;
    }


}



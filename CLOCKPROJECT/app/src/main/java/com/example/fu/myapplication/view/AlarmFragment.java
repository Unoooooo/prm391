package com.example.fu.myapplication.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.data.DataBaseHelper;
import com.example.fu.myapplication.presenter.AlarmAdapter;


public class AlarmFragment extends Fragment {
    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.recycleListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        createTabListAlarm();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MainActivity.sendAlarmViewModel.getListchanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    createTabListAlarm();
                    //reset live data
                    MainActivity.sendAlarmViewModel.getListchanged().postValue(null);
                }
            }
        });
    }

    private void createTabListAlarm() {

        //data list Alarm
        AlarmAdapter.mAlarmList = DataBaseHelper.getInstance(this.getContext()).getAlarmArray();
        if (AlarmAdapter.mAlarmList.size() == 0) {
            //AlarmAdapter.mAlarmList = Alarm.creatAlarmListDEMO();
        }
        alarmAdapter = new AlarmAdapter(AlarmAdapter.mAlarmList, getFragmentManager(), this.getContext());
        //move to new Alarm item
        alarmAdapter.notifyItemChanged(AlarmAdapter.mAlarmList.size() - 1);
        recyclerView.scrollToPosition(AlarmAdapter.mAlarmList.size() - 1);
        recyclerView.setAdapter(alarmAdapter);

    }


}

package com.example.fu.myapplication.view;

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
import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.presenter.AlarmAdapter;

import java.util.ArrayList;
import java.util.List;


public class AlarmFragment extends Fragment {
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.recycleListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        List<Alarm> list = new ArrayList<>();
        list = DataBaseHelper.getInstance(this.getContext()).getAlarmArray();
        if (list.size() == 0) {
            list = Alarm.creatAlarmListDEMO();
        }


        AlarmAdapter alarmAdapter = new AlarmAdapter(list);
        recyclerView.setAdapter(alarmAdapter);
        return view;
    }




}

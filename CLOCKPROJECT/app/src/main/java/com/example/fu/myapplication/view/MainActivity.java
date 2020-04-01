package com.example.fu.myapplication.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.data.DataBaseHelper;
import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.presenter.TabPagerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public static int MODE_ADD = 1;
    public static int MODE_EDIT = 2;

    public  static SendAlarmViewModel sendAlarmViewModel;
    public static AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOrEditAlarmDialog newFragment = new AddOrEditAlarmDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("alarmEdit", null);
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), null);

            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.clock));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.stopwatch));

        final ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter adapter = new TabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener
                (new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    fab.show();
                }
                if (tab.getPosition() == 1) {
                    fab.hide();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        List<Alarm> listTimeAlarm = DataBaseHelper.getInstance(getApplicationContext()).getAlarmArrayOn();
        alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        for (int i = 0; i < listTimeAlarm.size(); i++) {
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), listTimeAlarm.get(i).getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, listTimeAlarm.get(i).getTime(), pendingIntent);

        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        sendAlarmViewModel = ViewModelProviders.of(this).get(SendAlarmViewModel.class);
        sendAlarmViewModel.getAlarmMutableLiveData_DELETE().observe(this, new Observer<Alarm>() {
            @Override
            public void onChanged(@Nullable Alarm alarm) {
                 if(alarm!=null){
                     Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                     PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                     alarmManager.cancel(pendingIntent);
                     //default
                     sendAlarmViewModel.getAlarmMutableLiveData_DELETE().postValue(null);
                 }



            }
        });
        sendAlarmViewModel.getAlarmMutableLiveData_ADD().observe(this, new Observer<Alarm>() {
            @Override
            public void onChanged(@Nullable Alarm alarm) {
                if(alarm!=null){
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(), pendingIntent);

                    //default
                    sendAlarmViewModel.getAlarmMutableLiveData_ADD().postValue(null);
                }
            }
        });

    }
}

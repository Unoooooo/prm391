package com.example.fu.myapplication.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.fu.myapplication.R;
import com.example.fu.myapplication.data.DataBaseHelper;
import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.presenter.AlarmAdapter;
import com.example.fu.myapplication.presenter.TabPagerAdapter;
import com.example.fu.myapplication.util.AlarmUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public static int MODE_ADD = 1;
    public static int MODE_EDIT = 2;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

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
        List<Long> listTimeAlarm = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        String systemTime = AlarmUtils.convertTimeToHour12AmPm(cal.getTimeInMillis());
        listTimeAlarm = DataBaseHelper.getInstance(getApplicationContext()).selectTimeEqualsSystem();
        alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        for (int i = 0; i < listTimeAlarm.size(); i++) {
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, listTimeAlarm.get(i), pendingIntent);
        }


    }


}

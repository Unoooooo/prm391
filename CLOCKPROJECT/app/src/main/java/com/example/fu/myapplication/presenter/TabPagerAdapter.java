package com.example.fu.myapplication.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fu.myapplication.view.AlarmFragment;
import com.example.fu.myapplication.view.StopWatchFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {


    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTab) {
        super(fm);
        tabCount = numberOfTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                AlarmFragment alarmFragment = new AlarmFragment();
                return alarmFragment;
            case 1:
                StopWatchFragment stopWatchFragment = new StopWatchFragment();
                return stopWatchFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

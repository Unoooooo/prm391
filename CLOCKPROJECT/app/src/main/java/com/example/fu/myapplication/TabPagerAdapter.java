package com.example.fu.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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

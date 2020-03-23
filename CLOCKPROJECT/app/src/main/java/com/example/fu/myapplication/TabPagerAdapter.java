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
                Tab1Fragment tab1Fragment = new Tab1Fragment();
                return tab1Fragment;
            case 1:
                Tab2Fragment tab2Fragment = new Tab2Fragment();
                return tab2Fragment;
            case 2:
                Tab3Fragment tab3Fragment = new Tab3Fragment();
                return tab3Fragment;
            case 3:
                Tab4Fragment tab4Fragment = new Tab4Fragment();
                return tab4Fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

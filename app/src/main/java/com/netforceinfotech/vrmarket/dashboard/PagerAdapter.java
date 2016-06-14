package com.netforceinfotech.vrmarket.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.netforceinfotech.vrmarket.dashboard.app.AppFragment;
import com.netforceinfotech.vrmarket.dashboard.game.GameFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AppFragment appFragment = new AppFragment();
                return appFragment;
            case 1:
                GameFragment gameFragment = new GameFragment();
                return gameFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
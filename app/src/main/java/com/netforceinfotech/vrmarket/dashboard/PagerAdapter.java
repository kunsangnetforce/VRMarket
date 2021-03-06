package com.netforceinfotech.vrmarket.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.netforceinfotech.vrmarket.dashboard.app.AppFragmentStickyFilter;
import com.netforceinfotech.vrmarket.dashboard.games.GameFragmentStickyFilter;

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
                AppFragmentStickyFilter appFragment = new AppFragmentStickyFilter();
                return appFragment;
            case 1:
                GameFragmentStickyFilter gameFragment = new GameFragmentStickyFilter();
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
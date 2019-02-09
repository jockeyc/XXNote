package com.shu.xxnote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shu.xxnote.utils.Constants;
import com.shu.xxnote.utils.FragmentCache;

public class MainContentPagerAdapter extends FragmentPagerAdapter {
    public MainContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCache.getFragmentByPosition(position);
    }

    @Override
    public int getCount() {
        return Constants.TABS_COUNT;
    }
}

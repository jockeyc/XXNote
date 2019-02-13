package com.shu.xxnote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shu.xxnote.utils.Constants;

import com.shu.xxnote.utils.NoteFragmentCache;

public class NotePagerAdapter extends FragmentPagerAdapter {
    public NotePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NoteFragmentCache.getFragmentByPosition(position);
    }

    @Override
    public int getCount() {
        return Constants.NOTE_TYPE_COUNT;
    }
}

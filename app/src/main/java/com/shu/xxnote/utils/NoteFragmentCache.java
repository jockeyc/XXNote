package com.shu.xxnote.utils;

import com.shu.xxnote.fragment.BaseFragment;
import com.shu.xxnote.fragment.CollectionFragment;
import com.shu.xxnote.fragment.FolderFragment;
import com.shu.xxnote.fragment.MineFragment;
import com.shu.xxnote.fragment.NoteImageFragment;
import com.shu.xxnote.fragment.NoteRecordingFragment;
import com.shu.xxnote.fragment.NoteVideoFragment;
import com.shu.xxnote.fragment.RecentFragment;

import java.util.HashMap;
import java.util.Map;

public class NoteFragmentCache {
    public static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragmentByPosition(int position){
        BaseFragment baseFragment = sCache.get(position);
        if(baseFragment != null){
            return baseFragment;
        }

        switch (position){
            case Constants.PAGE_IMAGE:
                baseFragment = new NoteImageFragment();
                break;
            case Constants.PAGE_RECORDING:
                baseFragment = new NoteRecordingFragment();
                break;
            case Constants.PAGE_VIDEO:
                baseFragment = new NoteVideoFragment();
                break;
        }

        sCache.put(position, baseFragment);
        return baseFragment;
    }
}

package com.shu.xxnote.utils;

import com.shu.xxnote.R;
import com.shu.xxnote.fragment.BaseFragment;
import com.shu.xxnote.fragment.CollectionFragment;
import com.shu.xxnote.fragment.FolderFragment;
import com.shu.xxnote.fragment.MineFragment;
import com.shu.xxnote.fragment.RecentFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCache {
    public static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragmentByPosition(int position){
        BaseFragment baseFragment = sCache.get(position);
        if(baseFragment != null){
            return baseFragment;
        }

        switch (position){
            case Constants.PAGE_RECENT:
                baseFragment = new RecentFragment();
                break;
            case Constants.PAGE_FOLDER:
                baseFragment = new FolderFragment();
                break;
            case Constants.PAGE_COLLECTION:
                baseFragment = new CollectionFragment();
                break;
            case Constants.PAGE_MINE:
                baseFragment = new MineFragment();
                break;
        }

        sCache.put(position, baseFragment);
        return baseFragment;
    }
}

package com.shu.xxnote.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shu.xxnote.R;

public class NoteImageFragment extends BaseFragment {

    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_note_image,container,false);
        return rootView;
    }
}

package com.shu.xxnote.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.StackView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.shu.xxnote.R;
import com.shu.xxnote.adapter.TestStackAdapter;

import java.util.Arrays;

public class NoteImageFragment extends BaseFragment implements CardStackView.ItemExpendListener{
    View rootView;
    private CardStackView cardStackView;
    private ImageButton button_add;
    private TestStackAdapter mTestStackAdapter;
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getSubView(inflater,container);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        cardStackView = (CardStackView)rootView.findViewById(R.id.stackview);
        button_add = (ImageButton)rootView.findViewById(R.id.imageButton3);
        cardStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(getActivity());
        cardStackView.setAdapter(mTestStackAdapter);
        cardStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(cardStackView));


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
    }

    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_note_image,container,false);
        return rootView;
    }

    @Override
    public void onItemExpend(boolean expend) {

    }
}

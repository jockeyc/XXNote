package com.shu.xxnote.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.StackView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.shu.xxnote.Bmob.Note;
import com.shu.xxnote.Bmob.Notebook;
import com.shu.xxnote.Main2Activity;
import com.shu.xxnote.NoteActivity;
import com.shu.xxnote.R;
import com.shu.xxnote.adapter.TestStackAdapter;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NoteImageFragment extends BaseFragment implements CardStackView.ItemExpendListener{
    View rootView;
    CardStackView cardStackView;
    ImageButton button_add;
    TestStackAdapter mTestStackAdapter;
    private int[] test = new int[]{1,2,3,4,5};
    public Note[] notes;
    public Integer[] DATAS;
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1, R.color.color_2, R.color.color_3, R.color.color_4,
            R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8,
            R.color.color_9, R.color.color_10, R.color.color_11, R.color.color_12,
            R.color.color_13, R.color.color_14, R.color.color_15, R.color.color_16,
            R.color.color_17, R.color.color_18, R.color.color_19, R.color.color_20,
            R.color.color_21, R.color.color_22, R.color.color_23, R.color.color_24,
            R.color.color_25, R.color.color_26
    };
    private String notebookId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getSubView(inflater,container);
        initBmob();
        return rootView;
    }

    private void initBmob() {
        notebookId = ((NoteActivity)getActivity()).getNotebookId();
        Bmob.initialize(getActivity(),"b69650c5254cf30c803d7b958a002ef1");
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.addWhereEqualTo("notebook",notebookId);
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    notes = new Note[list.size()];
                    DATAS = new Integer[list.size()];
                    for (int i=0;i<list.size();i++) {
                        notes[i] = new Note();
                        notes[i].setObjectId(list.get(i).getObjectId());
                        notes[i].setType(list.get(i).getType());
                        notes[i].setComment(list.get(i).getComment());
                        notes[i].setTitle(list.get(i).getTitle());
                        notes[i].setBmobfile(list.get(i).getBmobfile());
                        notes[i].setDate(list.get(i).getCreatedAt());
                    }
                    int i=0,j=0;
                    while (i<list.size()){
                        DATAS[i++]=TEST_DATAS[i];
                        if(j++ ==26)j=0;
                    }
                    initView(rootView);
                }else{
                    System.out.println(e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void initView(View rootView) {
        cardStackView = (CardStackView)rootView.findViewById(R.id.stackview);
        button_add = (ImageButton)rootView.findViewById(R.id.imageButton3);
        cardStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(getActivity());
        mTestStackAdapter.setNotes(notes);
        cardStackView.setAdapter(mTestStackAdapter);
        cardStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(cardStackView));

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(DATAS));
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

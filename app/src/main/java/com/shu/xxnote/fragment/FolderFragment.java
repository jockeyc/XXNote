package com.shu.xxnote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shu.xxnote.Main2Activity;
import com.shu.xxnote.adapter.NoteBookAdapter;
import com.shu.xxnote.Notebook;
import com.shu.xxnote.R;
import com.shu.xxnote.Users;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FolderFragment extends BaseFragment {
    GridView gridView;
    public NoteBookAdapter adapter;
    ImageButton add;
    private Context context;
    List<Notebook> list = new ArrayList<>();
    private EditText tv1;
    private String userId="";
    View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getSubView(inflater,container);
        userId=((Main2Activity)getActivity()).getUserId();
        Bmob.initialize(getActivity(),"b69650c5254cf30c803d7b958a002ef1");
        gridView = (GridView) rootView.findViewById(R.id.folderGridView);
        BmobQuery<Notebook> query = new BmobQuery<>();
        Users users = new Users();
        users.setObjectId(userId);
        query.addWhereEqualTo("users",users);
        query.findObjects(new FindListener<Notebook>() {
            @Override
            public void done(List<Notebook> list1, BmobException e) {
                if(e==null){
                    list.addAll(list1);
                    System.out.println(list1.size());
                    System.out.println(list1.toString());
                    System.out.println(list.toString());

                    // gridView.setAdapter(new NoteBookAdapter(userId,notebook_main.this,list));
                    adapter=new NoteBookAdapter(userId,getActivity(),list);
                    gridView.setAdapter(adapter);
                }
            }
        });
        TextView textView=rootView.findViewById(R.id.textView8);

        //初始化添加按钮

        setSubListener();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_folder,container,false);
        return rootView;
    }

    public NoteBookAdapter getAdapter() {
        return adapter;
    }

}

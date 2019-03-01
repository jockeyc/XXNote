package com.shu.xxnote.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shu.xxnote.Main2Activity;
import com.shu.xxnote.NoteBookAdapter;
import com.shu.xxnote.Notebook;
import com.shu.xxnote.R;
import com.shu.xxnote.Users;
import com.shu.xxnote.utils.notebook_main;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class FolderFragment extends BaseFragment {
    GridView gridView;
    NoteBookAdapter adapter;
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
        textView.setText("123456");
        add = (ImageButton) rootView.findViewById(R.id.imageButton3);
        add.setOnClickListener(new FolderFragment.AddListener());


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

    class AddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog addDialog = new Dialog( getActivity(), R.style.HorizonDialog );
            addDialog.setContentView( R.layout.add_notebook_ask );
            Button btn_ok = (Button) addDialog.findViewById( R.id.ok );
            Button btn_cancel = (Button) addDialog.findViewById( R.id.cancel );

            btn_ok.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    final Dialog setname = new Dialog(getActivity(), R.style.HorizonDialog);
                    setname.setContentView(R.layout.setname);
                    Button btn_ok = (Button)setname.findViewById(R.id.btn_ok);
                    Button btn_cancel = (Button)setname.findViewById(R.id.btn_cancel);
                    tv1=(EditText)setname.findViewById( R.id.textView );
                    setname.show();
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String name = tv1.getText().toString();
                            if(name.length()==0)
                                name="我的笔记本";

                            Notebook book = new Notebook();
                            book.setNotename(name);
                            book.setCover(Notebook.getRandomCover());
                            Users users = new Users();
                            users.setObjectId(userId);
                            book.setUsers(users);
                            book.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId,BmobException e) {
                                    if(e==null){
                                        Toast.makeText( getActivity(), "添加笔记成功", Toast.LENGTH_SHORT ).show();
                                    }else{
                                        Toast.makeText( getActivity(), "添加笔记失败", Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            });
                            BmobQuery<Notebook> query = new BmobQuery<>();
                            Users users1 = new Users();
                            users1.setObjectId(userId);
                            query.addWhereEqualTo("users",users1);
                            query.findObjects(new FindListener<Notebook>() {
                                @Override
                                public void done(List<Notebook> list1, BmobException e) {
                                    if(e==null){
                                        //动态刷新
                                        adapter.setList(list1);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });
                            setname.cancel();
                        }
                    });

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            setname.cancel();
                        }
                    });
                    addDialog.cancel();
                }
            } );

            btn_cancel.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    addDialog.cancel();
                }
            } );
            addDialog.show();
        }
    }
}

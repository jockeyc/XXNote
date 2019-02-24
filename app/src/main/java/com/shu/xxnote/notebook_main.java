package com.shu.xxnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.shu.xxnote.module.serach;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class notebook_main extends AppCompatActivity {
    GridView gridView;
    NoteBookAdapter adapter;
    ImageButton add,search;
    private Context context;
    List<Notebook> list = new ArrayList<>();
    private EditText tv1;
    private String userId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.notebook_main );
        userId=this.getIntent().getExtras().getString("userId");
        Bmob.initialize(this,"b69650c5254cf30c803d7b958a002ef1");
        context = this;
        gridView = (GridView) findViewById( R.id.gridView );
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
                    adapter=new NoteBookAdapter(userId,notebook_main.this,list);
                    gridView.setAdapter(adapter);
                }
            }
        });

        //初始化搜索界面
        search = (ImageButton) findViewById(R.id.search);
        //初始化添加按钮
        add = (ImageButton) findViewById( R.id.add );
        add.setOnClickListener(new AddListener());
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(notebook_main.this, serach.class);
                startActivity(intent);
            }
        });

    }

    //添加一本笔记本
    class AddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog addDialog = new Dialog( context, R.style.HorizonDialog );
            addDialog.setContentView( R.layout.add_notebook_ask );
            Button btn_ok = (Button) addDialog.findViewById( R.id.ok );
            Button btn_cancel = (Button) addDialog.findViewById( R.id.cancel );

            btn_ok.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    final Dialog setname = new Dialog(context, R.style.HorizonDialog);
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
                                        Toast.makeText( context, "添加笔记成功", Toast.LENGTH_SHORT ).show();
                                    }else{
                                        Toast.makeText( context, "添加笔记失败", Toast.LENGTH_SHORT ).show();
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


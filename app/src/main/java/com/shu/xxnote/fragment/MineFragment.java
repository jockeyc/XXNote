package com.shu.xxnote.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shu.xxnote.Bmob.Users;
import com.shu.xxnote.Main2Activity;
import com.shu.xxnote.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MineFragment extends BaseFragment {
    View rootView;
    String userId = "";
    String name,phonemail,id,spe;
    TextView t1,t2,t3,t4;
    ImageButton bt;
    Context context;
    Users users;
    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_mine,container,false);
        return rootView;
    }
    @SuppressLint("WrongViewCast")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getSubView(inflater, container);
        context = rootView.getContext();
        userId=((Main2Activity)getActivity()).getUserId();
       t1 = (TextView) rootView.findViewById(R.id.textView7);
         t2 = (TextView) rootView.findViewById(R.id.textView9);
        t3 = (TextView) rootView.findViewById(R.id.textView10);
        t4 = (TextView) rootView.findViewById(R.id.textView12);
        bt = (ImageButton) rootView.findViewById(R.id.imageButton5);
        users = new Users();
        users.setObjectId(userId);
      update();
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.HorizonDialog);
                dialog.setContentView(R.layout.set_info);
                dialog.show();
                Button bt1 = (Button) dialog.findViewById(R.id.bt1);
                Button bt2 = (Button) dialog.findViewById(R.id.bt2);
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog1 = new Dialog(context, R.style.HorizonDialog);
                        dialog1.setContentView(R.layout.set_info_name);
                        dialog1.show();
                        dialog.cancel();
                        Button btn_ok = (Button) dialog1.findViewById(R.id.btn_ok);
                        Button btn_cancel = (Button)dialog1.findViewById(R.id.btn_cancel);
                        final EditText thename = (EditText)dialog1.findViewById(R.id.addid_et) ;
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                               String k = thename.getText().toString();
                                users.setUsername(k);
                                users.update(userId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Toast.makeText(context, "更新成功", Toast.LENGTH_LONG).show();
                                            update();
                                            dialog1.cancel();
                                        }else{
                                        }
                                    }
                                });
                            }
                        });

                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                dialog1.cancel();
                            }
                        });
                    }
                });

                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            dialog2();
                           dialog.cancel();
                    }
                });
            }
        });


        return rootView;
    }

  public void update(){
        BmobQuery<Users> bmobQuery = new BmobQuery<Users>();
        bmobQuery.getObject(userId, new QueryListener<Users>() {
            @Override
            public void done(Users object,BmobException e) {
                if(e==null){
                    id = object.getName();
                    name = object.getUsername();
                    phonemail = object.getPhonemail();
                    spe = object.getSpecialty();
                    if(name!=""){
                        t1.setText(name);
                    }
                    if(spe!=""){
                        t4.setText(spe);
                    }
                    t2.setText(id);
                    t3.setText(phonemail);
                }else{
                }
            }
        });
    }
    private void dialog2() {
        final String items[] = {"计算机工程与科学学院", "理学院", "生命科学学院", "文学院","法学院","外国语学院","经济学院","管理学院"};
        final boolean selected[] = {true, false, true, false};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("学院选择：");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String str = items[which];
                users.setSpecialty(str);
                users.update(userId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(context, "更新成功", Toast.LENGTH_LONG).show();
                        }else{
                        }
                    }
                });
                dialog.dismiss();
                update();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

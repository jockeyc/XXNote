package com.shu.xxnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.shu.xxnote.Bmob.Users;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {

    private Button btn1;
    private String userName,psw;//用户名、密码、加密密码
    private EditText et_username,et_psw;//用户名编辑框、密码编辑框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getExtras().getString("username");

        Bmob.initialize(this,"b69650c5254cf30c803d7b958a002ef1");
        setContentView(R.layout.activity_login);/////////////////////////登录页面//////////////////////////
        btn1 = (Button)findViewById(R.id.button);
        et_username = (EditText)findViewById(R.id.editText2);//用户名编辑框
        et_psw = (EditText)findViewById(R.id.editText3);//密码编辑框
        et_username.setText(userName);


        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                psw = et_psw.getText().toString();//密码
                userName = et_username.getText().toString().trim();//用户名(trim除去空格)
                final String md5Psw = RegisterActivity.MD5Utils.md5(psw);//对输入密码进行md5加密

                BmobQuery<Users> q1 = new BmobQuery<>();
                q1.addWhereEqualTo("name",userName);
                BmobQuery<Users> q2 = new BmobQuery<>();
                q2.addWhereEqualTo("password",md5Psw);
                BmobQuery<Users> q3 = new BmobQuery<>();
                q3.addWhereEqualTo("phonemail",userName);

                List<BmobQuery<Users>> queries = new ArrayList<BmobQuery<Users>>();
                queries.add(q1);
                queries.add(q3);

                BmobQuery<Users> mainQuery = new BmobQuery<Users>();
                BmobQuery<Users> or = mainQuery.or(queries);

                List<BmobQuery<Users>> andQurey = new ArrayList<BmobQuery<Users>>();
                andQurey.add(q2);
                andQurey.add(or);

                BmobQuery<Users> query = new BmobQuery<>();
                query.and(andQurey);
                query.findObjects(new FindListener<Users>() {
                    @Override
                    public void done(List<Users> list, BmobException e) {
                        if(list.size() != 0){
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                          //  Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                            Bundle bundle = new Bundle();
                            intent.putExtra("userId",list.get(0).getObjectId());
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
            }
        });
    }
}

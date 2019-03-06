package com.shu.xxnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shu.xxnote.Bmob.Users;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {

    private Button btn1,btn2;
    private EditText et_phonemail;
    public static String phoneMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.button);//继续按钮
        btn2 = (Button)findViewById(R.id.button2);//登录按钮
        et_phonemail = (EditText)findViewById(R.id.editText2) ;

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                phoneMail = et_phonemail.getText().toString();
                Bmob.initialize(MainActivity.this,"b69650c5254cf30c803d7b958a002ef1");
                BmobQuery<Users> query = new BmobQuery<>();
                query.addWhereEqualTo("phonemail",phoneMail);
                query.findObjects(new FindListener<Users>() {
                    @Override
                    public void done(List<Users> list, BmobException e) {
                        System.out.println(list.size()+"!!!!!!!!!!!!!!!!!!");
                        if(e==null){
                            if(list.size()!=0){
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                Bundle bundle = new Bundle();
                                intent.putExtra("username",phoneMail);
                                startActivity(intent);
                                finish();
                            }else {
                                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                                intent.putExtra("username",phoneMail);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //跳转到登录界面
//                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
////                startActivity(intent);
////                finish();
            }
        });

    }
}

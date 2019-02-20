package com.shu.xxnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //跳转到登录界面
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

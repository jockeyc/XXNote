package com.shu.xxnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shu.xxnote.Bmob.Users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private Button btn1;//注册按钮
    private EditText et_username,et_psw;//用户名编辑框，密码编辑框
    private String userName,psw;

    public static class MD5Utils{    //使用md5加密，之后使用加密密码进行操作
        public static String md5(String text){
            MessageDigest digest = null;
            try{
                digest = MessageDigest.getInstance("md5");
                byte[] result = digest.digest(text.getBytes());//文本变成数组
                StringBuffer sb = new StringBuffer();//创建StringBuffer空对象（比String偏重于对于字符串的变化）
                for (byte b:result){
                    int number = b & 0xff;//十六进制
                    String hex = Integer.toHexString(number);//把number转换成字符串
                    if (hex.length() == 1) {
                        sb.append("0" + hex);//在StringBuffer对象sb的尾部插入0+hex
                    }else{
                        sb.append(hex);//在StringBuffer对象sb的尾部插入hex
                    }
                }
                return sb.toString();//返回加密密码
            }
            catch (NoSuchAlgorithmException e){   //异常
                e.printStackTrace();//打印异常信息在程序中出错的位置及原因
                return "";//异常时返回空字符
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"b69650c5254cf30c803d7b958a002ef1");
        userName = getIntent().getExtras().getString("username");
        setContentView(R.layout.activity_register);/////////////////////////注册页面//////////////////////////
        et_username = (EditText)findViewById(R.id.editText2);//用户名编辑框
        et_psw = (EditText)findViewById(R.id.editText3);//密码编辑框
        et_username.setText(userName);
        btn1 = (Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userName = et_username.getText().toString().trim();//用户名(trim除去空格)
                psw = et_psw.getText().toString();//密码
                final String md5Psw = MD5Utils.md5(psw);

                BmobQuery<Users> query = new BmobQuery<>();
                final String name = userName;
                query.addWhereEqualTo("name",name);
                query.findObjects(new FindListener<Users>() {
                    @Override
                    public void done(List<Users> list, BmobException e) {
                        if(list.size()!=0){
                            Toast.makeText(RegisterActivity.this, "该用户名已被注册", Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            Users users = new Users();
                            users.setName(name);
                            users.setPassword(md5Psw);
                            users.setPhonemail(MainActivity.phoneMail);
                            users.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e!=null){
                                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        intent.putExtra("username",userName);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}

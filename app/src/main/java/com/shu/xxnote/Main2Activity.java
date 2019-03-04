package com.shu.xxnote;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shu.xxnote.adapter.MainContentPagerAdapter;
import com.shu.xxnote.adapter.NoteBookAdapter;
import com.shu.xxnote.customView.NoScrollViewPager;
import com.shu.xxnote.fragment.FolderFragment;
import com.shu.xxnote.utils.Constants;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Main2Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioButton[] Rdbtn = new RadioButton[5];
    private Drawable[] drawables = new Drawable[5];
    private NoScrollViewPager mViewPager;
    private RadioGroup radioGroup;
    private String userId;
    private EditText tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        userId = getIntent().getExtras().getString("userId");
        setImage();
        initView();
        initListener();
    }

    private void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        MainContentPagerAdapter fragmentAdapter = new MainContentPagerAdapter(supportFragmentManager);

        mViewPager = ((NoScrollViewPager) findViewById(R.id.content_paper));
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setScanScroll(false);

        radioGroup = ((RadioGroup) findViewById(R.id.radioGroup));
        radioGroup.setOnCheckedChangeListener(this);

        Rdbtn[0] = (RadioButton) findViewById(R.id.radioButton);
        Rdbtn[2] = (RadioButton) findViewById(R.id.radioButton3);
        Rdbtn[0].setChecked(true);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
        Rdbtn[2].setOnClickListener(new Main2Activity.AddListener());
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = Constants.PAGE_RECENT;
        switch (checkedId){
            case R.id.radioButton:
               index = Constants.PAGE_RECENT;
                break;
            case R.id.radioButton2:
                index = Constants.PAGE_FOLDER;
                break;
            case R.id.radioButton3:

                break;
            case R.id.radioButton4:
                index = Constants.PAGE_COLLECTION;
                break;
            case R.id.radioButton5:
                index = Constants.PAGE_MINE;
                break;
        }
        mViewPager.setCurrentItem(index,false);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case Constants.PAGE_RECENT:
                radioGroup.check(R.id.radioButton);
                break;
            case Constants.PAGE_FOLDER:
                radioGroup.check(R.id.radioButton2);
                break;
            case Constants.PAGE_COLLECTION:
                radioGroup.check(R.id.radioButton4);
                break;
            case Constants.PAGE_MINE:
                radioGroup.check(R.id.radioButton5);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void setImage() {
        Rdbtn[0] = (RadioButton)findViewById(R.id.radioButton);
        Rdbtn[1] = (RadioButton)findViewById(R.id.radioButton2);
        Rdbtn[2] = (RadioButton)findViewById(R.id.radioButton3);
        Rdbtn[3] = (RadioButton)findViewById(R.id.radioButton4);
        Rdbtn[4] = (RadioButton)findViewById(R.id.radioButton5);
        drawables[0]=getResources().getDrawable(R.drawable.selector_tab_button1,null); //获取图片
        drawables[1]=getResources().getDrawable(R.drawable.selector_tab_button2,null);
        drawables[2]=getResources().getDrawable(R.drawable.tab_button3,null);
        drawables[3]=getResources().getDrawable(R.drawable.selector_tab_button4,null);
        drawables[4]=getResources().getDrawable(R.drawable.selector_tab_button5,null);
        drawables[0].setBounds(0, 0, 60, 60);  //设置图片参数
        drawables[1].setBounds(0, 0, 60, 60);
        drawables[2].setBounds(0, 0, 100, 100);
        drawables[3].setBounds(0, 0, 60, 60);
        drawables[4].setBounds(0, 0, 60, 60);

        for(int i=0;i<5;i++){
            Rdbtn[i].setCompoundDrawables(null,drawables[i],null,null);
        }
        //设置到哪个控件的位置（）
    }

    public String getUserId(){
        return userId;
    }

    class AddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(1,false);
            final NoteBookAdapter adapter = ((FolderFragment)getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.content_paper + ":" + 1 )).getAdapter();
            final Dialog addDialog = new Dialog( Main2Activity.this, R.style.HorizonDialog );
            addDialog.setContentView( R.layout.add_notebook_ask );
            Button btn_ok = (Button) addDialog.findViewById( R.id.ok );
            Button btn_cancel = (Button) addDialog.findViewById( R.id.cancel );

            btn_ok.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    final Dialog setname = new Dialog(Main2Activity.this, R.style.HorizonDialog);
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
                                        Toast.makeText( Main2Activity.this, "添加笔记成功", Toast.LENGTH_SHORT ).show();
                                    }else{
                                        Toast.makeText( Main2Activity.this, "添加笔记失败", Toast.LENGTH_SHORT ).show();
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

package com.shu.xxnote;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shu.xxnote.adapter.MainContentPagerAdapter;
import com.shu.xxnote.customView.NoScrollViewPager;
import com.shu.xxnote.utils.Constants;

public class Main2Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioButton[] Rdbtn = new RadioButton[5];
    private Drawable[] drawables = new Drawable[5];
    private NoScrollViewPager mViewPager;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

        RadioButton Rdbtn = (RadioButton) findViewById(R.id.radioButton2);
        Rdbtn.setChecked(true);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
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
}

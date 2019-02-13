package com.shu.xxnote;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.shu.xxnote.adapter.MainContentPagerAdapter;
import com.shu.xxnote.adapter.NotePagerAdapter;
import com.shu.xxnote.customView.NoScrollViewPager;
import com.shu.xxnote.utils.Constants;

import static com.shu.xxnote.R.id.fill;
import static com.shu.xxnote.R.id.viewpager;

public class NoteActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private NoScrollViewPager viewPager;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initView();
        initListener();
    }

    private void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NotePagerAdapter fragmentAdapter = new NotePagerAdapter(supportFragmentManager);

        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        viewPager.setScanScroll(false);
        viewPager.setAdapter(fragmentAdapter);

        radioGroup = findViewById(R.id.radioGroup_note);
        radioGroup.check(R.id.radioButton_image);
        radioGroup.setOnCheckedChangeListener(this);

    }
    private void initListener() {
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = Constants.PAGE_IMAGE;
        switch (checkedId){
            case R.id.radioButton_image:
                index = Constants.PAGE_IMAGE;
                break;
            case R.id.radioButton_recording:
                index = Constants.PAGE_RECORDING;
                break;
            case R.id.radioButton_video:
                index = Constants.PAGE_VIDEO;
                break;
        }
        viewPager.setCurrentItem(index,false);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

package com.shu.xxnote.module;

import android.graphics.Bitmap;




public interface MainContract {

    interface View{
        void updateUI(String s);
    }

    interface Presenter{
        void getAccessToken();
        void getRecognitionResultByImage(Bitmap bitmap);
    }

}

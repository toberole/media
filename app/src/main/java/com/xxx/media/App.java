package com.xxx.media;

import android.app.Application;
import android.util.DisplayMetrics;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Constant.screen_w = displayMetrics.widthPixels;
        Constant.screen_h = displayMetrics.heightPixels;
    }
}

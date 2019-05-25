package com.xxx.media;

import android.app.Application;
import android.util.DisplayMetrics;

import com.xxx.media.uttils.AppUtil;
import com.xxx.media.uttils.CrashHandler;

import java.io.File;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppUtil.getInstance().init(this);
        CrashHandler.getInstance().init(this, null, null);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Constant.screen_w = displayMetrics.widthPixels;
        Constant.screen_h = displayMetrics.heightPixels;

        init();
    }

    private void init() {
        File file = new File(Constant.APP_DIR);
        if (!file.exists()) file.mkdirs();
    }
}

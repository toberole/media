package com.xxx.media;

import android.app.Application;
import android.util.DisplayMetrics;

import com.tencent.bugly.crashreport.CrashReport;
import com.xxx.media.uttils.AppUtil;
import com.xxx.media.uttils.CrashHandler;

import java.io.File;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppUtil.getInstance().init(this);
        CrashHandler.getInstance().init(this, null, null);
        CrashReport.initCrashReport(getApplicationContext(), "ff76ba0cbd", true);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Constant.screen_w = displayMetrics.widthPixels;
        Constant.screen_h = displayMetrics.heightPixels;

        init();
    }

    private void init() {
        File file = new File(Constant.APP_VIDEO_DIR);
        if (!file.exists()) file.mkdirs();

        file = new File(Constant.APP_AUDIO_DIR);
        if (!file.exists()) file.mkdirs();

        file = new File(Constant.APP_PIC_DIR);
        if (!file.exists()) file.mkdirs();
    }
}

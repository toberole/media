package com.xxx.media;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.sogou.speech.base.uttils.AppUtil;
import com.sogou.speech.base.uttils.CrashHandler;
import com.sogou.speech.base.uttils.LogUtil;
import com.sogou.speech.demo01.Initializer;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * attachBaseContext -- > onCreate
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName() + "---";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LogUtil.i(TAG, "attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.i(TAG, "onCreate");

        AppUtil.getInstance().init(this);
        CrashHandler.getInstance().init(this, null, null);
        CrashReport.initCrashReport(getApplicationContext(), "ff76ba0cbd", true);

        Initializer.init(this);

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

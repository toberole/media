package com.xxx.media.activitys;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.xxx.media.gl.MyRenderer1;
import com.xxx.media.uttils.AppUtil;
import com.xxx.media.uttils.LogUtil;

public class GLSurfaceViewActivity extends NoTitlebarActivity {
    public static final String TAG = GLSurfaceViewActivity.class.getSimpleName();
    private GLSurfaceView glSurfaceView;
    private LinearLayout ll_container;
    private MyRenderer1 myRenderer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        glSurfaceView = new GLSurfaceView(this);

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        // 检查支持opengl版本
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        int glEsVersion = configurationInfo.reqGlEsVersion;

        LogUtil.i(TAG, "glEsVersion: " + glEsVersion);

        // 判断是否支持 GL2
        boolean support = glEsVersion > 0x20000;
        if (!support) {
            AppUtil.getInstance().toast("系统不支持OpenGL2");
            return;
        }

        // 设置版本
        glSurfaceView.setEGLContextClientVersion(2);
        // 设置render
        myRenderer1 = new MyRenderer1();
        glSurfaceView.setRenderer(myRenderer1);

        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}

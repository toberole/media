package com.sogou.speech.demo01.activitys;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sogou.speech.demo01.R;
import com.sogou.speech.demo01.gl.render.MyRender;

public class Demo01MainActivity extends AppCompatActivity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo01_activity_main);
        init();
    }

    private void init() {
        surfaceView = findViewById(R.id.gl_SurfaceView_demo01);
        surfaceView.setEGLContextClientVersion(2);
        surfaceView.setRenderer(new MyRender(Demo01MainActivity.this));
    }
}

package com.sogou.speech.demo01.gl.render;

import android.content.Context;
import android.opengl.GLSurfaceView;


import com.sogou.speech.base.uttils.IOUtil;
import com.sogou.speech.demo01.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRender implements GLSurfaceView.Renderer {
    private Context context;

    public MyRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String shaderSource = IOUtil.readTextFromResource(context, R.raw.demo_vertex_shader_01);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}

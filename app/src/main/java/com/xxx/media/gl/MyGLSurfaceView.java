package com.xxx.media.gl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * OpenGL ES允许你使用三维空间坐标系定义绘制的图像，所以在绘制一个三角形之前必须要先定义它的坐标。
 * 在OpenGL中，这样做的典型方法是为坐标定义浮点数的顶点数组。
 * 为了获得最大的效率，可以将这些坐标写入ByteBuffer，并传递到OpenGL ES图形管道进行处理。
 */


public class MyGLSurfaceView extends GLSurfaceView {
    private MyGLRenderer renderer;

    public MyGLSurfaceView(Context context) {
        this(context, null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        renderer = new MyGLRenderer();
        setRenderer(renderer);
    }
}

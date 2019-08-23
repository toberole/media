package com.xxx.media;

import com.sogou.speech.demo01.activitys.Demo01MainActivity;
import com.xxx.media.activitys.CameraSurfaceActivity;
import com.xxx.media.activitys.CameraSurfaceViewActivity;
import com.xxx.media.activitys.CameraTextureViewActivity;
import com.xxx.media.activitys.GLSurfaceViewActivity;
import com.xxx.media.activitys.MediaMuxerActivity;
import com.xxx.media.activitys.OpenGLESActivity;
import com.xxx.media.activitys.OpenGLESTestActivity;
import com.xxx.media.activitys.SurfaceViewActivity;

public class Test {
    public static Class[] clazzs = new Class[]{
            SurfaceViewActivity.class, CameraSurfaceActivity.class,
            MediaMuxerActivity.class, OpenGLESActivity.class,
            CameraTextureViewActivity.class, CameraSurfaceViewActivity.class,
            OpenGLESTestActivity.class, GLSurfaceViewActivity.class,
            Demo01MainActivity.class
    };

    public static int test_index = 8;
}

package com.sogou.speech.demo01.gl;

import android.content.Context;
import android.opengl.GLES20;

import com.sogou.speech.base.uttils.IOUtil;

public class GLHelper {
    public static int createShader(Context context, int resourceId, int type) {
        String source = IOUtil.readTextFromResource(context, resourceId);
        int shaderId = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shaderId, source);
        GLES20.glCompileShader(shaderId);
        return shaderId;
    }
}

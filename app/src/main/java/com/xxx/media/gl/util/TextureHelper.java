package com.xxx.media.gl.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.sogou.speech.base.uttils.LogUtil;


public class TextureHelper {
    public static final String TAG = TextureHelper.class.getSimpleName();

    public static int loadTexture(Context context, int resourceId) {
        int textureObjectIds[] = new int[1];
        // 生成纹理
        GLES20.glGenTextures(1, textureObjectIds, 0);
        if (textureObjectIds[0] == 0) {
            return 0;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        if (null == bitmap) {
            GLES20.glDeleteTextures(1, textureObjectIds, 0);
        }

        // 绑定纹理对象
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]);

        // 设置纹理过滤参数
        // 缩小情况 GL_TEXTURE_MIN_FILTER
        // GL_LINEAR_MIPMAP_LINEAR 三线性过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        // 放大情况 GL_TEXTURE_MAG_FILTER
        // GL_LINEAR 双线性过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // 加载纹理到OpenGL
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        LogUtil.i(TAG, "textureObjectIds[0]: " + textureObjectIds[0]);

        return textureObjectIds[0];
    }
}

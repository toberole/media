package com.xxx.media.gl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.xxx.media.R;
import com.xxx.media.data.ColorShadeProgram;
import com.xxx.media.data.Mallet;
import com.xxx.media.data.Table;
import com.xxx.media.data.TextureShaderProgram;
import com.xxx.media.gl.util.MatrixHelper;
import com.xxx.media.gl.util.TextureHelper;
import com.xxx.media.uttils.LogUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * onSurfaceCreated  ---->  onSurfaceChanged ----> onDrawFrame ----> onDrawFrame ...
 * <p>
 * glsurface onDrawFrame 是在非UI线程中渲染
 */
public class MyRenderer1 implements GLSurfaceView.Renderer {
    public static final String TAG = MyRenderer1.class.getSimpleName();

    public final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram textureProgram;
    private ColorShadeProgram colorProgram;

    private int texture;

    private Context context;

    public MyRenderer1(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated");
        // 清屏
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0);

        table = new Table();
        mallet = new Mallet();

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShadeProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.test02);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        LogUtil.i(TAG, "MyRenderer1 onSurfaceChanged width: " + width + " height: " + height);

        // 设置视口
        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) (1.0 * width / height), 1f, 10f);

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2f);
//        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
//        Matrix.rotateM(modelMatrix, 0, -60f, -1f, 0f, 0f);

        float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    /**
     * 系统回调该方法 进行刷行
     */
    public void onDrawFrame(GL10 gl) {
        // 清空屏幕 使用GLES20.glClearColor设置的颜色填充屏幕
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();
        textureProgram.setUniforms(projectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        colorProgram.useProgram();
        colorProgram.setUniforms(projectionMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();
    }
}

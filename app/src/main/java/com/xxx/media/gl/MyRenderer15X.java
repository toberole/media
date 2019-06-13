package com.xxx.media.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sogou.speech.base.uttils.LogUtil;
import com.xxx.media.R;
import com.xxx.media.Vertices;
import com.xxx.media.gl.util.MatrixHelper;
import com.xxx.media.gl.util.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.xxx.media.Constant.BYTES_PER_FLOAT;

/**
 * onSurfaceCreated  ---->  onSurfaceChanged ----> onDrawFrame ----> onDrawFrame ...
 * <p>
 * glsurface onDrawFrame 是在非UI线程中渲染
 */
public class MyRenderer15X implements GLSurfaceView.Renderer {
    public static final String TAG = MyRenderer15X.class.getSimpleName();

    public static final int POSITION_COMPONENT_COUNT = 4;

    public static final int COLOR_COMPONENT_COUNT = 3;

    public static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    public static final String A_COLOR = "a_Color";
    private int aColorLocation;

    public static final String U_MATRIX = "u_Matrix";
    private int aMatrixLocation;

    public final float[] projectionMatrix = new float[16];

    private final float[] modelMatrix = new float[16];

    private FloatBuffer vertexData = ByteBuffer
            .allocateDirect(Vertices.tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    private int program;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated");

        // 清屏
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0);

        vertexData.put(Vertices.tableVerticesWithTriangles);

        String vertex_shader_resource = ShaderHelper.getShaderString(R.raw.simple_vertex_shader);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated vertex_shader_resource: " + vertex_shader_resource);

        String fragment_shader_resource = ShaderHelper.getShaderString(R.raw.simple_fragment_shader);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated fragment_shader_resource: " + fragment_shader_resource);

        int vertex_shader = ShaderHelper.compileVertexShader(vertex_shader_resource);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated vertex_shader: " + vertex_shader);

        int fragment_shader = ShaderHelper.compileFragmentShader(fragment_shader_resource);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated fragment_shaderr: " + fragment_shader);

        program = ShaderHelper.linProgram(vertex_shader, fragment_shader);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated program: " + program);

        boolean validate = ShaderHelper.validateProgram(program);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated validate: " + validate);

        GLES20.glUseProgram(program);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated aPositionLocation: " + aPositionLocation);

        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated aColorLocation: " + aColorLocation);

        aMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated aMatrixLocation: " + aMatrixLocation);

        int error = GLES20.glGetError();
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated error: " + error);
//        GLES20.GL_INVALID_ENUM

        ////////////////////////////////////
        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false,
                STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        vertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false,
                STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);
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

        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, projectionMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }
}

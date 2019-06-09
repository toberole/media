package com.xxx.media.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.xxx.media.R;
import com.xxx.media.TableVertices;
import com.xxx.media.uttils.LogUtil;
import com.xxx.media.uttils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * onSurfaceCreated  ---->  onSurfaceChanged ----> onDrawFrame ----> onDrawFrame ...
 * <p>
 * glsurface onDrawFrame 是在非UI线程中渲染
 */
public class MyRenderer1_X implements GLSurfaceView.Renderer {
    public static final String TAG = MyRenderer1_X.class.getSimpleName();

    public static final int POSITION_COMPONENT_COUNT = 2;

    public static final String U_COLOR = "u_Color";
    private int uColorLocation;

    public static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private static final int BYTES_PER_FLOAT = 4;

    private FloatBuffer vertexData = ByteBuffer
            .allocateDirect(TableVertices.tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    private int program;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated");

        // 清屏
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0);

        vertexData.put(TableVertices.tableVerticesWithTriangles);

        String vertex_shader_resource = ShaderUtil.getShaderString(R.raw.simple_vertex_shader);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated vertex_shader_resource: " + vertex_shader_resource);

        String fragment_shader_resource = ShaderUtil.getShaderString(R.raw.simple_fragment_shader);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated fragment_shader_resource: " + fragment_shader_resource);

        int vertex_shader = ShaderUtil.compileVertexShader(vertex_shader_resource);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated vertex_shader: " + vertex_shader);

        int fragment_shader = ShaderUtil.compileFragmentShader(fragment_shader_resource);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated fragment_shaderr: " + fragment_shader);

        program = ShaderUtil.linProgram(vertex_shader, fragment_shader);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated program: " + program);

        boolean validate = ShaderUtil.validateProgram(program);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated validate: " + validate);

        GLES20.glUseProgram(program);

        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated uColorLocation: " + uColorLocation);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        LogUtil.i(TAG, "MyRenderer1 onSurfaceCreated aPositionLocation: " + aPositionLocation);

        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData);

        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        LogUtil.i(TAG, "MyRenderer1 onSurfaceChanged width: " + width + " height: " + height);

        // 设置视口
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    /**
     * 系统回调该方法 进行刷行
     */
    public void onDrawFrame(GL10 gl) {
        // 清空屏幕 使用GLES20.glClearColor设置的颜色填充屏幕
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniform4f(aPositionLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }
}

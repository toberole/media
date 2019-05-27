package com.xxx.media.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 可以通过设置GLSurfaceView.RENDERMODE_WHEN_DIRTY来让GLSurfaceView监听到数据变化的时候再去刷新，
 * 即修改GLSurfaceView的渲染模式。这个设置可以防止重绘GLSurfaceView，直到你调用了requestRender()，
 */

/**
 * GL10的参数，因为这些方法签名被简单地用于2.0
 * API这样可以保持Android框架代码的简单。
 */

/**
 * 做任何绘制操作之前，必须要初始化并加载准备绘制的形状。除非形状的结构(指原始的坐标)在执行过程中发生改变，
 * 否则都应该在你的Renderer的方法onSurfaceCreated()中进行内存和效率方面的初始化工作。
 */

/**
 * 使用OpenGLES 2.0画一个定义好的形状需要比较多的代码，因为你必须为图形渲染管线提供一大堆信息。特别的，你必须定义以下几个东西：
 *
 * Vertex Shader - 用于渲染形状的顶点的OpenGLES 图形代码。
 * Fragment Shader - 用于渲染形状的外观（颜色或纹理）的OpenGLES 代码。
 * Program - 一个OpenGLES对象，包含了要用来绘制一个或多个形状的shader。
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;
    private Square mSquare;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // GLES20.glViewport(0, 0, width, height);
        // initialize a triangle
        mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mTriangle.draw();
    }

    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}

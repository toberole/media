package com.xxx.media.uttils;

import android.opengl.GLES20;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class ShaderUtil {
    public static final String TAG = ShaderUtil.class.getSimpleName();

    public static String getShaderString(int id) {
        String res = null;
        try {
            InputStream in = AppUtil.getInstance().getAppContext().getResources().openRawResource(id);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, len);
            }

            res = new String(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            res = null;
        }
        return res;
    }

    public static int compileVertexShader(String vertexShader) {
        return compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
    }

    public static int compileFragmentShader(String fragmentShader) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
    }

    public static int linProgram(int verTexShaderId, int fragmentShaderId) {
        int programObjectId = GLES20.glCreateProgram();
        if (0 == programObjectId) return programObjectId;
        GLES20.glAttachShader(programObjectId, verTexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);
        GLES20.glLinkProgram(programObjectId);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);
        int[] validataStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validataStatus, 0);
        LogUtil.i(TAG, "validateProgram: " + GLES20.glGetProgramInfoLog(programObjectId));
        LogUtil.i(TAG, "validataStatus[0]: " + validataStatus[0]);

        return validataStatus[0] != 0;
    }

    private static int compileShader(int type, String shader) {
        int shaderObjectId;
        shaderObjectId = GLES20.glCreateShader(type);

        LogUtil.i(TAG, "ShaderInfoLog: " + GLES20.glGetShaderInfoLog(shaderObjectId));

        if (shaderObjectId == 0) return shaderObjectId;

        GLES20.glShaderSource(shaderObjectId, shader);
        GLES20.glCompileShader(shaderObjectId);

        // 检查编译状态
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        return shaderObjectId;
    }
}

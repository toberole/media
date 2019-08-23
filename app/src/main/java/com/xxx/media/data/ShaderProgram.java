package com.xxx.media.data;

import android.content.Context;
import android.opengl.GLES20;

import com.sogou.speech.base.uttils.IOUtil;
import com.xxx.media.gl.util.ShaderHelper;

public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURECOORDINATES = "a_TextureCoordinates";

    protected int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(
                IOUtil.readTextFromResource(context, vertexShaderResourceId),
                IOUtil.readTextFromResource(context, fragmentShaderResourceId)
        );
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}

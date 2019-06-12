package com.xxx.media.data;

import android.content.Context;
import android.opengl.GLES20;

import com.xxx.media.gl.util.ShaderHelper;
import com.xxx.media.uttils.TextResourceReader;

public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURECOORDINATES = "a_TextureCoordinates";

    protected int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId)
        );
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}

package com.xxx.media.data;

import android.content.Context;
import android.opengl.GLES20;

import com.xxx.media.R;

public class TextureShaderProgram extends ShaderProgram {
    private int uMatrixLocation;
    private int uTextureUnitLocation;

    private int aPositionLocation;
    private int aTextureCoordinatesLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program, A_TEXTURECOORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        /**
         * 在OpenGL里面使用纹理绘制时，不需要直接给着色器传递纹理
         * 而是通过使用纹理单元保存那个纹理
         * 之所以这么做，是因为GPU只能同时绘制数量有限的纹理，它使用这些纹理单元表示当前正在被绘制的活动的纹理。
         *
         * 如果需要切换纹理，可以在纹理单元中来回切换纹理，注意如果切换的太频繁，可能会导致渲染的速度变慢
         *
         * 通过glActiveTexture把活动的纹理单元设置为纹理单元0，然后通过调用glBindTexture把纹理绑定
         * 到这个单元，接着通过调用glUniform1i把被选定的纹理单元传递给片段着色器中的u_Textureunit
         */
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getaPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getaTextureCoordinatesAttributeLocation() {
        return aTextureCoordinatesLocation;
    }
}

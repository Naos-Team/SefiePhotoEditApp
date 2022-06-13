package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.texture;

import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.constant.GLEtc;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;

public class GLOESTexture {
    public static final String TAG = "GLOESTexture";
    private int textureId = 0;
    private boolean textureLoaded = false;

    public void loadTexture() {
        if (!this.textureLoaded) {
            int[] iArr = new int[1];
            GLES20.glGenTextures(1, iArr, 0);
            this.textureId = iArr[0];
            GLES20.glBindTexture(GLEtc.GL_TEXTURE_EXTERNAL_OES, this.textureId);
            ShaderUtils.checkGlError("glBindTexture mTextureID");
            GLES20.glTexParameterf(GLEtc.GL_TEXTURE_EXTERNAL_OES, 10241, 9728.0f);
            GLES20.glTexParameterf(GLEtc.GL_TEXTURE_EXTERNAL_OES, 10240, 9729.0f);
            this.textureLoaded = true;
        }
    }

    public void deleteTexture() {
        GLES20.glDeleteTextures(1, new int[]{this.textureId}, 0);
    }

    public int getTextureId() {
        return this.textureId;
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.opengl.GLES20;

import java.nio.Buffer;

public class FBO {
    private int frameBuffer;
    private int frameBufferTexture;

    private FBO() {
    }

    public static FBO newInstance() {
        return new FBO();
    }

    public FBO create(int i, int i2) {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        GLES20.glGenFramebuffers(1, iArr, 0);
        GLES20.glGenTextures(1, iArr2, 0);
        GLES20.glBindTexture(3553, iArr2[0]);
        GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, (Buffer) null);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glBindFramebuffer(36160, iArr[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, iArr2[0], 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.frameBuffer = iArr[0];
        this.frameBufferTexture = iArr2[0];
        return this;
    }

    public void destroy() {
        GLES20.glDeleteTextures(1, new int[]{this.frameBufferTexture}, 0);
        GLES20.glDeleteFramebuffers(1, new int[]{this.frameBuffer}, 0);
    }

    public void bind() {
        GLES20.glBindFramebuffer(36160, this.frameBuffer);
    }

    public void unbind() {
        GLES20.glBindFramebuffer(36160, 0);
    }

    public int getFrameBufferTextureId() {
        return this.frameBufferTexture;
    }

    public int getFrameBuffer() {
        return this.frameBuffer;
    }
}

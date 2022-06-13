package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;

public abstract class GLAbsProgram {
    private String mFragmentShader;
    private int mProgramId;
    private String mVertexShader;
    private int maPositionHandle;
    private int maTextureCoordinateHandle;

    public GLAbsProgram(Context context, String str, String str2) {
        this.mVertexShader = ShaderUtils.readAssetsTextFile(context, str);
        this.mFragmentShader = ShaderUtils.readAssetsTextFile(context, str2);
    }

    public GLAbsProgram(String str, String str2) {
        this.mVertexShader = str;
        this.mFragmentShader = str2;
    }

    public GLAbsProgram(Context context, int i, int i2) {
        this.mVertexShader = ShaderUtils.readRawTextFile(context, i);
        this.mFragmentShader = ShaderUtils.readRawTextFile(context, i2);
    }

    public void create() {
        this.mProgramId = ShaderUtils.createProgram(this.mVertexShader, this.mFragmentShader);
        if (this.mProgramId != 0) {
            this.maPositionHandle = GLES20.glGetAttribLocation(getProgramId(), "aPosition");
            ShaderUtils.checkGlError("glGetAttribLocation aPosition");
            if (this.maPositionHandle != -1) {
                this.maTextureCoordinateHandle = GLES20.glGetAttribLocation(getProgramId(), "aTextureCoord");
                ShaderUtils.checkGlError("glGetAttribLocation aTextureCoord");
                if (this.maTextureCoordinateHandle == -1) {
                    throw new RuntimeException("Could not get attrib location for aTextureCoord");
                }
                return;
            }
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
    }

    public void use() {
        GLES20.glUseProgram(getProgramId());
        ShaderUtils.checkGlError("glUseProgram");
    }

    public int getProgramId() {
        return this.mProgramId;
    }

    public void onDestroy() {
        GLES20.glDeleteProgram(this.mProgramId);
    }

    public int getPositionHandle() {
        return this.maPositionHandle;
    }

    public int getTextureCoordinateHandle() {
        return this.maTextureCoordinateHandle;
    }
}

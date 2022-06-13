package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;

public class GLPassThroughProgram extends GLAbsProgram {
    private int uMVPMatrixHandle;
    private int uTextureSamplerHandle;

    public GLPassThroughProgram(Context context) {
        super(context, "filter/vsh/base/pass_through.glsl", "filter/fsh/base/pass_through.glsl");
    }

    public void create() {
        super.create();
        this.uTextureSamplerHandle = GLES20.glGetUniformLocation(getProgramId(), "sTexture");
        ShaderUtils.checkGlError("glGetUniformLocation uniform sTexture");
        this.uMVPMatrixHandle = GLES20.glGetUniformLocation(getProgramId(), "uMVPMatrix");
        ShaderUtils.checkGlError("glGetUniformLocation uMVPMatrix");
    }

    public int getTextureSamplerHandle() {
        return this.uTextureSamplerHandle;
    }

    public int getMVPMatrixHandle() {
        return this.uMVPMatrixHandle;
    }
}

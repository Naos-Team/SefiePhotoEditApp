package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;

public class GLOESProgram extends GLAbsProgram {
    private int muSTMatrixHandle;
    private int uTextureSamplerHandle;

    public GLOESProgram(Context context) {
        super(context, "filter/vsh/base/oes.glsl", "filter/fsh/base/oes.glsl");
    }

    public void create() {
        super.create();
        this.muSTMatrixHandle = GLES20.glGetUniformLocation(getProgramId(), "uSTMatrix");
        ShaderUtils.checkGlError("glGetUniformLocation uSTMatrix");
        if (this.muSTMatrixHandle != -1) {
            this.uTextureSamplerHandle = GLES20.glGetUniformLocation(getProgramId(), "sTexture");
            ShaderUtils.checkGlError("glGetUniformLocation uniform samplerExternalOES sTexture");
            return;
        }
        throw new RuntimeException("Could not get attrib location for uSTMatrix");
    }

    public int getMuSTMatrixHandle() {
        return this.muSTMatrixHandle;
    }

    public int getUTextureSamplerHandle() {
        return this.uTextureSamplerHandle;
    }
}

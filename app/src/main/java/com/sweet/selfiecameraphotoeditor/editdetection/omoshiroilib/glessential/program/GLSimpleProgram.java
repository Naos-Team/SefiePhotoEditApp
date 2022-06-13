package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;

public class GLSimpleProgram extends GLAbsProgram {
    private int uTextureSamplerHandle;

    public GLSimpleProgram(Context context, String str, String str2) {
        super(context, str, str2);
    }

    public GLSimpleProgram(String str, String str2) {
        super(str, str2);
    }

    public void create() {
        super.create();
        this.uTextureSamplerHandle = GLES20.glGetUniformLocation(getProgramId(), "sTexture");
        ShaderUtils.checkGlError("glGetUniformLocation uniform samplerExternalOES sTexture");
    }

    public int getTextureSamplerHandle() {
        return this.uTextureSamplerHandle;
    }
}

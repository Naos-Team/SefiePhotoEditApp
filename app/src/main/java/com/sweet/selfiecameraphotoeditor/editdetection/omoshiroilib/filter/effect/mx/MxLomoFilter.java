package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class MxLomoFilter extends SimpleFragmentShaderFilter {
    public MxLomoFilter(Context context) {
        super(context, "filter/fsh/mx/mx_lomo.glsl");
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "rOffset", 1.0f);
        setUniform1f(this.glSimpleProgram.getProgramId(), "gOffset", 1.0f);
        setUniform1f(this.glSimpleProgram.getProgramId(), "bOffset", 1.0f);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}

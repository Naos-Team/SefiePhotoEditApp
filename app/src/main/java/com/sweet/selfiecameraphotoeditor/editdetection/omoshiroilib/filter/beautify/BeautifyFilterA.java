package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.beautify;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class BeautifyFilterA extends SimpleFragmentShaderFilter {
    private float texelHeightOffset = 2.0f;
    private float texelWidthOffset = 2.0f;

    public BeautifyFilterA(Context context) {
        super(context, "filter/fsh/beautify/beautify_a.glsl");
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", this.texelWidthOffset / ((float) this.surfaceWidth));
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", this.texelHeightOffset / ((float) this.surfaceHeight));
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public BeautifyFilterA setStepLength(int i) {
        float f = (float) i;
        this.texelHeightOffset = f;
        this.texelWidthOffset = f;
        return this;
    }
}

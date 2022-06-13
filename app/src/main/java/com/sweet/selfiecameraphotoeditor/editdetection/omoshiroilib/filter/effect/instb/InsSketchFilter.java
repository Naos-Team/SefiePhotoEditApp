package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.instb;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsSketchFilter extends MultipleTextureFilter {
    public InsSketchFilter(Context context) {
        super(context, "filter/fsh/instb/sketch.glsl");
        this.textureSize = 0;
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 0.9f);
        setUniform2fv(this.glSimpleProgram.getProgramId(), "singleStepOffset", new float[]{1.0f / ((float) this.surfaceWidth), 1.0f / ((float) this.surfaceHeight)});
    }
}

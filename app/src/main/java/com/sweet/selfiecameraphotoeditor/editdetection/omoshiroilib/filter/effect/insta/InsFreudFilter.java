package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.insta;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsFreudFilter extends MultipleTextureFilter {
    public InsFreudFilter(Context context) {
        super(context, "filter/fsh/insta/freud.glsl");
        this.textureSize = 1;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/freud_rand.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
        setUniform1f(this.glSimpleProgram.getProgramId(), "inputImageTextureHeight", (float) this.surfaceHeight);
        setUniform1f(this.glSimpleProgram.getProgramId(), "inputImageTextureWidth", (float) this.surfaceWidth);
    }
}

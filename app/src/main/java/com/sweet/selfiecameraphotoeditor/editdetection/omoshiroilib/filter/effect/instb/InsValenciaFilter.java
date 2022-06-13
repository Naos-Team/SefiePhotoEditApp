package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.instb;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsValenciaFilter extends MultipleTextureFilter {
    public InsValenciaFilter(Context context) {
        super(context, "filter/fsh/instb/valencia.glsl");
        this.textureSize = 2;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/valenciamap.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/valenciagradientmap.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}

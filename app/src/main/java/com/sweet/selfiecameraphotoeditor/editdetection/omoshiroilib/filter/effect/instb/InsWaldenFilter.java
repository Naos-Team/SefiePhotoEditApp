package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.instb;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsWaldenFilter extends MultipleTextureFilter {
    public InsWaldenFilter(Context context) {
        super(context, "filter/fsh/instb/walden.glsl");
        this.textureSize = 2;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/walden_map.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/vignette_map.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}

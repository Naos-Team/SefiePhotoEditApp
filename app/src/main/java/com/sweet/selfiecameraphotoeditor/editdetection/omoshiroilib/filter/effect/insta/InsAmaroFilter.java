package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.insta;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsAmaroFilter extends MultipleTextureFilter {
    public InsAmaroFilter(Context context) {
        super(context, "filter/fsh/insta/amaro.glsl");
        this.textureSize = 3;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/brannan_blowout.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/overlaymap.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/amaromap.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}

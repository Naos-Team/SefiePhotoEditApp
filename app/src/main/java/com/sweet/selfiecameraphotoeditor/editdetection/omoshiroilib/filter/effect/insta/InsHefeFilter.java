package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.insta;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsHefeFilter extends MultipleTextureFilter {
    public InsHefeFilter(Context context) {
        super(context, "filter/fsh/insta/hefe.glsl");
        this.textureSize = 5;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/edgeburn.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/hefemap.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/hefegradientmap.png");
        this.externalBitmapTextures[3].load(this.context, "filter/textures/inst/hefesoftlight.png");
        this.externalBitmapTextures[4].load(this.context, "filter/textures/inst/hefemetal.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}

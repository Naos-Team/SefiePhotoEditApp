package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.insta;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsN1977Filter extends MultipleTextureFilter {
    public InsN1977Filter(Context context) {
        super(context, "filter/fsh/insta/n1977.glsl");
        this.textureSize = 2;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/n1977map.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/n1977blowout.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}

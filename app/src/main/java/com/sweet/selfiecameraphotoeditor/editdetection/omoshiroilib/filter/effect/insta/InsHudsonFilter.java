package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.insta;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsHudsonFilter extends MultipleTextureFilter {
    public InsHudsonFilter(Context context) {
        super(context, "filter/fsh/insta/hudson.glsl");
        this.textureSize = 3;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/hudsonbackground.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/overlaymap.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/hudsonmap.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.insta;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;

public class InsBrooklynFilter extends MultipleTextureFilter {
    public InsBrooklynFilter(Context context) {
        super(context, "filter/fsh/insta/brooklyn.glsl");
        this.textureSize = 3;
    }

    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/brooklynCurves1.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/filter_map_first.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/brooklynCurves2.png");
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}

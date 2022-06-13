package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;

public class TileMosaicFilter extends ShaderToyAbsFilter {
    public TileMosaicFilter(Context context) {
        super(context, "filter/fsh/shadertoy/tile_mosaic.glsl");
    }
}

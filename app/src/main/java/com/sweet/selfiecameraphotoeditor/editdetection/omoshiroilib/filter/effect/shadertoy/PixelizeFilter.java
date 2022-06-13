package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;

public class PixelizeFilter extends ShaderToyAbsFilter {
    public PixelizeFilter(Context context) {
        super(context, "filter/fsh/shadertoy/pixelize.glsl");
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;

public class CrosshatchFilter extends ShaderToyAbsFilter {
    public CrosshatchFilter(Context context) {
        super(context, "filter/fsh/shadertoy/crosshatch.glsl");
    }
}

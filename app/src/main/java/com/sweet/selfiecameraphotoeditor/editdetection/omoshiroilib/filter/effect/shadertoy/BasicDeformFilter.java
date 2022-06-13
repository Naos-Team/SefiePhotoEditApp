package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;

public class BasicDeformFilter extends ShaderToyAbsFilter {
    public BasicDeformFilter(Context context) {
        super(context, "filter/fsh/shadertoy/basic_deform.glsl");
    }
}

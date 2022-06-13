package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;

public class ContrastFilter extends ShaderToyAbsFilter {
    public ContrastFilter(Context context) {
        super(context, "filter/fsh/shadertoy/contrast.glsl");
    }
}

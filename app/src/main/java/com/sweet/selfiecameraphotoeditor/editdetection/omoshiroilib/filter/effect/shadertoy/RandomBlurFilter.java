package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;

public class RandomBlurFilter extends ShaderToyAbsFilter {
    public RandomBlurFilter(Context context) {
        super(context, "filter/fsh/shadertoy/random_blur.glsl");
    }
}

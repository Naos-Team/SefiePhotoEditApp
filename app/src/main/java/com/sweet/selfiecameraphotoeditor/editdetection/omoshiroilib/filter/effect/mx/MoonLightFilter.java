package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class MoonLightFilter extends SimpleFragmentShaderFilter {
    public MoonLightFilter(Context context) {
        super(context, "filter/fsh/mx/mx_moon_light.glsl");
    }
}

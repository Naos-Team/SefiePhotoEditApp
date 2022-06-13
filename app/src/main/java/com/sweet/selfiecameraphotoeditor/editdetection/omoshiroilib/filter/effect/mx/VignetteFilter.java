package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class VignetteFilter extends SimpleFragmentShaderFilter {
    public VignetteFilter(Context context) {
        super(context, "filter/fsh/mx/mx_vignette.glsl");
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class BlackWhiteFilter extends SimpleFragmentShaderFilter {
    public BlackWhiteFilter(Context context) {
        super(context, "filter/fsh/mx/mx_black_white.glsl");
    }
}

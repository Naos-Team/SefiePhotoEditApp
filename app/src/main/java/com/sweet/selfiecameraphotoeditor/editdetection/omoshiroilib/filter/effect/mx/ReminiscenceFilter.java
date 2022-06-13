package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class ReminiscenceFilter extends SimpleFragmentShaderFilter {
    public ReminiscenceFilter(Context context) {
        super(context, "filter/fsh/mx/mx_reminiscence.glsl");
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.beautify;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class BeautifyFilterFUD extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUD(Context context) {
        super(context, "filter/fsh/beautify/beautify_d.glsl");
    }
}

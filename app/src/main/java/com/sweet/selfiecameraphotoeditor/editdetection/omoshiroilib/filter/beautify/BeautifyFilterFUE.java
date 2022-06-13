package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.beautify;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class BeautifyFilterFUE extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUE(Context context) {
        super(context, "filter/fsh/beautify/beautify_e.glsl");
    }
}

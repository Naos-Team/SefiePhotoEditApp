package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.beautify;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class BeautifyFilterFUB extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUB(Context context) {
        super(context, "filter/fsh/beautify/beautify_b.glsl");
    }
}

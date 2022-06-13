package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.beautify;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class BeautifyFilterFUF extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUF(Context context) {
        super(context, "filter/fsh/beautify/beautify_f.glsl");
    }
}

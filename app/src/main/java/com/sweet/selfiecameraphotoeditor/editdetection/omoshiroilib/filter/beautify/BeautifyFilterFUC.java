package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.beautify;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class BeautifyFilterFUC extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUC(Context context) {
        super(context, "filter/fsh/beautify/beautify_c.glsl");
    }
}

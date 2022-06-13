package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.imgproc;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class InvertColorFilter extends SimpleFragmentShaderFilter {
    public InvertColorFilter(Context context) {
        super(context, "filter/fsh/imgproc/invert_color.glsl");
    }
}

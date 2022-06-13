package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.imgproc;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class GrayScaleShaderFilter extends SimpleFragmentShaderFilter {
    public GrayScaleShaderFilter(Context context) {
        super(context, "filter/fsh/imgproc/gray_scale.glsl");
    }
}

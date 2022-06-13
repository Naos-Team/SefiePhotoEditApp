package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class ShiftColorFilter extends SimpleFragmentShaderFilter {
    public ShiftColorFilter(Context context) {
        super(context, "filter/fsh/mx/mx_shift_color.glsl");
    }
}

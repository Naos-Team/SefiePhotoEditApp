package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;

public class GreenHouseFilter extends SimpleFragmentShaderFilter {
    public GreenHouseFilter(Context context) {
        super(context, "filter/fsh/mx/mx_green_house.glsl");
    }
}

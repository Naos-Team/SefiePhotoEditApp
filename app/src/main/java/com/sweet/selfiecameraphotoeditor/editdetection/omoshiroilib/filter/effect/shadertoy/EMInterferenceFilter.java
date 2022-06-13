package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;

public class EMInterferenceFilter extends ShaderToyAbsFilter {
    public EMInterferenceFilter(Context context) {
        super(context, "filter/fsh/shadertoy/em_interference.glsl");
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.ext;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;

public class ScalingFilter extends PassThroughFilter {
    private boolean drawOnTop = false;

    public ScalingFilter(Context context) {
        super(context);
    }

    public void onPreDrawElements() {
        if (!this.drawOnTop) {
            super.onPreDrawElements();
        }
    }

    public ScalingFilter setScalingFactor(float f) {
        this.plane.scale(f);
        return this;
    }

    public ScalingFilter setDrawOnTop(boolean z) {
        this.drawOnTop = z;
        return this;
    }
}

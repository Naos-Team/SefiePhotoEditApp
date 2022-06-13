package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.ext;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.FilterGroup;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy.FastBlurFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.imgproc.CustomizedGaussianBlurFilter;

public class BlurredFrameEffect extends FilterGroup {
    private static final int BLUR_STEP_LENGTH = 2;
    private static final float SCALING_FACTOR = 0.6f;
    private ScalingFilter scalingFilter;

    public BlurredFrameEffect(Context context) {
        addFilter(new FastBlurFilter(context).setScale(true));
        addFilter(CustomizedGaussianBlurFilter.initWithBlurRadiusInPixels(4).setTexelHeightOffset(2.0f).setScale(true));
        addFilter(CustomizedGaussianBlurFilter.initWithBlurRadiusInPixels(4).setTexelWidthOffset(2.0f).setScale(true));
        addFilter(new PassThroughFilter(context));
        this.scalingFilter = new ScalingFilter(context).setScalingFactor(SCALING_FACTOR).setDrawOnTop(true);
    }

    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
        this.scalingFilter.onDrawFrame(i);
    }

    public void init() {
        super.init();
        this.scalingFilter.init();
    }

    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
        this.scalingFilter.onFilterChanged(i, i2);
    }

    public void destroy() {
        super.destroy();
        this.scalingFilter.destroy();
    }
}

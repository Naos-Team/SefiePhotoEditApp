package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class GLRootView extends GLSurfaceView {
    private int surfaceHeight;
    private double surfaceRatio;
    private int surfaceWidth;

    public GLRootView(Context context) {
        super(context);
    }

    public GLRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setAspectRatio(int i, int i2) {
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        this.surfaceWidth = i;
        this.surfaceHeight = i2;
        double d = (double) this.surfaceWidth;
        double d2 = (double) this.surfaceHeight;
        Double.isNaN(d);
        Double.isNaN(d2);
        this.surfaceRatio = d / d2;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(13);
        setLayoutParams(layoutParams);
        requestLayout();
    }


    @Override
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (this.surfaceWidth == 0 || this.surfaceHeight == 0) {
            setMeasuredDimension(size, size2);
            return;
        }
        double d = (double) size;
        double d2 = (double) size2;
        double d3 = this.surfaceRatio;
        Double.isNaN(d2);
        if (d < d2 * d3) {
            Double.isNaN(d);
            setMeasuredDimension(size, (int) (d / d3));
            return;
        }
        Double.isNaN(d2);
        setMeasuredDimension((int) (d2 * d3), size2);
    }
}

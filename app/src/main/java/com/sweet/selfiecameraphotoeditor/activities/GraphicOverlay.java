package com.sweet.selfiecameraphotoeditor.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

public class GraphicOverlay extends View {

    public int mFacing = 0;
    private Set<Graphic> mGraphics = new HashSet();

    public float mHeightScaleFactor = 1.0f;
    private final Object mLock = new Object();
    private int mPreviewHeight;
    private int mPreviewWidth;

    public float mWidthScaleFactor = 1.0f;

    public static abstract class Graphic {
        private GraphicOverlay mOverlay;

        public abstract void draw(Canvas canvas);

        public Graphic(GraphicOverlay graphicOverlay) {
            this.mOverlay = graphicOverlay;
        }

        public float scaleX(float f) {
            return f * this.mOverlay.mWidthScaleFactor;
        }

        public float scaleY(float f) {
            return f * this.mOverlay.mHeightScaleFactor;
        }

        public float translateX(float f) {
            if (this.mOverlay.mFacing == 1) {
                return ((float) this.mOverlay.getWidth()) - scaleX(f);
            }
            return scaleX(f);
        }

        public float translateY(float f) {
            return scaleY(f) - 140.0f;
        }

        public void postInvalidate() {
            this.mOverlay.postInvalidate();
        }
    }

    public GraphicOverlay(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void clear() {
        synchronized (this.mLock) {
            this.mGraphics.clear();
        }
        postInvalidate();
    }

    public void add(Graphic graphic) {
        synchronized (this.mLock) {
            this.mGraphics.add(graphic);
        }
        postInvalidate();
    }

    public void remove(Graphic graphic) {
        synchronized (this.mLock) {
            this.mGraphics.remove(graphic);
        }
        postInvalidate();
    }

    public void setCameraInfo(int i, int i2, int i3) {
        synchronized (this.mLock) {
            this.mPreviewWidth = i;
            this.mPreviewHeight = i2;
            this.mFacing = i3;
        }
        postInvalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (this.mLock) {
            if (!(this.mPreviewWidth == 0 || this.mPreviewHeight == 0)) {
                this.mWidthScaleFactor = ((float) canvas.getWidth()) / ((float) this.mPreviewWidth);
                double height = (double) (((float) canvas.getHeight()) / ((float) this.mPreviewHeight));
                Double.isNaN(height);
                this.mHeightScaleFactor = (float) (height + 0.1d);
            }
            for (Graphic draw : this.mGraphics) {
                draw.draw(canvas);
            }
        }
    }
}

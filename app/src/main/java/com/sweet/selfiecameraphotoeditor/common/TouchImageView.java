package com.sweet.selfiecameraphotoeditor.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;

import androidx.appcompat.widget.AppCompatImageView;

import com.sweet.selfiecameraphotoeditor.R;

public class TouchImageView extends AppCompatImageView {
    public static final float AUTOMATIC_MIN_ZOOM = -1.0f;
     static final String DEBUG = "DEBUG";
     static final float SUPER_MAX_MULTIPLIER = 1.25f;
     static final float SUPER_MIN_MULTIPLIER = 0.75f;
     ZoomVariables delayedZoomVariables;

    public GestureDetector.OnDoubleTapListener doubleTapListener;

    public Fling fling;
     boolean imageRenderedAtLeastOnce;

    public boolean isRotateImageToFitScreen;

    public float[] m;

    public GestureDetector mGestureDetector;

    public ScaleGestureDetector mScaleDetector;
     ScaleType mScaleType;
     float matchViewHeight;
     float matchViewWidth;

    public Matrix matrix;

    public float maxScale;
     boolean maxScaleIsSetByMultiplier;
     float maxScaleMultiplier;

    public float minScale;

    public float normalizedScale;
     boolean onDrawReady;
     int orientation;
     FixedPixel orientationChangeFixedPixel;
     boolean orientationJustChanged;
     float prevMatchViewHeight;
     float prevMatchViewWidth;
     Matrix prevMatrix;
     int prevViewHeight;
     int prevViewWidth;

    public State state;
     float superMaxScale;
     float superMinScale;

    public OnTouchImageViewListener touchImageViewListener;
     float userSpecifiedMinScale;

    public OnTouchListener userTouchListener;

    public int viewHeight;
     FixedPixel viewSizeChangeFixedPixel;

    public int viewWidth;
     boolean zoomEnabled;

    public enum FixedPixel {
        CENTER,
        TOP_LEFT,
        BOTTOM_RIGHT
    }

    public interface OnTouchImageViewListener {
        void onMove();
    }

    public interface OnZoomFinishedListener {
        void onZoomFinished();
    }

     enum State {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM
    }


    public float getFixDragTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            return 0.0f;
        }
        return f;
    }

     float getFixTrans(float f, float f2, float f3, float f4) {
        float f5;
        if (f3 <= f2) {
            float f6 = f4;
            f4 = (f2 + f4) - f3;
            f5 = f6;
        } else {
            f5 = (f2 + f4) - f3;
        }
        if (f < f5) {
            return (-f) + f5;
        }
        if (f > f4) {
            return (-f) + f4;
        }
        return 0.0f;
    }

    public TouchImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TouchImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TouchImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.orientationChangeFixedPixel = FixedPixel.CENTER;
        this.viewSizeChangeFixedPixel = FixedPixel.CENTER;
        this.orientationJustChanged = false;
        this.maxScaleIsSetByMultiplier = false;
        this.doubleTapListener = null;
        this.userTouchListener = null;
        this.touchImageViewListener = null;
        configureImageView(context, attributeSet, i);
    }

     void configureImageView(Context context, AttributeSet attributeSet, int i) {
        super.setClickable(true);
        this.orientation = getResources().getConfiguration().orientation;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener(this, (AnonymousClass1) null));
        this.mGestureDetector = new GestureDetector(context, new GestureListener(this, (AnonymousClass1) null));
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.m = new float[9];
        this.normalizedScale = 1.0f;
        if (this.mScaleType == null) {
            this.mScaleType = ScaleType.FIT_CENTER;
        }
        this.minScale = 1.0f;
        this.maxScale = 3.0f;
        this.superMinScale = this.minScale * SUPER_MIN_MULTIPLIER;
        this.superMaxScale = this.maxScale * SUPER_MAX_MULTIPLIER;
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        this.onDrawReady = false;
        super.setOnTouchListener(new PrivateOnTouchListener(this, (AnonymousClass1) null));
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TouchImageView, i, 0);
        try {
            if (!isInEditMode()) {
                setZoomEnabled(obtainStyledAttributes.getBoolean(0, true));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.userTouchListener = onTouchListener;
    }

    public boolean isZoomEnabled() {
        return this.zoomEnabled;
    }

    public void setZoomEnabled(boolean z) {
        this.zoomEnabled = z;
    }

    @Override
    public void setImageResource(int i) {
        this.imageRenderedAtLeastOnce = false;
        super.setImageResource(i);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        this.imageRenderedAtLeastOnce = false;
        super.setImageBitmap(bitmap);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        this.imageRenderedAtLeastOnce = false;
        super.setImageDrawable(drawable);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageURI(Uri uri) {
        this.imageRenderedAtLeastOnce = false;
        super.setImageURI(uri);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);
            return;
        }
        this.mScaleType = scaleType;
        if (this.onDrawReady) {
            setZoom(this);
        }
    }

    @Override
    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public boolean isZoomed() {
        return this.normalizedScale != 1.0f;
    }


    public void savePreviousImageValues() {
        Matrix matrix2 = this.matrix;
        if (matrix2 != null && this.viewHeight != 0 && this.viewWidth != 0) {
            matrix2.getValues(this.m);
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = this.matchViewHeight;
            this.prevMatchViewWidth = this.matchViewWidth;
            this.prevViewHeight = this.viewHeight;
            this.prevViewWidth = this.viewWidth;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("orientation", this.orientation);
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.m);
        bundle.putFloatArray("matrix", this.m);
        bundle.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
        bundle.putSerializable("viewSizeChangeFixedPixel", this.viewSizeChangeFixedPixel);
        bundle.putSerializable("orientationChangeFixedPixel", this.orientationChangeFixedPixel);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.normalizedScale = bundle.getFloat("saveScale");
            this.m = bundle.getFloatArray("matrix");
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            this.prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            this.prevViewHeight = bundle.getInt("viewHeight");
            this.prevViewWidth = bundle.getInt("viewWidth");
            this.imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            this.viewSizeChangeFixedPixel = (FixedPixel) bundle.getSerializable("viewSizeChangeFixedPixel");
            this.orientationChangeFixedPixel = (FixedPixel) bundle.getSerializable("orientationChangeFixedPixel");
            if (this.orientation != bundle.getInt("orientation")) {
                this.orientationJustChanged = true;
            }
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }


    @Override
    public void onDraw(Canvas canvas) {
        this.onDrawReady = true;
        this.imageRenderedAtLeastOnce = true;
        ZoomVariables zoomVariables = this.delayedZoomVariables;
        if (zoomVariables != null) {
            setZoom(zoomVariables.scale, this.delayedZoomVariables.focusX, this.delayedZoomVariables.focusY, this.delayedZoomVariables.scaleType);
            this.delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = getResources().getConfiguration().orientation;
        if (i != this.orientation) {
            this.orientationJustChanged = true;
            this.orientation = i;
        }
        savePreviousImageValues();
    }

    public void setMaxZoomRatio(float f) {
        this.maxScaleMultiplier = f;
        this.maxScale = this.minScale * this.maxScaleMultiplier;
        this.superMaxScale = this.maxScale * SUPER_MAX_MULTIPLIER;
        this.maxScaleIsSetByMultiplier = true;
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public void setMinZoom(float f) {
        this.userSpecifiedMinScale = f;
        if (f != -1.0f) {
            this.minScale = this.userSpecifiedMinScale;
        } else if (this.mScaleType == ScaleType.CENTER || this.mScaleType == ScaleType.CENTER_CROP) {
            Drawable drawable = getDrawable();
            int drawableWidth = getDrawableWidth(drawable);
            int drawableHeight = getDrawableHeight(drawable);
            if (drawable != null && drawableWidth > 0 && drawableHeight > 0) {
                float f2 = ((float) this.viewWidth) / ((float) drawableWidth);
                float f3 = ((float) this.viewHeight) / ((float) drawableHeight);
                if (this.mScaleType == ScaleType.CENTER) {
                    this.minScale = Math.min(f2, f3);
                } else {
                    this.minScale = Math.min(f2, f3) / Math.max(f2, f3);
                }
            }
        } else {
            this.minScale = 1.0f;
        }
        if (this.maxScaleIsSetByMultiplier) {
            setMaxZoomRatio(this.maxScaleMultiplier);
        }
        this.superMinScale = this.minScale * SUPER_MIN_MULTIPLIER;
    }

    public void resetZoom() {
        this.normalizedScale = 1.0f;
        fitImageToView();
    }

    public void setZoom(float f, float f2, float f3) {
        setZoom(f, f2, f3, this.mScaleType);
    }

    public void setZoom(float f, float f2, float f3, ScaleType scaleType) {
        if (!this.onDrawReady) {
            this.delayedZoomVariables = new ZoomVariables(f, f2, f3, scaleType);
            return;
        }
        if (this.userSpecifiedMinScale == -1.0f) {
            setMinZoom(-1.0f);
            float f4 = this.normalizedScale;
            float f5 = this.minScale;
            if (f4 < f5) {
                this.normalizedScale = f5;
            }
        }
        if (scaleType != this.mScaleType) {
            setScaleType(scaleType);
        }
        resetZoom();
        scaleImage((double) f, (float) (this.viewWidth / 2), (float) (this.viewHeight / 2), true);
        this.matrix.getValues(this.m);
        this.m[2] = -((f2 * getImageWidth()) - (((float) this.viewWidth) * 0.5f));
        this.m[5] = -((f3 * getImageHeight()) - (((float) this.viewHeight) * 0.5f));
        this.matrix.setValues(this.m);
        fixTrans();
        savePreviousImageValues();
        setImageMatrix(this.matrix);
    }

    public void setZoom(TouchImageView touchImageView) {
        PointF scrollPosition = touchImageView.getScrollPosition();
        setZoom(touchImageView.getCurrentZoom(), scrollPosition.x, scrollPosition.y, touchImageView.getScaleType());
    }

    public PointF getScrollPosition() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        int drawableWidth = getDrawableWidth(drawable);
        int drawableHeight = getDrawableHeight(drawable);
        PointF transformCoordTouchToBitmap = transformCoordTouchToBitmap((float) (this.viewWidth / 2), (float) (this.viewHeight / 2), true);
        transformCoordTouchToBitmap.x /= (float) drawableWidth;
        transformCoordTouchToBitmap.y /= (float) drawableHeight;
        return transformCoordTouchToBitmap;
    }


    public boolean orientationMismatch(Drawable drawable) {
        return (this.viewWidth > this.viewHeight) != (drawable.getIntrinsicWidth() > drawable.getIntrinsicHeight());
    }

     int getDrawableWidth(Drawable drawable) {
        if (!orientationMismatch(drawable) || !this.isRotateImageToFitScreen) {
            return drawable.getIntrinsicWidth();
        }
        return drawable.getIntrinsicHeight();
    }

     int getDrawableHeight(Drawable drawable) {
        if (!orientationMismatch(drawable) || !this.isRotateImageToFitScreen) {
            return drawable.getIntrinsicHeight();
        }
        return drawable.getIntrinsicWidth();
    }


    public void fixTrans() {
        this.matrix.getValues(this.m);
        float[] fArr = this.m;
        this.matrix.postTranslate(getFixTrans(fArr[2], (float) this.viewWidth, getImageWidth(), (!this.isRotateImageToFitScreen || !orientationMismatch(getDrawable())) ? 0.0f : getImageWidth()), getFixTrans(fArr[5], (float) this.viewHeight, getImageHeight(), 0.0f));
    }


    public void fixScaleTrans() {
        fixTrans();
        this.matrix.getValues(this.m);
        float imageWidth = getImageWidth();
        int i = this.viewWidth;
        if (imageWidth < ((float) i)) {
            float imageWidth2 = (((float) i) - getImageWidth()) / 2.0f;
            if (this.isRotateImageToFitScreen && orientationMismatch(getDrawable())) {
                imageWidth2 += getImageWidth();
            }
            this.m[2] = imageWidth2;
        }
        float imageHeight = getImageHeight();
        int i2 = this.viewHeight;
        if (imageHeight < ((float) i2)) {
            this.m[5] = (((float) i2) - getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.m);
    }


    public float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }


    public float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }


    @Override
    public void onMeasure(int i, int i2) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int drawableWidth = getDrawableWidth(drawable);
        int drawableHeight = getDrawableHeight(drawable);
        int size = MeasureSpec.getSize(i);
        int mode = MeasureSpec.getMode(i);
        int size2 = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i2);
        int viewSize = setViewSize(mode, size, drawableWidth);
        int viewSize2 = setViewSize(mode2, size2, drawableHeight);
        if (!this.orientationJustChanged) {
            savePreviousImageValues();
        }
        setMeasuredDimension((viewSize - getPaddingLeft()) - getPaddingRight(), (viewSize2 - getPaddingTop()) - getPaddingBottom());
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.viewWidth = i;
        this.viewHeight = i2;
        fitImageToView();
    }

     void fitImageToView() {
        FixedPixel fixedPixel = this.orientationJustChanged ? this.orientationChangeFixedPixel : this.viewSizeChangeFixedPixel;
        this.orientationJustChanged = false;
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0 && this.matrix != null && this.prevMatrix != null) {
            if (this.userSpecifiedMinScale == -1.0f) {
                setMinZoom(-1.0f);
                float f = this.normalizedScale;
                float f2 = this.minScale;
                if (f < f2) {
                    this.normalizedScale = f2;
                }
            }
            int drawableWidth = getDrawableWidth(drawable);
            int drawableHeight = getDrawableHeight(drawable);
            float f3 = (float) drawableWidth;
            float f4 = ((float) this.viewWidth) / f3;
            float f5 = (float) drawableHeight;
            float f6 = ((float) this.viewHeight) / f5;
            switch (AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                case 1:
                    f4 = 1.0f;
                    f6 = 1.0f;
                    break;
                case 2:
                    f4 = Math.max(f4, f6);
                    f6 = f4;
                    break;
                case 3:
                    f4 = Math.min(1.0f, Math.min(f4, f6));
                    f6 = f4;
                    break;
                case 4:
                case 5:
                case 6:
                    break;
            }
            f4 = Math.min(f4, f6);
            f6 = f4;
            int i = this.viewWidth;
            float f7 = ((float) i) - (f4 * f3);
            int i2 = this.viewHeight;
            float f8 = ((float) i2) - (f6 * f5);
            this.matchViewWidth = ((float) i) - f7;
            this.matchViewHeight = ((float) i2) - f8;
            if (isZoomed() || this.imageRenderedAtLeastOnce) {
                if (this.prevMatchViewWidth == 0.0f || this.prevMatchViewHeight == 0.0f) {
                    savePreviousImageValues();
                }
                this.prevMatrix.getValues(this.m);
                float[] fArr = this.m;
                float f9 = this.matchViewWidth / f3;
                float f10 = this.normalizedScale;
                fArr[0] = f9 * f10;
                fArr[4] = (this.matchViewHeight / f5) * f10;
                float f11 = fArr[2];
                float f12 = fArr[5];
                FixedPixel fixedPixel2 = fixedPixel;
                this.m[2] = newTranslationAfterChange(f11, f10 * this.prevMatchViewWidth, getImageWidth(), this.prevViewWidth, this.viewWidth, drawableWidth, fixedPixel2);
                this.m[5] = newTranslationAfterChange(f12, this.prevMatchViewHeight * this.normalizedScale, getImageHeight(), this.prevViewHeight, this.viewHeight, drawableHeight, fixedPixel2);
                this.matrix.setValues(this.m);
            } else {
                if (!this.isRotateImageToFitScreen || !orientationMismatch(drawable)) {
                    this.matrix.setScale(f4, f6);
                } else {
                    this.matrix.setRotate(90.0f);
                    this.matrix.postTranslate(f3, 0.0f);
                    this.matrix.postScale(f4, f6);
                }
                switch (AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                    case 5:
                        this.matrix.postTranslate(0.0f, 0.0f);
                        break;
                    case 6:
                        this.matrix.postTranslate(f7, f8);
                        break;
                    default:
                        this.matrix.postTranslate(f7 / 2.0f, f8 / 2.0f);
                        break;
                }
                this.normalizedScale = 1.0f;
            }
            fixTrans();
            setImageMatrix(this.matrix);
        }
    }


    static  class AnonymousClass1 {
        static final  int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ScaleType.values().length];

        static {
            $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER.ordinal()] = 1;
            $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 5;
            $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 6;
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 7;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

     int setViewSize(int i, int i2, int i3) {
        if (i != Integer.MIN_VALUE) {
            return i != 0 ? i2 : i3;
        }
        return Math.min(i3, i2);
    }

     float newTranslationAfterChange(float f, float f2, float f3, int i, int i2, int i3, FixedPixel fixedPixel) {
        float f4 = (float) i2;
        float f5 = 0.5f;
        if (f3 < f4) {
            return (f4 - (((float) i3) * this.m[0])) * 0.5f;
        }
        if (f > 0.0f) {
            return -((f3 - f4) * 0.5f);
        }
        if (fixedPixel == FixedPixel.BOTTOM_RIGHT) {
            f5 = 1.0f;
        } else if (fixedPixel == FixedPixel.TOP_LEFT) {
            f5 = 0.0f;
        }
        return -(((((-f) + (((float) i) * f5)) / f2) * f3) - (f4 * f5));
    }


    public void setState(State state2) {
        this.state = state2;
    }

    @Deprecated
    public boolean canScrollHorizontallyFroyo(int i) {
        return canScrollHorizontally(i);
    }

    @Override
    public boolean canScrollHorizontally(int i) {
        this.matrix.getValues(this.m);
        float f = this.m[2];
        if (getImageWidth() < ((float) this.viewWidth)) {
            return false;
        }
        if (f >= -1.0f && i < 0) {
            return false;
        }
        if (Math.abs(f) + ((float) this.viewWidth) + 1.0f < getImageWidth() || i <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canScrollVertically(int i) {
        this.matrix.getValues(this.m);
        float f = this.m[5];
        if (getImageHeight() < ((float) this.viewHeight)) {
            return false;
        }
        if (f >= -1.0f && i < 0) {
            return false;
        }
        if (Math.abs(f) + ((float) this.viewHeight) + 1.0f < getImageHeight() || i <= 0) {
            return true;
        }
        return false;
    }

     class GestureListener extends GestureDetector.SimpleOnGestureListener {
         GestureListener() {
        }

         GestureListener(TouchImageView touchImageView, AnonymousClass1 r2) {
            this();
        }

         @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (TouchImageView.this.doubleTapListener != null) {
                return TouchImageView.this.doubleTapListener.onSingleTapConfirmed(motionEvent);
            }
            return TouchImageView.this.performClick();
        }

         @Override
        public void onLongPress(MotionEvent motionEvent) {
            TouchImageView.this.performLongClick();
        }

         @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (TouchImageView.this.fling != null) {
                TouchImageView.this.fling.cancelFling();
            }
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.fling = new Fling((int) f, (int) f2);
            TouchImageView touchImageView2 = TouchImageView.this;
            touchImageView2.compatPostOnAnimation(touchImageView2.fling);
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

         @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            boolean z = false;
            if (!TouchImageView.this.isZoomEnabled()) {
                return false;
            }
            if (TouchImageView.this.doubleTapListener != null) {
                z = TouchImageView.this.doubleTapListener.onDoubleTap(motionEvent);
            }
            if (TouchImageView.this.state != State.NONE) {
                return z;
            }
            TouchImageView.this.compatPostOnAnimation(new DoubleTapZoom(TouchImageView.this.normalizedScale == TouchImageView.this.minScale ? TouchImageView.this.maxScale : TouchImageView.this.minScale, motionEvent.getX(), motionEvent.getY(), false));
            return true;
        }

         @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            if (TouchImageView.this.doubleTapListener != null) {
                return TouchImageView.this.doubleTapListener.onDoubleTapEvent(motionEvent);
            }
            return false;
        }
    }

     class PrivateOnTouchListener implements OnTouchListener {
         PointF last;

         PrivateOnTouchListener() {
            this.last = new PointF();
        }

         PrivateOnTouchListener(TouchImageView touchImageView, AnonymousClass1 r2) {
            this();
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (TouchImageView.this.getDrawable() == null) {
                TouchImageView.this.setState(State.NONE);
                return false;
            }
            TouchImageView.this.mScaleDetector.onTouchEvent(motionEvent);
            TouchImageView.this.mGestureDetector.onTouchEvent(motionEvent);
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            if (TouchImageView.this.state == State.NONE || TouchImageView.this.state == State.DRAG || TouchImageView.this.state == State.FLING) {
                int action = motionEvent.getAction();
                if (action != 6) {
                    switch (action) {
                        case 0:
                            this.last.set(pointF);
                            if (TouchImageView.this.fling != null) {
                                TouchImageView.this.fling.cancelFling();
                            }
                            TouchImageView.this.setState(State.DRAG);
                            break;
                        case 1:
                            break;
                        case 2:
                            if (TouchImageView.this.state == State.DRAG) {
                                float f = pointF.x - this.last.x;
                                float f2 = pointF.y - this.last.y;
                                TouchImageView touchImageView = TouchImageView.this;
                                float fixDragTrans = touchImageView.getFixDragTrans(f, (float) touchImageView.viewWidth, TouchImageView.this.getImageWidth());
                                TouchImageView touchImageView2 = TouchImageView.this;
                                TouchImageView.this.matrix.postTranslate(fixDragTrans, touchImageView2.getFixDragTrans(f2, (float) touchImageView2.viewHeight, TouchImageView.this.getImageHeight()));
                                TouchImageView.this.fixTrans();
                                this.last.set(pointF.x, pointF.y);
                                break;
                            }
                            break;
                    }
                }
                TouchImageView.this.setState(State.NONE);
            }
            TouchImageView touchImageView3 = TouchImageView.this;
            touchImageView3.setImageMatrix(touchImageView3.matrix);
            if (TouchImageView.this.userTouchListener != null) {
                TouchImageView.this.userTouchListener.onTouch(view, motionEvent);
            }
            if (TouchImageView.this.touchImageViewListener == null) {
                return true;
            }
            TouchImageView.this.touchImageViewListener.onMove();
            return true;
        }
    }

     class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
         ScaleListener() {
        }

         ScaleListener(TouchImageView touchImageView, AnonymousClass1 r2) {
            this();
        }

         @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.setState(State.ZOOM);
            return true;
        }

         @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.scaleImage((double) scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            if (TouchImageView.this.touchImageViewListener == null) {
                return true;
            }
            TouchImageView.this.touchImageViewListener.onMove();
            return true;
        }

         @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            float f;
            super.onScaleEnd(scaleGestureDetector);
            TouchImageView.this.setState(State.NONE);
            float normalizedScale = TouchImageView.this.normalizedScale;
            boolean z = true;
            if (TouchImageView.this.normalizedScale > TouchImageView.this.maxScale) {
                f = TouchImageView.this.maxScale;
            } else if (TouchImageView.this.normalizedScale < TouchImageView.this.minScale) {
                f = TouchImageView.this.minScale;
            } else {
                z = false;
                f = normalizedScale;
            }
            if (z) {
                TouchImageView touchImageView = TouchImageView.this;
                TouchImageView.this.compatPostOnAnimation(new DoubleTapZoom(f, (float) (touchImageView.viewWidth / 2), (float) (TouchImageView.this.viewHeight / 2), true));
            }
        }
    }


    public void scaleImage(double d, float f, float f2, boolean z) {
        float f3;
        float f4;
        if (z) {
            f3 = this.superMinScale;
            f4 = this.superMaxScale;
        } else {
            f3 = this.minScale;
            f4 = this.maxScale;
        }
        float f5 = this.normalizedScale;
        double d2 = (double) f5;
        Double.isNaN(d2);
        this.normalizedScale = (float) (d2 * d);
        float f6 = this.normalizedScale;
        if (f6 > f4) {
            this.normalizedScale = f4;
            d = (double) (f4 / f5);
        } else if (f6 < f3) {
            this.normalizedScale = f3;
            d = (double) (f3 / f5);
        }
        float f7 = (float) d;
        this.matrix.postScale(f7, f7, f, f2);
        fixScaleTrans();
    }

     class DoubleTapZoom implements Runnable {
         static final float ZOOM_TIME = 500.0f;
         float bitmapX;
         float bitmapY;
         PointF endTouch;
         AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
         long startTime;
         PointF startTouch;
         float startZoom;
         boolean stretchImageToSuper;
         float targetZoom;

        DoubleTapZoom(float f, float f2, float f3, boolean z) {
            TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = TouchImageView.this.normalizedScale;
            this.targetZoom = f;
            this.stretchImageToSuper = z;
            PointF f1 = TouchImageView.this.transformCoordTouchToBitmap(f2, f3, false);
            this.bitmapX = f1.x;
            this.bitmapY = f1.y;
            this.startTouch = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            this.endTouch = new PointF((float) (TouchImageView.this.viewWidth / 2), (float) (TouchImageView.this.viewHeight / 2));
        }

        public void run() {
            if (TouchImageView.this.getDrawable() == null) {
                TouchImageView.this.setState(State.NONE);
                return;
            }
            float interpolate = interpolate();
            TouchImageView.this.scaleImage(calculateDeltaScale(interpolate), this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            translateImageToCenterTouchPosition(interpolate);
            TouchImageView.this.fixScaleTrans();
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.setImageMatrix(touchImageView.matrix);
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (interpolate < 1.0f) {
                TouchImageView.this.compatPostOnAnimation(this);
            } else {
                TouchImageView.this.setState(State.NONE);
            }
        }

         void translateImageToCenterTouchPosition(float f) {
            float f2 = this.startTouch.x + ((this.endTouch.x - this.startTouch.x) * f);
            float f3 = this.startTouch.y + (f * (this.endTouch.y - this.startTouch.y));
            PointF pointF = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            TouchImageView.this.matrix.postTranslate(f2 - pointF.x, f3 - pointF.y);
        }

         float interpolate() {
            return this.interpolator.getInterpolation(Math.min(1.0f, ((float) (System.currentTimeMillis() - this.startTime)) / ZOOM_TIME));
        }

         double calculateDeltaScale(float f) {
            float f2 = this.startZoom;
            double d = (double) (f2 + (f * (this.targetZoom - f2)));
            double normalizedScale = (double) TouchImageView.this.normalizedScale;
            Double.isNaN(d);
            Double.isNaN(normalizedScale);
            return d / normalizedScale;
        }
    }


    public PointF transformCoordTouchToBitmap(float f, float f2, boolean z) {
        this.matrix.getValues(this.m);
        float intrinsicWidth = (float) getDrawable().getIntrinsicWidth();
        float intrinsicHeight = (float) getDrawable().getIntrinsicHeight();
        float[] fArr = this.m;
        float f3 = fArr[2];
        float f4 = fArr[5];
        float imageWidth = ((f - f3) * intrinsicWidth) / getImageWidth();
        float imageHeight = ((f2 - f4) * intrinsicHeight) / getImageHeight();
        if (z) {
            imageWidth = Math.min(Math.max(imageWidth, 0.0f), intrinsicWidth);
            imageHeight = Math.min(Math.max(imageHeight, 0.0f), intrinsicHeight);
        }
        return new PointF(imageWidth, imageHeight);
    }


    public PointF transformCoordBitmapToTouch(float f, float f2) {
        this.matrix.getValues(this.m);
        return new PointF(this.m[2] + (getImageWidth() * (f / ((float) getDrawable().getIntrinsicWidth()))), this.m[5] + (getImageHeight() * (f2 / ((float) getDrawable().getIntrinsicHeight()))));
    }

     class Fling implements Runnable {
        int currX;
        int currY;
        CompatScroller scroller;

        Fling(int i, int i2) {
            int i3;
            int i4;
            int i5;
            int i6;
            TouchImageView.this.setState(State.FLING);
            this.scroller = new CompatScroller(TouchImageView.this.getContext());
            TouchImageView.this.matrix.getValues(TouchImageView.this.m);
            int i7 = (int) TouchImageView.this.m[2];
            int i8 = (int) TouchImageView.this.m[5];
            if (TouchImageView.this.isRotateImageToFitScreen && TouchImageView.this.orientationMismatch(TouchImageView.this.getDrawable())) {
                i7 = (int) (((float) i7) - TouchImageView.this.getImageWidth());
            }
            if (TouchImageView.this.getImageWidth() > ((float) TouchImageView.this.viewWidth)) {
                i4 = TouchImageView.this.viewWidth - ((int) TouchImageView.this.getImageWidth());
                i3 = 0;
            } else {
                i4 = i7;
                i3 = i4;
            }
            if (TouchImageView.this.getImageHeight() > ((float) TouchImageView.this.viewHeight)) {
                i6 = TouchImageView.this.viewHeight - ((int) TouchImageView.this.getImageHeight());
                i5 = 0;
            } else {
                i6 = i8;
                i5 = i6;
            }
            this.scroller.fling(i7, i8, i, i2, i4, i3, i6, i5);
            this.currX = i7;
            this.currY = i8;
        }

        public void cancelFling() {
            if (this.scroller != null) {
                TouchImageView.this.setState(State.NONE);
                this.scroller.forceFinished(true);
            }
        }

        public void run() {
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (this.scroller.isFinished()) {
                this.scroller = null;
            } else if (this.scroller.computeScrollOffset()) {
                int currX2 = this.scroller.getCurrX();
                int currY2 = this.scroller.getCurrY();
                int i = currX2 - this.currX;
                int i2 = currY2 - this.currY;
                this.currX = currX2;
                this.currY = currY2;
                TouchImageView.this.matrix.postTranslate((float) i, (float) i2);
                TouchImageView.this.fixTrans();
                TouchImageView touchImageView = TouchImageView.this;
                touchImageView.setImageMatrix(touchImageView.matrix);
                TouchImageView.this.compatPostOnAnimation(this);
            }
        }
    }

    @TargetApi(9)
     class CompatScroller {
        OverScroller overScroller;

        CompatScroller(Context context) {
            this.overScroller = new OverScroller(context);
        }


        public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.overScroller.fling(i, i2, i3, i4, i5, i6, i7, i8);
        }


        public void forceFinished(boolean z) {
            this.overScroller.forceFinished(z);
        }

        public boolean isFinished() {
            return this.overScroller.isFinished();
        }


        public boolean computeScrollOffset() {
            this.overScroller.computeScrollOffset();
            return this.overScroller.computeScrollOffset();
        }


        public int getCurrX() {
            return this.overScroller.getCurrX();
        }


        public int getCurrY() {
            return this.overScroller.getCurrY();
        }
    }


    @TargetApi(16)
    public void compatPostOnAnimation(Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 16);
        }
    }

     class ZoomVariables {
        float focusX;
        float focusY;
        float scale;
        ScaleType scaleType;

        ZoomVariables(float f, float f2, float f3, ScaleType scaleType2) {
            this.scale = f;
            this.focusX = f2;
            this.focusY = f3;
            this.scaleType = scaleType2;
        }
    }

     class AnimatedZoom implements Runnable {
         LinearInterpolator interpolator = new LinearInterpolator();
         OnZoomFinishedListener listener;
         PointF startFocus;
         long startTime;
         float startZoom;
         PointF targetFocus;
         float targetZoom;
         final int zoomTime;

        AnimatedZoom(float f, PointF pointF, int i) {
            TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = TouchImageView.this.normalizedScale;
            this.targetZoom = f;
            this.zoomTime = i;
            this.startFocus = TouchImageView.this.getScrollPosition();
            this.targetFocus = pointF;
        }

        public void run() {
            float interpolate = interpolate();
            float f = this.startZoom;
            TouchImageView.this.setZoom(f + ((this.targetZoom - f) * interpolate), this.startFocus.x + ((this.targetFocus.x - this.startFocus.x) * interpolate), this.startFocus.y + ((this.targetFocus.y - this.startFocus.y) * interpolate));
            if (interpolate < 1.0f) {
                TouchImageView.this.compatPostOnAnimation(this);
                return;
            }
            TouchImageView.this.setState(State.NONE);
            OnZoomFinishedListener onZoomFinishedListener = this.listener;
            if (onZoomFinishedListener != null) {
                onZoomFinishedListener.onZoomFinished();
            }
        }

         float interpolate() {
            return this.interpolator.getInterpolation(Math.min(1.0f, (float) ((System.currentTimeMillis() - this.startTime) / ((long) this.zoomTime))));
        }
    }
}

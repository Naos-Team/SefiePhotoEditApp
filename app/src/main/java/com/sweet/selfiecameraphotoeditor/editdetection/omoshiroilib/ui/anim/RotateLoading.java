package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sweet.selfiecameraphotoeditor.R;

public class RotateLoading extends View {
     static final int DEFAULT_SHADOW_POSITION = 2;
     static final int DEFAULT_SPEED_OF_DEGREE = 10;
     static final int DEFAULT_WIDTH = 6;
     float arc;
     int bottomDegree = 190;
     boolean changeBigger = true;
     int color;

    public boolean isStart = false;
     RectF loadingRectF;
     Paint mPaint;
     int shadowPosition;
     RectF shadowRectF;
     float speedOfArc;
     int speedOfDegree;
     int topDegree = 10;
     int width;

    public RotateLoading(Context context) {
        super(context);
        initView(context, (AttributeSet) null);
    }

    public RotateLoading(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context, attributeSet);
    }

    public RotateLoading(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

     void initView(Context context, AttributeSet attributeSet) {
        this.color = -1;
        this.width = dpToPx(context, 6.0f);
        this.shadowPosition = dpToPx(getContext(), 2.0f);
        this.speedOfDegree = 10;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RotateLoading);
            this.color = obtainStyledAttributes.getColor(R.styleable.RotateLoading_loading_color, -1);
            this.width = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RotateLoading_loading_width, dpToPx(context, 6.0f));
            this.shadowPosition = obtainStyledAttributes.getInt(R.styleable.RotateLoading_shadow_position, 2);
            this.speedOfDegree = obtainStyledAttributes.getInt(R.styleable.RotateLoading_loading_speed, 10);
            obtainStyledAttributes.recycle();
        }
        this.speedOfArc = (float) (this.speedOfDegree / 4);
        this.mPaint = new Paint();
        this.mPaint.setColor(this.color);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.width);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.arc = 10.0f;
        int i5 = this.width;
        this.loadingRectF = new RectF((float) (i5 * 2), (float) (i5 * 2), (float) (i - (i5 * 2)), (float) (i2 - (i5 * 2)));
        int i6 = this.width;
        int i7 = this.shadowPosition;
        this.shadowRectF = new RectF((float) ((i6 * 2) + i7), (float) ((i6 * 2) + i7), (float) ((i - (i6 * 2)) + i7), (float) ((i2 - (i6 * 2)) + i7));
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isStart) {
            this.mPaint.setColor(Color.parseColor("#1a000000"));
            Canvas canvas2 = canvas;
            canvas2.drawArc(this.shadowRectF, (float) this.topDegree, this.arc, false, this.mPaint);
            Canvas canvas3 = canvas;
            canvas3.drawArc(this.shadowRectF, (float) this.bottomDegree, this.arc, false, this.mPaint);
            this.mPaint.setColor(this.color);
            canvas2.drawArc(this.loadingRectF, (float) this.topDegree, this.arc, false, this.mPaint);
            canvas3.drawArc(this.loadingRectF, (float) this.bottomDegree, this.arc, false, this.mPaint);
            int i = this.topDegree;
            int i2 = this.speedOfDegree;
            this.topDegree = i + i2;
            this.bottomDegree += i2;
            int i3 = this.topDegree;
            if (i3 > 360) {
                this.topDegree = i3 - 360;
            }
            int i4 = this.bottomDegree;
            if (i4 > 360) {
                this.bottomDegree = i4 - 360;
            }
            if (this.changeBigger) {
                float f = this.arc;
                if (f < 160.0f) {
                    this.arc = f + this.speedOfArc;
                    invalidate();
                }
            } else {
                float f2 = this.arc;
                if (f2 > ((float) this.speedOfDegree)) {
                    this.arc = f2 - (this.speedOfArc * 2.0f);
                    invalidate();
                }
            }
            float f3 = this.arc;
            if (f3 >= 160.0f || f3 <= 10.0f) {
                this.changeBigger = !this.changeBigger;
                invalidate();
            }
        }
    }

    public void setLoadingColor(int i) {
        this.color = i;
    }

    public int getLoadingColor() {
        return this.color;
    }

    public void start() {
        startAnimator();
        this.isStart = true;
        invalidate();
    }

    public void stop() {
        stopAnimator();
        invalidate();
    }

    public boolean isStart() {
        return this.isStart;
    }

     void startAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "scaleX", new float[]{0.0f, 1.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "scaleY", new float[]{0.0f, 1.0f});
        ofFloat.setDuration(300);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(300);
        ofFloat2.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.start();
    }

     void stopAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.0f, 0.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.0f, 0.0f});
        ofFloat.setDuration(300);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(300);
        ofFloat2.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
                Log.d("","");
            }

            public void onAnimationRepeat(Animator animator) {
                Log.d("","");
            }

            public void onAnimationStart(Animator animator) {
                Log.d("","");
            }

            public void onAnimationEnd(Animator animator) {
                boolean unused = RotateLoading.this.isStart = false;
            }
        });
        animatorSet.start();
    }

    public int dpToPx(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }
}

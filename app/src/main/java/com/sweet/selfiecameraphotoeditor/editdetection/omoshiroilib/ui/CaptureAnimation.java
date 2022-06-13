package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BitmapUtils;

public class CaptureAnimation extends View {
    private long mAnimStartTime;
    private Bitmap mFlashBitmap;
    Paint mPaint = new Paint();
    private RectF mPortClipRect;
    RectF mRectF = new RectF();
    private int mScreenHeight;
    private int mScreenWidth;

    public CaptureAnimation(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initDisplayMetrics();
        this.mFlashBitmap = BitmapUtils.loadBitmapFromAssets(context, "ui/capture/capture_animation.png");
    }

    private void initDisplayMetrics() {
        this.mScreenWidth = 1080;
        this.mScreenHeight = 1920;
        this.mPortClipRect = new RectF(0.0f, 0.0f, (float) this.mScreenWidth, (float) this.mScreenHeight);
    }

    public CaptureAnimation resetAnimationSize(int i, int i2) {
        this.mScreenWidth = i;
        this.mScreenHeight = i2;
        this.mPortClipRect = new RectF(0.0f, 0.0f, (float) this.mScreenWidth, (float) this.mScreenHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) this.mFlashBitmap.getWidth()), ((float) i2) / ((float) this.mFlashBitmap.getHeight()));
        Bitmap bitmap = this.mFlashBitmap;
        this.mFlashBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.mFlashBitmap.getHeight(), matrix, true);
        return this;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mAnimStartTime;
        if (elapsedRealtime <= 400) {
            canvas.save();
            if (elapsedRealtime < 200) {
                canvas.drawBitmap(this.mFlashBitmap, 0.0f, 0.0f, (Paint) null);
            } else {
                this.mPaint.setColor(Color.argb(204, 255, 255, 255));
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setStrokeWidth(40.0f);
                this.mRectF.inset(-1.5f, -1.5f);
                canvas.clipRect(this.mPortClipRect);
            }
            canvas.drawRoundRect(this.mRectF, 50.0f, 50.0f, this.mPaint);
            canvas.restore();
            invalidate();
        }
    }

    public void startAnimation() {
        this.mAnimStartTime = SystemClock.elapsedRealtime();
        this.mRectF.set(this.mPortClipRect);
        invalidate();
    }
}

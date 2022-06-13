package com.sweet.selfiecameraphotoeditor.collageutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TransitionImageView extends ImageView {
     final GestureDetector mGestureDetector;
     Bitmap mImage;
     Matrix mImageMatrix;

    public OnImageClickListener mOnImageClickListener;
     Paint mPaint;
     float mScale = 1.0f;
     Matrix mScaleMatrix;
     MultiTouchHandler mTouchHandler;
     int mViewHeight;
     int mViewWidth;

    public interface OnImageClickListener {
        void onDoubleClickImage(TransitionImageView transitionImageView);

        void onLongClickImage(TransitionImageView transitionImageView);
    }

    public TransitionImageView(Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        this.mImageMatrix = new Matrix();
        this.mScaleMatrix = new Matrix();
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent motionEvent) {
                if (TransitionImageView.this.mOnImageClickListener != null) {
                    TransitionImageView.this.mOnImageClickListener.onLongClickImage(TransitionImageView.this);
                }
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (TransitionImageView.this.mOnImageClickListener == null) {
                    return true;
                }
                TransitionImageView.this.mOnImageClickListener.onDoubleClickImage(TransitionImageView.this);
                return true;
            }
        });
    }

    public void reset() {
        this.mTouchHandler = null;
        setScaleType(ScaleType.MATRIX);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.mOnImageClickListener = onImageClickListener;
    }

    public void recycleImages() {
        Bitmap bitmap = this.mImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mImage.recycle();
            this.mImage = null;
            System.gc();
            invalidate();
        }
    }

    public void init(Bitmap bitmap, int i, int i2, float f) {
        this.mImage = bitmap;
        this.mViewWidth = i;
        this.mViewHeight = i2;
        this.mScale = f;
        Bitmap bitmap2 = this.mImage;
        if (bitmap2 != null) {
            float f2 = (float) i;
            float f3 = (float) i2;
            this.mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f2, f3, (float) bitmap2.getWidth(), (float) this.mImage.getHeight()));
            this.mScaleMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f2 * f, f3 * f, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        }
        this.mTouchHandler = new MultiTouchHandler();
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        this.mTouchHandler.setScale(f);
        this.mTouchHandler.setEnableRotation(false);
        this.mTouchHandler.setEnableZoom(false);
        float f4 = (float) i;
        float width = f4 / ((float) this.mImage.getWidth());
        float f5 = (float) i2;
        float height = f5 / ((float) this.mImage.getHeight());
        if (width > height) {
            this.mTouchHandler.setEnableTranslateX(false);
            this.mTouchHandler.setMaxPositionOffset(((width * ((float) this.mImage.getHeight())) - f5) / 2.0f);
        } else {
            this.mTouchHandler.setEnableTranslateY(false);
            this.mTouchHandler.setMaxPositionOffset(((height * ((float) this.mImage.getWidth())) - f4) / 2.0f);
        }
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.mImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(this.mImage, this.mImageMatrix, this.mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Bitmap bitmap;
        this.mGestureDetector.onTouchEvent(motionEvent);
        if (this.mTouchHandler == null || (bitmap = this.mImage) == null || bitmap.isRecycled()) {
            return true;
        }
        this.mTouchHandler.touch(motionEvent);
        this.mImageMatrix.set(this.mTouchHandler.getMatrix());
        this.mScaleMatrix.set(this.mTouchHandler.getScaleMatrix());
        invalidate();
        return true;
    }

    @Override
    public Matrix getImageMatrix() {
        return this.mImageMatrix;
    }

    public Matrix getScaleMatrix() {
        return this.mScaleMatrix;
    }

    public Bitmap getImage() {
        return this.mImage;
    }
}

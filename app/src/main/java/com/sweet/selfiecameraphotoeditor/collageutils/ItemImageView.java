package com.sweet.selfiecameraphotoeditor.collageutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ItemImageView extends ImageView {
     static final String TAG = "ItemImageView";
     boolean mEnableTouch = true;
     final GestureDetector mGestureDetector;
     Bitmap mImage;
     Matrix mImageMatrix;
     Bitmap mMaskImage;
     Matrix mMaskMatrix;

    public OnImageClickListener mOnImageClickListener;
     RelativeLayout.LayoutParams mOriginalLayoutParams;
     float mOutputScale = 1.0f;
     Paint mPaint;
     PhotoItem mPhotoItem;
     Matrix mScaleMaskMatrix;
     Matrix mScaleMatrix;
     MultiTouchHandler mTouchHandler;
     float mViewHeight;
     float mViewWidth;

    public interface OnImageClickListener {
        void onDoubleClickImage(ItemImageView itemImageView);

        void onLongClickImage(ItemImageView itemImageView);
    }

    public ItemImageView(Context context, PhotoItem photoItem) {
        super(context);
        this.mPhotoItem = photoItem;
        if (photoItem.imagePath != null && photoItem.imagePath.length() > 0) {
            this.mImage = ResultContainer.getInstance().getImage(photoItem.imagePath);
            Bitmap bitmap = this.mImage;
            if (bitmap == null || bitmap.isRecycled()) {
                this.mImage = ImageDecoder.decodeFileToBitmap(photoItem.imagePath);
                ResultContainer.getInstance().putImage(photoItem.imagePath, this.mImage);
                Log.d(TAG, "create ItemImageView, decode image");
            } else {
                Log.d(TAG, "create ItemImageView, use decoded image");
            }
        }
        if (photoItem.maskPath != null && photoItem.maskPath.length() > 0) {
            this.mMaskImage = ResultContainer.getInstance().getImage(photoItem.maskPath);
            Bitmap bitmap2 = this.mMaskImage;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                this.mMaskImage = PhotoUtils.decodePNGImage(context, photoItem.maskPath);
                ResultContainer.getInstance().putImage(photoItem.maskPath, this.mMaskImage);
                Log.d(TAG, "create ItemImageView, decode mask image");
            } else {
                Log.d(TAG, "create ItemImageView, use decoded mask image");
            }
        }
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        setScaleType(ScaleType.MATRIX);
        setLayerType(2, this.mPaint);
        this.mImageMatrix = new Matrix();
        this.mScaleMatrix = new Matrix();
        this.mMaskMatrix = new Matrix();
        this.mScaleMaskMatrix = new Matrix();
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
          
            @Override
            public void onLongPress(MotionEvent motionEvent) {
                if (ItemImageView.this.mOnImageClickListener != null) {
                    ItemImageView.this.mOnImageClickListener.onLongClickImage(ItemImageView.this);
                }
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (ItemImageView.this.mOnImageClickListener == null) {
                    return true;
                }
                ItemImageView.this.mOnImageClickListener.onDoubleClickImage(ItemImageView.this);
                return true;
            }
        });
    }

    public void swapImage(ItemImageView itemImageView) {
        Bitmap image = itemImageView.getImage();
        itemImageView.setImage(this.mImage);
        this.mImage = image;
        String str = itemImageView.getPhotoItem().imagePath;
        itemImageView.getPhotoItem().imagePath = this.mPhotoItem.imagePath;
        this.mPhotoItem.imagePath = str;
        resetImageMatrix();
        itemImageView.resetImageMatrix();
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.mOnImageClickListener = onImageClickListener;
    }

    public void setOriginalLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        this.mOriginalLayoutParams = new RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height);
        this.mOriginalLayoutParams.leftMargin = layoutParams.leftMargin;
        this.mOriginalLayoutParams.topMargin = layoutParams.topMargin;
    }

    public void setImage(Bitmap bitmap) {
        this.mImage = bitmap;
    }

    public PhotoItem getPhotoItem() {
        return this.mPhotoItem;
    }

    public Bitmap getImage() {
        return this.mImage;
    }

    public Bitmap getMaskImage() {
        return this.mMaskImage;
    }

    @Override
    public Matrix getImageMatrix() {
        return this.mImageMatrix;
    }

    public void init(float f, float f2, float f3) {
        this.mViewWidth = f;
        this.mViewHeight = f2;
        this.mOutputScale = f3;
        Bitmap bitmap = this.mImage;
        if (bitmap != null) {
            this.mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f, f2, (float) bitmap.getWidth(), (float) this.mImage.getHeight()));
            this.mScaleMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f3 * f, f3 * f2, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        }
        Bitmap bitmap2 = this.mMaskImage;
        if (bitmap2 != null) {
            this.mMaskMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f, f2, (float) bitmap2.getWidth(), (float) this.mMaskImage.getHeight()));
            this.mScaleMaskMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f * f3, f2 * f3, (float) this.mMaskImage.getWidth(), (float) this.mMaskImage.getHeight()));
        }
        this.mTouchHandler = new MultiTouchHandler();
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        this.mTouchHandler.setScale(f3);
        this.mTouchHandler.setEnableRotation(true);
        invalidate();
    }

    public void resetImageMatrix() {
        this.mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mViewWidth, this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        Matrix matrix = this.mScaleMatrix;
        float f = this.mOutputScale;
        matrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mViewWidth * f, f * this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        invalidate();
    }

    public void clearMainImage() {
        this.mPhotoItem.imagePath = null;
        recycleMainImage();
        invalidate();
    }

     void recycleMainImage() {
        Bitmap bitmap = this.mImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mImage.recycle();
            this.mImage = null;
            System.gc();
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        Bitmap bitmap;
        super.onDraw(canvas);
        Bitmap bitmap2 = this.mImage;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mMaskImage) != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(this.mImage, this.mImageMatrix, this.mPaint);
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(this.mMaskImage, this.mMaskMatrix, this.mPaint);
            this.mPaint.setXfermode((Xfermode) null);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Bitmap bitmap;
        if (!this.mEnableTouch) {
            return super.onTouchEvent(motionEvent);
        }
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
}

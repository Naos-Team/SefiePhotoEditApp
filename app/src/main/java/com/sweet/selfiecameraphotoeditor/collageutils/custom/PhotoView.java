package com.sweet.selfiecameraphotoeditor.collageutils.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.InputDeviceCompat;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.collageutils.DoubleClickDetector;
import com.sweet.selfiecameraphotoeditor.collageutils.FrameTouch;
import com.sweet.selfiecameraphotoeditor.collageutils.ImageDecoder;
import com.sweet.selfiecameraphotoeditor.collageutils.ImageEntity;
import com.sweet.selfiecameraphotoeditor.collageutils.MultiTouchController;
import com.sweet.selfiecameraphotoeditor.collageutils.MultiTouchEntity;
import com.sweet.selfiecameraphotoeditor.collageutils.OnFrameTouchListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PhotoView extends View implements MultiTouchController.MultiTouchObjectCanvas<MultiTouchEntity> {
     static final long DOUBLE_CLICK_TIME_INTERVAL = 700;
     static final float SCREEN_MARGIN = 100.0f;
     static final int UI_MODE_ANISOTROPIC_SCALE = 2;
     static final int UI_MODE_ROTATE = 1;
     MultiTouchController.PointInfo currTouchPoint;
     int displayHeight;
     int displayWidth;
     int height;
     OnDoubleClickListener mClickListener;
     MultiTouchEntity mCurrentSelectedObject;
     DoubleClickDetector mDoubleClickDetector;
     OnFrameTouchListener mFrameTouchListener;
     ArrayList<MultiTouchEntity> mImages;
     Paint mLinePaintTouchPointCircle;
     float mOldX;
     float mOldY;
     Uri mPhotoBackgroundUri;
     int mSelectedCount;
     long mSelectedTime;
     boolean mShowDebugInfo;
     float mTouchAreaInterval;
     MultiTouchEntity mTouchedObject;
     int mUIMode;
     MultiTouchController<MultiTouchEntity> multiTouchController;
     int width;

    public boolean pointInObjectGrabArea(MultiTouchController.PointInfo pointInfo, MultiTouchEntity multiTouchEntity) {
        return false;
    }

    public PhotoView(Context context) {
        this(context, (AttributeSet) null);
        init(context);
    }

    public PhotoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        init(context);
    }

    public PhotoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mImages = new ArrayList<>();
        this.multiTouchController = new MultiTouchController<>(this);
        this.currTouchPoint = new MultiTouchController.PointInfo();
        this.mShowDebugInfo = false;
        this.mUIMode = 1;
        this.mLinePaintTouchPointCircle = new Paint();
        this.mCurrentSelectedObject = null;
        this.mSelectedCount = 0;
        this.mSelectedTime = System.currentTimeMillis();
        this.mClickListener = null;
        this.mPhotoBackgroundUri = null;
        this.mOldX = 0.0f;
        this.mOldY = 0.0f;
        this.mTouchAreaInterval = 10.0f;
        this.mTouchedObject = null;
        init(context);
    }

     void init(Context context) {
        Resources resources = context.getResources();
        this.mLinePaintTouchPointCircle.setColor(InputDeviceCompat.SOURCE_ANY);
        this.mLinePaintTouchPointCircle.setStrokeWidth(5.0f);
        this.mLinePaintTouchPointCircle.setStyle(Paint.Style.STROKE);
        this.mLinePaintTouchPointCircle.setAntiAlias(true);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.displayWidth = resources.getConfiguration().orientation == 2 ? Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels) : Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.displayHeight = resources.getConfiguration().orientation == 2 ? Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) : Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mTouchAreaInterval = resources.getDimension(R.dimen.touch_area_interval);
        this.mDoubleClickDetector = new DoubleClickDetector();
        this.mDoubleClickDetector.setTouchAreaInterval(this.mTouchAreaInterval);
    }

    public void loadImages(Context context) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mImages.get(i).load(context);
            }
            cleanImages();
        }
    }

     void cleanImages() {
        if (this.mImages != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<MultiTouchEntity> it2 = this.mImages.iterator();
            while (it2.hasNext()) {
                MultiTouchEntity next = it2.next();
                if (!((ImageEntity) next).isNull()) {
                    arrayList.add(next);
                }
            }
            this.mImages.clear();
            this.mImages.addAll(arrayList);
        }
    }

    public void addImageEntity(MultiTouchEntity multiTouchEntity) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            if (arrayList.size() > 0 && (this.mImages.get(0) instanceof ImageEntity) && (multiTouchEntity instanceof ImageEntity)) {
                ImageEntity imageEntity = (ImageEntity) this.mImages.get(0);
                ImageEntity imageEntity2 = (ImageEntity) multiTouchEntity;
                imageEntity2.setBorderColor(imageEntity.getBorderColor());
                imageEntity2.setDrawImageBorder(imageEntity.isDrawImageBorder());
            }
            this.mImages.add(multiTouchEntity);
            multiTouchEntity.load(getContext(), (float) ((getWidth() - multiTouchEntity.getWidth()) / 2), (float) ((getHeight() - multiTouchEntity.getHeight()) / 2));
            invalidate();
        }
    }

    public void clearAllImageEntities() {
        if (this.mImages != null) {
            unloadImages();
            this.mImages.clear();
            invalidate();
        }
    }

    public void removeImageEntity(MultiTouchEntity multiTouchEntity) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            arrayList.remove(multiTouchEntity);
            invalidate();
        }
    }

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.mClickListener = onDoubleClickListener;
    }

    public void setFrameTouchListener(OnFrameTouchListener onFrameTouchListener) {
        this.mFrameTouchListener = onFrameTouchListener;
    }

    public void setBorderColor(int i) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (this.mImages.get(i2) instanceof ImageEntity) {
                    ((ImageEntity) this.mImages.get(i2)).setBorderColor(i);
                }
            }
            invalidate();
        }
    }

    public void setBorderSize(float f) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (this.mImages.get(i) instanceof ImageEntity) {
                    ((ImageEntity) this.mImages.get(i)).setBorderSize(f);
                }
            }
            invalidate();
        }
    }

    public void setDrawImageBound(boolean z) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (this.mImages.get(i) instanceof ImageEntity) {
                    ((ImageEntity) this.mImages.get(i)).setDrawImageBorder(z);
                }
            }
            invalidate();
        }
    }

    public void setDrawShadow(boolean z) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (this.mImages.get(i) instanceof ImageEntity) {
                    ((ImageEntity) this.mImages.get(i)).setDrawShadow(z);
                }
            }
            invalidate();
        }
    }

    public void setShadowSize(int i) {
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (this.mImages.get(i2) instanceof ImageEntity) {
                    ((ImageEntity) this.mImages.get(i2)).setShadowSize(i);
                }
            }
            invalidate();
        }
    }

    public void setImageEntities(ArrayList<MultiTouchEntity> arrayList) {
        this.mImages = arrayList;
    }

    public ArrayList<MultiTouchEntity> getImageEntities() {
        return this.mImages;
    }

    public void setPhotoBackground(Uri uri) {
        destroyBackground();
        this.mPhotoBackgroundUri = uri;
        if (this.mPhotoBackgroundUri != null) {
            BitmapDrawable decodeUriToDrawable = ImageDecoder.decodeUriToDrawable(getContext(), uri);
            if (Build.VERSION.SDK_INT >= 16) {
                setBackground(decodeUriToDrawable);
            } else {
                setBackgroundDrawable(decodeUriToDrawable);
            }
        } else if (Build.VERSION.SDK_INT >= 16) {
            setBackground((Drawable) null);
        } else {
            setBackgroundDrawable((Drawable) null);
        }
    }

    public Uri getPhotoBackgroundUri() {
        return this.mPhotoBackgroundUri;
    }

    public void destroyBackground() {
        Bitmap bitmap;
        Drawable background = getBackground();
        if (background != null && (background instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) background).getBitmap()) != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground((Drawable) null);
        } else {
            setBackgroundDrawable((Drawable) null);
        }
        this.mPhotoBackgroundUri = null;
    }

    public void unloadImages() {
        int size = this.mImages.size();
        for (int i = 0; i < size; i++) {
            this.mImages.get(i).unload();
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ArrayList<MultiTouchEntity> arrayList = this.mImages;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mImages.get(i).draw(canvas);
            }
            if (this.mShowDebugInfo) {
                drawMultitouchDebugMarks(canvas);
            }
        }
    }

    public Bitmap getImage(float f) {
        Bitmap bitmap;
        if (this.mImages == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap((int) (((float) getWidth()) * f), (int) (((float) getHeight()) * f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = getBackground();
        if (!(background == null || !(background instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) background).getBitmap()) == null)) {
            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, createBitmap.getWidth(), createBitmap.getHeight()), new Paint(1));
        }
        int size = this.mImages.size();
        for (int i = 0; i < size; i++) {
            MultiTouchEntity multiTouchEntity = this.mImages.get(i);
            if (multiTouchEntity instanceof ImageEntity) {
                ((ImageEntity) multiTouchEntity).draw(canvas, f);
            } else {
                multiTouchEntity.draw(canvas);
            }
        }
        return createBitmap;
    }

    public void trackballClicked() {
        this.mUIMode = (this.mUIMode + 1) % 3;
        invalidate();
    }

     void drawMultitouchDebugMarks(Canvas canvas) {
        if (this.currTouchPoint.isDown()) {
            float[] xs = this.currTouchPoint.getXs();
            float[] ys = this.currTouchPoint.getYs();
            float[] pressures = this.currTouchPoint.getPressures();
            int min = Math.min(this.currTouchPoint.getNumTouchPoints(), 2);
            for (int i = 0; i < min; i++) {
                canvas.drawCircle(xs[i], ys[i], (pressures[i] * 80.0f) + 50.0f, this.mLinePaintTouchPointCircle);
            }
            if (min == 2) {
                canvas.drawLine(xs[0], ys[0], xs[1], ys[1], this.mLinePaintTouchPointCircle);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        OnDoubleClickListener onDoubleClickListener;
        OnFrameTouchListener onFrameTouchListener = this.mFrameTouchListener;
        boolean z2 = true;
        if (onFrameTouchListener == null || !(onFrameTouchListener instanceof FrameTouch) || !((FrameTouch) onFrameTouchListener).isImageFrameMoving()) {
            z = this.multiTouchController.onTouchEvent(motionEvent);
            OnFrameTouchListener onFrameTouchListener2 = this.mFrameTouchListener;
            if (onFrameTouchListener2 == null || this.mTouchedObject != null) {
                z2 = false;
            } else {
                onFrameTouchListener2.onFrameTouch(motionEvent);
            }
        } else {
            OnFrameTouchListener onFrameTouchListener3 = this.mFrameTouchListener;
            if (onFrameTouchListener3 == null || this.mTouchedObject != null) {
                z = this.multiTouchController.onTouchEvent(motionEvent);
                z2 = false;
            } else {
                onFrameTouchListener3.onFrameTouch(motionEvent);
                z = false;
            }
        }
        if (!z2 && this.mTouchedObject == null && this.mDoubleClickDetector.doubleClick(motionEvent) && (onDoubleClickListener = this.mClickListener) != null) {
            onDoubleClickListener.onBackgroundDoubleClick();
        }
        return z;
    }

    public MultiTouchEntity getDraggableObjectAtPoint(MultiTouchController.PointInfo pointInfo) {
        float x = pointInfo.getX();
        float y = pointInfo.getY();
        for (int size = this.mImages.size() - 1; size >= 0; size--) {
            ImageEntity imageEntity = (ImageEntity) this.mImages.get(size);
            if (imageEntity.contain(x, y)) {
                return imageEntity;
            }
        }
        return null;
    }

    public void selectObject(MultiTouchEntity multiTouchEntity, MultiTouchController.PointInfo pointInfo) {
        this.currTouchPoint.set(pointInfo);
        this.mTouchedObject = multiTouchEntity;
        if (multiTouchEntity != null) {
            this.mImages.remove(multiTouchEntity);
            this.mImages.add(multiTouchEntity);
            if (!pointInfo.isMultiTouch() && pointInfo.isDown()) {
                long currentTimeMillis = System.currentTimeMillis();
                if (this.mCurrentSelectedObject != multiTouchEntity) {
                    this.mCurrentSelectedObject = multiTouchEntity;
                    this.mSelectedCount = 1;
                    this.mOldX = pointInfo.getX();
                    this.mOldY = pointInfo.getY();
                } else {
                    if (currentTimeMillis - this.mSelectedTime < DOUBLE_CLICK_TIME_INTERVAL) {
                        float x = pointInfo.getX();
                        float y = pointInfo.getY();
                        float f = this.mOldX;
                        float f2 = this.mTouchAreaInterval;
                        if (f + f2 > x && f - f2 < x) {
                            float f3 = this.mOldY;
                            if (f3 + f2 > y && f3 - f2 < y) {
                                this.mSelectedCount++;
                            }
                        }
                        this.mOldX = x;
                        this.mOldY = y;
                    } else {
                        this.mOldX = pointInfo.getX();
                        this.mOldY = pointInfo.getY();
                    }
                    if (this.mSelectedCount == 2) {
                        OnDoubleClickListener onDoubleClickListener = this.mClickListener;
                        if (onDoubleClickListener != null) {
                            onDoubleClickListener.onPhotoViewDoubleClick(this, multiTouchEntity);
                        }
                        this.mCurrentSelectedObject = null;
                        this.mSelectedCount = 0;
                        this.mOldX = 0.0f;
                        this.mOldY = 0.0f;
                    }
                }
                this.mSelectedTime = currentTimeMillis;
            }
        }
        invalidate();
    }

    public void getPositionAndScale(MultiTouchEntity multiTouchEntity, MultiTouchController.PositionAndScale positionAndScale) {
        positionAndScale.set(multiTouchEntity.getCenterX(), multiTouchEntity.getCenterY(), (this.mUIMode & 2) == 0, (multiTouchEntity.getScaleX() + multiTouchEntity.getScaleY()) / 2.0f, (this.mUIMode & 2) != 0, multiTouchEntity.getScaleX(), multiTouchEntity.getScaleY(), (this.mUIMode & 1) != 0, multiTouchEntity.getAngle());
    }

    public boolean setPositionAndScale(MultiTouchEntity multiTouchEntity, MultiTouchController.PositionAndScale positionAndScale, MultiTouchController.PointInfo pointInfo) {
        this.currTouchPoint.set(pointInfo);
        boolean pos = ((ImageEntity) multiTouchEntity).setPos(positionAndScale);
        if (pos) {
            invalidate();
        }
        return pos;
    }
}

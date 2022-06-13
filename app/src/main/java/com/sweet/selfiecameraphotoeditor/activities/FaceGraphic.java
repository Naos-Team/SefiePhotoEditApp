package com.sweet.selfiecameraphotoeditor.activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;

import com.google.android.gms.vision.face.Face;

class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float BOX_STROKE_WIDTH = 5.0f;
    private static final int[] COLOR_CHOICES = {-16776961, -16711681, -16711936, -65281, SupportMenu.CATEGORY_MASK, -1, InputDeviceCompat.SOURCE_ANY};
    private static final float ID_TEXT_SIZE = 40.0f;
    private static int mCurrentColorIndex = 0;
    private Bitmap f118op = SweetCameraActivity.bitmap;
    private Paint mBoxPaint;
    private volatile Face mFace;
     int mFaceId;
    private Paint mFacePositionPaint;
    private Paint mIdPaint;

    FaceGraphic(GraphicOverlay graphicOverlay) {
        super(graphicOverlay);
        int[] iArr = COLOR_CHOICES;
        mCurrentColorIndex = (mCurrentColorIndex + 1) % iArr.length;
        int i = iArr[mCurrentColorIndex];
        this.mFacePositionPaint = new Paint();
        this.mFacePositionPaint.setColor(i);
        this.mIdPaint = new Paint();
        this.mIdPaint.setColor(i);
        this.mIdPaint.setTextSize(ID_TEXT_SIZE);
        this.mBoxPaint = new Paint();
        this.mBoxPaint.setColor(i);
        this.mBoxPaint.setStyle(Paint.Style.STROKE);
        this.mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    public void setId(int i) {
        this.mFaceId = i;
    }

    public void updateFace(Face face) {
        this.mFace = face;
        this.f118op = Bitmap.createScaledBitmap(SweetCameraActivity.bitmap, (int) scaleX(face.getWidth()), (int) scaleY((((float) SweetCameraActivity.bitmap.getHeight()) * face.getWidth()) / ((float) SweetCameraActivity.bitmap.getWidth())), false);
        postInvalidate();
    }

    public void draw(Canvas canvas) {
        Face face = this.mFace;
        if (face != null) {
            face.getEulerY();
            face.getEulerZ();
            float translateX = translateX(face.getPosition().x + (face.getWidth() / 2.0f));
            float translateY = translateY(face.getPosition().y + (face.getHeight() / 2.0f));
            float scaleX = translateX - scaleX(face.getWidth() / 2.0f);
            float scaleY = translateY - scaleY(face.getHeight() / 2.0f);
            if (!SweetCameraActivity.hat) {
                canvas.drawBitmap(this.f118op, scaleX, scaleY, new Paint());
            } else if (SweetCameraActivity.hat) {
                canvas.drawBitmap(this.f118op, scaleX, scaleY - 60.0f, new Paint());
            }
        }
    }
}

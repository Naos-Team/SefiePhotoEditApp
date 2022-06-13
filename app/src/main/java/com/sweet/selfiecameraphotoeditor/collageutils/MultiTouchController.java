package com.sweet.selfiecameraphotoeditor.collageutils;

import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Method;

public class MultiTouchController<T> {
     static int actionPointerIndexShift = 8;
     static int actionPointerUp = 6;
    public static final boolean DEBUG = false;
     static final long EVENT_SETTLE_TIME_INTERVAL = 20;
     static final float MAX_MULTITOUCH_DIM_JUMP_SIZE = 40.0f;
     static final float MAX_MULTITOUCH_POS_JUMP_SIZE = 30.0f;
    public static final int MAX_TOUCH_POINTS = 20;
     static final float MIN_MULTITOUCH_SEPARATION = 30.0f;
    public static final int MODE_DRAG = 1;
    public static final int MODE_NOTHING = 0;
    public static final int MODE_PINCH = 2;
    public static final int MODE_ST_GRAB = 3;
     static final float THRESHOLD = 3.0f;
     static Method mGethistoricalpressure;
     static Method mGethistoricalx;
     static Method mGethistoricaly;
     static Method mGetpointercount;
     static Method mGetpointerid;
     static Method mGetpressure;
     static Method mGetx;
     static Method mGety;
    public static final boolean MULTI_TOUCH_SUPPORTED;
     static final int[] pointerIds = new int[20];
     static final float[] pressureVals = new float[20];
     static final float[] xVals = new float[20];
     static final float[] yVals = new float[20];
     boolean handleSingleTouchEvents;
     PointInfo mCurrPt;
     float mCurrPtAng;
     float mCurrPtDiam;
     float mCurrPtHeight;
     float mCurrPtWidth;
     float mCurrPtX;
     float mCurrPtY;
     PositionAndScale mCurrXform;
     boolean mDragOccurred;
     int mMode;
     PointInfo mPrevPt;
     long mSettleEndTime;
     long mSettleStartTime;
    MultiTouchObjectCanvas<T> objectCanvas;
     T selectedObject;
     float startAngleMinusPinchAngle;
     float startPosX;
     float startPosY;
     float startScaleOverPinchDiam;
     float startScaleXOverPinchWidth;
     float startScaleYOverPinchHeight;

    public interface MultiTouchObjectCanvas<T> {
        T getDraggableObjectAtPoint(PointInfo pointInfo);

        void getPositionAndScale(T t, PositionAndScale positionAndScale);

        boolean pointInObjectGrabArea(PointInfo pointInfo, T t);

        void selectObject(T t, PointInfo pointInfo);

        boolean setPositionAndScale(T t, PositionAndScale positionAndScale, PointInfo pointInfo);
    }

     void extractCurrPtInfo() {
        float f;
        float f2;
        float f3;
        this.mCurrPtX = this.mCurrPt.getX();
        this.mCurrPtY = this.mCurrPt.getY();
        float f4 = 0.0f;
        if (!this.mCurrXform.updateScale) {
            f = 0.0f;
        } else {
            f = this.mCurrPt.getMultiTouchDiameter();
        }
        this.mCurrPtDiam = Math.max(21.3f, f);
        if (!this.mCurrXform.updateScaleXY) {
            f2 = 0.0f;
        } else {
            f2 = this.mCurrPt.getMultiTouchWidth();
        }
        this.mCurrPtWidth = Math.max(30.0f, f2);
        if (!this.mCurrXform.updateScaleXY) {
            f3 = 0.0f;
        } else {
            f3 = this.mCurrPt.getMultiTouchHeight();
        }
        this.mCurrPtHeight = Math.max(30.0f, f3);
        if (this.mCurrXform.updateAngle) {
            f4 = this.mCurrPt.getMultiTouchAngle();
        }
        this.mCurrPtAng = f4;
    }

    public MultiTouchController(MultiTouchObjectCanvas<T> multiTouchObjectCanvas) {
        this(multiTouchObjectCanvas, true);
    }

    public MultiTouchController(MultiTouchObjectCanvas<T> multiTouchObjectCanvas, boolean z) {
        this.selectedObject = null;
        this.mCurrXform = new PositionAndScale();
        this.mDragOccurred = false;
        this.mMode = 0;
        this.mCurrPt = new PointInfo();
        this.mPrevPt = new PointInfo();
        this.handleSingleTouchEvents = z;
        this.objectCanvas = multiTouchObjectCanvas;
    }


    public void setHandleSingleTouchEvents(boolean z) {
        this.handleSingleTouchEvents = z;
    }


    public boolean getHandleSingleTouchEvents() {
        return this.handleSingleTouchEvents;
    }

    public boolean dragOccurred() {
        return this.mDragOccurred;
    }

    static {
        boolean z = true;
        try {
            mGetpointercount = MotionEvent.class.getMethod("getPointerCount", new Class[0]);
            mGetpointerid = MotionEvent.class.getMethod("getPointerId", new Class[]{Integer.TYPE});
            mGetpressure = MotionEvent.class.getMethod("getPressure", new Class[]{Integer.TYPE});
            mGethistoricalx = MotionEvent.class.getMethod("getHistoricalX", new Class[]{Integer.TYPE, Integer.TYPE});
            mGethistoricaly = MotionEvent.class.getMethod("getHistoricalY", new Class[]{Integer.TYPE, Integer.TYPE});
            mGethistoricalpressure = MotionEvent.class.getMethod("getHistoricalPressure", new Class[]{Integer.TYPE, Integer.TYPE});
            mGetx = MotionEvent.class.getMethod("getX", new Class[]{Integer.TYPE});
            mGety = MotionEvent.class.getMethod("getY", new Class[]{Integer.TYPE});
        } catch (Exception e) {
            Log.e("MultiTouchController", "static initializer failed", e);
            z = false;
        }
        MULTI_TOUCH_SUPPORTED = z;
        if (MULTI_TOUCH_SUPPORTED) {
            try {
                actionPointerUp = MotionEvent.class.getField("ACTION_POINTER_UP").getInt((Object) null);
                actionPointerIndexShift = MotionEvent.class.getField("ACTION_POINTER_INDEX_SHIFT").getInt((Object) null);
            } catch (Exception unused) {
            }
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        try {
            int pointerCount = MULTI_TOUCH_SUPPORTED ? (Integer) mGetpointercount.invoke(event) : 1;
            if (DEBUG)
                Log.i("MultiTouch", "Got here 1 - " + MULTI_TOUCH_SUPPORTED + " " + mMode + " " + handleSingleTouchEvents + " " + pointerCount);
            if (mMode == MODE_NOTHING && !handleSingleTouchEvents && pointerCount == 1)
                // Not handling initial single touch events, just pass them on
                return false;
            if (DEBUG)
                Log.i("MultiTouch", "Got here 2");

            // Handle history first (we sometimes get history with ACTION_MOVE events)
            int action = event.getAction();
            int histLen = event.getHistorySize() / pointerCount;
            for (int histIdx = 0; histIdx <= histLen; histIdx++) {
                // Read from history entries until histIdx == histLen, then read from current event
                boolean processingHist = histIdx < histLen;
                if (!MULTI_TOUCH_SUPPORTED || pointerCount == 1) {
                    // Use single-pointer methods -- these are needed as a special case (for some weird reason) even if
                    // multitouch is supported but there's only one touch point down currently -- event.getX(0) etc. throw
                    // an exception if there's only one point down.
                    if (DEBUG)
                        Log.i("MultiTouch", "Got here 3");
                    xVals[0] = processingHist ? event.getHistoricalX(histIdx) : event.getX();
                    yVals[0] = processingHist ? event.getHistoricalY(histIdx) : event.getY();
                    pressureVals[0] = processingHist ? event.getHistoricalPressure(histIdx) : event.getPressure();
                } else {
                    // Read x, y and pressure of each pointer
                    if (DEBUG)
                        Log.i("MultiTouch", "Got here 4");
                    int numPointers = Math.min(pointerCount, MAX_TOUCH_POINTS);
                    if (DEBUG && pointerCount > MAX_TOUCH_POINTS)
                        Log.i("MultiTouch", "Got more pointers than MAX_TOUCH_POINTS");
                    for (int ptrIdx = 0; ptrIdx < numPointers; ptrIdx++) {
                        int ptrId = (Integer) mGetpointerid.invoke(event, ptrIdx);
                        pointerIds[ptrIdx] = ptrId;
                        // N.B. if pointerCount == 1, then the following methods throw an array index out of range exception,
                        // and the code above is therefore required not just for Android 1.5/1.6 but also for when there is
                        // only one touch point on the screen -- pointlessly inconsistent :(
                        xVals[ptrIdx] = (Float) (processingHist ? mGethistoricalx.invoke(event, ptrIdx, histIdx) : mGetx.invoke(event, ptrIdx));
                        yVals[ptrIdx] = (Float) (processingHist ? mGethistoricaly.invoke(event, ptrIdx, histIdx) : mGety.invoke(event, ptrIdx));
                        pressureVals[ptrIdx] = (Float) (processingHist ? mGethistoricalpressure.invoke(event, ptrIdx, histIdx) : mGetpressure
                                .invoke(event, ptrIdx));
                    }
                }
                // Decode event
                decodeTouchEvent(pointerCount, xVals, yVals, pressureVals, pointerIds, //
                        /* action = */processingHist ? MotionEvent.ACTION_MOVE : action, //
                        /* down = */processingHist ? true : action != MotionEvent.ACTION_UP //
                                && (action & ((1 << actionPointerIndexShift) - 1)) != actionPointerUp //
                                && action != MotionEvent.ACTION_CANCEL, //
                        processingHist ? event.getHistoricalEventTime(histIdx) : event.getEventTime());
            }

            return true;
        } catch (Exception e) {
            // In case any of the introspection stuff fails (it shouldn't)
            Log.e("MultiTouchController", "onTouchEvent() failed", e);
            return false;
        }
    }

     void decodeTouchEvent(int i, float[] fArr, float[] fArr2, float[] fArr3, int[] iArr, int i2, boolean z, long j) {
        PointInfo pointInfo = this.mPrevPt;
        this.mPrevPt = this.mCurrPt;
        this.mCurrPt = pointInfo;
        this.mCurrPt.set(i, fArr, fArr2, fArr3, iArr, i2, z, j);
        multiTouchController();
    }

     void anchorAtThisPositionAndScale() {
        float f;
        T t = this.selectedObject;
        if (t != null) {
            this.objectCanvas.getPositionAndScale(t, this.mCurrXform);
            if (!this.mCurrXform.updateScale) {
                f = 1.0f;
            } else {
                f = this.mCurrXform.scale == 0.0f ? 1.0f : this.mCurrXform.scale;
            }
            float f2 = 1.0f / f;
            extractCurrPtInfo();
            this.startPosX = (this.mCurrPtX - this.mCurrXform.xOff) * f2;
            this.startPosY = (this.mCurrPtY - this.mCurrXform.yOff) * f2;
            this.startScaleOverPinchDiam = this.mCurrXform.scale / this.mCurrPtDiam;
            this.startScaleXOverPinchWidth = this.mCurrXform.scaleX / this.mCurrPtWidth;
            this.startScaleYOverPinchHeight = this.mCurrXform.scaleY / this.mCurrPtHeight;
            this.startAngleMinusPinchAngle = this.mCurrXform.angle - this.mCurrPtAng;
        }
    }

     void performDragOrPinch() {
        float f;
        float f2;
        if (this.selectedObject != null) {
            float f3 = 1.0f;
            if (this.mCurrXform.updateScale && this.mCurrXform.scale != 0.0f) {
                f3 = this.mCurrXform.scale;
            }
            extractCurrPtInfo();
            float f4 = this.mCurrPtX - (this.startPosX * f3);
            float f5 = this.mCurrPtY - (this.startPosY * f3);
            float x = this.mCurrPt.getX() - this.mPrevPt.getX();
            float y = this.mCurrPt.getY() - this.mPrevPt.getY();
            float unused = this.mCurrXform.scale;
            if (this.mMode == 3) {
                if (x < 0.0f || y < 0.0f) {
                    f2 = this.mCurrXform.scale - 0.04f;
                } else {
                    f2 = this.mCurrXform.scale + 0.04f;
                }
                if (f2 >= 0.35f) {
                    f = f2;
                } else {
                    return;
                }
            } else {
                f = this.startScaleOverPinchDiam * this.mCurrPtDiam;
            }
            if (this.mDragOccurred || pastThreshold(Math.abs(x), Math.abs(y), f)) {
                this.mCurrXform.set(f4, f5, f, this.startScaleXOverPinchWidth * this.mCurrPtWidth, this.startScaleYOverPinchHeight * this.mCurrPtHeight, this.startAngleMinusPinchAngle + this.mCurrPtAng);
                this.objectCanvas.setPositionAndScale(this.selectedObject, this.mCurrXform, this.mCurrPt);
                this.mDragOccurred = true;
            }
        }
    }

     boolean pastThreshold(float f, float f2, float f3) {
        if (f >= THRESHOLD || f2 >= THRESHOLD || f3 != this.mCurrXform.scale) {
            this.mDragOccurred = true;
            return true;
        }
        this.mDragOccurred = false;
        return false;
    }

     void multiTouchController() {
        switch (this.mMode) {
            case 0:
                if (this.mCurrPt.isDown()) {
                    this.selectedObject = this.objectCanvas.getDraggableObjectAtPoint(this.mCurrPt);
                    T t = this.selectedObject;
                    if (t == null) {
                        return;
                    }
                    if (this.objectCanvas.pointInObjectGrabArea(this.mCurrPt, t)) {
                        this.mMode = 3;
                        this.objectCanvas.selectObject(this.selectedObject, this.mCurrPt);
                        anchorAtThisPositionAndScale();
                        long eventTime = this.mCurrPt.getEventTime();
                        this.mSettleEndTime = eventTime;
                        this.mSettleStartTime = eventTime;
                        return;
                    }
                    this.mMode = 1;
                    this.objectCanvas.selectObject(this.selectedObject, this.mCurrPt);
                    anchorAtThisPositionAndScale();
                    long eventTime2 = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = eventTime2;
                    this.mSettleStartTime = eventTime2;
                    return;
                }
                return;
            case 1:
                if (!this.mCurrPt.isDown()) {
                    this.mMode = 0;
                    MultiTouchObjectCanvas<T> multiTouchObjectCanvas = this.objectCanvas;
                    this.selectedObject = null;
                    multiTouchObjectCanvas.selectObject(null, this.mCurrPt);
                    this.mDragOccurred = false;
                    return;
                } else if (this.mCurrPt.isMultiTouch()) {
                    this.mMode = 2;
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                    return;
                } else if (this.mCurrPt.getEventTime() < this.mSettleEndTime) {
                    anchorAtThisPositionAndScale();
                    return;
                } else {
                    performDragOrPinch();
                    return;
                }
            case 2:
                if (!this.mCurrPt.isMultiTouch() || !this.mCurrPt.isDown()) {
                    if (!this.mCurrPt.isDown()) {
                        this.mMode = 0;
                        MultiTouchObjectCanvas<T> multiTouchObjectCanvas2 = this.objectCanvas;
                        this.selectedObject = null;
                        multiTouchObjectCanvas2.selectObject(null, this.mCurrPt);
                        return;
                    }
                    this.mMode = 1;
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                    return;
                } else if (Math.abs(this.mCurrPt.getX() - this.mPrevPt.getX()) > 30.0f || Math.abs(this.mCurrPt.getY() - this.mPrevPt.getY()) > 30.0f || Math.abs(this.mCurrPt.getMultiTouchWidth() - this.mPrevPt.getMultiTouchWidth()) * 0.5f > MAX_MULTITOUCH_DIM_JUMP_SIZE || Math.abs(this.mCurrPt.getMultiTouchHeight() - this.mPrevPt.getMultiTouchHeight()) * 0.5f > MAX_MULTITOUCH_DIM_JUMP_SIZE) {
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                    return;
                } else if (this.mCurrPt.eventTime < this.mSettleEndTime) {
                    anchorAtThisPositionAndScale();
                    return;
                } else {
                    performDragOrPinch();
                    return;
                }
            case 3:
                if (!this.mCurrPt.isDown()) {
                    this.mMode = 0;
                    MultiTouchObjectCanvas<T> multiTouchObjectCanvas3 = this.objectCanvas;
                    this.selectedObject = null;
                    multiTouchObjectCanvas3.selectObject(null, this.mCurrPt);
                    this.mDragOccurred = false;
                    return;
                }
                performDragOrPinch();
                return;
            default:
                return;
        }
    }

    public int getMode() {
        return this.mMode;
    }

    public static class PointInfo {
         int action;
         float angle;
         boolean angleIsCalculated;
         float diameter;
         boolean diameterIsCalculated;
         float diameterSq;
         boolean diameterSqIsCalculated;
         float dx;
         float dy;

        public long eventTime;
         boolean isDown;
         boolean isMultiTouch;
         int numPoints;
         int[] pointerIds = new int[20];
         float pressureMid;
         float[] pressures = new float[20];
         float xMid;
         float[] xs = new float[20];
         float yMid;
         float[] ys = new float[20];

         int julery_isqrt(int i) {
            int i2 = 0;
            int i3 = 32768;
            int i4 = 15;
            while (true) {
                int i5 = i4 - 1;
                int i6 = ((i2 << 1) + i3) << i4;
                if (i >= i6) {
                    i2 += i3;
                    i -= i6;
                }
                i3 >>= 1;
                if (i3 <= 0) {
                    return i2;
                }
                i4 = i5;
            }
        }


        public void set(int i, float[] fArr, float[] fArr2, float[] fArr3, int[] iArr, int i2, boolean z, long j) {
            this.eventTime = j;
            this.action = i2;
            this.numPoints = i;
            for (int i3 = 0; i3 < i; i3++) {
                this.xs[i3] = fArr[i3];
                this.ys[i3] = fArr2[i3];
                this.pressures[i3] = fArr3[i3];
                this.pointerIds[i3] = iArr[i3];
            }
            this.isDown = z;
            this.isMultiTouch = i >= 2;
            if (this.isMultiTouch) {
                this.xMid = (fArr[0] + fArr[1]) * 0.5f;
                this.yMid = (fArr2[0] + fArr2[1]) * 0.5f;
                this.pressureMid = (fArr3[0] + fArr3[1]) * 0.5f;
                this.dx = Math.abs(fArr[1] - fArr[0]);
                this.dy = Math.abs(fArr2[1] - fArr2[0]);
            } else {
                this.xMid = fArr[0];
                this.yMid = fArr2[0];
                this.pressureMid = fArr3[0];
                this.dy = 0.0f;
                this.dx = 0.0f;
            }
            this.angleIsCalculated = false;
            this.diameterIsCalculated = false;
            this.diameterSqIsCalculated = false;
        }

        public void set(PointInfo pointInfo) {
            this.numPoints = pointInfo.numPoints;
            for (int i = 0; i < this.numPoints; i++) {
                this.xs[i] = pointInfo.xs[i];
                this.ys[i] = pointInfo.ys[i];
                this.pressures[i] = pointInfo.pressures[i];
                this.pointerIds[i] = pointInfo.pointerIds[i];
            }
            this.xMid = pointInfo.xMid;
            this.yMid = pointInfo.yMid;
            this.pressureMid = pointInfo.pressureMid;
            this.dx = pointInfo.dx;
            this.dy = pointInfo.dy;
            this.diameter = pointInfo.diameter;
            this.diameterSq = pointInfo.diameterSq;
            this.angle = pointInfo.angle;
            this.isDown = pointInfo.isDown;
            this.action = pointInfo.action;
            this.isMultiTouch = pointInfo.isMultiTouch;
            this.diameterIsCalculated = pointInfo.diameterIsCalculated;
            this.diameterSqIsCalculated = pointInfo.diameterSqIsCalculated;
            this.angleIsCalculated = pointInfo.angleIsCalculated;
            this.eventTime = pointInfo.eventTime;
        }

        public boolean isMultiTouch() {
            return this.isMultiTouch;
        }

        public float getMultiTouchWidth() {
            if (this.isMultiTouch) {
                return this.dx;
            }
            return 0.0f;
        }

        public float getMultiTouchHeight() {
            if (this.isMultiTouch) {
                return this.dy;
            }
            return 0.0f;
        }

        public float getMultiTouchDiameterSq() {
            float f;
            if (!this.diameterSqIsCalculated) {
                if (this.isMultiTouch) {
                    float f2 = this.dx;
                    float f3 = this.dy;
                    f = (f2 * f2) + (f3 * f3);
                } else {
                    f = 0.0f;
                }
                this.diameterSq = f;
                this.diameterSqIsCalculated = true;
            }
            return this.diameterSq;
        }

        public float getMultiTouchDiameter() {
            if (!this.diameterIsCalculated) {
                float f = 0.0f;
                if (!this.isMultiTouch) {
                    this.diameter = 0.0f;
                } else {
                    float multiTouchDiameterSq = getMultiTouchDiameterSq();
                    if (multiTouchDiameterSq != 0.0f) {
                        f = ((float) julery_isqrt((int) (multiTouchDiameterSq * 256.0f))) / 16.0f;
                    }
                    this.diameter = f;
                    float f2 = this.diameter;
                    float f3 = this.dx;
                    if (f2 < f3) {
                        this.diameter = f3;
                    }
                    float f4 = this.diameter;
                    float f5 = this.dy;
                    if (f4 < f5) {
                        this.diameter = f5;
                    }
                }
                this.diameterIsCalculated = true;
            }
            return this.diameter;
        }

        public float getMultiTouchAngle() {
            if (!this.angleIsCalculated) {
                if (!this.isMultiTouch) {
                    this.angle = 0.0f;
                } else {
                    float[] fArr = this.ys;
                    float[] fArr2 = this.xs;
                    this.angle = (float) Math.atan2((double) (fArr[1] - fArr[0]), (double) (fArr2[1] - fArr2[0]));
                }
                this.angleIsCalculated = true;
            }
            return this.angle;
        }

        public int getNumTouchPoints() {
            return this.numPoints;
        }

        public float getX() {
            return this.xMid;
        }

        public float[] getXs() {
            return this.xs;
        }

        public float getY() {
            return this.yMid;
        }

        public float[] getYs() {
            return this.ys;
        }

        public int[] getPointerIds() {
            return this.pointerIds;
        }

        public float getPressure() {
            return this.pressureMid;
        }

        public float[] getPressures() {
            return this.pressures;
        }

        public boolean isDown() {
            return this.isDown;
        }

        public int getAction() {
            return this.action;
        }

        public long getEventTime() {
            return this.eventTime;
        }
    }

    public static class PositionAndScale {

        public float angle;

        public float scale;

        public float scaleX;

        public float scaleY;

        public boolean updateAngle;

        public boolean updateScale;

        public boolean updateScaleXY;

        public float xOff;

        public float yOff;

        public void set(float f, float f2, boolean z, float f3, boolean z2, float f4, float f5, boolean z3, float f6) {
            this.xOff = f;
            this.yOff = f2;
            this.updateScale = z;
            float f7 = 1.0f;
            if (f3 == 0.0f) {
                f3 = 1.0f;
            }
            this.scale = f3;
            this.updateScaleXY = z2;
            if (f4 == 0.0f) {
                f4 = 1.0f;
            }
            this.scaleX = f4;
            if (f5 != 0.0f) {
                f7 = f5;
            }
            this.scaleY = f7;
            this.updateAngle = z3;
            this.angle = f6;
        }


        public void set(float f, float f2, float f3, float f4, float f5, float f6) {
            this.xOff = f;
            this.yOff = f2;
            float f7 = 1.0f;
            if (f3 == 0.0f) {
                f3 = 1.0f;
            }
            this.scale = f3;
            if (f4 == 0.0f) {
                f4 = 1.0f;
            }
            this.scaleX = f4;
            if (f5 != 0.0f) {
                f7 = f5;
            }
            this.scaleY = f7;
            this.angle = f6;
        }

        public float getXOff() {
            return this.xOff;
        }

        public float getYOff() {
            return this.yOff;
        }

        public float getScale() {
            if (!this.updateScale) {
                return 1.0f;
            }
            return this.scale;
        }

        public float getScaleX() {
            if (!this.updateScaleXY) {
                return 1.0f;
            }
            return this.scaleX;
        }

        public float getScaleY() {
            if (!this.updateScaleXY) {
                return 1.0f;
            }
            return this.scaleY;
        }

        public float getAngle() {
            if (!this.updateAngle) {
                return 0.0f;
            }
            return this.angle;
        }
    }
}

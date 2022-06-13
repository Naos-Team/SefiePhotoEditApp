package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.module;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.DisplayUtils;

public class RecordButton extends View {
    public static final float PROGRESS_LIM_TO_FINISH_STARTING_ANIM = 0.1f;
    public static final int RECORD_ENDED = 2;
    public static final int RECORD_NOT_STARTED = 0;
    public static final int RECORD_STARTED = 1;
    static final String TAG = "RecordButton";
    public static final float TIME_LIMIT_IN_MILS = 10000.0f;
    public static final long TIME_TO_START_RECORD = 500;
    int boundingBoxSize;
    float innerCircleRadius;

    public int outerCircleWidthInc;

    public int outCircleWidth;

    public long btnpresstime;

    public Paint centercirclepaint;

    public float centerX;

    public float centerY;

    public ClickListener clickListener;
    int colorBlackP40;
    int colorBlackP80;

    public int colorRecord;
    int colorTranslucent;

    public int colorWhite;
    int colorWhiteP60;

    public float innerCircleRadiusToDraw;

    public float innerCircleRadiusWhenRecord;
    Context mContext;
    Paint outBlackCirclePaint;

    public float outBlackCircleRadius;

    public int outBlackCircleRadiusInc;
    Paint outMostBlackCirclePaint;

    public float outMostBlackCircleRadius;

    public float outMostCircleRadius;

    public RectF outMostCircleRect;

    public Paint outMostWhiteCirclePaint;

    public float percentInDegree;

    public Paint processBarPaint;

    public int recordState;

    public boolean recordable;
    float startAngle270;
    TouchTimeHandler touchTimeHandler;
    boolean touchable;

    public int translucentCircleRadius = 0;
    Paint translucentPaint;
    TouchTimeHandler.Task updateUITask = new TouchTimeHandler.Task() {
        public void run() {
            long currentTimeMillis = System.currentTimeMillis() - RecordButton.this.btnpresstime;
            float f = ((float) (currentTimeMillis - 500)) / 10000.0f;
            if (currentTimeMillis >= 500) {
                synchronized (RecordButton.this) {
                    if (RecordButton.this.recordState == 0) {
                        RecordButton.this.recordState = 1;
                        if (RecordButton.this.clickListener != null) {
                            RecordButton.this.clickListener.onLongClickStart();
                        }
                    }
                }
                if (RecordButton.this.recordable) {
                    RecordButton.this.centercirclepaint.setColor(RecordButton.this.colorRecord);
                    RecordButton.this.outMostWhiteCirclePaint.setColor(RecordButton.this.colorWhite);
                    RecordButton.this.percentInDegree = 360.0f * f;
                    if (f <= 1.0f) {
                        if (f <= 0.1f) {
                            float f2 = f / 0.1f;
                            float access900 = ((float) RecordButton.this.outBlackCircleRadiusInc) * f2;
                            float access1000 = ((float) RecordButton.this.outCircleWidth) + (((float) RecordButton.this.outerCircleWidthInc) * f2);
                            RecordButton.this.processBarPaint.setStrokeWidth(access1000);
                            RecordButton.this.outMostWhiteCirclePaint.setStrokeWidth(access1000);
                            RecordButton recordButton = RecordButton.this;
                            float f3 = access1000 / 2.0f;
                            recordButton.outBlackCircleRadius = (recordButton.outMostCircleRadius + access900) - f3;
                            RecordButton recordButton2 = RecordButton.this;
                            recordButton2.outMostBlackCircleRadius = f3 + recordButton2.outMostCircleRadius + access900;
                            RecordButton recordButton3 = RecordButton.this;
                            recordButton3.outMostCircleRect = new RectF((recordButton3.centerX - RecordButton.this.outMostCircleRadius) - access900, (RecordButton.this.centerY - RecordButton.this.outMostCircleRadius) - access900, RecordButton.this.centerX + RecordButton.this.outMostCircleRadius + access900, RecordButton.this.centerY + RecordButton.this.outMostCircleRadius + access900);
                            RecordButton recordButton4 = RecordButton.this;
                            recordButton4.translucentCircleRadius = (int) (access900 + recordButton4.outMostCircleRadius);
                            RecordButton recordButton5 = RecordButton.this;
                            recordButton5.innerCircleRadiusToDraw = f2 * recordButton5.innerCircleRadiusWhenRecord;
                        }
                        RecordButton.this.invalidate();
                        return;
                    }
                    RecordButton.this.reset();
                }
            }
        }
    };

    public interface ClickListener {
        void onClick();

        void onLongClickEnd();

        void onLongClickStart();
    }

    public RecordButton(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public RecordButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    public RecordButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        init();
    }


    public void init() {
        this.recordable = true;
        this.touchable = true;
        this.boundingBoxSize = DisplayUtils.getRefLength(this.mContext, 100.0f);
        this.outCircleWidth = DisplayUtils.getRefLength(this.mContext, 2.3f);
        this.outerCircleWidthInc = DisplayUtils.getRefLength(this.mContext, 4.3f);
        this.innerCircleRadius = (float) DisplayUtils.getRefLength(this.mContext, 32.0f);
        this.colorRecord = getResources().getColor(R.color.app_color);
        this.colorWhite = getResources().getColor(R.color.white);
        this.colorWhiteP60 = getResources().getColor(R.color.white_sixty_percent);
        this.colorBlackP40 = getResources().getColor(R.color.black_forty_percent);
        this.colorBlackP80 = getResources().getColor(R.color.black_eighty_percent);
        this.colorTranslucent = getResources().getColor(R.color.circle_shallow_translucent_bg);
        this.processBarPaint = new Paint();
        this.processBarPaint.setColor(this.colorRecord);
        this.processBarPaint.setAntiAlias(true);
        this.processBarPaint.setStrokeWidth((float) this.outCircleWidth);
        this.processBarPaint.setStyle(Paint.Style.STROKE);
        this.processBarPaint.setStrokeCap(Paint.Cap.ROUND);
        this.outMostWhiteCirclePaint = new Paint();
        this.outMostWhiteCirclePaint.setColor(this.colorWhite);
        this.outMostWhiteCirclePaint.setAntiAlias(true);
        this.outMostWhiteCirclePaint.setStrokeWidth((float) this.outCircleWidth);
        this.outMostWhiteCirclePaint.setStyle(Paint.Style.STROKE);
        this.centercirclepaint = new Paint();
        this.centercirclepaint.setColor(this.colorWhiteP60);
        this.centercirclepaint.setAntiAlias(true);
        this.centercirclepaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.outBlackCirclePaint = new Paint();
        this.outBlackCirclePaint.setColor(this.colorBlackP40);
        this.outBlackCirclePaint.setAntiAlias(true);
        this.outBlackCirclePaint.setStyle(Paint.Style.STROKE);
        this.outBlackCirclePaint.setStrokeWidth(1.0f);
        this.outMostBlackCirclePaint = new Paint();
        this.outMostBlackCirclePaint.setColor(this.colorBlackP80);
        this.outMostBlackCirclePaint.setAntiAlias(true);
        this.outMostBlackCirclePaint.setStyle(Paint.Style.STROKE);
        this.outMostBlackCirclePaint.setStrokeWidth(1.0f);
        this.translucentPaint = new Paint();
        this.translucentPaint.setColor(this.colorTranslucent);
        this.translucentPaint.setAntiAlias(true);
        this.translucentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int i = this.boundingBoxSize;
        this.centerX = (float) (i / 2);
        this.centerY = (float) (i / 2);
        this.outMostCircleRadius = (float) DisplayUtils.getRefLength(this.mContext, 37.0f);
        this.outBlackCircleRadiusInc = DisplayUtils.getRefLength(this.mContext, 7.0f);
        this.innerCircleRadiusWhenRecord = (float) DisplayUtils.getRefLength(this.mContext, 35.0f);
        this.innerCircleRadiusToDraw = this.innerCircleRadius;
        float f = this.outMostCircleRadius;
        int i2 = this.outCircleWidth;
        this.outBlackCircleRadius = f - (((float) i2) / 2.0f);
        this.outMostBlackCircleRadius = (((float) i2) / 2.0f) + f;
        this.startAngle270 = 270.0f;
        this.percentInDegree = 0.0f;
        float f2 = this.centerX;
        float f3 = this.centerY;
        this.outMostCircleRect = new RectF(f2 - f, f3 - f, f2 + f, f3 + f);
        this.touchTimeHandler = new TouchTimeHandler(Looper.getMainLooper(), this.updateUITask);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(this.centerX, this.centerY, (float) this.translucentCircleRadius, this.translucentPaint);
        canvas.drawCircle(this.centerX, this.centerY, this.innerCircleRadiusToDraw, this.centercirclepaint);
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.outMostCircleRect, this.startAngle270, 360.0f, false, this.outMostWhiteCirclePaint);
        canvas2.drawArc(this.outMostCircleRect, this.startAngle270, this.percentInDegree, false, this.processBarPaint);
        canvas.drawCircle(this.centerX, this.centerY, this.outBlackCircleRadius, this.outBlackCirclePaint);
        canvas.drawCircle(this.centerX, this.centerY, this.outMostBlackCircleRadius, this.outMostBlackCirclePaint);
    }

    @Override

    public void onMeasure(int i, int i2) {
        int i3 = this.boundingBoxSize;
        setMeasuredDimension(i3, i3);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.touchable) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                Log.d(TAG, "onTouchEvent: down");
                startTicking();
                return true;
            case 1:
                Log.d(TAG, "onTouchEvent: up");
                reset();
                return true;
            case 2:
                Log.d(TAG, "onTouchEvent: move");
                return true;
            default:
                return true;
        }
    }

    public void reset() {
        synchronized (this) {
            if (this.recordState == 1) {
                if (this.clickListener != null) {
                    this.clickListener.onLongClickEnd();
                }
                this.recordState = 2;
            } else if (this.recordState == 2) {
                this.recordState = 0;
            } else if (this.clickListener != null) {
                this.clickListener.onClick();
            }
        }
        this.touchTimeHandler.clearMsg();
        this.percentInDegree = 0.0f;
        this.centercirclepaint.setColor(this.colorWhiteP60);
        this.outMostWhiteCirclePaint.setColor(this.colorWhite);
        this.innerCircleRadiusToDraw = this.innerCircleRadius;
        float f = this.centerX;
        float f2 = this.outMostCircleRadius;
        float f3 = this.centerY;
        this.outMostCircleRect = new RectF(f - f2, f3 - f2, f + f2, f3 + f2);
        this.translucentCircleRadius = 0;
        this.processBarPaint.setStrokeWidth((float) this.outCircleWidth);
        this.outMostWhiteCirclePaint.setStrokeWidth((float) this.outCircleWidth);
        float f4 = this.outMostCircleRadius;
        int i = this.outCircleWidth;
        this.outBlackCircleRadius = f4 - (((float) i) / 2.0f);
        this.outMostBlackCircleRadius = f4 + (((float) i) / 2.0f);
        invalidate();
    }

    public boolean isTouchable() {
        return this.touchable;
    }

    public boolean isRecordable() {
        return this.recordable;
    }

    public void setRecordable(boolean z) {
        this.recordable = z;
    }

    public void setTouchable(boolean z) {
        this.touchable = z;
    }

    public void startTicking() {
        synchronized (this) {
            if (this.recordState != 0) {
                this.recordState = 0;
            }
        }
        this.btnpresstime = System.currentTimeMillis();
        this.touchTimeHandler.sendLoopMsg(0, 16);
    }

    public void setClickListener(ClickListener clickListener2) {
        this.clickListener = clickListener2;
    }
}

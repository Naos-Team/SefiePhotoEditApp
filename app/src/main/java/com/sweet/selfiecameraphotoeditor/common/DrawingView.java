package com.sweet.selfiecameraphotoeditor.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

public class DrawingView extends View {
    private int mBackgroundColor = 0;
    private Paint mBackgroundPaint;
    private Bitmap mCanvasBitmap;
    private Canvas mDrawCanvas;
    private Paint mDrawPaint;
    private Path mDrawPath;
    private int mPaintColor = -10092544;
    private ArrayList<Paint> mPaints = new ArrayList<>();
    private ArrayList<Path> mPaths = new ArrayList<>();
    private int mStrokeWidth = 10;
    private ArrayList<Paint> mUndonePaints = new ArrayList<>();
    private ArrayList<Path> mUndonePaths = new ArrayList<>();

    public DrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mDrawPath = new Path();
        this.mBackgroundPaint = new Paint();
        initPaint();
    }

    private void initPaint() {
        this.mDrawPaint = new Paint();
        this.mDrawPaint.setColor(this.mPaintColor);
        this.mDrawPaint.setAntiAlias(true);
        this.mDrawPaint.setStrokeWidth((float) this.mStrokeWidth);
        this.mDrawPaint.setStyle(Paint.Style.STROKE);
        this.mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void drawBackground(Canvas canvas) {
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.mBackgroundPaint);
    }

    private void drawPaths(Canvas canvas) {
        Iterator<Path> it2 = this.mPaths.iterator();
        int i = 0;
        while (it2.hasNext()) {
            canvas.drawPath(it2.next(), this.mPaints.get(i));
            i++;
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawPaths(canvas);
        canvas.drawPath(this.mDrawPath, this.mDrawPaint);
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mCanvasBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.mDrawCanvas = new Canvas(this.mCanvasBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                this.mDrawPath.moveTo(x, y);
                break;
            case 1:
                this.mDrawPath.lineTo(x, y);
                this.mPaths.add(this.mDrawPath);
                this.mPaints.add(this.mDrawPaint);
                this.mDrawPath = new Path();
                initPaint();
                break;
            case 2:
                this.mDrawPath.lineTo(x, y);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void clearCanvas() {
        this.mPaths.clear();
        this.mPaints.clear();
        this.mUndonePaths.clear();
        this.mUndonePaints.clear();
        this.mDrawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void setPaintColor(int i) {
        this.mPaintColor = i;
        this.mDrawPaint.setColor(this.mPaintColor);
    }

    public void setPaintStrokeWidth(int i) {
        this.mStrokeWidth = i;
        this.mDrawPaint.setStrokeWidth((float) this.mStrokeWidth);
    }

    @Override
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
        invalidate();
    }

    public Bitmap getBitmap() {
        drawBackground(this.mDrawCanvas);
        drawPaths(this.mDrawCanvas);
        return this.mCanvasBitmap;
    }

    public void undo() {
        if (this.mPaths.size() > 0) {
            ArrayList<Path> arrayList = this.mUndonePaths;
            ArrayList<Path> arrayList2 = this.mPaths;
            arrayList.add(arrayList2.remove(arrayList2.size() - 1));
            ArrayList<Paint> arrayList3 = this.mUndonePaints;
            ArrayList<Paint> arrayList4 = this.mPaints;
            arrayList3.add(arrayList4.remove(arrayList4.size() - 1));
            invalidate();
        }
    }

    public void redo() {
        if (this.mUndonePaths.size() > 0) {
            ArrayList<Path> arrayList = this.mPaths;
            ArrayList<Path> arrayList2 = this.mUndonePaths;
            arrayList.add(arrayList2.remove(arrayList2.size() - 1));
            ArrayList<Paint> arrayList3 = this.mPaints;
            ArrayList<Paint> arrayList4 = this.mUndonePaints;
            arrayList3.add(arrayList4.remove(arrayList4.size() - 1));
            invalidate();
        }
    }
}

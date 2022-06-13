package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.camera.CameraEngine;

public class CameraView {
    private static final String TAG = "CameraView";

    public CameraEngine cameraEngine;

    public Context context;

    public GLRender glRender;

    public GLRootView glRootView;

    public RootViewClickListener rootViewClickListener;

    public ScaleGestureDetector scaleGestureDetector;

    public ScreenSizeChangedListener screenSizeChangedListener;

    public interface PreviewSizeChangedCallback {
        void updatePreviewSize(int i, int i2);
    }

    public interface RenderCallback {
        void renderImmediately();
    }

    public interface RootViewClickListener {
        void onRootViewClicked();

        void onRootViewLongClicked();

        void onRootViewTouched(MotionEvent motionEvent);
    }

    public interface ScreenSizeChangedListener {
        void updateScreenSize(int i, int i2);
    }

    public CameraView(Context context2, GLRootView gLRootView) {
        this.glRootView = gLRootView;
        this.context = context2;
        init();
    }

    private void init() {
        this.glRootView.setEGLContextClientVersion(2);
        this.cameraEngine = new CameraEngine();
        this.cameraEngine.setRenderCallback(new RenderCallback() {
            public void renderImmediately() {
                CameraView.this.glRootView.requestRender();
            }
        });
        this.cameraEngine.setPreviewSizeChangedCallback(new PreviewSizeChangedCallback() {
            public void updatePreviewSize(final int i, final int i2) {
                ((Activity) CameraView.this.context).runOnUiThread(new Runnable() {
                    public void run() {
                        CameraView.this.glRender.getOrthoFilter().updateProjection(i, i2);
                        if (CameraView.this.screenSizeChangedListener != null) {
                            CameraView.this.screenSizeChangedListener.updateScreenSize(CameraView.this.glRootView.getWidth(), CameraView.this.glRootView.getHeight());
                        }
                    }
                });
            }
        });
        this.scaleGestureDetector = new ScaleGestureDetector(this.context, new ScaleGestureDetector.OnScaleGestureListener() {
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }

            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                CameraView.this.cameraEngine.requestZoom((double) (scaleGestureDetector.getScaleFactor() - 1.0f));
                return true;
            }
        });
        this.glRender = new GLRender(this.context, this.cameraEngine);
        this.glRootView.setRenderer(this.glRender);
        this.glRootView.setRenderMode(0);
        this.glRootView.setClickable(true);
        if (Build.VERSION.SDK_INT >= 11) {
            this.glRootView.setPreserveEGLContextOnPause(true);
        }
        this.glRootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CameraView.this.scaleGestureDetector.onTouchEvent(motionEvent);
                if (!CameraView.this.scaleGestureDetector.isInProgress() && CameraView.this.rootViewClickListener != null && motionEvent.getAction() == 1) {
                    CameraView.this.rootViewClickListener.onRootViewTouched(motionEvent);
                }
                if (motionEvent.getPointerCount() != 1) {
                    return true;
                }
                return false;
            }
        });
        this.glRootView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!CameraView.this.scaleGestureDetector.isInProgress()) {
                    CameraView.this.cameraEngine.focusCamera();
                    Log.d(CameraView.TAG, "onClick: " + CameraView.this.glRootView.getWidth() + " " + CameraView.this.glRootView.getHeight());
                    if (CameraView.this.rootViewClickListener != null) {
                        CameraView.this.rootViewClickListener.onRootViewClicked();
                    }
                }
            }
        });
        this.glRootView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if (CameraView.this.scaleGestureDetector.isInProgress()) {
                    return true;
                }
                Log.d(CameraView.TAG, "onLongClick: ");
                if (CameraView.this.rootViewClickListener != null) {
                    CameraView.this.rootViewClickListener.onRootViewLongClicked();
                }
                return true;
            }
        });
        this.cameraEngine.setGlRender(this.glRender);
    }

    public void onPause() {
        this.glRootView.onPause();
        this.glRender.onPause();
    }

    public void onResume() {
        this.glRootView.onResume();
        this.glRender.onResume();
    }

    public void onDestroy() {
        this.glRender.onDestroy();
    }

    public CameraEngine getCameraEngine() {
        return this.cameraEngine;
    }

    public GLRender getGlRender() {
        return this.glRender;
    }

    public void setScreenSizeChangedListener(ScreenSizeChangedListener screenSizeChangedListener2) {
        this.screenSizeChangedListener = screenSizeChangedListener2;
    }

    public void setRootViewClickListener(RootViewClickListener rootViewClickListener2) {
        this.rootViewClickListener = rootViewClickListener2;
    }
}

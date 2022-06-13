package com.sweet.selfiecameraphotoeditor.cameraview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.sweet.selfiecameraphotoeditor.activities.GraphicOverlay;
import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;

import java.io.IOException;

public class CameraSourcePreview extends ViewGroup {
    private static final String TAG = "CameraSourcePreview";
    private CameraSource mCameraSource;
    private Context mContext;
    private GraphicOverlay mOverlay;
    private boolean mStartRequested = false;

    public boolean mSurfaceAvailable = false;
    private SurfaceView mSurfaceView;

    public CameraSourcePreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mSurfaceView = new SurfaceView(context);
        this.mSurfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(this.mSurfaceView);
    }

    public void start(CameraSource cameraSource) throws IOException {
        if (cameraSource == null) {
            stop();
        }
        this.mCameraSource = cameraSource;
        if (this.mCameraSource != null) {
            this.mStartRequested = true;
            startIfReady();
        }
    }

    public void start(CameraSource cameraSource, GraphicOverlay graphicOverlay) throws IOException {
        this.mOverlay = graphicOverlay;
        start(cameraSource);
    }

    public void stop() {
        CameraSource cameraSource = this.mCameraSource;
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    public void release() {
        CameraSource cameraSource = this.mCameraSource;
        if (cameraSource != null) {
            cameraSource.release();
            this.mCameraSource = null;
        }
    }


    public void startIfReady() throws IOException {
        if (this.mStartRequested && this.mSurfaceAvailable) {
            this.mCameraSource.start(this.mSurfaceView.getHolder());
            if (this.mOverlay != null) {
                Size previewSize = this.mCameraSource.getPreviewSize();
                int min = Math.min(previewSize.getWidth(), previewSize.getHeight());
                int max = Math.max(previewSize.getWidth(), previewSize.getHeight());
                if (isPortraitMode()) {
                    this.mOverlay.setCameraInfo(min, max, this.mCameraSource.getCameraFacing());
                } else {
                    this.mOverlay.setCameraInfo(max, min, this.mCameraSource.getCameraFacing());
                }
                this.mOverlay.clear();
            }
            this.mStartRequested = false;
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            Log.d("","");
        }

        private SurfaceCallback() {
            Log.d("","");
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            CameraSourcePreview.this.mSurfaceAvailable = true;
            try {
                CameraSourcePreview.this.startIfReady();
            } catch (IOException e) {
                Log.e(CameraSourcePreview.TAG, "Could not start camera source.", e);
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            CameraSourcePreview.this.mSurfaceAvailable = false;
        }
    }


    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        Size previewSize;
        CameraSource cameraSource = this.mCameraSource;
        if (cameraSource == null || (previewSize = cameraSource.getPreviewSize()) == null) {
            i6 = 320;
            i5 = 240;
        } else {
            i6 = previewSize.getWidth();
            i5 = previewSize.getHeight();
        }
        if (!isPortraitMode()) {
            int i7 = i6;
            i6 = i5;
            i5 = i7;
        }
        int i8 = i3 - i;
        int i9 = i4 - i2;
        float f = (float) i5;
        float f2 = (float) i6;
        int i10 = (int) ((((float) i8) / f) * f2);
        if (i10 > i9) {
            i8 = (int) ((((float) i9) / f2) * f);
            i10 = i9;
        }
        for (int i11 = 0; i11 < getChildCount(); i11++) {
            getChildAt(i11).layout(0, 0, i8, i10);
        }
        try {
            startIfReady();
        } catch (IOException e) {
            Log.e(TAG, "Could not start camera source.", e);
        }
    }

    private boolean isPortraitMode() {
        int i = this.mContext.getResources().getConfiguration().orientation;
        if (i == 2) {
            return false;
        }
        if (i == 1) {
            return true;
        }
        Log.d(TAG, "isPortraitMode returning false by default");
        return false;
    }
}

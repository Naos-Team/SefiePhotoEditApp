package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.camera;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.GlobalConfig;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.CameraView;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.GLRender;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.FileUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraEngine implements SurfaceTexture.OnFrameAvailableListener, Camera.AutoFocusCallback, Camera.PreviewCallback {
     static final String TAG = "CameraEngine";
     static final double PREFERRED_RATIO = 1.7777777777777777d;
     Camera camera;
     boolean cameraOpened = false;
     int currentCameraId;
    int displayRotate;
     int frameHeight = 640;
     int frameWidth = 480;
     GLRender glRender;
     int lastZoomValue;
     double lastZoomValueRec;
     byte[] mBuffer;

    public boolean mCameraFrameReady;

    public int mChainIdx = 0;
     FakeMat[] mFrameChain = new FakeMat[2];
     MediaRecorder mMediaRecorder;
     Camera.Parameters mParams;

    public boolean mStopThread;
     SurfaceTexture mSurfaceTexture;
     Thread mWorkerThread;
     Camera.Size previewSize;
     CameraView.PreviewSizeChangedCallback previewSizeChangedCallback;
     CameraView.RenderCallback renderCallback;

    public void onAutoFocus(boolean z, Camera camera2) {
    }

    public CameraEngine() {
        this.mFrameChain[0] = new FakeMat();
        this.mFrameChain[1] = new FakeMat();
    }

    public void setTexture(int i) {
        this.mSurfaceTexture = new SurfaceTexture(i);
        this.mSurfaceTexture.setOnFrameAvailableListener(this);
    }

    public long doTextureUpdate(float[] fArr) {
        this.mSurfaceTexture.updateTexImage();
        this.mSurfaceTexture.getTransformMatrix(fArr);
        return this.mSurfaceTexture.getTimestamp();
    }

    public void openCamera(boolean z) {
        synchronized (this) {
            this.currentCameraId = getCameraIdWithFacing(z ? 1 : 0);
            this.camera = Camera.open(this.currentCameraId);
            this.camera.setPreviewCallbackWithBuffer(this);
            initRotateDegree(this.currentCameraId);
            if (this.camera != null) {
                this.mParams = this.camera.getParameters();
                List<Camera.Size> supportedPictureSizes = this.mParams.getSupportedPictureSizes();
                List<Camera.Size> supportedVideoSizes = this.mParams.getSupportedVideoSizes();
                List<Camera.Size> supportedPreviewSizes = this.mParams.getSupportedPreviewSizes();
                Logger.logCameraSizes(supportedPictureSizes);
                Logger.logCameraSizes(supportedVideoSizes);
                Logger.logCameraSizes(supportedPreviewSizes);
                this.previewSize = choosePreferredSize(supportedPreviewSizes, PREFERRED_RATIO);
                Camera.Size choosePreferredSize = choosePreferredSize(supportedPictureSizes, PREFERRED_RATIO);
                this.frameHeight = this.previewSize.width;
                this.frameWidth = this.previewSize.height;
                Log.d(TAG, "openCamera: choose preview size" + this.previewSize.height + "x" + this.previewSize.width);
                this.mParams.setPreviewSize(this.frameHeight, this.frameWidth);
                this.mParams.setPictureSize(choosePreferredSize.width, choosePreferredSize.height);
                Log.d(TAG, "openCamera: choose photo size" + choosePreferredSize.height + "x" + choosePreferredSize.width);
                int bitsPerPixel = ((this.frameWidth * this.frameHeight) * ImageFormat.getBitsPerPixel(this.mParams.getPreviewFormat())) / 8;
                if (this.mBuffer == null || this.mBuffer.length != bitsPerPixel) {
                    this.mBuffer = new byte[bitsPerPixel];
                }
                this.mFrameChain[0].init(bitsPerPixel);
                this.mFrameChain[1].init(bitsPerPixel);
                this.camera.addCallbackBuffer(this.mBuffer);
                this.camera.setParameters(this.mParams);
                this.cameraOpened = true;
            }
        }
    }

    public void startPreview() {
        this.lastZoomValue = 0;
        this.lastZoomValueRec = (double) 0;
        Camera camera2 = this.camera;
        if (camera2 != null) {
            try {
                camera2.setPreviewTexture(this.mSurfaceTexture);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.previewSizeChangedCallback.updatePreviewSize(this.frameWidth, this.frameHeight);
            this.camera.startPreview();
            this.mCameraFrameReady = false;
            this.mStopThread = false;
            this.mWorkerThread = new Thread(new CameraWorker());
            this.mWorkerThread.start();
        }
    }


    public void stopPreview() {
        synchronized (this) {
            if (this.camera != null) {
                this.mStopThread = true;
                synchronized (this) {
                    notify();
                    this.mWorkerThread = null;
                    this.camera.stopPreview();
                }
            }
        }
    }


    public void releaseCamera() {
        synchronized (this) {
            if (this.camera != null) {
                this.camera.setPreviewCallback((Camera.PreviewCallback) null);
                this.camera.release();
                this.camera = null;
            }
            this.cameraOpened = false;
        }
    }

    public void switchCamera(boolean z) {
        stopPreview();
        releaseCamera();
        openCamera(z);
        startPreview();
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.renderCallback.renderImmediately();
    }

    public static int getCameraIdWithFacing(int i) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == i) {
                return i2;
            }
        }
        return 0;
    }

    public static int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    public void onPreviewFrame(byte[] bArr, Camera camera2) {
        Log.d(TAG, "onPreviewFrame: ");
        synchronized (this) {
            this.mFrameChain[this.mChainIdx].putData(bArr);
            this.mCameraFrameReady = true;
            camera2.addCallbackBuffer(this.mBuffer);
            notify();
        }
    }

     class CameraWorker implements Runnable {
         CameraWorker() {
        }

        public void run() {
            do {
                synchronized (CameraEngine.this) {
                    while (!CameraEngine.this.mCameraFrameReady && !CameraEngine.this.mStopThread) {
                        try {
                            CameraEngine.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (CameraEngine.this.mCameraFrameReady) {
                        int unused = CameraEngine.this.mChainIdx = 1 - CameraEngine.this.mChainIdx;
                        boolean unused2 = CameraEngine.this.mCameraFrameReady = false;
                    }
                }
                boolean unused3 = CameraEngine.this.mStopThread;
            } while (!CameraEngine.this.mStopThread);
            Log.d(CameraEngine.TAG, "Finish processing thread");
        }
    }

    public void setRenderCallback(CameraView.RenderCallback renderCallback2) {
        this.renderCallback = renderCallback2;
    }

    public boolean isCameraOpened() {
        return this.cameraOpened;
    }

    public void focusCamera() {
        synchronized (this) {
            if (this.camera != null) {
                this.camera.cancelAutoFocus();
                this.camera.autoFocus(this);
            }
        }
    }

    public void requestOpenFlashLight(boolean z) {
        synchronized (this) {
            if (this.camera != null) {
                Camera.Parameters parameters = this.camera.getParameters();
                if (z) {
                    parameters.setFlashMode("torch");
                } else {
                    parameters.setFlashMode("auto");
                }
                this.camera.setParameters(parameters);
            }
        }
    }
    public void requestZoom(double d) {
        Log.d(TAG, "requestZoom: " + d + " " + this.lastZoomValueRec + " " + this.lastZoomValue);
        synchronized (this) {
            if (this.camera != null) {
                Camera.Parameters parameters = this.camera.getParameters();
                if (parameters.isZoomSupported()) {
                    this.lastZoomValueRec += d;
                    this.lastZoomValueRec = Math.max(0.0d, Math.min(this.lastZoomValueRec, 1.0d));
                    double d2 = this.lastZoomValueRec;
                    double maxZoom = (double) parameters.getMaxZoom();
                    Double.isNaN(maxZoom);
                    int i = (int) (d2 * maxZoom);
                    if (Math.abs(i - this.lastZoomValue) >= 1) {
                        this.lastZoomValue = i;
                        parameters.setZoom(this.lastZoomValue);
                    }
                    this.camera.setParameters(parameters);
                }
            }
        }
    }

    public void requestCloseFlashLight() {
        synchronized (this) {
            if (this.camera != null) {
                Camera.Parameters parameters = this.camera.getParameters();
                parameters.setFlashMode("off");
                this.camera.setParameters(parameters);
            }
        }
    }

    public PreviewSize getPreviewSize() {
        return new PreviewSize(this.frameWidth, this.frameHeight);
    }

    public void setPreviewSize(PreviewSize previewSize2) {
        this.frameWidth = previewSize2.width;
        this.frameHeight = previewSize2.height;
    }

    public class PreviewSize {

        public int height;

        public int width;

        public PreviewSize(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }

    public void setPreviewSizeChangedCallback(CameraView.PreviewSizeChangedCallback previewSizeChangedCallback2) {
        this.previewSizeChangedCallback = previewSizeChangedCallback2;
    }

     static Camera.Size choosePreferredSize(List<Camera.Size> list, double d) {
        ArrayList arrayList = new ArrayList();
        for (Camera.Size next : list) {
            if (next.width == 1280 && next.height == 720) {
                return next;
            }
            double d2 = (double) next.height;
            Double.isNaN(d2);
            if (Math.abs(((int) (d2 * d)) - next.width) < 10) {
                arrayList.add(next);
            }
        }
        if (arrayList.size() > 0) {
            return (Camera.Size) Collections.max(arrayList, new CompareSizesByArea());
        }
        return list.get(list.size() - 1);
    }

    static class CompareSizesByArea implements Comparator<Camera.Size> {
        CompareSizesByArea() {
        }

        public int compare(Camera.Size size, Camera.Size size2) {
            return Long.signum((((long) size.width) * ((long) size.height)) - (((long) size2.width) * ((long) size2.height)));
        }
    }

    public boolean startRecordingVideo() {
        if (!prepareMediaRecorder()) {
            return false;
        }
        try {
            this.mMediaRecorder.start();
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

     boolean prepareMediaRecorder() {
        try {
            this.mMediaRecorder = new MediaRecorder();
            this.camera.stopPreview();
            this.camera.unlock();
            this.mMediaRecorder.setCamera(this.camera);
            this.mMediaRecorder.setAudioSource(0);
            this.mMediaRecorder.setVideoSource(0);
            CamcorderProfile camcorderProfile = CamcorderProfile.get(this.currentCameraId, 1);
            this.mMediaRecorder.setOutputFormat(camcorderProfile.fileFormat);
            this.mMediaRecorder.setVideoFrameRate(camcorderProfile.videoFrameRate);
            this.mMediaRecorder.setVideoSize(this.previewSize.width, this.previewSize.height);
            this.mMediaRecorder.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
            this.mMediaRecorder.setVideoEncoder(camcorderProfile.videoCodec);
            this.mMediaRecorder.setAudioEncodingBitRate(camcorderProfile.audioBitRate);
            this.mMediaRecorder.setAudioChannels(camcorderProfile.audioChannels);
            this.mMediaRecorder.setAudioSamplingRate(camcorderProfile.audioSampleRate);
            this.mMediaRecorder.setAudioEncoder(camcorderProfile.audioCodec);
            this.mMediaRecorder.setOutputFile(Uri.fromFile(FileUtils.makeTempFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Omoshiroi/videos").getAbsolutePath(), "VID_", ".mp4")).getPath());
            this.mMediaRecorder.setOrientationHint(90);
            this.mMediaRecorder.prepare();
            return true;
        } catch (Exception e) {
            this.camera.lock();
            e.printStackTrace();
            return false;
        }
    }

    public final void releaseRecorder() {
        MediaRecorder mediaRecorder = this.mMediaRecorder;
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (Throwable th) {
                th.printStackTrace();
            }
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            this.camera.startPreview();
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.mSurfaceTexture;
    }


    public void initRotateDegree(int i) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        Log.d(TAG, "cameraId: " + i + ", rotation: " + cameraInfo.orientation);
        int i2 = 0;
        switch (((Activity) GlobalConfig.context).getWindowManager().getDefaultDisplay().getRotation()) {
            case 1:
                i2 = 90;
                break;
            case 2:
                i2 = 180;
                break;
            case 3:
                i2 = 270;
                break;
        }
        this.displayRotate = ((cameraInfo.orientation - i2) + 360) % 360;
    }

    public int getDisplayRotate() {
        return this.displayRotate;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void setGlRender(GLRender gLRender) {
        this.glRender = gLRender;
    }
}

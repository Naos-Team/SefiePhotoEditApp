package com.sweet.selfiecameraphotoeditor.cameraview;

import android.content.Context;
import android.hardware.Camera;

public class CameraHelperBase implements CameraHelper.CameraHelperImpl {
    private final Context mContext;

    public CameraHelperBase(Context context) {
        this.mContext = context;
    }

    public int getNumberOfCameras() {
        return hasCameraSupport() ? 1 : 0;
    }

    public Camera openCamera(int i) {
        return Camera.open();
    }

    public Camera openDefaultCamera() {
        return Camera.open();
    }

    public boolean hasCamera(int i) {
        if (i == 0) {
            return hasCameraSupport();
        }
        return false;
    }

    public Camera openCameraFacing(int i) {
        if (i == 0) {
            return Camera.open();
        }
        return null;
    }

    public void getCameraInfo(int i, CameraHelper.CameraInfo2 cameraInfo2) {
        cameraInfo2.facing = 0;
        cameraInfo2.orientation = 90;
    }

    private boolean hasCameraSupport() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera");
    }
}

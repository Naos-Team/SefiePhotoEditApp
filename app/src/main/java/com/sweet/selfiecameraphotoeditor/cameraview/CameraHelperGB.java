package com.sweet.selfiecameraphotoeditor.cameraview;

import android.annotation.TargetApi;
import android.hardware.Camera;

@TargetApi(9)
public class CameraHelperGB implements CameraHelper.CameraHelperImpl {
    public int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    public Camera openCamera(int i) {
        return Camera.open(i);
    }

    public Camera openDefaultCamera() {
        return Camera.open(0);
    }

    public boolean hasCamera(int i) {
        return getCameraId(i) != -1;
    }

    public Camera openCameraFacing(int i) {
        return Camera.open(getCameraId(i));
    }

    public void getCameraInfo(int i, CameraHelper.CameraInfo2 cameraInfo2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        cameraInfo2.facing = cameraInfo.facing;
        cameraInfo2.orientation = cameraInfo.orientation;
    }

    private int getCameraId(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == i) {
                return i2;
            }
        }
        return -1;
    }
}

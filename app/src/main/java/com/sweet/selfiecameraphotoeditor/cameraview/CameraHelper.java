package com.sweet.selfiecameraphotoeditor.cameraview;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;

public class CameraHelper {
    private final CameraHelperImpl mImpl;

    public interface CameraHelperImpl {
        void getCameraInfo(int i, CameraInfo2 cameraInfo2);

        int getNumberOfCameras();

        boolean hasCamera(int i);

        Camera openCamera(int i);

        Camera openCameraFacing(int i);

        Camera openDefaultCamera();
    }

    public static class CameraInfo2 {
        public int facing;
        public int orientation;
    }

    public CameraHelper(Context context) {
        if (Build.VERSION.SDK_INT >= 9) {
            this.mImpl = new CameraHelperGB();
        } else {
            this.mImpl = new CameraHelperBase(context);
        }
    }

    public int getNumberOfCameras() {
        return this.mImpl.getNumberOfCameras();
    }

    public Camera openCamera(int i) {
        return this.mImpl.openCamera(i);
    }

    public Camera openDefaultCamera() {
        return this.mImpl.openDefaultCamera();
    }

    public Camera openFrontCamera() {
        return this.mImpl.openCameraFacing(1);
    }

    public Camera openBackCamera() {
        return this.mImpl.openCameraFacing(0);
    }

    public boolean hasFrontCamera() {
        return this.mImpl.hasCamera(1);
    }

    public boolean hasBackCamera() {
        return this.mImpl.hasCamera(0);
    }

    public void getCameraInfo(int i, CameraInfo2 cameraInfo2) {
        this.mImpl.getCameraInfo(i, cameraInfo2);
    }

    public void setCameraDisplayOrientation(Activity activity, int i, Camera camera) {
        camera.setDisplayOrientation(getCameraDisplayOrientation(activity, i));
    }

    public int getCameraDisplayOrientation(Activity activity, int i) {
        int i2;
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case 1:
                i2 = 90;
                break;
            case 2:
                i2 = 180;
                break;
            case 3:
                i2 = 270;
                break;
            default:
                i2 = 0;
                break;
        }
        CameraInfo2 cameraInfo2 = new CameraInfo2();
        getCameraInfo(i, cameraInfo2);
        if (cameraInfo2.facing == 1) {
            return (cameraInfo2.orientation + i2) % 360;
        }
        return ((cameraInfo2.orientation - i2) + 360) % 360;
    }
}

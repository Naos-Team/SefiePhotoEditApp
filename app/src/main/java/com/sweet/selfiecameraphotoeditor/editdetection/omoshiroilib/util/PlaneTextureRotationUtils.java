package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.constant.Rotation;

public class PlaneTextureRotationUtils {
    public static final float[] TEXTURE_NO_ROTATION = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    public static final float[] TEXTURE_ROTATED_180 = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    public static final float[] TEXTURE_ROTATED_270 = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] TEXTURE_ROTATED_90 = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};

    private static float flip(float f) {
        return 1.0f - f;
    }

    private PlaneTextureRotationUtils() {
    }

    public static float[] getRotation(Rotation rotation, boolean z, boolean z2) {
        float[] fArr;
        switch (rotation) {
            case ROTATION_90:
                fArr = TEXTURE_ROTATED_90;
                break;
            case ROTATION_180:
                fArr = TEXTURE_ROTATED_180;
                break;
            case ROTATION_270:
                fArr = TEXTURE_ROTATED_270;
                break;
            default:
                fArr = TEXTURE_NO_ROTATION;
                break;
        }
        if (z) {
            fArr = new float[]{flip(fArr[0]), fArr[1], flip(fArr[2]), fArr[3], flip(fArr[4]), fArr[5], flip(fArr[6]), fArr[7]};
        }
        if (!z2) {
            return fArr;
        }
        return new float[]{fArr[0], flip(fArr[1]), fArr[2], flip(fArr[3]), fArr[4], flip(fArr[5]), fArr[6], flip(fArr[7])};
    }
}

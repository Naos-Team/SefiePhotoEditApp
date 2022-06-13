package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.opengl.Matrix;

public class MatrixUtils {
    public static void updateProjectionFit(int i, int i2, int i3, int i4, float[] fArr) {
        float f = ((float) i3) / ((float) i4);
        float f2 = ((float) i) / ((float) i2);
        if (f2 > f) {
            Matrix.orthoM(fArr, 0, -1.0f, 1.0f, (-f2) / f, f2 / f, -1.0f, 1.0f);
            return;
        }
        Matrix.orthoM(fArr, 0, (-f) / f2, f / f2, -1.0f, 1.0f, -1.0f, 1.0f);
    }

    public static void updateProjectionCrop(int i, int i2, int i3, int i4, float[] fArr) {
        float f = ((float) i3) / ((float) i4);
        float f2 = ((float) i) / ((float) i2);
        if (f2 < f) {
            Matrix.orthoM(fArr, 0, -1.0f, 1.0f, (-f2) / f, f2 / f, -1.0f, 1.0f);
            return;
        }
        Matrix.orthoM(fArr, 0, (-f) / f2, f / f2, -1.0f, 1.0f, -1.0f, 1.0f);
    }
}

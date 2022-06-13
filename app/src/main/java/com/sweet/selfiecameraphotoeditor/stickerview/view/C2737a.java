package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class C2737a {
    protected static float m18218a(float f, float f2) {
        return Math.min(f2, Math.max(-f2, f));
    }

    public static ColorFilter m18219a(float f) {
        ColorMatrix colorMatrix = new ColorMatrix();
        m18220a(colorMatrix, f);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    public static void m18220a(ColorMatrix colorMatrix, float f) {
        float m18218a = (m18218a(f, 180.0f) / 180.0f) * 3.1415927f;
        if (m18218a != 0.0f) {
            double d = (double) m18218a;
            float[] fArr = new float[25];
            fArr[12] = (((float) Math.sin(d)) * 0.072f) + (((float) Math.cos(d)) * 0.928f) + 0.072f;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
            fArr[20] = 0.0f;
            fArr[21] = 0.0f;
            fArr[22] = 0.0f;
            fArr[23] = 0.0f;
            fArr[24] = 1.0f;
            colorMatrix.postConcat(new ColorMatrix(fArr));
        }
    }
}

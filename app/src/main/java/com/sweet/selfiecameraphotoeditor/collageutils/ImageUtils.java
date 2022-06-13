package com.sweet.selfiecameraphotoeditor.collageutils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.support.v4.media.session.PlaybackStateCompat;

import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtils {
    private static final float MIN_OUTPUT_IMAGE_SIZE = 640.0f;

    public static class MemoryInfo {
        public long availMem = 0;
        public long totalMem = 0;
    }

    public static MemoryInfo getMemoryInfo(Context context) {
        MemoryInfo memoryInfo = new MemoryInfo();
        ActivityManager.MemoryInfo memoryInfo2 = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo2);
        memoryInfo.availMem = memoryInfo2.availMem;
        if (Build.VERSION.SDK_INT >= 16) {
            memoryInfo.totalMem = memoryInfo2.totalMem;
        } else {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
                Matcher matcher = Pattern.compile("(\\d+)").matcher(randomAccessFile.readLine());
                String str = "";
                while (matcher.find()) {
                    str = matcher.group(1);
                }
                randomAccessFile.close();
                memoryInfo.totalMem = ((long) Double.parseDouble(str)) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return memoryInfo;
    }

    public static float calculateOutputScaleFactor(int i, int i2) {
        float min = ((float) Math.min(i, i2)) / MIN_OUTPUT_IMAGE_SIZE;
        if (min >= 1.0f || min <= 0.0f) {
            return 1.0f;
        }
        return 1.0f / min;
    }

    public static float pxFromDp(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }

    public static Matrix createMatrixToDrawImageInCenterView(float f, float f2, float f3, float f4) {
        float max = Math.max(f / f3, f2 / f4);
        Matrix matrix = new Matrix();
        matrix.postTranslate((f - f3) / 2.0f, (f2 - f4) / 2.0f);
        matrix.postScale(max, max, f / 2.0f, f2 / 2.0f);
        return matrix;
    }
}

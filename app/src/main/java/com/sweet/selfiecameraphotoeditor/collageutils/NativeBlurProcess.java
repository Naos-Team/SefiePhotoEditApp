package com.sweet.selfiecameraphotoeditor.collageutils;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.concurrent.Callable;

class NativeBlurProcess implements BlurProcess {

    public static native void functionToBlur(Bitmap bitmap, int i, int i2, int i3, int i4);

    NativeBlurProcess() {
    }

    static {
        System.loadLibrary("img_ps");
    }

    public Bitmap blur(Bitmap bitmap, float f) {
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        int i = StackBlurManager.EXECUTOR_THREADS;
        ArrayList arrayList = new ArrayList(i);
        ArrayList arrayList2 = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            Bitmap bitmap2 = copy;
            int i3 = (int) f;
            int i4 = i;
            int i5 = i2;
            arrayList.add(new NativeTask(bitmap2, i3, i4, i5, 1));
            arrayList2.add(new NativeTask(bitmap2, i3, i4, i5, 2));
        }
        try {
            StackBlurManager.EXECUTOR.invokeAll(arrayList);
            try {
                StackBlurManager.EXECUTOR.invokeAll(arrayList2);
                return copy;
            } catch (InterruptedException unused) {
                return copy;
            }
        } catch (InterruptedException unused2) {
            return copy;
        }
    }

    private static class NativeTask implements Callable<Void> {
        private final Bitmap mBitmapOut;
        private final int mCoreIndex;
        private final int mRadius;
        private final int mRound;
        private final int mTotalCores;

        public NativeTask(Bitmap bitmap, int i, int i2, int i3, int i4) {
            this.mBitmapOut = bitmap;
            this.mRadius = i;
            this.mTotalCores = i2;
            this.mCoreIndex = i3;
            this.mRound = i4;
        }

        public Void call() throws Exception {
            NativeBlurProcess.functionToBlur(this.mBitmapOut, this.mRadius, this.mTotalCores, this.mCoreIndex, this.mRound);
            return null;
        }
    }
}

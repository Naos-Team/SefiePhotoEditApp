package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib;

import android.graphics.Bitmap;

public class filtercommom {
    private static filtercommom mInstance;
    public Bitmap bitmap;
    public String path;

    public static filtercommom getInstance() {
        if (mInstance == null) {
            mInstance = new filtercommom();
        }
        return mInstance;
    }
}

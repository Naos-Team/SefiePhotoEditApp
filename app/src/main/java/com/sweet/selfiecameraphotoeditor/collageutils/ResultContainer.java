package com.sweet.selfiecameraphotoeditor.collageutils;

import android.graphics.Bitmap;

import java.util.HashMap;

public class ResultContainer {
    private static ResultContainer instance;
    private HashMap<String, Bitmap> mDecodedImageMap = new HashMap<>();

    public static ResultContainer getInstance() {
        if (instance == null) {
            instance = new ResultContainer();
        }
        return instance;
    }

    private ResultContainer() {
    }

    public void putImage(String str, Bitmap bitmap) {
        this.mDecodedImageMap.put(str, bitmap);
    }

    public Bitmap getImage(String str) {
        return this.mDecodedImageMap.get(str);
    }
}

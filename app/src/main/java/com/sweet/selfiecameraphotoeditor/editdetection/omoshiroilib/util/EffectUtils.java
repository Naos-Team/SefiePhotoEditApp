package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.flyu.hardcode.HardCodeData;

public class EffectUtils {
    public static Bitmap getEffectThumbFromFile(Context context, HardCodeData.EffectItem effectItem) {
        return BitmapUtils.loadBitmapFromAssets(context, effectItem.getThumbFilePath());
    }
}

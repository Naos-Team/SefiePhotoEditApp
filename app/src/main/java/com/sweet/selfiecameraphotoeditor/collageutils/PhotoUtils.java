package com.sweet.selfiecameraphotoeditor.collageutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

public class PhotoUtils {
    public static final String ASSET_PREFIX = "assets://";
    public static final String DRAWABLE_PREFIX = "drawable://";

    public static Bitmap decodePNGImage(Context context, String str) {
        if (str.startsWith(DRAWABLE_PREFIX)) {
            try {
                return BitmapFactory.decodeResource(context.getResources(), Integer.parseInt(str.substring(11)));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (!str.startsWith(ASSET_PREFIX)) {
            return BitmapFactory.decodeFile(str);
        } else {
            try {
                return BitmapFactory.decodeStream(context.getAssets().open(str.substring(9)));
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap blurImage(Bitmap bitmap, float f) {
        return new StackBlurManager(bitmap).processNatively((int) f);
    }

    public static float calculateScaleRatio(int i, int i2, int i3, int i4) {
        return Math.max(((float) i) / ((float) i3), ((float) i2) / ((float) i4));
    }
}

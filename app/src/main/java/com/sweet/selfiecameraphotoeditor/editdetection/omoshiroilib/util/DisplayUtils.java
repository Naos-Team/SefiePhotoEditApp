package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.content.Context;

public class DisplayUtils {
    public static int getRefLength(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.xiuxiuxiu;

import android.content.Context;
import android.util.Log;

public class XiuXiuXiuFilterWrapper extends XiuXiuXiuAbsFilter {
    public XiuXiuXiuFilterWrapper(Context context, String str) {
        super(context, "filter/fsh/xiuxiuxiu/" + str.toLowerCase() + ".glsl", "filter/textures/xiuxiuxiu/" + str.toLowerCase() + ".idx", "filter/textures/xiuxiuxiu/" + str.toLowerCase() + ".dat");
        StringBuilder sb = new StringBuilder();
        sb.append("XiuXiuXiuFilterWrapper: ");
        sb.append(str);
        Log.d("AbsFilter", sb.toString());
    }
}

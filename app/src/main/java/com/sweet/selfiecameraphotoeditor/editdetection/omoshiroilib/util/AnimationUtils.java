package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

public class AnimationUtils {
    public static void displayAnim(View view, Context context, int i, int i2) {
        view.clearAnimation();
        Animation loadAnimation = android.view.animation.AnimationUtils.loadAnimation(context, i);
        view.setVisibility(i2);
        view.startAnimation(loadAnimation);
    }
}

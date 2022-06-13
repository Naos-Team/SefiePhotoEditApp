package com.sweet.selfiecameraphotoeditor.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {
    public CustomTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public CustomTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    @SuppressLint({"WrongConstant"})
    public void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/Poppins-Regular.otf"), 1);
    }
}

package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.graphics.PointF;

public class C2736c extends PointF {
    public C2736c(float f, float f2) {
        super(f, f2);
    }

    public static float m18216a(C2736c c2736c, C2736c c2736c2) {
        c2736c.m18217a();
        c2736c2.m18217a();
        return (float) ((Math.atan2((double) c2736c2.y, (double) c2736c2.x) - Math.atan2((double) c2736c.y, (double) c2736c.x)) * 57.29577951308232d);
    }

    public void m18217a() {
        float sqrt = (float) Math.sqrt((double) ((this.x * this.x) + (this.y * this.y)));
        this.x /= sqrt;
        this.y /= sqrt;
    }
}

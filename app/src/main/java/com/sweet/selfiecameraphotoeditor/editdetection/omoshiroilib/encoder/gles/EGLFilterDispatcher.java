package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.encoder.gles;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec.RenderHandler;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;

public class EGLFilterDispatcher extends PassThroughFilter implements RenderHandler.EGLDrawer {
    public EGLFilterDispatcher(Context context) {
        super(context);
    }

    public void deInit() {
        destroy();
    }

    public void draw(int i) {
        onDrawFrame(i);
    }

    public void setViewportSize(int i, int i2) {
        onFilterChanged(i, i2);
    }
}

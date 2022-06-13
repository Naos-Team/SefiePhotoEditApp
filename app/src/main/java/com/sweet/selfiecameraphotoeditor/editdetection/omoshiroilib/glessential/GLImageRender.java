package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.AbsFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterFactory;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterType;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.texture.BitmapTexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLImageRender implements GLSurfaceView.Renderer {
    private Bitmap bitmap;
    private BitmapTexture bitmapTexture = new BitmapTexture();
    private Context context;
    private AbsFilter filter;

    public GLImageRender(Context context2, Bitmap bitmap2, FilterType filterType) {
        this.context = context2;
        this.filter = FilterFactory.createFilter(filterType, context2).resetPlane(false);
        this.bitmap = bitmap2;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.filter.init();
        this.bitmapTexture.loadBitmap(this.bitmap);
    }

    public void onDrawFrame(GL10 gl10) {
        this.filter.onDrawFrame(this.bitmapTexture.getImageTextureId());
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        GLES20.glViewport(0, 0, i, i2);
        this.filter.onFilterChanged(i, i2);
    }
}

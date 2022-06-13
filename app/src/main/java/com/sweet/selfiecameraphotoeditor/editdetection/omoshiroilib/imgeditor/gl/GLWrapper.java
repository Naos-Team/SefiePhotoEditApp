package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.imgeditor.gl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.FilterGroup;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterFactory;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterType;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.imgeditor.MiscUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BitmapUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLWrapper implements GLSurfaceView.Renderer {
    private Context context;
    private FilterGroup customizedFilters;
    private String filePath;
    private FilterGroup filterGroup;
    private GLSurfaceView glImageView;
    private int surfaceHeight;
    private int surfaceWidth;
    private int textureId = 0;

    private GLWrapper() {
    }

    public static GLWrapper newInstance() {
        return new GLWrapper();
    }

    public GLWrapper init() {
        this.glImageView.setEGLContextClientVersion(2);
        this.glImageView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.glImageView.getHolder().setFormat(-3);
        this.glImageView.setRenderer(this);
        this.glImageView.setRenderMode(1);
        if (Build.VERSION.SDK_INT >= 11) {
            this.glImageView.setPreserveEGLContextOnPause(true);
        }
        this.filterGroup = new FilterGroup();
        this.customizedFilters = new FilterGroup();
        this.filterGroup.addFilter(new FirstPassFilter(this.context));
        this.customizedFilters.addFilter(new PassThroughFilter(this.context));
        this.filterGroup.addFilter(this.customizedFilters);
        return this;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.filterGroup.init();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.surfaceWidth = i;
        this.surfaceHeight = i2;
        this.filterGroup.onFilterChanged(i, i2);
        if (!MiscUtils.isNull(this.filePath)) {
            this.textureId = TextureUtils.loadTextureWithOldTexId(BitmapUtils.loadBitmapFromFile(this.filePath), 0);
            Log.d("GL_THREAD", this.textureId + " ");
        }
    }

    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16640);
        int i = this.textureId;
        if (i != 0) {
            this.filterGroup.onDrawFrame(i);
        }
    }

    public GLWrapper setGlImageView(GLSurfaceView gLSurfaceView) {
        this.glImageView = gLSurfaceView;
        return this;
    }

    public GLWrapper setContext(Context context2) {
        this.context = context2;
        return this;
    }

    public void setTextureId(int i) {
        this.textureId = i;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public void switchLastFilterOfCustomizedFilters(FilterType filterType) {
        if (filterType != null) {
            this.customizedFilters.switchLastFilter(FilterFactory.createFilter(filterType, this.context));
        }
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.object.Plane;

import java.nio.FloatBuffer;
import java.util.LinkedList;

public abstract class AbsFilter {
    protected static final String TAG = "AbsFilter";
    private final LinkedList<Runnable> mPostDrawTaskList = new LinkedList<>();
    private final LinkedList<Runnable> mPreDrawTaskList = new LinkedList<>();
    protected Plane plane = new Plane(true);
    protected int surfaceHeight;
    protected int surfaceWidth;

    public abstract void destroy();

    public abstract void init();

    public abstract void onDrawFrame(int i);

    public void onPreDrawElements() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
    }

    public void onFilterChanged(int i, int i2) {
        this.surfaceWidth = i;
        this.surfaceHeight = i2;
    }


    public void setViewport() {
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
    }

    public void runPreDrawTasks() {
        while (!this.mPreDrawTaskList.isEmpty()) {
            this.mPreDrawTaskList.removeFirst().run();
        }
    }

    public void addPreDrawTask(Runnable runnable) {
        synchronized (this.mPreDrawTaskList) {
            this.mPreDrawTaskList.addLast(runnable);
        }
    }

    public void runPostDrawTasks() {
        while (!this.mPostDrawTaskList.isEmpty()) {
            this.mPostDrawTaskList.removeFirst().run();
        }
    }

    public void addPostDrawTask(Runnable runnable) {
        synchronized (this.mPostDrawTaskList) {
            this.mPostDrawTaskList.addLast(runnable);
        }
    }

    public void setUniform1i(int i, String str, int i2) {
        GLES20.glUniform1i(GLES20.glGetUniformLocation(i, str), i2);
    }

    public void setUniform1f(int i, String str, float f) {
        GLES20.glUniform1f(GLES20.glGetUniformLocation(i, str), f);
    }

    public void setUniform2fv(int i, String str, float[] fArr) {
        GLES20.glUniform2fv(GLES20.glGetUniformLocation(i, str), 1, FloatBuffer.wrap(fArr));
    }

    public int getSurfaceWidth() {
        return this.surfaceWidth;
    }

    public int getSurfaceHeight() {
        return this.surfaceHeight;
    }

    public Plane getPlane() {
        return this.plane;
    }

    public AbsFilter resetPlane(boolean z) {
        this.plane.resetTextureCoordinateBuffer(z);
        return this;
    }
}

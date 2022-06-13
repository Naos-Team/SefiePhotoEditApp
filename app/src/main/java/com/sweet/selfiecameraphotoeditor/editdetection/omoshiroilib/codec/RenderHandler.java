package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec;

import android.graphics.SurfaceTexture;
import android.opengl.EGLContext;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

public final class RenderHandler implements Runnable {
     static final boolean DEBUG = false;
     static final String TAG = "RenderHandler";
     EGLDrawer eglDrawer;
     int height;
     EGLBase mEgl;
     EGLBase.EglSurface mInputSurface;
     boolean mIsRecordable;
     int mRequestDraw;
     boolean mRequestRelease;
     boolean mRequestSetEglContext;
     EGLContext mshardContext;
     Object mSurface;
     final Object mSync = new Object();
     int mTexId = -1;
     int width;

    public interface EGLDrawer {
        void deInit();

        void draw(int i);

        void init();

        void setViewportSize(int i, int i2);
    }


    public static final RenderHandler createHandler(String str, int i, int i2) {
        RenderHandler renderHandler = new RenderHandler(i, i2);
        synchronized (renderHandler.mSync) {
            if (TextUtils.isEmpty(str)) {
                str = TAG;
            }
            new Thread(renderHandler, str).start();
            try {
                renderHandler.mSync.wait();
            } catch (InterruptedException unused) {
            }
        }
        return renderHandler;
    }


    public RenderHandler(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public final void setEglContext(EGLContext eGLContext, int i, Object obj, boolean z) {
        if ((obj instanceof Surface) || (obj instanceof SurfaceTexture) || (obj instanceof SurfaceHolder)) {
            synchronized (this.mSync) {
                if (!this.mRequestRelease) {
                    this.mshardContext = eGLContext;
                    this.mTexId = i;
                    this.mSurface = obj;
                    this.mIsRecordable = z;
                    this.mRequestSetEglContext = true;
                    this.mSync.notifyAll();
                    try {
                        this.mSync.wait();
                    } catch (InterruptedException unused) {
                    }
                    return;
                }
                return;
            }
        }
        throw new RuntimeException("unsupported window type:" + obj);
    }
    public final void draw() {
        draw(this.mTexId);
    }

    public final void draw(int i) {
        synchronized (this.mSync) {
            if (!this.mRequestRelease) {
                this.mTexId = i;
                this.mRequestDraw++;
                this.mSync.notifyAll();
            }
        }
    }

    public boolean isValid() {
        boolean z;
        synchronized (this.mSync) {
            if (this.mSurface instanceof Surface) {
                if (!((Surface) this.mSurface).isValid()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public final void release() {
        synchronized (this.mSync) {
            if (!this.mRequestRelease) {
                this.mRequestRelease = true;
                this.mSync.notifyAll();
                try {
                    this.mSync.wait();
                } catch (InterruptedException unused) {
                    Log.d("","");
                }
            }
        }
    }


    public final void run() {
        if (DEBUG) Log.i(TAG, "RenderHandler thread started:");
        synchronized (mSync) {
            this.mRequestRelease = false;
            this.mRequestSetEglContext = false;
            this.mRequestDraw = 0;
            this.mSync.notifyAll();
        }
        boolean localRequestDraw;
        for (; ; ) {
            synchronized (mSync) {
                if (mRequestRelease) break;
                if (mRequestSetEglContext) {
                    mRequestSetEglContext = false;
                    internalPrepare();
                }
                localRequestDraw = mRequestDraw > 0;
                if (localRequestDraw) {
                    mRequestDraw--;

                }
            }
            if (localRequestDraw) {
                if ((mEgl != null) && mTexId >= 0) {
                    mInputSurface.makeCurrent();
                    draw(mTexId);
                    mInputSurface.swap();
                }
            } else {
                synchronized (mSync) {
                    try {
                        mSync.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }
        synchronized (mSync) {
            mRequestRelease = true;
            internalRelease();
            mSync.notifyAll();
        }
        if (DEBUG) Log.i(TAG, "RenderHandler thread finished:");
    }


     final void internalPrepare() {
        internalRelease();
        this.mEgl = new EGLBase(this.mshardContext, false, this.mIsRecordable);
        this.mInputSurface = this.mEgl.createFromSurface(this.mSurface);
        this.mInputSurface.makeCurrent();
        this.eglDrawer.init();
        this.eglDrawer.setViewportSize(this.width, this.height);
        this.mSurface = null;
        this.mSync.notifyAll();
    }

     final void internalRelease() {
        EGLBase.EglSurface eglSurface = this.mInputSurface;
        if (eglSurface != null) {
            eglSurface.release();
            this.mInputSurface = null;
        }
        this.eglDrawer.deInit();
        EGLBase eGLBase = this.mEgl;
        if (eGLBase != null) {
            eGLBase.release();
            this.mEgl = null;
        }
    }

    public void setEglDrawer(EGLDrawer eGLDrawer) {
        this.eglDrawer = eGLDrawer;
    }
}

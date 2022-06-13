package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@TargetApi(18)
public class EGLBase {
     static final boolean DEBUG = false;
     static final int EGL_RECORDABLE_ANDROID = 12610;
     static final String TAG = "EGLBase";
     EGLContext mDefaultContext = EGL14.EGL_NO_CONTEXT;
     EGLConfig mEglConfig = null;
     EGLContext mEglContext = EGL14.EGL_NO_CONTEXT;
     EGLDisplay mEglDisplay = EGL14.EGL_NO_DISPLAY;

    public static class EglSurface {
         final EGLBase mEgl;
         EGLSurface mEglSurface = EGL14.EGL_NO_SURFACE;
         final int mHeight;
         final int mWidth;

        EglSurface(EGLBase eGLBase, Object obj) {
            if ((obj instanceof SurfaceView) || (obj instanceof Surface) || (obj instanceof SurfaceHolder) || (obj instanceof SurfaceTexture)) {
                this.mEgl = eGLBase;
                this.mEglSurface = this.mEgl.createWindowSurface(obj);
                this.mWidth = this.mEgl.querySurface(this.mEglSurface, 12375);
                this.mHeight = this.mEgl.querySurface(this.mEglSurface, 12374);
                return;
            }
            throw new IllegalArgumentException("unsupported surface");
        }

        EglSurface(EGLBase eGLBase, int i, int i2) {
            this.mEgl = eGLBase;
            this.mEglSurface = this.mEgl.createOffscreenSurface(i, i2);
            this.mWidth = i;
            this.mHeight = i2;
        }

        public void makeCurrent() {
            boolean unused = this.mEgl.makeCurrent(this.mEglSurface);
        }

        public void swap() {
            int unused = this.mEgl.swap(this.mEglSurface);
        }

        public EGLContext getContext() {
            return this.mEgl.getContext();
        }

        public void release() {
            this.mEgl.makeDefault();
            this.mEgl.destroyWindowSurface(this.mEglSurface);
            this.mEglSurface = EGL14.EGL_NO_SURFACE;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }
    }

    public EGLBase(EGLContext eGLContext, boolean z, boolean z2) {
        init(eGLContext, z, z2);
    }

    public void release() {
        if (this.mEglDisplay != EGL14.EGL_NO_DISPLAY) {
            destroyContext();
            EGL14.eglTerminate(this.mEglDisplay);
            EGL14.eglReleaseThread();
        }
        this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
        this.mEglContext = EGL14.EGL_NO_CONTEXT;
    }

    public EglSurface createFromSurface(Object obj) {
        EglSurface eglSurface = new EglSurface(this, obj);
        eglSurface.makeCurrent();
        return eglSurface;
    }

    public EglSurface createOffscreen(int i, int i2) {
        EglSurface eglSurface = new EglSurface(this, i, i2);
        eglSurface.makeCurrent();
        return eglSurface;
    }

    public EGLContext getContext() {
        return this.mEglContext;
    }

    public int querySurface(EGLSurface eGLSurface, int i) {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.mEglDisplay, eGLSurface, i, iArr, 0);
        return iArr[0];
    }

     void init(EGLContext eGLContext, boolean z, boolean z2) {
        if (this.mEglDisplay == EGL14.EGL_NO_DISPLAY) {
            this.mEglDisplay = EGL14.eglGetDisplay(0);
            if (this.mEglDisplay != EGL14.EGL_NO_DISPLAY) {
                int[] iArr = new int[2];
                if (EGL14.eglInitialize(this.mEglDisplay, iArr, 0, iArr, 1)) {
                    if (eGLContext == null) {
                        eGLContext = EGL14.EGL_NO_CONTEXT;
                    }
                    if (this.mEglContext == EGL14.EGL_NO_CONTEXT) {
                        this.mEglConfig = getConfig(z, z2);
                        if (this.mEglConfig != null) {
                            this.mEglContext = createContext(eGLContext);
                        } else {
                            throw new RuntimeException("chooseConfig failed");
                        }
                    }
                    EGL14.eglQueryContext(this.mEglDisplay, this.mEglContext, 12440, new int[1], 0);
                    makeDefault();
                    return;
                }
                this.mEglDisplay = null;
                throw new RuntimeException("eglInitialize failed");
            }
            throw new RuntimeException("eglGetDisplay failed");
        }
        throw new RuntimeException("EGL already set up");
    }


    public boolean makeCurrent(EGLSurface eGLSurface) {
        EGLDisplay eGLDisplay = this.mEglDisplay;
        if (eGLSurface == null || eGLSurface == EGL14.EGL_NO_SURFACE) {
            if (EGL14.eglGetError() == 12299) {
                Log.e(TAG, "makeCurrent:returned EGL_BAD_NATIVE_WINDOW.");
            }
            return false;
        } else if (EGL14.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, this.mEglContext)) {
            return true;
        } else {
            Log.w(TAG, "eglMakeCurrent:" + EGL14.eglGetError());
            return false;
        }
    }


    public void makeDefault() {
        if (!EGL14.eglMakeCurrent(this.mEglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)) {
            Log.w("TAG", "makeDefault" + EGL14.eglGetError());
        }
    }


    public int swap(EGLSurface eGLSurface) {
        if (!EGL14.eglSwapBuffers(this.mEglDisplay, eGLSurface)) {
            return EGL14.eglGetError();
        }
        return 12288;
    }

     EGLContext createContext(EGLContext eGLContext) {
        EGLContext eglCreateContext = EGL14.eglCreateContext(this.mEglDisplay, this.mEglConfig, eGLContext, new int[]{12440, 2, 12344}, 0);
        checkEglError("eglCreateContext");
        return eglCreateContext;
    }

     void destroyContext() {
        if (!EGL14.eglDestroyContext(this.mEglDisplay, this.mEglContext)) {
            Log.e("destroyContext", "display:" + this.mEglDisplay + " context: " + this.mEglContext);
            StringBuilder sb = new StringBuilder();
            sb.append("eglDestroyContex:");
            sb.append(EGL14.eglGetError());
            Log.e(TAG, sb.toString());
        }
        this.mEglContext = EGL14.EGL_NO_CONTEXT;
        if (this.mDefaultContext != EGL14.EGL_NO_CONTEXT) {
            if (!EGL14.eglDestroyContext(this.mEglDisplay, this.mDefaultContext)) {
                Log.e("destroyContext", "display:" + this.mEglDisplay + " context: " + this.mDefaultContext);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("eglDestroyContex:");
                sb2.append(EGL14.eglGetError());
                Log.e(TAG, sb2.toString());
            }
            this.mDefaultContext = EGL14.EGL_NO_CONTEXT;
        }
    }


    public EGLSurface createWindowSurface(Object obj) {
        try {
            return EGL14.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, obj, new int[]{12344}, 0);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "eglCreateWindowSurface", e);
            return null;
        }
    }


    public EGLSurface createOffscreenSurface(int i, int i2) {
        int[] iArr = {12375, i, 12374, i2, 12344};
        EGLSurface eGLSurface = null;
        try {
            eGLSurface = EGL14.eglCreatePbufferSurface(this.mEglDisplay, this.mEglConfig, iArr, 0);
            checkEglError("eglCreatePbufferSurface");
            if (eGLSurface != null) {
                return eGLSurface;
            }
            throw new RuntimeException("surface was null");
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "createOffscreenSurface", e);
        } catch (RuntimeException e2) {
            Log.e(TAG, "createOffscreenSurface", e2);
        }

        return eGLSurface;
    }


    public void destroyWindowSurface(EGLSurface eGLSurface) {
        if (eGLSurface != EGL14.EGL_NO_SURFACE) {
            EGL14.eglMakeCurrent(this.mEglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.mEglDisplay, eGLSurface);
        }
        EGLSurface eGLSurface2 = EGL14.EGL_NO_SURFACE;
    }

     void checkEglError(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }

     EGLConfig getConfig(boolean z, boolean z2) {
        int[] iArr = {12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344, 12344, 12344, 12344, 12344, 12344, 12344};
        int i = 10;
        if (z) {
            iArr[10] = 12325;
            i = 12;
            iArr[11] = 16;
        }
        if (z2 && Build.VERSION.SDK_INT >= 18) {
            int i2 = i + 1;
            iArr[i] = EGL_RECORDABLE_ANDROID;
            i = i2 + 1;
            iArr[i2] = 1;
        }
        for (int length = iArr.length - 1; length >= i; length--) {
            iArr[length] = 12344;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.mEglDisplay, iArr, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0)) {
            return eGLConfigArr[0];
        }
        Log.w(TAG, "unable to find RGBA8888 /  EGLConfig");
        return null;
    }
}

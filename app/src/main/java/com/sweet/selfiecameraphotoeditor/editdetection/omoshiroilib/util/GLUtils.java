package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.opengl.GLES20;
import android.util.Log;

public class GLUtils {
    public static final boolean CHECK_GLES30 = false;
    private static final String TAG = "GLUtils";

    public static void logVersionInfo() {
        Log.i(TAG, "vendor  : " + GLES20.glGetString(7936));
        Log.i(TAG, "renderer: " + GLES20.glGetString(7937));
        Log.i(TAG, "version : " + GLES20.glGetString(7938));
    }
}

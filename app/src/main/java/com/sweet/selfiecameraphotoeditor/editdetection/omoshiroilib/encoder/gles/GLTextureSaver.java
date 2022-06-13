package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.encoder.gles;

import android.content.Context;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;

public class GLTextureSaver extends PassThroughFilter {
    private FrameAvailableCallback frameAvailableCallback = null;
    private int lastTextureId;
    private int savedTextureId = 0;

    public interface FrameAvailableCallback {
        void onFrameAvailable(int i);
    }

    public GLTextureSaver(Context context) {
        super(context);
    }

    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
        this.lastTextureId = i;
        FrameAvailableCallback frameAvailableCallback2 = this.frameAvailableCallback;
        if (frameAvailableCallback2 != null) {
            frameAvailableCallback2.onFrameAvailable(i);
        }
    }

    public void setSavedTextureId(int i) {
        this.savedTextureId = i;
    }

    public int getSavedTextureId() {
        return this.savedTextureId;
    }

    public int getLastTextureId() {
        return this.lastTextureId;
    }

    public void setFrameAvailableCallback(FrameAvailableCallback frameAvailableCallback2) {
        this.frameAvailableCallback = frameAvailableCallback2;
    }
}

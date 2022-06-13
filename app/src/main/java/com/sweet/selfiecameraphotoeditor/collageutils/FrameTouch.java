package com.sweet.selfiecameraphotoeditor.collageutils;

public abstract class FrameTouch implements OnFrameTouchListener {
    private boolean mImageFrameMoving = false;

    public boolean isImageFrameMoving() {
        return this.mImageFrameMoving;
    }
}

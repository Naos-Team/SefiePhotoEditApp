package com.sweet.selfiecameraphotoeditor.collageutils.custom;

import com.sweet.selfiecameraphotoeditor.collageutils.MultiTouchEntity;

public interface OnDoubleClickListener {
    void onBackgroundDoubleClick();

    void onPhotoViewDoubleClick(PhotoView photoView, MultiTouchEntity multiTouchEntity);
}

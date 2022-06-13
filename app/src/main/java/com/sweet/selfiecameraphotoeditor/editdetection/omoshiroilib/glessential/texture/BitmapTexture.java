package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BitmapUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class BitmapTexture {
    private int[] imageSize = new int[2];
    private int imageTextureId;

    public BitmapTexture load(Context context, String str) {
        return loadBitmap(BitmapUtils.loadBitmapFromAssets(context, str));
    }

    public BitmapTexture loadBitmap(Bitmap bitmap) {
        this.imageTextureId = TextureUtils.getTextureFromBitmap(bitmap, this.imageSize);
        return this;
    }

    public void setImageTextureId(int i) {
        this.imageTextureId = i;
    }

    public int getImageTextureId() {
        return this.imageTextureId;
    }

    public int getImageWidth() {
        return this.imageSize[0];
    }

    public int getImageHeight() {
        return this.imageSize[1];
    }

    public void destroy() {
        GLES20.glDeleteTextures(1, new int[]{this.imageTextureId}, 0);
    }
}

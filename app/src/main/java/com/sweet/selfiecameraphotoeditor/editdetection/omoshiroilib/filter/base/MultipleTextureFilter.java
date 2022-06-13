package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.texture.BitmapTexture;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public abstract class MultipleTextureFilter extends SimpleFragmentShaderFilter {
    protected Context context;
    protected BitmapTexture[] externalBitmapTextures;
    protected int[] externalTextureHandles;
    protected int textureSize = 0;

    public MultipleTextureFilter(Context context2, String str) {
        super(context2, str);
        this.context = context2;
    }

    public void init() {
        int i;
        super.init();
        this.externalBitmapTextures = new BitmapTexture[this.textureSize];
        int i2 = 0;
        while (true) {
            i = this.textureSize;
            if (i2 >= i) {
                break;
            }
            this.externalBitmapTextures[i2] = new BitmapTexture();
            i2++;
        }
        this.externalTextureHandles = new int[i];
        for (int i3 = 0; i3 < this.textureSize; i3++) {
            int[] iArr = this.externalTextureHandles;
            int programId = this.glSimpleProgram.getProgramId();
            iArr[i3] = GLES20.glGetUniformLocation(programId, "sTexture" + (i3 + 2));
        }
    }

    public void destroy() {
        this.glSimpleProgram.onDestroy();
        for (BitmapTexture destroy : this.externalBitmapTextures) {
            destroy.destroy();
        }
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        int i = 0;
        while (i < this.textureSize) {
            int i2 = i + 1;
            TextureUtils.bindTexture2D(this.externalBitmapTextures[i].getImageTextureId(), 33984 + i2, this.externalTextureHandles[i], i2);
            i = i2;
        }
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}

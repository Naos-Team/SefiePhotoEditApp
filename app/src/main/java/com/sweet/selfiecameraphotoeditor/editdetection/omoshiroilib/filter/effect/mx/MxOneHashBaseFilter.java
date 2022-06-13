package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.SimpleFragmentShaderFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.texture.BitmapTexture;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

class MxOneHashBaseFilter extends SimpleFragmentShaderFilter {
    private static final int HISTOGRAM_SIZE = 256;
    static int[] rgbMap;
    private BitmapTexture bitmapTexture;
    private int[] mHistogram = new int[256];
    private int uTextureSamplerHandle2;

    public MxOneHashBaseFilter(Context context, String str) {
        super(context, str);
    }

    public void init() {
        super.init();
        for (int i = 0; i < 256; i++) {
            this.mHistogram[i] = rgbMap[i] << 24;
        }
        this.bitmapTexture = new BitmapTexture();
        this.bitmapTexture.loadBitmap(Bitmap.createBitmap(this.mHistogram, 256, 1, Bitmap.Config.ARGB_8888));
        this.uTextureSamplerHandle2 = GLES20.glGetUniformLocation(this.glSimpleProgram.getProgramId(), "sTexture2");
        ShaderUtils.checkGlError("glGetUniformLocation sTexture2");
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        TextureUtils.bindTexture2D(this.bitmapTexture.getImageTextureId(), 33985, this.uTextureSamplerHandle2, 1);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.shadertoy;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.MultipleTextureFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

import java.nio.FloatBuffer;

public class ShaderToyAbsFilter extends MultipleTextureFilter {
    private final long START_TIME = System.currentTimeMillis();

    public ShaderToyAbsFilter(Context context, String str) {
        super(context, str);
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        GLES20.glUniform3fv(GLES20.glGetUniformLocation(this.glSimpleProgram.getProgramId(), "iResolution"), 1, FloatBuffer.wrap(new float[]{(float) this.surfaceWidth, (float) this.surfaceHeight, 1.0f}));
        setUniform1f(this.glSimpleProgram.getProgramId(), "iGlobalTime", ((float) (System.currentTimeMillis() - this.START_TIME)) / 1000.0f);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}

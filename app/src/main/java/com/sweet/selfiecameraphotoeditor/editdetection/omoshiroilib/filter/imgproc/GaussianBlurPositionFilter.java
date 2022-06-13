package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.imgproc;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.AbsFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLSimpleProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class GaussianBlurPositionFilter extends AbsFilter {
    private float blurRadius = 0.3f;
    protected GLSimpleProgram glSimpleProgram;
    private boolean scale = false;
    private float texelHeightOffset = 0.0f;
    private float texelWidthOffset = 0.0f;

    public GaussianBlurPositionFilter(Context context) {
        this.glSimpleProgram = new GLSimpleProgram(context, "filter/vsh/imgproc/gaussian_blur_position.glsl", "filter/fsh/imgproc/gaussian_blur_position.glsl");
    }

    public void init() {
        this.glSimpleProgram.create();
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        this.glSimpleProgram.use();
        this.plane.uploadTexCoordinateBuffer(this.glSimpleProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glSimpleProgram.getPositionHandle());
    }

    public void destroy() {
        this.glSimpleProgram.onDestroy();
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", this.texelWidthOffset / ((float) this.surfaceWidth));
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", this.texelHeightOffset / ((float) this.surfaceHeight));
        setUniform1f(this.glSimpleProgram.getProgramId(), "aspectRatio", ((float) this.surfaceWidth) / ((float) this.surfaceHeight));
        setUniform1f(this.glSimpleProgram.getProgramId(), "blurRadius", this.blurRadius);
        setUniform2fv(this.glSimpleProgram.getProgramId(), "blurCenter", new float[]{0.5f, 0.5f});
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public GaussianBlurPositionFilter setTexelHeightOffset(float f) {
        this.texelHeightOffset = f;
        return this;
    }

    public GaussianBlurPositionFilter setTexelWidthOffset(float f) {
        this.texelWidthOffset = f;
        return this;
    }

    public void onFilterChanged(int i, int i2) {
        if (!this.scale) {
            super.onFilterChanged(i, i2);
        } else {
            super.onFilterChanged(i / 4, i2 / 4);
        }
    }

    public GaussianBlurPositionFilter setScale(boolean z) {
        this.scale = z;
        return this;
    }
}

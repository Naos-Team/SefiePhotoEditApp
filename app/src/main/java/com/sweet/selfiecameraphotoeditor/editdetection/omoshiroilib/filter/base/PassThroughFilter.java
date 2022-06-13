package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLPassThroughProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class PassThroughFilter extends AbsFilter {
    protected Context context;
    protected GLPassThroughProgram glPassThroughProgram;
    protected float[] projectionMatrix = new float[16];

    public PassThroughFilter(Context context2) {
        this.context = context2;
        this.glPassThroughProgram = new GLPassThroughProgram(context2);
    }

    public void init() {
        this.glPassThroughProgram.create();
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
    }

    public void destroy() {
        this.glPassThroughProgram.onDestroy();
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        this.glPassThroughProgram.use();
        Matrix.setIdentityM(this.projectionMatrix, 0);
        this.plane.uploadTexCoordinateBuffer(this.glPassThroughProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glPassThroughProgram.getPositionHandle());
        GLES20.glUniformMatrix4fv(this.glPassThroughProgram.getMVPMatrixHandle(), 1, false, this.projectionMatrix, 0);
        TextureUtils.bindTexture2D(i, 33984, this.glPassThroughProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
    }
}

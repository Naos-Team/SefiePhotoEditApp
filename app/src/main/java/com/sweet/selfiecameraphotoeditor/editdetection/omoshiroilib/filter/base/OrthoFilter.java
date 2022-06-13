package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLPassThroughProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.MatrixUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class OrthoFilter extends AbsFilter {
    private GLPassThroughProgram glPassThroughProgram;
    private float[] projectionMatrix = new float[16];

    public OrthoFilter(Context context) {
        this.glPassThroughProgram = new GLPassThroughProgram(context);
        Matrix.setIdentityM(this.projectionMatrix, 0);
    }

    public void init() {
        this.glPassThroughProgram.create();
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        this.glPassThroughProgram.use();
        this.plane.uploadTexCoordinateBuffer(this.glPassThroughProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glPassThroughProgram.getPositionHandle());
        GLES20.glUniformMatrix4fv(this.glPassThroughProgram.getMVPMatrixHandle(), 1, false, this.projectionMatrix, 0);
    }

    public void destroy() {
        this.glPassThroughProgram.onDestroy();
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        TextureUtils.bindTexture2D(i, 33984, this.glPassThroughProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public void updateProjection(int i, int i2) {
        MatrixUtils.updateProjectionCrop(i, i2, this.surfaceWidth, this.surfaceHeight, this.projectionMatrix);
    }
}

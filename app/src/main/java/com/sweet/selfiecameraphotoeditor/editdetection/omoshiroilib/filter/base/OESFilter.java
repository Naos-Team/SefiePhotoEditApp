package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLOESProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.texture.GLOESTexture;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BufferUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class OESFilter extends AbsFilter {

    public static final float[] TRIANGLES_DATA_CAMERA = {-1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f};
    private GLOESProgram glOESProgram;
    private GLOESTexture glOESTexture;
    private float[] mSTMatrix = new float[16];

    public OESFilter(Context context) {
        this.plane.setVerticesBuffer(BufferUtils.getFloatBuffer(TRIANGLES_DATA_CAMERA, 0));
        this.glOESProgram = new GLOESProgram(context);
        this.glOESTexture = new GLOESTexture();
        Matrix.setIdentityM(this.mSTMatrix, 0);
    }

    public void init() {
        this.glOESProgram.create();
        this.glOESTexture.loadTexture();
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        this.glOESProgram.use();
        this.plane.uploadTexCoordinateBuffer(this.glOESProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glOESProgram.getPositionHandle());
        GLES20.glUniformMatrix4fv(this.glOESProgram.getMuSTMatrixHandle(), 1, false, this.mSTMatrix, 0);
    }

    public void destroy() {
        this.glOESProgram.onDestroy();
        this.glOESTexture.deleteTexture();
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        TextureUtils.bindTextureOES(i, 33984, this.glOESProgram.getUTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public GLOESProgram getGlOESProgram() {
        return this.glOESProgram;
    }

    public GLOESTexture getGlOESTexture() {
        return this.glOESTexture;
    }

    public float[] getSTMatrix() {
        return this.mSTMatrix;
    }
}

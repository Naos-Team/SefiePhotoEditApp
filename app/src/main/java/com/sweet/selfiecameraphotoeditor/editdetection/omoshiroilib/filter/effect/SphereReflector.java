package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.object.Sphere;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLPassThroughProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class SphereReflector extends PassThroughFilter {
    private GLPassThroughProgram glSphereProgram;
    private float[] mMVPMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private float[] modelViewMatrix = new float[16];
    private float ratio;
    private Sphere sphere = new Sphere(8.0f, 75, 150);
    private float[] viewMatrix = new float[16];

    public SphereReflector(Context context) {
        super(context);
        this.glSphereProgram = new GLPassThroughProgram(context);
        initMatrix();
    }

    public void init() {
        super.init();
        this.glSphereProgram.create();
    }

    public void destroy() {
        super.destroy();
        this.glSphereProgram.onDestroy();
    }

    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
        this.glSphereProgram.use();
        this.sphere.uploadTexCoordinateBuffer(this.glSphereProgram.getTextureCoordinateHandle());
        this.sphere.uploadVerticesBuffer(this.glSphereProgram.getPositionHandle());
        Matrix.perspectiveM(this.projectionMatrix, 0, 90.0f, this.ratio, 1.0f, 500.0f);
        Matrix.multiplyMM(this.modelViewMatrix, 0, this.viewMatrix, 0, this.modelMatrix, 0);
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.projectionMatrix, 0, this.modelViewMatrix, 0);
        GLES20.glUniformMatrix4fv(this.glSphereProgram.getMVPMatrixHandle(), 1, false, this.mMVPMatrix, 0);
        TextureUtils.bindTexture2D(i, 33984, this.glSphereProgram.getTextureSamplerHandle(), 0);
        this.sphere.draw();
    }

    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
        this.ratio = ((float) i) / ((float) i2);
    }

    private void initMatrix() {
        Matrix.setIdentityM(this.modelMatrix, 0);
        Matrix.rotateM(this.modelMatrix, 0, 90.0f, 0.0f, 1.0f, 0.0f);
        Matrix.setIdentityM(this.projectionMatrix, 0);
        Matrix.setIdentityM(this.viewMatrix, 0);
        Matrix.setLookAtM(this.viewMatrix, 0, 0.0f, 10.0f, 10.0f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
    }
}

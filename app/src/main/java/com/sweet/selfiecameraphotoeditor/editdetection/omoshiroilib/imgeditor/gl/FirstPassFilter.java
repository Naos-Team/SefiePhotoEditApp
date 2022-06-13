package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.imgeditor.gl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.AbsFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.object.Plane;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLPassThroughProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class FirstPassFilter extends AbsFilter {
    protected Context context;
    protected GLPassThroughProgram glPassThroughProgram;
    private Plane plane;
    protected float[] projectionMatrix = new float[16];

    public FirstPassFilter(Context context2) {
        this.context = context2;
        this.glPassThroughProgram = new GLPassThroughProgram(context2);
        this.plane = new Plane(false);
    }

    public void init() {
        this.glPassThroughProgram.create();
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

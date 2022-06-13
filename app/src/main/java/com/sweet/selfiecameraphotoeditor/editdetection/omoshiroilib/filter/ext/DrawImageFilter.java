package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.ext;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.object.Plane;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.texture.BitmapTexture;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.MatrixUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class DrawImageFilter extends PassThroughFilter {
    private BitmapTexture bitmapTexture = new BitmapTexture();
    private String imagePath;
    private Plane imagePlane;

    public DrawImageFilter(Context context, String str) {
        super(context);
        this.imagePath = str;
        this.imagePlane = new Plane(false);
    }

    public void init() {
        super.init();
        this.bitmapTexture.load(this.context, this.imagePath);
    }

    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        TextureUtils.bindTexture2D(this.bitmapTexture.getImageTextureId(), 33984, this.glPassThroughProgram.getTextureSamplerHandle(), 0);
        this.imagePlane.uploadTexCoordinateBuffer(this.glPassThroughProgram.getTextureCoordinateHandle());
        this.imagePlane.uploadVerticesBuffer(this.glPassThroughProgram.getPositionHandle());
        MatrixUtils.updateProjectionFit(this.bitmapTexture.getImageWidth(), this.bitmapTexture.getImageHeight(), this.surfaceWidth, this.surfaceHeight, this.projectionMatrix);
        GLES20.glUniformMatrix4fv(this.glPassThroughProgram.getMVPMatrixHandle(), 1, false, this.projectionMatrix, 0);
        this.imagePlane.draw();
        GLES20.glDisable(3042);
    }

    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
    }
}

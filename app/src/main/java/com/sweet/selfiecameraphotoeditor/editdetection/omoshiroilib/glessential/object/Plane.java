package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.object;

import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.constant.Rotation;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BufferUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.PlaneTextureRotationUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;

import java.nio.FloatBuffer;

public class Plane {
    private final float[] trianglesData = {-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f};
    private FloatBuffer mTexCoordinateBuffer;
    private FloatBuffer mVerticesBuffer = BufferUtils.getFloatBuffer(this.trianglesData, 0);

    public Plane(boolean z) {
        if (z) {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.getRotation(Rotation.NORMAL, false, true), 0);
        } else {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.TEXTURE_NO_ROTATION, 0);
        }
    }

    public void uploadVerticesBuffer(int i) {
        FloatBuffer verticesBuffer = getVerticesBuffer();
        if (verticesBuffer != null) {
            verticesBuffer.position(0);
            GLES20.glVertexAttribPointer(i, 3, 5126, false, 0, verticesBuffer);
            ShaderUtils.checkGlError("glVertexAttribPointer maPosition");
            GLES20.glEnableVertexAttribArray(i);
            ShaderUtils.checkGlError("glEnableVertexAttribArray maPositionHandle");
        }
    }

    public void uploadTexCoordinateBuffer(int i) {
        FloatBuffer texCoordinateBuffer = getTexCoordinateBuffer();
        if (texCoordinateBuffer != null) {
            texCoordinateBuffer.position(0);
            GLES20.glVertexAttribPointer(i, 2, 5126, false, 0, texCoordinateBuffer);
            ShaderUtils.checkGlError("glVertexAttribPointer maTextureHandle");
            GLES20.glEnableVertexAttribArray(i);
            ShaderUtils.checkGlError("glEnableVertexAttribArray maTextureHandle");
        }
    }

    public FloatBuffer getVerticesBuffer() {
        return this.mVerticesBuffer;
    }

    public FloatBuffer getTexCoordinateBuffer() {
        return this.mTexCoordinateBuffer;
    }

    public void setTexCoordinateBuffer(FloatBuffer floatBuffer) {
        this.mTexCoordinateBuffer = floatBuffer;
    }

    public void setVerticesBuffer(FloatBuffer floatBuffer) {
        this.mVerticesBuffer = floatBuffer;
    }

    public void resetTextureCoordinateBuffer(boolean z) {
        this.mTexCoordinateBuffer = null;
        if (z) {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.getRotation(Rotation.NORMAL, false, true), 0);
        } else {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.TEXTURE_NO_ROTATION, 0);
        }
    }

    public void draw() {
        GLES20.glDrawArrays(5, 0, 4);
    }

    public Plane scale(float f) {
        float[] fArr = this.trianglesData;
        float[] fArr2 = new float[fArr.length];
        System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
        for (int i = 0; i < fArr2.length; i++) {
            fArr2[i] = fArr2[i] * f;
        }
        this.mVerticesBuffer = BufferUtils.getFloatBuffer(fArr2, 0);
        return this;
    }
}

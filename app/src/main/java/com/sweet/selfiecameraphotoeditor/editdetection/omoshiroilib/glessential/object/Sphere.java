package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.object;

import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Sphere {
    private static final int sPositionDataSize = 3;
    private static final int sTextureCoordinateDataSize = 2;
    private ShortBuffer indexBuffer;
    private int mNumIndices;
    private FloatBuffer mTexCoordinateBuffer;
    private FloatBuffer mVerticesBuffer;

    public Sphere(float f, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        float f2 = 1.0f / ((float) i3);
        float f3 = 1.0f / ((float) i4);
        int i5 = i3 + 1;
        int i6 = i4 + 1;
        int i7 = i5 * i6;
        float[] fArr = new float[(i7 * 3)];
        float[] fArr2 = new float[(i7 * 2)];
        short[] sArr = new short[(i7 * 6)];
        short s = 0;
        int i8 = 0;
        int i9 = 0;
        while (s < i5) {
            int i10 = i9;
            int i11 = i8;
            short s2 = 0;
            while (s2 < i6) {
                float f4 = (float) s2;
                int i12 = i5;
                double d = (double) (6.2831855f * f4 * f3);
                float f5 = (float) s;
                float f6 = 3.1415927f * f5 * f2;
                double d2 = (double) f6;
                short s3 = s2;
                float[] fArr3 = fArr;
                int i13 = i11 + 1;
                fArr2[i11] = f4 * f3;
                i11 = i13 + 1;
                fArr2[i13] = f5 * f2;
                int i14 = i10 + 1;
                fArr3[i10] = ((float) (Math.cos(d) * Math.sin(d2))) * f;
                int i15 = i14 + 1;
                fArr3[i14] = ((float) Math.sin((double) (f6 - 2.8584073f))) * f;
                i10 = i15 + 1;
                fArr3[i15] = ((float) (Math.sin(d) * Math.sin(d2))) * f;
                fArr = fArr3;
                i5 = i12;
                i6 = i6;
                sArr = sArr;
                s = s;
                int i16 = i;
                int i17 = i2;
                s2 = (short) (s3 + 1);
            }
            int i18 = i5;
            int i19 = i6;
            short[] sArr2 = sArr;
            float[] fArr4 = fArr;
            s = (short) (s + 1);
            i8 = i11;
            i9 = i10;
            int i20 = i;
            int i21 = i2;
        }
        int i22 = i6;
        short[] sArr3 = sArr;
        float[] fArr5 = fArr;
        int i23 = i;
        short s4 = 0;
        int i24 = 0;
        while (s4 < i23) {
            int i25 = i24;
            int i26 = i2;
            short s5 = 0;
            while (s5 < i26) {
                int i27 = i25 + 1;
                int i28 = s4 * i22;
                sArr3[i25] = (short) (i28 + s5);
                int i29 = i27 + 1;
                int i30 = (s4 + 1) * i22;
                short s6 = (short) (i30 + s5);
                sArr3[i27] = s6;
                int i31 = i29 + 1;
                int i32 = s5 + 1;
                short s7 = (short) (i28 + i32);
                sArr3[i29] = s7;
                int i33 = i31 + 1;
                sArr3[i31] = s7;
                int i34 = i33 + 1;
                sArr3[i33] = s6;
                i25 = i34 + 1;
                sArr3[i34] = (short) (i30 + i32);
                s5 = (short) i32;
            }
            s4 = (short) (s4 + 1);
            i24 = i25;
        }
        float[] fArr6 = fArr5;
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr6.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr6);
        asFloatBuffer.position(0);
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(fArr2.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer2 = allocateDirect2.asFloatBuffer();
        asFloatBuffer2.put(fArr2);
        asFloatBuffer2.position(0);
        short[] sArr4 = sArr3;
        ByteBuffer allocateDirect3 = ByteBuffer.allocateDirect(sArr4.length * 2);
        allocateDirect3.order(ByteOrder.nativeOrder());
        this.indexBuffer = allocateDirect3.asShortBuffer();
        this.indexBuffer.put(sArr4);
        this.indexBuffer.position(0);
        this.mTexCoordinateBuffer = asFloatBuffer2;
        this.mVerticesBuffer = asFloatBuffer;
        this.mNumIndices = sArr4.length;
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

    public void draw() {
        ShortBuffer shortBuffer = this.indexBuffer;
        if (shortBuffer != null) {
            shortBuffer.position(0);
            GLES20.glDrawElements(4, this.mNumIndices, 5123, this.indexBuffer);
            return;
        }
        GLES20.glDrawArrays(4, 0, this.mNumIndices);
    }
}

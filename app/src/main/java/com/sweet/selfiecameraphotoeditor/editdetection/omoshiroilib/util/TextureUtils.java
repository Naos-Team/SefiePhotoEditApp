package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.constant.GLEtc;

import java.nio.ByteBuffer;

public class TextureUtils {
    private static final String TAG = "TextureUtils";

    public static void bindTexture2D(int i, int i2, int i3, int i4) {
        if (i != 0) {
            GLES20.glActiveTexture(i2);
            GLES20.glBindTexture(3553, i);
            GLES20.glUniform1i(i3, i4);
        }
    }

    public static void bindTextureOES(int i, int i2, int i3, int i4) {
        if (i != 0) {
            GLES20.glActiveTexture(i2);
            GLES20.glBindTexture(GLEtc.GL_TEXTURE_EXTERNAL_OES, i);
            GLES20.glUniform1i(i3, i4);
        }
    }

    public static int loadTextureFromResources(Context context, int i, int[] iArr) {
        return getTextureFromBitmap(BitmapUtils.loadBitmapFromRaw(context, i), iArr);
    }

    public static int loadTextureFromAssets(Context context, String str, int[] iArr) {
        return getTextureFromBitmap(BitmapUtils.loadBitmapFromAssets(context, str), iArr);
    }

    public static int getTextureFromBitmap(Bitmap bitmap, int[] iArr) {
        int[] iArr2 = new int[1];
        GLES20.glGenTextures(1, iArr2, 0);
        if (iArr2[0] == 0) {
            Log.d(TAG, "Failed at glGenTextures");
            return 0;
        } else if (bitmap == null) {
            Log.d(TAG, "Failed at decoding bitmap");
            GLES20.glDeleteTextures(1, iArr2, 0);
            return 0;
        } else {
            if (iArr != null && iArr.length >= 2) {
                iArr[0] = bitmap.getWidth();
                iArr[1] = bitmap.getHeight();
            }
            GLES20.glBindTexture(3553, iArr2[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
            bitmap.recycle();
            GLES20.glBindTexture(3553, 0);
            return iArr2[0];
        }
    }

    public static int loadTextureWithOldTexId(Bitmap bitmap, int i) {
        int[] iArr = new int[1];
        if (i == 0) {
            return getTextureFromBitmap(bitmap, (int[]) null);
        }
        GLES20.glBindTexture(3553, i);
        GLUtils.texSubImage2D(3553, 0, 0, 0, bitmap);
        iArr[0] = i;
        return iArr[0];
    }

    public static int getTextureFromByteArray(byte[] bArr, int i, int i2) {
        if (bArr.length == i * i2 * 4) {
            return getTextureFromByteBuffer(ByteBuffer.wrap(bArr), i, i2);
        }
        throw new RuntimeException("Illegal byte array");
    }

    public static int getTextureFromByteArrayWithOldTexId(byte[] bArr, int i, int i2, int i3) {
        if (bArr.length == i * i2 * 4) {
            return getTextureFromByteBufferWithOldTexId(ByteBuffer.wrap(bArr), i, i2, i3);
        }
        throw new RuntimeException("Illegal byte array");
    }

    public static int getTextureFromByteBuffer(ByteBuffer byteBuffer, int i, int i2) {
        if (byteBuffer.array().length == i * i2 * 4) {
            int[] iArr = new int[1];
            GLES20.glGenTextures(1, iArr, 0);
            if (iArr[0] == 0) {
                Log.d(TAG, "Failed at glGenTextures");
                return 0;
            }
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, byteBuffer);
            GLES20.glBindTexture(3553, 0);
            return iArr[0];
        }
        throw new RuntimeException("Illegal byte array");
    }

    public static int getTextureFromByteBufferWithOldTexId(ByteBuffer byteBuffer, int i, int i2, int i3) {
        if (byteBuffer.array().length == i * i2 * 4) {
            int[] iArr = new int[1];
            if (i3 == 0) {
                return getTextureFromByteBuffer(byteBuffer, i, i2);
            }
            GLES20.glBindTexture(3553, i3);
            GLES20.glTexSubImage2D(3553, 0, 0, 0, i, i2, 6408, 5121, byteBuffer);
            iArr[0] = i3;
            return iArr[0];
        }
        throw new RuntimeException("Illegal byte array");
    }
}

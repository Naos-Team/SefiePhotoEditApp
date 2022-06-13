package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class BufferUtils {
    public static final int FLOAT_SIZE_BYTES = 4;
    public static final int SHORT_SIZE_BYTES = 2;

    public static FloatBuffer getFloatBuffer(float[] fArr, int i) {
        FloatBuffer put = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fArr);
        put.position(i);
        return put;
    }

    public static ShortBuffer getShortBuffer(short[] sArr, int i) {
        ShortBuffer put = ByteBuffer.allocateDirect(sArr.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer().put(sArr);
        put.position(i);
        return put;
    }
}

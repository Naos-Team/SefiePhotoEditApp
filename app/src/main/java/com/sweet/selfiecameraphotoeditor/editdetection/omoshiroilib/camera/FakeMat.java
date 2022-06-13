package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.camera;

public class FakeMat {
    private byte[] mFrame = null;

    public void init(int i) {
        byte[] bArr = this.mFrame;
        if (bArr == null || bArr.length != i) {
            this.mFrame = new byte[i];
        }
    }

    public void putData(byte[] bArr) {
        byte[] bArr2 = this.mFrame;
        if (bArr2 != null) {
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        }
    }

    public byte[] getFrame() {
        return this.mFrame;
    }
}

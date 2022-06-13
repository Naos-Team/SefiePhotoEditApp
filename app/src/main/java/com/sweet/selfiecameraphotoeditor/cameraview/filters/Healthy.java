package com.sweet.selfiecameraphotoeditor.cameraview.filters;

import android.opengl.GLES20;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.nio.ByteBuffer;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

public class Healthy extends GPUImageFilter {
    public static final String HUE_FRAGMENT_SHADER = "precision mediump float; \nuniform sampler2D inputImageTexture; \nuniform sampler2D curve;\nuniform sampler2D mask;\nuniform float texelWidthOffset ;\nuniform float texelHeightOffset;\nvarying mediump vec2 textureCoordinate;\nvec4 level0c(vec4 color, sampler2D sampler) \n{ \ncolor.r = texture2D(sampler, vec2(color.r, 0.)).r; \ncolor.g = texture2D(sampler, vec2(color.g, 0.)).r;\ncolor.b = texture2D(sampler, vec2(color.b, 0.)).r;\nreturn color;\n} \nvec4 level1c(vec4 color, sampler2D sampler) \n{ \ncolor.r = texture2D(sampler, vec2(color.r, 0.)).g;\ncolor.g = texture2D(sampler, vec2(color.g, 0.)).g;\ncolor.b = texture2D(sampler, vec2(color.b, 0.)).g;\nreturn color;\n} \nvec4 level2c(vec4 color, sampler2D sampler) \n{ \ncolor.r = texture2D(sampler, vec2(color.r,0.)).b; \ncolor.g = texture2D(sampler, vec2(color.g,0.)).b;\ncolor.b = texture2D(sampler, vec2(color.b,0.)).b; \nreturn color; \n} \nvec3 rgb2hsv(vec3 c) \n{\nvec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0); \nvec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g)); \nvec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r)); \nfloat d = q.x - min(q.w, q.y); \nfloat e = 1.0e-10; \nreturn vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x); \n} \nvec3 hsv2rgb(vec3 c) \n{ \nvec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0); \nvec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www); \nreturn c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y); \n}\nvec4 normal(vec4 c1, vec4 c2, float alpha) \n{ \nreturn (c2-c1) * alpha + c1; \n} \nvec4 multiply(vec4 c1, vec4 c2) \n{\nreturn c1 * c2 * 1.01;\n}\nvec4 overlay(vec4 c1, vec4 c2)\n{\nvec4 color = vec4(0.,0.,0.,1.);\ncolor.r = c1.r < 0.5 ? 2.0*c1.r*c2.r : 1.0 - 2.0*(1.0-c1.r)*(1.0-c2.r);\ncolor.g = c1.g < 0.5 ? 2.0*c1.g*c2.g : 1.0 - 2.0*(1.0-c1.g)*(1.0-c2.g);\ncolor.b = c1.b < 0.5 ? 2.0*c1.b*c2.b : 1.0 - 2.0*(1.0-c1.b)*(1.0-c2.b); \nreturn color;\n}\nvec4 screen(vec4 c1, vec4 c2) \n{ \nreturn vec4(1.) - ((vec4(1.) - c1) * (vec4(1.) - c2)); \n} \nvoid main() \n{ \nvec4 textureColor; \nvec4 t0 = texture2D(mask, vec2(textureCoordinate.x, textureCoordinate.y)); \nvec4 c2 = texture2D(inputImageTexture, textureCoordinate); \nvec4 c5 = c2; \nvec3 hsv = rgb2hsv(c5.rgb); \nlowp float h = hsv.x; \nlowp float s = hsv.y; \nlowp float v = hsv.z; \nlowp float cF = 0.;   \nlowp float cG = 0.;   \nlowp float sF = 0.06; \nif(h >= 0.125 && h <= 0.208) \n{ \ns = s - (s * sF); \n} \nelse if (h >= 0.208 && h < 0.292) \n{ \ncG = abs(h - 0.208); \ncF = (cG / 0.0833); \ns = s - (s * sF * cF); \n} \nelse if (h > 0.042 && h <=  0.125) \n{ \ncG = abs(h - 0.125); \ncF = (cG / 0.0833); \ns = s - (s * sF * cF); \n} \nhsv.y = s; \nvec4 c6 = vec4(hsv2rgb(hsv),1.); \nc6 = normal(c6, screen  (c6, c6), 0.275); // screen 70./255. \nc6 = normal(c6, overlay (c6, vec4(1., 0.61176, 0.25098, 1.)), 0.04); // overlay 10./255. \nc6 = normal(c6, multiply(c6, t0), 0.262); // multiply 67./255. \nc6 = level1c(level0c(c6,curve),curve); \ngl_FragColor = c6; \n} \n";
    float mHue;
    private int mMaskGrey1TextureId;
    private int mMaskGrey1UniformLocation;
    int mTexelHeightUniformLocation;
    int mTexelWidthUniformLocation;
    public int[] mToneCurveTexture;
    private int mToneCurveTextureUniformLocation;

    class C11351 implements Runnable {
        C11351() {
        }

        public void run() {
            GLES20.glGenTextures(1, Healthy.this.mToneCurveTexture, 0);
            GLES20.glBindTexture(3553, Healthy.this.mToneCurveTexture[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            byte[] bArr = new byte[1024];
            int[] iArr = {95, 95, 96, 97, 97, 98, 99, 99, 100, 101, 101, 102, 103, 104, 104, 105, 106, 106, 107, 108, 108, 109, 110, 111, 111, 112, 113, 113, 114, 115, 115, 116, 117, 117, 118, 119, 120, 120, 121, 122, 122, 123, 124, 124, 125, 126, 127, 127, 128, 129, 129, 130, 131, 131, 132, 133, 133, 134, 135, 136, 136, 137, 138, 138, 139, 140, 140, 141, 142, 143, 143, 144, 145, 145, 146, 147, 147, 148, 149, 149, 150, 151, 152, 152, 153, 154, 154, 155, 156, 156, 157, 158, 159, 159, 160, 161, 161, 162, 163, 163, 164, 165, 165, 166, 167, 168, 168, 169, 170, 170, 171, 172, 172, 173, 174, 175, 175, 176, 177, 177, 178, 179, 179, 180, 181, 181, 182, 183, 184, 184, 185, 186, 186, 187, 188, 188, 189, 190, 191, 191, 192, 193, 193, 194, 195, 195, 196, 197, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 202, 203, 204, 204, 205, 206, 207, 207, 208, 209, 209, 210, 211, 211, 212, 213, 213, 214, 215, 216, 216, 217, 218, 218, 219, 220, 220, 221, 222, 223, 223, 224, 225, 225, 226, 227, 227, 228, 229, 229, 230, 231, 232, 232, 233, 234, 234, 235, 236, 236, 237, 238, 239, 239, 240, 241, 241, 242, 243, 243, 244, 245, 245, 246, 247, 248, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
            int[] iArr2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 7, 8, 9, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 26, 27, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 41, 42, 43, 44, 45, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 60, 61, 62, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 168, 169, 170, 171, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 189, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 204, 205, 206, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 219, 220, 221, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 234, 235, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 253, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255};
            int[] iArr3 = {0, 1, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 10, 11, 12, 13, 14, 15, 16, 17, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 95, 96, 97, 98, 99, 100, 101, 102, 103, 105, 106, 107, 108, 109, 110, 111, 112, 114, 115, 116, 117, 118, 119, 120, 121, 122, 124, 125, 126, 127, 128, 129, 130, 131, 132, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 253, 254, 255};
            for (int i = 0; i < 256; i++) {
                int i2 = i * 4;
                bArr[i2] = (byte) iArr3[i];
                bArr[i2 + 1] = (byte) iArr2[i];
                bArr[i2 + 2] = (byte) iArr[i];
                bArr[i2 + 3] = -1;
            }
            GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
        }
    }

    public Healthy() {
        this(90.0f);
    }

    public Healthy(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, HUE_FRAGMENT_SHADER);
        this.mToneCurveTexture = new int[]{-1};
        this.mMaskGrey1TextureId = -1;
        this.mHue = f;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(1, this.mToneCurveTexture, 0);
        this.mToneCurveTexture[0] = -1;
        GLES20.glDeleteTextures(1, new int[]{this.mMaskGrey1TextureId}, 0);
        this.mMaskGrey1TextureId = -1;
    }

    @Override
    public void onInit() {
        super.onInit();
        this.mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(this.mGLProgId, "curve");
        this.mMaskGrey1UniformLocation = GLES20.glGetUniformLocation(getProgram(), "mask");
        this.mTexelWidthUniformLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidthOffset");
        this.mTexelHeightUniformLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeightOffset");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        runOnDraw(new C11351());
    }

    @Override
    public void onDrawArraysPre() {
        super.onDrawArraysPre();
        if (this.mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
            GLES20.glUniform1i(this.mToneCurveTextureUniformLocation, 3);
        }
        if (this.mMaskGrey1TextureId != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, this.mMaskGrey1TextureId);
            GLES20.glUniform1i(this.mMaskGrey1UniformLocation, 4);
        }
    }
}

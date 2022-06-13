package com.sweet.selfiecameraphotoeditor.cameraview.filters;

import android.opengl.GLES20;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.nio.ByteBuffer;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

public class Sakura extends GPUImageFilter {
    public static final String HUE_FRAGMENT_SHADER = "precision highp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D curve; \nuniform float texelWidthOffset; \nuniform float texelHeightOffset; \nvarying mediump vec2 textureCoordinate; \nconst mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721); \nvec4 gaussianBlur(sampler2D sampler) { \nlowp float strength = 1.; \nvec4 color = vec4(0.); \nvec2 step  = vec2(0.); \ncolor += texture2D(sampler,textureCoordinate)* 0.25449 ; \nstep.x = 1.37754 * texelWidthOffset  * strength; \nstep.y = 1.37754 * texelHeightOffset * strength;\ncolor += texture2D(sampler,textureCoordinate+step) * 0.24797; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.24797; \nstep.x = 3.37754 * texelWidthOffset  * strength; \nstep.y = 3.37754 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+step) * 0.09122; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.09122; \nstep.x = 5.37754 * texelWidthOffset  * strength; \nstep.y = 5.37754 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+step) * 0.03356; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.03356; \nreturn color; \n} \nvoid main() { \nvec4 blurColor; \nlowp vec4 textureColor; \nlowp float strength = -1.0 / 510.0; \nfloat xCoordinate = textureCoordinate.x; \nfloat yCoordinate = textureCoordinate.y;\nlowp float satura = 0.7; \ntextureColor = texture2D(inputImageTexture, textureCoordinate); \nblurColor = gaussianBlur(inputImageTexture); \nlowp float luminance = dot(blurColor.rgb, luminanceWeighting); \nlowp vec3 greyScaleColor = vec3(luminance); \nblurColor = vec4(mix(greyScaleColor, blurColor.rgb, satura), blurColor.w); \nlowp float redCurveValue = texture2D(curve, vec2(textureColor.r, 0.0)).r; \nlowp float greenCurveValue = texture2D(curve, vec2(textureColor.g, 0.0)).r; \nlowp float blueCurveValue = texture2D(curve, vec2(textureColor.b, 0.0)).r; \nredCurveValue = min(1.0, redCurveValue + strength); \ngreenCurveValue = min(1.0, greenCurveValue + strength); \nblueCurveValue = min(1.0, blueCurveValue + strength); \nmediump vec4 overlay = blurColor;\nmediump vec4 base = vec4(redCurveValue, greenCurveValue, blueCurveValue, 1.0); \nmediump float ra; \nif (base.r < 0.5) { \nra = overlay.r * base.r * 2.0; \n} else { \nra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n} \nmediump float ga; \nif (base.g < 0.5) { \nga = overlay.g * base.g * 2.0; \n} else { \nga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0); \n} \nmediump float ba; \nif (base.b < 0.5) { \nba = overlay.b * base.b * 2.0;\n} else { \nba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0); \n} \ntextureColor = vec4(ra, ga, ba, 1.0); \ngl_FragColor = vec4(textureColor.r, textureColor.g, textureColor.b, 1.0); \n}\n";
    private float mHue;
    private int mHueLocation;
     int mTexelHeightUniformLocation;
     int mTexelWidthUniformLocation;
    public int[] mToneCurveTexture;
    private int mToneCurveTextureUniformLocation;

    class C11391 implements Runnable {
        C11391() {
        }

        public void run() {
            GLES20.glGenTextures(1, Sakura.this.mToneCurveTexture, 0);
            GLES20.glBindTexture(3553, Sakura.this.mToneCurveTexture[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            byte[] bArr = new byte[1024];
            int[] iArr = {95, 95, 96, 97, 97, 98, 99, 99, 100, 101, 101, 102, 103, 104, 104, 105, 106, 106, 107, 108, 108, 109, 110, 111, 111, 112, 113, 113, 114, 115, 115, 116, 117, 117, 118, 119, 120, 120, 121, 122, 122, 123, 124, 124, 125, 126, 127, 127, 128, 129, 129, 130, 131, 131, 132, 133, 133, 134, 135, 136, 136, 137, 138, 138, 139, 140, 140, 141, 142, 143, 143, 144, 145, 145, 146, 147, 147, 148, 149, 149, 150, 151, 152, 152, 153, 154, 154, 155, 156, 156, 157, 158, 159, 159, 160, 161, 161, 162, 163, 163, 164, 165, 165, 166, 167, 168, 168, 169, 170, 170, 171, 172, 172, 173, 174, 175, 175, 176, 177, 177, 178, 179, 179, 180, 181, 181, 182, 183, 184, 184, 185, 186, 186, 187, 188, 188, 189, 190, 191, 191, 192, 193, 193, 194, 195, 195, 196, 197, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 202, 203, 204, 204, 205, 206, 207, 207, 208, 209, 209, 210, 211, 211, 212, 213, 213, 214, 215, 216, 216, 217, 218, 218, 219, 220, 220, 221, 222, 223, 223, 224, 225, 225, 226, 227, 227, 228, 229, 229, 230, 231, 232, 232, 233, 234, 234, 235, 236, 236, 237, 238, 239, 239, 240, 241, 241, 242, 243, 243, 244, 245, 245, 246, 247, 248, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
            for (int i = 0; i < 256; i++) {
                int i2 = i * 4;
                bArr[i2] = (byte) iArr[i];
                bArr[i2 + 1] = (byte) iArr[i];
                bArr[i2 + 2] = (byte) iArr[i];
                bArr[i2 + 3] = (byte) iArr[i];
            }
            GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
        }
    }

    public Sakura() {
        this(90.0f);
    }

    public Sakura(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, "precision highp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D curve; \nuniform float texelWidthOffset; \nuniform float texelHeightOffset; \nvarying mediump vec2 textureCoordinate; \nconst mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721); \nvec4 gaussianBlur(sampler2D sampler) { \nlowp float strength = 1.; \nvec4 color = vec4(0.); \nvec2 step  = vec2(0.); \ncolor += texture2D(sampler,textureCoordinate)* 0.25449 ; \nstep.x = 1.37754 * texelWidthOffset  * strength; \nstep.y = 1.37754 * texelHeightOffset * strength;\ncolor += texture2D(sampler,textureCoordinate+step) * 0.24797; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.24797; \nstep.x = 3.37754 * texelWidthOffset  * strength; \nstep.y = 3.37754 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+step) * 0.09122; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.09122; \nstep.x = 5.37754 * texelWidthOffset  * strength; \nstep.y = 5.37754 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+step) * 0.03356; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.03356; \nreturn color; \n} \nvoid main() { \nvec4 blurColor; \nlowp vec4 textureColor; \nlowp float strength = -1.0 / 510.0; \nfloat xCoordinate = textureCoordinate.x; \nfloat yCoordinate = textureCoordinate.y;\nlowp float satura = 0.7; \ntextureColor = texture2D(inputImageTexture, textureCoordinate); \nblurColor = gaussianBlur(inputImageTexture); \nlowp float luminance = dot(blurColor.rgb, luminanceWeighting); \nlowp vec3 greyScaleColor = vec3(luminance); \nblurColor = vec4(mix(greyScaleColor, blurColor.rgb, satura), blurColor.w); \nlowp float redCurveValue = texture2D(curve, vec2(textureColor.r, 0.0)).r; \nlowp float greenCurveValue = texture2D(curve, vec2(textureColor.g, 0.0)).r; \nlowp float blueCurveValue = texture2D(curve, vec2(textureColor.b, 0.0)).r; \nredCurveValue = min(1.0, redCurveValue + strength); \ngreenCurveValue = min(1.0, greenCurveValue + strength); \nblueCurveValue = min(1.0, blueCurveValue + strength); \nmediump vec4 overlay = blurColor;\nmediump vec4 base = vec4(redCurveValue, greenCurveValue, blueCurveValue, 1.0); \nmediump float ra; \nif (base.r < 0.5) { \nra = overlay.r * base.r * 2.0; \n} else { \nra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n} \nmediump float ga; \nif (base.g < 0.5) { \nga = overlay.g * base.g * 2.0; \n} else { \nga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0); \n} \nmediump float ba; \nif (base.b < 0.5) { \nba = overlay.b * base.b * 2.0;\n} else { \nba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0); \n} \ntextureColor = vec4(ra, ga, ba, 1.0); \ngl_FragColor = vec4(textureColor.r, textureColor.g, textureColor.b, 1.0); \n}\n");
        this.mToneCurveTexture = new int[]{-1};
        this.mHue = f;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(1, this.mToneCurveTexture, 0);
        this.mToneCurveTexture[0] = -1;
    }

    public void onDrawArraysAfter() {
        if (this.mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
    }

    @Override
    public void onDrawArraysPre() {
        if (this.mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
            GLES20.glUniform1i(this.mToneCurveTextureUniformLocation, 3);
        }
    }

    @Override
    public void onInit() {
        super.onInit();
        this.mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(this.mGLProgId, "curve");
        this.mTexelWidthUniformLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidthOffset");
        this.mTexelHeightUniformLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeightOffset");
    }
    @Override
    public void onInitialized() {
        super.onInitialized();
        runOnDraw(new C11391());
    }

    public void setHue(float f) {
        this.mHue = f;
        setFloat(this.mHueLocation, ((this.mHue % 360.0f) * 3.1415927f) / 180.0f);
    }
}

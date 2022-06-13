package com.sweet.selfiecameraphotoeditor.cameraview.filters;

import android.opengl.GLES20;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.nio.ByteBuffer;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

public class Sweety extends GPUImageFilter {
    public static final String HUE_FRAGMENT_SHADER = "precision mediump float; \nuniform lowp sampler2D inputImageTexture; \nuniform lowp sampler2D curve;\nuniform lowp sampler2D samplerMask;\nuniform lowp int lowPerformance;\nuniform float texelWidthOffset ;\nuniform float texelHeightOffset;\nvarying mediump vec2 textureCoordinate;\nvec4 sharpen(sampler2D sampler) \n{ \nvec4 color = texture2D(sampler, textureCoordinate) * 5.; \ncolor -= texture2D(sampler, textureCoordinate-vec2(texelWidthOffset, 0. )) * 0.25;\ncolor -= texture2D(sampler, textureCoordinate+vec2(texelWidthOffset, 0. )) * 0.25; \ncolor -= texture2D(sampler, textureCoordinate-vec2(0., texelHeightOffset)) * 0.25; \ncolor -= texture2D(sampler, textureCoordinate+vec2(0., texelHeightOffset)) * 0.25; \nreturn color; \n} \nvec4 gaussianBlur(sampler2D sampler) \n{ \nlowp float strength = 1.; \nvec4 color = vec4(0.); \nvec2 step  = vec2(0.);\ncolor += texture2D(sampler,textureCoordinate)* 0.0443 ; \nstep.x = 1.49583 * texelWidthOffset  * strength; \nstep.y = 1.49583 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+vec2(step.x, 0.)) * 0.04321; \ncolor += texture2D(sampler,textureCoordinate-vec2(step.x, 0.)) * 0.04321; \ncolor += texture2D(sampler,textureCoordinate+vec2(0., step.y)) * 0.04321; \ncolor += texture2D(sampler,textureCoordinate-vec2(0., step.y)) * 0.04321; \nstep.x = 2.4719250988753685 * texelWidthOffset  * strength; \nstep.y = 2.4719250988753685 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+step) * 1.041795; \ncolor += texture2D(sampler,textureCoordinate-step) * 1.041795; \ncolor += texture2D(sampler,textureCoordinate+vec2(-step.x, step.y)) * 0.041795; \ncolor += texture2D(sampler,textureCoordinate+vec2( step.x,-step.y)) * 0.041795; \nstep.x = 5.49583 * texelWidthOffset  * strength; \nstep.y = 5.49583 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+vec2(step.x, 0.)) * 0.040425; \ncolor += texture2D(sampler,textureCoordinate-vec2(step.x, 0.)) * 0.040425; \ncolor += texture2D(sampler,textureCoordinate+vec2(0., step.y)) * 0.040425; \ncolor += texture2D(sampler,textureCoordinate-vec2(0., step.y)) * 0.040425; \nstep.x = 5.300352223621558 * texelWidthOffset  * strength; \nstep.y = 5.300352223621558 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+step) * 0.0391; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.0391; \ncolor += texture2D(sampler,textureCoordinate+vec2(-step.x, step.y)) * 0.0391; \ncolor += texture2D(sampler,textureCoordinate+vec2( step.x,-step.y)) * 0.0391; \nstep.x = 9.49583 * texelWidthOffset  * strength; \nstep.y = 9.49583 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+vec2(step.x, 0.)) * 0.037815; \ncolor += texture2D(sampler,textureCoordinate-vec2(step.x, 0.)) * 0.037815; \ncolor += texture2D(sampler,textureCoordinate+vec2(0., step.y)) * 0.037815; \ncolor += texture2D(sampler,textureCoordinate-vec2(0., step.y)) * 0.037815; \nstep.x = 8.128779348367749 * texelWidthOffset  * strength; \nstep.y = 8.128779348367749 * texelHeightOffset * strength; \ncolor += texture2D(sampler,textureCoordinate+step) * 0.03658; \ncolor += texture2D(sampler,textureCoordinate-step) * 0.03658; \ncolor += texture2D(sampler,textureCoordinate+vec2(-step.x, step.y)) * 0.03658; \ncolor += texture2D(sampler,textureCoordinate+vec2( step.x,-step.y)) * 0.03658; \nreturn color; \n} \nvec4 level(vec4 color, sampler2D sampler) \n{ \ncolor.r = texture2D(sampler, vec2(color.r, 0.)).r; \ncolor.g = texture2D(sampler, vec2(color.g, 0.)).g;\ncolor.b = texture2D(sampler, vec2(color.b, 0.)).b; \nreturn color; \n} \nvec4 normal(vec4 c1, vec4 c2, float alpha) \n{ \nreturn (c2-c1) * alpha + c1;\n}   \nvec4 lighten(vec4 c1, vec4 c2) \n{ \nreturn max(c1,c2);\n}\nvec4 overlay(vec4 c1, vec4 c2)\n{\nvec4 r1 = vec4(0.,0.,0.,1.); \nr1.r = c1.r < 0.5 ? 2.0*c1.r*c2.r : 1.0 - 2.0*(1.0-c1.r)*(1.0-c2.r);\nr1.g = c1.g < 0.5 ? 2.0*c1.g*c2.g : 1.0 - 2.0*(1.0-c1.g)*(1.0-c2.g);\nr1.b = c1.b < 0.5 ? 2.0*c1.b*c2.b : 1.0 - 2.0*(1.0-c1.b)*(1.0-c2.b);\nreturn r1;\n} \nvec3 lerp (vec3 x, vec3 y, float s) \n{\nreturn x+s*(y-x);\n} \nvec4 adjust (vec4 color, float brightness1, float contrast, float saturation)\n{\nvec3 averageLuminance = vec3(0.5);\nvec3 brightedColor    = color.rgb * (brightness1+1.);\nvec3 intensity        = vec3(dot(brightedColor, vec3(0.299, 0.587, 0.114)));\nvec3 saturatedColor   = lerp(intensity, brightedColor, saturation+1.);\nvec3 contrastedColor  = lerp(averageLuminance, saturatedColor, contrast+1.);\nreturn vec4(contrastedColor,1.); \n}\nvec4 vibrance(vec4 color, float strength)\n{ \nfloat luminance = (color.r+color.g+color.b)/3.;\nfloat maximum   = max(color.r, max(color.g, color.b));\nfloat amount    = (maximum-luminance)*-strength; \nreturn vec4(color.rgb * (1.-amount) + maximum*amount, 1.); \n} \nvoid main() \n{ \nvec4 c1; \nvec4 c2; \nif (lowPerformance == 1) \n{ \t\nc1 = texture2D(inputImageTexture, textureCoordinate); \t\nc2 = texture2D(inputImageTexture, textureCoordinate); \n} \nelse \n{ \nc1 = sharpen(inputImageTexture); \nc2 = normal(c1, gaussianBlur(inputImageTexture), 0.8); // radius = 13. sharpen?? gaussian blur? ???? ??, ?? blending?? ?? \n} \nvec4 c3 = normal(c1, lighten(c1,c2), 0.6); // lighten (0.6) \nc3 = adjust(c3, 0.12, 0., 0.05); // brightness1 = 12, saturation = 0.5; \nc3 = vibrance(level(c3, curve), 0.5); // vibrance = 0.5; \nc3 = normal(c3, overlay(c3, vec4(0.)), 1.-texture2D(samplerMask, textureCoordinate).r); // vignetting \ngl_FragColor = c3;\n}\n";
    private float mHue;
     int mHueLocation;
     int mLowPerformanceUniformLocation;
     int mMaskGrey1TextureId;
     int mMaskGrey1UniformLocation;
    public int[] mToneCurveTexture;
    private int mToneCurveTextureUniformLocation;

    class C11431 implements Runnable {
        C11431() {
        }

        public void run() {
            GLES20.glGenTextures(1, Sweety.this.mToneCurveTexture, 0);
            GLES20.glBindTexture(3553, Sweety.this.mToneCurveTexture[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            byte[] bArr = new byte[1024];
            int[] iArr = {0, 1, 2, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 19, 20, 21, 22, 23, 24, 24, 25, 26, 27, 28, 29, 30, 30, 31, 32, 33, 34, 35, 36, 37, 38, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 71, 72, 73, 74, 75, 76, 77, 79, 80, 81, 82, 83, 84, 86, 87, 88, 89, 90, 92, 93, 94, 95, 96, 98, 99, 100, 101, 103, 104, 105, 106, 108, 109, 110, 111, 113, 114, 115, 116, 118, 119, 120, 121, 123, 124, 125, 126, 128, 129, 130, 132, 133, 134, 135, 137, 138, 139, 140, 142, 143, 144, 145, 147, 148, 149, 150, 152, 153, 154, 155, 157, 158, 159, 160, 161, 163, 164, 165, 166, 167, 169, 170, 171, 172, 173, 174, 176, 177, 178, 179, 180, 181, 182, 183, 184, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 205, 206, 207, 208, 209, 209, 210, 211, 212, 213, 214, 215, 216, 217, 217, 218, 219, 220, 221, 222, 222, 223, 224, 225, 226, 227, 227, 228, 229, 230, 230, 231, 232, 233, 234, 234, 235, 236, 237, 237, 238, 239, 240, 240, 241, 242, 243, 243, 244, 245, 246, 246, 247, 248, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 252, 253, 254, 254, 255};
            for (int i = 0; i < 256; i++) {
                int i2 = i * 4;
                bArr[i2] = (byte) iArr[i];
                bArr[i2 + 1] = (byte) iArr[i];
                bArr[i2 + 2] = (byte) iArr[i];
                bArr[i2 + 3] = (byte) i;
            }
            GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
        }
    }

    public Sweety() {
        this(90.0f);
    }


    public Sweety(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, HUE_FRAGMENT_SHADER);
        this.mToneCurveTexture = new int[]{-1};
        this.mMaskGrey1TextureId = -1;
        this.mHue = f;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(2, new int[]{this.mToneCurveTexture[0], this.mMaskGrey1TextureId}, 0);
        this.mToneCurveTexture[0] = -1;
        this.mMaskGrey1TextureId = -1;
    }

    @Override
    public void onInit() {
        super.onInit();
        this.mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(this.mGLProgId, "curve");
        this.mMaskGrey1UniformLocation = GLES20.glGetUniformLocation(getProgram(), "grey1Frame");
        this.mLowPerformanceUniformLocation = GLES20.glGetUniformLocation(getProgram(), "lowPerformance");
        setInteger(this.mLowPerformanceUniformLocation, 1);
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        runOnDraw(new C11431());
    }

    public void setHue(float f) {
        this.mHue = f;
        setFloat(this.mHueLocation, ((this.mHue % 360.0f) * 3.1415927f) / 180.0f);
    }

    public void onDrawArraysAfter() {
        if (this.mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
        if (this.mMaskGrey1TextureId != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
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

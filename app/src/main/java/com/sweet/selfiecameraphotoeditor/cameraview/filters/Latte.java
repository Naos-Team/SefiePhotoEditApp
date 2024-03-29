package com.sweet.selfiecameraphotoeditor.cameraview.filters;

import android.opengl.GLES20;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.nio.ByteBuffer;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

public class Latte extends GPUImageFilter {
    public static final String HUE_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate; \nprecision highp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D curve;\nvec3 rgb2hsv(vec3 c) \n{ \nvec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\nvec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\nvec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\nfloat d = q.x - min(q.w, q.y);\nfloat e = 1.0e-10;\nreturn vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n} \nvec3 hsv2rgb(vec3 c) \n{ \nvec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\nvec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\nreturn c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y); \n}\nvoid main()\n{\nfloat GreyVal;\nlowp vec4 textureColor;\nlowp vec4 textureColorOri;\nfloat xCoordinate = textureCoordinate.x;\nfloat yCoordinate = textureCoordinate.y; \nhighp float redCurveValue; \nhighp float greenCurveValue; \nhighp float blueCurveValue;\ntextureColor = texture2D( inputImageTexture, vec2(xCoordinate, yCoordinate)); \nmediump vec4 base = textureColor; \nmediump vec4 overlay = vec4(0.792, 0.58, 0.372, 1.0); \nmediump float ra; \nif (base.r < 0.5) \n{ \n\tra = overlay.r * base.r * 2.0;\n} \nelse \n{ \n\tra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n} \nmediump float ga; \nif (base.g < 0.5) \n{ \n\tga = overlay.g * base.g * 2.0;\n} \nelse \n{ \nga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0);\n} \nmediump float ba; \nif (base.b < 0.5) \n{ \n\tba = overlay.b * base.b * 2.0; \n } \nelse \n{ \n\tba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0); \n} \ntextureColor = vec4(ra, ga, ba, 1.0); \ntextureColor = (textureColor - base) * 0.3 + base; \nredCurveValue = texture2D(curve, vec2(textureColor.r, 0.0)).r; \ngreenCurveValue = texture2D(curve, vec2(textureColor.g, 0.0)).g; \nblueCurveValue = texture2D(curve, vec2(textureColor.b, 0.0)).b; \nredCurveValue = texture2D(curve, vec2(redCurveValue, 1.0)).g; \ngreenCurveValue = texture2D(curve, vec2(greenCurveValue, 1.0)).g; \nblueCurveValue = texture2D(curve, vec2(blueCurveValue, 1.0)).g; \nvec3 tColor = vec3(redCurveValue, greenCurveValue, blueCurveValue); \ntColor = rgb2hsv(tColor); \ntColor.g = tColor.g * 0.6; \nfloat dStrength = 1.0; \nfloat dSatStrength = 0.2; \nfloat dGap = 0.0; \nif( tColor.r >= 0.0 && tColor.r < 0.417)\n{ \ntColor.g = tColor.g + (tColor.g * dSatStrength); \n } \nelse if( tColor.r > 0.958 && tColor.r <= 1.0) \n{ \n\ttColor.g = tColor.g + (tColor.g * dSatStrength); \n  } \nelse if( tColor.r >= 0.875 && tColor.r <= 0.958) \n{ \n\tdGap = abs(tColor.r - 0.875); \n\tdStrength = (dGap / 0.0833); \n\ttColor.g = tColor.g + (tColor.g * dSatStrength * dStrength); \n } \nelse if( tColor.r >= 0.0417 && tColor.r <= 0.125) \n{ \n\tdGap = abs(tColor.r - 0.125);\n\tdStrength = (dGap / 0.0833); \n\ttColor.g = tColor.g + (tColor.g * dSatStrength * dStrength); \n} \ntColor = hsv2rgb(tColor); \ntColor = clamp(tColor, 0.0, 1.0); \nredCurveValue = texture2D(curve, vec2(tColor.r, 1.0)).r; \ngreenCurveValue = texture2D(curve, vec2(tColor.g, 1.0)).r; \nblueCurveValue = texture2D(curve, vec2(tColor.b, 1.0)).r; \nbase = vec4(redCurveValue, greenCurveValue, blueCurveValue, 1.0); \noverlay = vec4(0.792, 0.494, 0.372, 1.0); \nif (base.r < 0.5) \n{\n\tra = overlay.r * base.r * 2.0; \n} \nelse \n{ \n\tra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n } \nif (base.g < 0.5) \n{ \nga = overlay.g * base.g * 2.0; \n} \nelse \n{ \n\tga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0); \n} \nif (base.b < 0.5) \n{ \n\tba = overlay.b * base.b * 2.0; \n}\nelse \n{ \n\tba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0);\n} \ntextureColor = vec4(ra, ga, ba, 1.0); \ntextureColor = (textureColor - base) * 0.15 + base; \ngl_FragColor = vec4(textureColor.r, textureColor.g, textureColor.b, 1.0); \n}\n";
     float mHue;
     int mHueLocation;
     int mMaskGrey1TextureId;
     int mMaskGrey2TextureId;
    public int[] mToneCurveTexture;
    private int mToneCurveTextureUniformLocation;

    class C11361 implements Runnable {
        C11361() {
        }

        public void run() {
            GLES20.glGenTextures(1, Latte.this.mToneCurveTexture, 0);
            GLES20.glBindTexture(3553, Latte.this.mToneCurveTexture[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            byte[] bArr = new byte[2048];
            int[] iArr = {5, 6, 8, 9, 11, 12, 14, 15, 16, 18, 19, 21, 22, 23, 25, 26, 28, 29, 30, 32, 33, 34, 36, 37, 39, 40, 41, 43, 44, 45, 46, 48, 49, 50, 52, 53, 54, 55, 56, 58, 59, 60, 61, 62, 64, 65, 66, 67, 68, 69, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 108, 109, 110, 111, 112, 113, 114, 115, 115, 116, 117, 118, 119, 120, 120, 121, 122, 123, 124, 125, 125, 126, 127, 128, 129, 130, 130, 131, 132, 133, 134, 134, 135, 136, 137, 137, 138, 139, 140, 141, 141, 142, 143, 144, 145, 145, 146, 147, 148, 148, 149, 150, 151, 151, 152, 153, 154, 155, 155, 156, 157, 158, 158, 159, 160, 161, 162, 162, 163, 164, 165, 166, 166, 167, 168, 169, 170, 170, 171, 172, 173, 174, 174, 175, 176, 177, 178, 178, 179, 180, 181, 182, 183, 183, 184, 185, 186, 187, 188, 189, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 253, 254, 255};
            int[] iArr2 = {5, 6, 8, 11, 12, 14, 15, 18, 19, 21, 22, 25, 26, 28, 29, 32, 33, 34, 36, 39, 40, 41, 43, 44, 46, 48, 49, 50, 52, 54, 55, 56, 58, 59, 61, 62, 64, 65, 66, 67, 69, 71, 72, 73, 74, 75, 76, 78, 79, 80, 81, 82, 83, 85, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 108, 108, 109, 110, 111, 112, 113, 114, 115, 115, 116, 117, 118, 119, 120, 120, 121, 122, 123, 125, 125, 126, 127, 128, 129, 130, 130, 131, 132, 133, 134, 134, 135, 136, 137, 137, 138, 139, 140, 141, 141, 142, 143, 144, 145, 145, 146, 147, 148, 148, 149, 149, 150, 151, 151, 152, 153, 154, 155, 155, 156, 157, 158, 158, 159, 160, 161, 162, 162, 163, 164, 165, 165, 166, 166, 167, 168, 169, 170, 170, 171, 172, 173, 174, 174, 175, 175, 176, 177, 178, 178, 179, 180, 181, 182, 183, 183, 184, 184, 185, 186, 187, 188, 189, 189, 190, 191, 192, 192, 193, 194, 195, 196, 197, 198, 198, 199, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 205, 206, 206, 207, 208, 209, 210, 211, 212, 213, 213, 214, 215, 215, 216, 217, 218, 219, 219, 220, 221, 222, 223, 224, 225, 226, 226, 227, 228, 229, 230, 231, 232, 232, 233, 234, 235, 237, 238, 239, 240, 240, 241, 242, 243, 244, 245, 246, 246, 247, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, 254, 255};
            int[] iArr3 = {5, 6, 8, 11, 12, 14, 15, 16, 18, 21, 22, 23, 25, 26, 28, 30, 32, 33, 34, 36, 37, 40, 41, 43, 44, 45, 46, 49, 50, 52, 53, 54, 55, 58, 59, 60, 61, 62, 64, 66, 67, 68, 69, 71, 72, 73, 75, 76, 77, 78, 79, 80, 81, 83, 85, 86, 87, 88, 89, 90, 92, 93, 94, 95, 95, 96, 97, 98, 99, 101, 102, 103, 104, 105, 106, 107, 108, 108, 109, 111, 112, 113, 114, 115, 115, 116, 117, 118, 119, 120, 120, 121, 123, 124, 125, 125, 126, 127, 128, 129, 130, 130, 131, 132, 133, 134, 134, 135, 136, 137, 137, 138, 139, 140, 141, 141, 142, 143, 144, 145, 145, 146, 147, 148, 148, 149, 150, 151, 151, 152, 153, 154, 155, 155, 156, 156, 157, 158, 158, 159, 160, 161, 162, 162, 163, 164, 165, 165, 166, 166, 167, 168, 169, 170, 170, 170, 171, 172, 173, 174, 174, 175, 176, 176, 177, 178, 178, 179, 180, 180, 181, 182, 183, 183, 184, 184, 185, 186, 187, 188, 189, 189, 189, 190, 191, 192, 192, 193, 194, 195, 196, 196, 197, 198, 198, 199, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 202, 203, 204, 205, 206, 206, 207, 208, 209, 209, 210, 211, 212, 213, 213, 214, 215, 215, 215, 216, 217, 218, 218, 219, 220, 221, 221, 222, 223, 224, 224, 225, 226, 227, 227, 228, 229, 230, 230, 231, 232, 233, 233, 234, 235, 237, 237, 238, 239, 240, 240, 241, 242, 243, 243, 244};
            for (int i = 0; i < 256; i++) {
                int i2 = i * 4;
                bArr[i2] = (byte) iArr[i];
                bArr[i2 + 1] = (byte) iArr2[i];
                bArr[i2 + 2] = (byte) iArr3[i];
                bArr[i2 + 3] = -1;
            }
            int[] iArr4 = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 10, 12, 13, 14, 15, 16, 18, 19, 20, 21, 22, 24, 25, 26, 27, 28, 30, 31, 32, 33, 34, 36, 37, 38, 39, 40, 42, 43, 44, 45, 46, 48, 49, 50, 51, 52, 54, 55, 56, 57, 58, 60, 61, 62, 63, 64, 66, 67, 68, 69, 71, 72, 73, 74, 75, 77, 78, 79, 80, 81, 83, 84, 85, 86, 87, 89, 90, 91, 92, 93, 95, 96, 97, 98, 99, 101, 102, 103, 104, 105, 107, 108, 109, 110, 111, 113, 114, 115, 116, 117, 119, 120, 121, 122, 123, 125, 126, 127, 128, 129, 131, 132, 133, 134, 135, 137, 138, 139, 140, 141, 143, 144, 145, 146, 147, 149, 150, 151, 152, 153, 155, 156, 157, 158, 159, 161, 162, 163, 164, 165, 167, 168, 169, 170, 171, 173, 174, 175, 176, 177, 179, 180, 181, 182, 184, 185, 186, 187, 188, 190, 191, 192, 193, 194, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 202, 203, 204, 205, 206, 208, 209, 210, 211, 212, 214, 215, 216, 217, 218, 220, 221, 222, 223, 224, 226, 227, 228, 229, 230, 232, 233, 234, 235, 236, 238, 239, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240};
            int[] iArr5 = {0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 35, 36, 37, 38, 39, 41, 42, 43, 44, 46, 47, 48, 49, 51, 52, 53, 54, 56, 57, 58, 59, 61, 62, 63, 65, 66, 67, 69, 70, 71, 73, 74, 75, 77, 78, 79, 81, 82, 83, 85, 86, 87, 89, 90, 92, 93, 94, 96, 97, 99, 100, 101, 103, 104, 106, 107, 108, 110, 111, 113, 114, 116, 117, 119, 120, 121, 123, 124, 126, 127, 129, 130, 132, 133, 135, 136, 138, 139, 140, 142, 143, 145, 146, 147, 149, 150, 151, 153, 154, 155, 157, 158, 159, 160, 162, 163, 164, 165, 167, 168, 169, 170, 171, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 210, 211, 212, 213, 214, 215, 215, 216, 217, 218, 219, 219, 220, 221, 222, 222, 223, 224, 225, 225, 226, 227, 227, 228, 229, 229, 230, 231, 231, 232, 233, 233, 234, 234, 235, 236, 236, 237, 237, 238, 238, 239, 240, 240, 241, 241, 242, 242, 243, 243, 244, 244, 244, 245, 245, 246, 246, 247, 247, 247, 248, 248, 249, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 251, 252, 252, 252, 252, 253, 253, 253, 253, 254, 254, 254, 254, 255, 255, 255};

            int[] iArr7 = {0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 23, 23, 23, 24, 24, 24, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 28, 28, 29, 29, 30, 29, 31, 31, 31, 31, 32, 32, 33, 33, 34, 34, 34, 34, 35, 35, 36, 36, 37, 37, 37, 38, 38, 39, 39, 39, 40, 40, 40, 41, 42, 42, 43, 43, 44, 44, 45, 45, 45, 46, 47, 47, 48, 48, 49, 50, 51, 51, 52, 52, 53, 53, 54, 55, 55, 56, 57, 57, 58, 59, 60, 60, 61, 62, 63, 63, 64, 65, 66, 67, 68, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 100, 101, 103, 104, 105, 107, 108, 110, 111, 113, 115, 116, 118, 119, 120, 122, 123, 125, 127, 128, 130, 132, 134, 135, 137, 139, 141, 143, 144, 146, 148, 150, 152, 154, 156, 158, 160, 163, 165, 167, 169, 171, 173, 175, 178, 180, 182, 185, 187, 189, 192, 194, 197, 199, 201, 204, 206, 209, 211, 214, 216, 219, 221, 224, 226, 229, 232, 234, 236, 239, 241, 245, 247, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 252, 255};
            for (int i3 = 0; i3 < 256; i3++) {
                int i4 = (i3 * 4) + 1024;
                bArr[i4] = (byte) iArr5[i3];
                bArr[i4 + 1] = (byte) iArr4[i3];
                bArr[i4 + 2] = (byte) iArr7[i3];
                bArr[i4 + 3] = -1;
            }
            GLES20.glTexImage2D(3553, 0, 6408, 256, 2, 0, 6408, 5121, ByteBuffer.wrap(bArr));
        }
    }

    public Latte() {
        this(90.0f);
    }

    public Latte(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, "varying highp vec2 textureCoordinate; \nprecision highp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D curve;\nvec3 rgb2hsv(vec3 c) \n{ \nvec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\nvec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\nvec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\nfloat d = q.x - min(q.w, q.y);\nfloat e = 1.0e-10;\nreturn vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n} \nvec3 hsv2rgb(vec3 c) \n{ \nvec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\nvec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\nreturn c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y); \n}\nvoid main()\n{\nfloat GreyVal;\nlowp vec4 textureColor;\nlowp vec4 textureColorOri;\nfloat xCoordinate = textureCoordinate.x;\nfloat yCoordinate = textureCoordinate.y; \nhighp float redCurveValue; \nhighp float greenCurveValue; \nhighp float blueCurveValue;\ntextureColor = texture2D( inputImageTexture, vec2(xCoordinate, yCoordinate)); \nmediump vec4 base = textureColor; \nmediump vec4 overlay = vec4(0.792, 0.58, 0.372, 1.0); \nmediump float ra; \nif (base.r < 0.5) \n{ \n\tra = overlay.r * base.r * 2.0;\n} \nelse \n{ \n\tra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n} \nmediump float ga; \nif (base.g < 0.5) \n{ \n\tga = overlay.g * base.g * 2.0;\n} \nelse \n{ \nga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0);\n} \nmediump float ba; \nif (base.b < 0.5) \n{ \n\tba = overlay.b * base.b * 2.0; \n } \nelse \n{ \n\tba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0); \n} \ntextureColor = vec4(ra, ga, ba, 1.0); \ntextureColor = (textureColor - base) * 0.3 + base; \nredCurveValue = texture2D(curve, vec2(textureColor.r, 0.0)).r; \ngreenCurveValue = texture2D(curve, vec2(textureColor.g, 0.0)).g; \nblueCurveValue = texture2D(curve, vec2(textureColor.b, 0.0)).b; \nredCurveValue = texture2D(curve, vec2(redCurveValue, 1.0)).g; \ngreenCurveValue = texture2D(curve, vec2(greenCurveValue, 1.0)).g; \nblueCurveValue = texture2D(curve, vec2(blueCurveValue, 1.0)).g; \nvec3 tColor = vec3(redCurveValue, greenCurveValue, blueCurveValue); \ntColor = rgb2hsv(tColor); \ntColor.g = tColor.g * 0.6; \nfloat dStrength = 1.0; \nfloat dSatStrength = 0.2; \nfloat dGap = 0.0; \nif( tColor.r >= 0.0 && tColor.r < 0.417)\n{ \ntColor.g = tColor.g + (tColor.g * dSatStrength); \n } \nelse if( tColor.r > 0.958 && tColor.r <= 1.0) \n{ \n\ttColor.g = tColor.g + (tColor.g * dSatStrength); \n  } \nelse if( tColor.r >= 0.875 && tColor.r <= 0.958) \n{ \n\tdGap = abs(tColor.r - 0.875); \n\tdStrength = (dGap / 0.0833); \n\ttColor.g = tColor.g + (tColor.g * dSatStrength * dStrength); \n } \nelse if( tColor.r >= 0.0417 && tColor.r <= 0.125) \n{ \n\tdGap = abs(tColor.r - 0.125);\n\tdStrength = (dGap / 0.0833); \n\ttColor.g = tColor.g + (tColor.g * dSatStrength * dStrength); \n} \ntColor = hsv2rgb(tColor); \ntColor = clamp(tColor, 0.0, 1.0); \nredCurveValue = texture2D(curve, vec2(tColor.r, 1.0)).r; \ngreenCurveValue = texture2D(curve, vec2(tColor.g, 1.0)).r; \nblueCurveValue = texture2D(curve, vec2(tColor.b, 1.0)).r; \nbase = vec4(redCurveValue, greenCurveValue, blueCurveValue, 1.0); \noverlay = vec4(0.792, 0.494, 0.372, 1.0); \nif (base.r < 0.5) \n{\n\tra = overlay.r * base.r * 2.0; \n} \nelse \n{ \n\tra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n } \nif (base.g < 0.5) \n{ \nga = overlay.g * base.g * 2.0; \n} \nelse \n{ \n\tga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0); \n} \nif (base.b < 0.5) \n{ \n\tba = overlay.b * base.b * 2.0; \n}\nelse \n{ \n\tba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0);\n} \ntextureColor = vec4(ra, ga, ba, 1.0); \ntextureColor = (textureColor - base) * 0.15 + base; \ngl_FragColor = vec4(textureColor.r, textureColor.g, textureColor.b, 1.0); \n}\n");
        this.mToneCurveTexture = new int[]{-1};
        this.mMaskGrey1TextureId = -1;
        this.mMaskGrey2TextureId = -1;
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
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        runOnDraw(new C11361());
    }


    public void setHue(float f) {
        this.mHue = f;
        setFloat(this.mHueLocation, ((this.mHue % 360.0f) * 3.1415927f) / 180.0f);
    }
}

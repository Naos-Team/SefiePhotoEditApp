package com.sweet.selfiecameraphotoeditor.cameraview.filters;

import android.opengl.GLES20;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.nio.ByteBuffer;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

public class Sunrise extends GPUImageFilter {
    public static final String HUE_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nprecision highp float; \nuniform sampler2D inputImageTexture;\nuniform sampler2D curve;\nuniform sampler2D grey1Frame; \nuniform sampler2D grey2Frame;\nuniform sampler2D grey3Frame;\nvoid main() \n{ \nfloat GreyVal; \nlowp vec4 textureColor; \nlowp vec4 textureColorOri; \nfloat xCoordinate = textureCoordinate.x; \nfloat yCoordinate = textureCoordinate.y;\nhighp float redCurveValue; \nhighp float greenCurveValue;\nhighp float blueCurveValue; \nvec4 grey1Color;\nvec4 grey2Color; \nvec4 grey3Color;\ntextureColor = texture2D( inputImageTexture, vec2(xCoordinate, yCoordinate)); \ngrey1Color = texture2D(grey1Frame, vec2(xCoordinate, yCoordinate)); \ngrey2Color = texture2D(grey2Frame, vec2(xCoordinate, yCoordinate)); \ngrey3Color = texture2D(grey3Frame, vec2(xCoordinate, yCoordinate)); \nmediump vec4 overlay = vec4(0, 0, 0, 1.0); \nmediump vec4 base = textureColor; \nmediump float ra; \nif (base.r < 0.5) \n{ \nra = overlay.r * base.r * 2.0; \n} \nelse \n{ \nra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n} \nmediump float ga; \nif (base.g < 0.5)\n{ \nga = overlay.g * base.g * 2.0; \n}\nelse \n{ \nga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0); \n} \nmediump float ba;\nif (base.b < 0.5) \n{ \nba = overlay.b * base.b * 2.0; \n} \nelse \n{\nba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0); \n} \ntextureColor = vec4(ra, ga, ba, 1.0); \nbase = (textureColor - base) * (grey1Color.r*0.1019) + base; \ntextureColor = vec4(base.r, base.g, base.b, 1.0); \nmediump vec4 textureColor2 = vec4(0.098, 0.0, 0.1843, 1.0); \ntextureColor2 = textureColor + textureColor2 - (2.0 * textureColor2 * textureColor); \ntextureColor = (textureColor2 - textureColor) * 0.6 + textureColor; \nredCurveValue = texture2D(curve, vec2(textureColor.r, 0.0)).r; \ngreenCurveValue = texture2D(curve, vec2(textureColor.g, 0.0)).g;\nblueCurveValue = texture2D(curve, vec2(textureColor.b, 0.0)).b; \ntextureColorOri = vec4(redCurveValue, greenCurveValue, blueCurveValue, 1.0);\ntextureColor = (textureColorOri - textureColor) * grey2Color.r + textureColor; \nredCurveValue = texture2D(curve, vec2(textureColor.r, 1.0)).r; \ngreenCurveValue = texture2D(curve, vec2(textureColor.g, 1.0)).g; \nblueCurveValue = texture2D(curve, vec2(textureColor.b, 1.0)).b; \ntextureColorOri = vec4(redCurveValue, greenCurveValue, blueCurveValue, 1.0); \ntextureColor = (textureColorOri - textureColor) * (grey3Color.r) * 1.0 + textureColor; \noverlay = vec4(0.6117, 0.6117, 0.6117, 1.0); \nbase = textureColor;\nif (base.r < 0.5) \n{ \nra = overlay.r * base.r * 2.0; \n} \nelse \n{ \nra = 1.0 - ((1.0 - base.r) * (1.0 - overlay.r) * 2.0); \n} \nif (base.g < 0.5) \n{ \nga = overlay.g * base.g * 2.0; \n} \nelse \n{ \nga = 1.0 - ((1.0 - base.g) * (1.0 - overlay.g) * 2.0); \n} \nif (base.b < 0.5) \n{\nba = overlay.b * base.b * 2.0; \n} \nelse \n{ \nba = 1.0 - ((1.0 - base.b) * (1.0 - overlay.b) * 2.0);\n}\ntextureColor = vec4(ra, ga, ba, 1.0); \nbase = (textureColor - base) + base; \ntextureColor = vec4(base.r, base.g, base.b, 1.0); \ntextureColor2 = vec4(0.113725, 0.0039, 0.0, 1.0); \ntextureColor2 = textureColor + textureColor2 - (2.0 * textureColor2 * textureColor); \nbase = (textureColor2 - textureColor) * 0.3 + textureColor; \nredCurveValue = texture2D(curve, vec2(base.r, 1.0)).a; \ngreenCurveValue = texture2D(curve, vec2(base.g, 1.0)).a; \nblueCurveValue = texture2D(curve, vec2(base.b, 1.0)).a; \nbase = vec4(redCurveValue, greenCurveValue, blueCurveValue, 1.0); \noverlay = vec4(1.0, 1.0, 1.0, 1.0); \ntextureColor = 1.0 - ((1.0 - base) * (1.0 - overlay));\ntextureColor = (textureColor - base) * 0.05098 + base; \ngl_FragColor = vec4(textureColor.r, textureColor.g, textureColor.b, 1.0);\n}\n";
     float mHue;
     int mMaskGrey1TextureId;
     int mMaskGrey1UniformLocation;
     int mMaskGrey2TextureId;
     int mMaskGrey2UniformLocation;
     int mMaskGrey3TextureId;
     int mMaskGrey3UniformLocation;
    public int[] mToneCurveTexture;
     int mToneCurveTextureUniformLocation;

    class C11411 implements Runnable {
        C11411() {
        }

        public void run() {
            GLES20.glActiveTexture(33987);
            GLES20.glGenTextures(1, Sunrise.this.mToneCurveTexture, 0);
            GLES20.glBindTexture(3553, Sunrise.this.mToneCurveTexture[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            byte[] bArr = new byte[2048];
            int[] iArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20, 21, 22, 23, 25, 26, 27, 28, 30, 31, 32, 34, 35, 36, 38, 39, 41, 42, 44, 45, 47, 49, 50, 52, 54, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 92, 94, 96, 98, 101, 103, 105, 107, 110, 111, 113, 115, 118, 120, 122, 124, 126, 129, 131, 133, 135, 137, 139, 141, 143, 145, 147, 149, 150, 152, 154, 156, 158, 159, 161, 162, 164, 166, 167, 169, 170, 172, 173, 174, 176, 177, 178, 180, 181, 182, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 203, 204, 205, 205, 207, 208, 208, 209, 209, 210, 210, 211, 211, 212, 212, 213, 213, 213, 214, 214, 215, 215, 215, 216, 216, 216, 216, 217, 217, 217, 218, 218, 218, 218, 219, 219, 219, 219, 219, 220, 220, 220, 220, 220, 220, 221, 221, 221, 221, 221, 222, 222, 222, 222, 222, 223, 223, 223, 223, 223, 224, 224, 224, 224, 224, 225, 225, 225, 225, 226, 226, 226, 227, 227, 227, 228, 228, 228, 229, 229, 230, 230, 230, 231, 231, 232, 232, 233, 233, 234, 234, 235, 235, 236, 236, 237, 238, 238, 239, 239, 241, 241, 242, 243, 243, 244, 245, 245, 246, 246, 247, 248, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, 254, 254, 255};
            int[] iArr2 = {0, 1, 3, 4, 5, 7, 8, 10, 11, 12, 14, 15, 17, 18, 19, 21, 22, 24, 25, 27, 28, 30, 31, 33, 34, 36, 37, 39, 40, 42, 44, 45, 47, 48, 50, 52, 54, 55, 57, 59, 61, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 83, 85, 87, 90, 92, 94, 96, 98, 101, 103, 105, 107, 110, 111, 113, 115, 117, 119, 122, 124, 126, 128, 130, 132, 134, 136, 138, 140, 142, 143, 145, 147, 149, 150, 152, 154, 155, 157, 158, 160, 161, 163, 164, 165, 167, 168, 169, 171, 172, 173, 174, 175, 176, 177, 179, 180, 181, 182, 183, 184, 184, 185, 186, 187, 188, 189, 190, 190, 191, 192, 193, 193, 194, 195, 195, 196, 197, 197, 198, 198, 199, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 201, 202, 202, 203, 203, 204, 204, 205, 205, 205, 207, 207, 208, 208, 208, 209, 209, 210, 210, 210, 211, 211, 211, 212, 212, 212, 213, 213, 213, 214, 214, 214, 215, 215, 215, 216, 216, 216, 217, 217, 217, 218, 218, 219, 219, 219, 220, 220, 220, 221, 221, 222, 222, 222, 223, 223, 224, 224, 225, 225, 225, 226, 226, 227, 227, 228, 228, 228, 229, 229, 230, 230, 231, 231, 232, 232, 233, 233, 234, 234, 235, 235, 236, 236, 236, 237, 237, 238, 238, 239, 239, 241, 241, 242, 242, 243, 244, 244, 245, 245, 246, 246, 247, 247, 248, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 252, 252, 253, 253, 254, 254, 255};
            int[] iArr3 = {0, 1, 3, 4, 5, 6, 8, 9, 10, 11, 13, 14, 15, 17, 18, 19, 21, 22, 23, 25, 26, 28, 29, 30, 32, 33, 35, 36, 38, 39, 41, 43, 44, 46, 47, 49, 51, 53, 54, 56, 58, 60, 62, 64, 66, 68, 69, 71, 73, 76, 78, 80, 82, 83, 85, 87, 89, 91, 93, 95, 97, 100, 102, 104, 106, 108, 110, 111, 113, 115, 117, 119, 121, 123, 125, 127, 129, 130, 132, 134, 136, 138, 139, 141, 143, 145, 146, 148, 150, 151, 153, 155, 156, 158, 159, 161, 162, 164, 165, 167, 168, 169, 171, 172, 173, 175, 176, 177, 179, 180, 181, 182, 183, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 204, 205, 207, 207, 208, 209, 209, 210, 210, 211, 212, 212, 213, 213, 214, 214, 215, 215, 215, 216, 216, 217, 217, 217, 218, 218, 218, 219, 219, 219, 220, 220, 220, 220, 221, 221, 221, 221, 222, 222, 222, 222, 223, 223, 223, 223, 224, 224, 224, 224, 224, 225, 225, 225, 225, 225, 226, 226, 226, 226, 227, 227, 227, 227, 228, 228, 228, 228, 229, 229, 229, 230, 230, 230, 231, 231, 231, 232, 232, 233, 233, 233, 234, 234, 235, 235, 235, 236, 236, 237, 237, 238, 238, 239, 239, 241, 241, 242, 242, 243, 243, 244, 244, 245, 245, 246, 247, 247, 248, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, 253, 254, 254, 255};
            int[] iArr4 = {0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 23, 23, 23, 24, 24, 24, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 28, 28, 29, 29, 30, 29, 31, 31, 31, 31, 32, 32, 33, 33, 34, 34, 34, 34, 35, 35, 36, 36, 37, 37, 37, 38, 38, 39, 39, 39, 40, 40, 40, 41, 42, 42, 43, 43, 44, 44, 45, 45, 45, 46, 47, 47, 48, 48, 49, 50, 51, 51, 52, 52, 53, 53, 54, 55, 55, 56, 57, 57, 58, 59, 60, 60, 61, 62, 63, 63, 64, 65, 66, 67, 68, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 100, 101, 103, 104, 105, 107, 108, 110, 111, 113, 115, 116, 118, 119, 120, 122, 123, 125, 127, 128, 130, 132, 134, 135, 137, 139, 141, 143, 144, 146, 148, 150, 152, 154, 156, 158, 160, 163, 165, 167, 169, 171, 173, 175, 178, 180, 182, 185, 187, 189, 192, 194, 197, 199, 201, 204, 206, 209, 211, 214, 216, 219, 221, 224, 226, 229, 232, 234, 236, 239, 241, 245, 247, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 252, 255};
            for (int i = 0; i < 256; i++) {
                int i2 = i * 4;
                bArr[i2] = (byte) iArr[i];
                bArr[i2 + 1] = (byte) iArr2[i];
                bArr[i2 + 2] = (byte) iArr3[i];
                bArr[i2 + 3] = (byte) iArr4[i];
            }
            int[] iArr5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 26, 27, 28, 29, 30, 31, 32, 34, 35, 36, 37, 38, 39, 41, 42, 43, 44, 45, 46, 48, 49, 50, 51, 52, 53, 54, 56, 57, 58, 59, 60, 61, 63, 64, 65, 66, 67, 68, 70, 71, 72, 73, 74, 75, 77, 78, 79, 80, 81, 82, 83, 85, 86, 87, 88, 89, 90, 92, 93, 94, 95, 96, 97, 99, 100, 101, 102, 103, 104, 105, 107, 108, 109, 110, 111, 112, 114, 115, 116, 117, 118, 119, 121, 122, 123, 124, 125, 126, 128, 129, 130, 131, 132, 133, 134, 136, 137, 138, 139, 140, 141, 143, 144, 145, 146, 147, 148, 150, 151, 152, 153, 154, 155, 156, 158, 159, 160, 161, 162, 163, 165, 166, 167, 168, 169, 170, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 184, 185, 187, 188, 189, 190, 191, 192, 194, 195, 196, 197, 198, 199, 201, 202, 203, 204, 205, 206, 207, 209, 210, 211, 212, 213, 214, 216, 217, 218, 219, 220, 221, 223, 224, 225, 226, 227, 228, 230, 231, 232, 233, 234, 235, 236, 238, 239, 240, 241, 242, 243, 245, 246, 247, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 252, 253, 254, 255};

            int[] iArr7 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 4, 5, 6, 7, 9, 10, 11, 12, 14, 15, 16, 17, 19, 20, 21, 22, 24, 25, 26, 27, 29, 30, 31, 32, 34, 35, 36, 37, 39, 40, 41, 42, 44, 45, 46, 47, 49, 50, 51, 52, 53, 55, 56, 57, 58, 60, 61, 62, 63, 65, 66, 67, 68, 70, 71, 72, 73, 75, 76, 77, 78, 80, 81, 82, 83, 85, 86, 87, 88, 90, 91, 92, 93, 95, 96, 97, 98, 100, 101, 102, 103, 104, 106, 107, 108, 109, 111, 112, 113, 114, 116, 117, 118, 119, 121, 122, 123, 124, 126, 127, 128, 129, 131, 132, 133, 134, 136, 137, 138, 139, 141, 142, 143, 144, 146, 147, 148, 149, 151, 152, 153, 154, 155, 157, 158, 159, 160, 162, 163, 164, 165, 167, 168, 169, 170, 172, 173, 174, 175, 177, 178, 179, 180, 182, 183, 184, 185, 187, 188, 189, 190, 192, 193, 194, 195, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 202, 203, 204, 205, 206, 208, 209, 210, 211, 213, 214, 215, 216, 218, 219, 220, 221, 223, 224, 225, 226, 228, 229, 230, 231, 233, 234, 235, 236, 238, 239, 240, 241, 243, 244, 245, 246, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 253, 254, 255};

            int[] iArr9 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23, 24, 25, 26, 28, 29, 30, 31, 32, 33, 34, 36, 37, 38, 39, 40, 41, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 54, 55, 56, 57, 59, 60, 61, 62, 63, 64, 65, 67, 68, 69, 70, 71, 72, 74, 75, 76, 77, 78, 79, 80, 82, 83, 84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 95, 96, 98, 99, 100, 101, 102, 103, 105, 106, 107, 108, 109, 110, 111, 113, 114, 115, 116, 117, 118, 119, 121, 122, 123, 124, 125, 126, 128, 129, 130, 131, 132, 133, 134, 136, 137, 138, 139, 140, 141, 142, 144, 145, 146, 147, 148, 149, 150, 152, 153, 154, 155, 156, 157, 159, 160, 161, 162, 163, 164, 165, 167, 168, 169, 170, 171, 172, 173, 175, 176, 177, 178, 179, 180, 181, 183, 184, 185, 186, 187, 188, 190, 191, 192, 193, 194, 195, 196, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 206, 207, 208, 209, 210, 211, 213, 214, 215, 216, 217, 218, 219, 221, 222, 223, 224, 225, 226, 227, 229, 230, 231, 232, 233, 234, 235, 237, 238, 239, 240, 241, 242, 244, 245, 246, 247, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 252, 253, 254, 255};

            int[] iArr11 = {0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15, 15, 16, 17, 17, 18, 19, 19, 20, 21, 22, 22, 23, 24, 24, 25, 26, 27, 27, 28, 29, 30, 31, 31, 32, 33, 34, 34, 35, 36, 37, 38, 39, 39, 40, 41, 42, 43, 44, 44, 45, 46, 47, 48, 49, 50, 51, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 126, 127, 128, 129, 130, 131, 132, 134, 135, 136, 137, 138, 139, 140, 142, 143, 144, 145, 146, 147, 149, 150, 151, 152, 153, 155, 156, 157, 158, 159, 160, 162, 163, 164, 165, 166, 168, 169, 170, 171, 173, 174, 175, 176, 177, 179, 180, 181, 182, 184, 185, 186, 187, 189, 190, 191, 192, 194, 195, 196, 197, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 204, 205, 206, 208, 209, 210, 211, 213, 214, 215, 217, 218, 219, 221, 222, 223, 224, 226, 227, 228, 230, 231, 232, 234, 235, 236, 238, 239, 240, 242, 243, 244, 246, 247, 248, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 254, 255};
            for (int i3 = 0; i3 < 256; i3++) {
                int i4 = (i3 * 4) + 1024;
                bArr[i4] = (byte) iArr5[i3];
                bArr[i4 + 1] = (byte) iArr7[i3];
                bArr[i4 + 2] = (byte) iArr9[i3];
                bArr[i4 + 3] = (byte) iArr11[i3];
            }
            GLES20.glTexImage2D(3553, 0, 6408, 256, 2, 0, 6408, 5121, ByteBuffer.wrap(bArr));
            GLES20.glActiveTexture(33988);
            GLES20.glActiveTexture(33989);
            GLES20.glActiveTexture(33990);
        }
    }

    public Sunrise() {
        this(90.0f);
    }

    public Sunrise(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, HUE_FRAGMENT_SHADER);
        this.mMaskGrey1TextureId = -1;
        this.mMaskGrey2TextureId = -1;
        this.mMaskGrey3TextureId = -1;
        this.mToneCurveTexture = new int[]{-1};
        this.mHue = f;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(1, this.mToneCurveTexture, 0);
        this.mToneCurveTexture[0] = -1;
        GLES20.glDeleteTextures(1, new int[]{this.mMaskGrey1TextureId}, 0);
        this.mMaskGrey1TextureId = -1;
        GLES20.glDeleteTextures(1, new int[]{this.mMaskGrey2TextureId}, 0);
        this.mMaskGrey2TextureId = -1;
        GLES20.glDeleteTextures(1, new int[]{this.mMaskGrey3TextureId}, 0);
        this.mMaskGrey3TextureId = -1;
    }

    @Override
    public void onInit() {
        super.onInit();
        this.mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(this.mGLProgId, "curve");
        this.mMaskGrey1UniformLocation = GLES20.glGetUniformLocation(getProgram(), "grey1Frame");
        this.mMaskGrey2UniformLocation = GLES20.glGetUniformLocation(getProgram(), "grey2Frame");
        this.mMaskGrey3UniformLocation = GLES20.glGetUniformLocation(getProgram(), "grey3Frame");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        runOnDraw(new C11411());
    }

    @Override
    public void onDrawArraysPre() {
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
        if (this.mMaskGrey2TextureId != -1) {
            GLES20.glActiveTexture(33989);
            GLES20.glBindTexture(3553, this.mMaskGrey2TextureId);
            GLES20.glUniform1i(this.mMaskGrey2UniformLocation, 5);
        }
        if (this.mMaskGrey3TextureId != -1) {
            GLES20.glActiveTexture(33990);
            GLES20.glBindTexture(3553, this.mMaskGrey3TextureId);
            GLES20.glUniform1i(this.mMaskGrey3UniformLocation, 6);
        }
    }
}

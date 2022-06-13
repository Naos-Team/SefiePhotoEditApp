package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.imgproc;

import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.AbsFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLSimpleProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class CustomizedGaussianBlurFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;
    private boolean scale = false;
    private float texelHeightOffset = 0.0f;
    private float texelWidthOffset = 0.0f;

    public CustomizedGaussianBlurFilter(int i, double d) {
        this.glSimpleProgram = new GLSimpleProgram(generateCustomizedGaussianBlurVertexShader(i, d), generateCustomizedGaussianBlurFragmentShader(i, d));
    }

    public void init() {
        this.glSimpleProgram.create();
    }

    public void onPreDrawElements() {
        super.onPreDrawElements();
        this.glSimpleProgram.use();
        this.plane.uploadTexCoordinateBuffer(this.glSimpleProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glSimpleProgram.getPositionHandle());
    }

    public void destroy() {
        this.glSimpleProgram.onDestroy();
    }

    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", this.texelWidthOffset / ((float) this.surfaceWidth));
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", this.texelHeightOffset / ((float) this.surfaceHeight));
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public CustomizedGaussianBlurFilter setTexelHeightOffset(float f) {
        this.texelHeightOffset = f;
        return this;
    }

    public CustomizedGaussianBlurFilter setTexelWidthOffset(float f) {
        this.texelWidthOffset = f;
        return this;
    }

    public void onFilterChanged(int i, int i2) {
        if (!this.scale) {
            super.onFilterChanged(i, i2);
        } else {
            super.onFilterChanged(i / 4, i2 / 4);
        }
    }

    public CustomizedGaussianBlurFilter setScale(boolean z) {
        this.scale = z;
        return this;
    }

    private static String generateCustomizedGaussianBlurVertexShader(int i, double d) {
        int i2;
        double d2;
        int i3 = i;
        double d3 = d;
        if (i3 < 1) {
            return "";
        }
        double[] dArr = new double[(i3 + 2)];
        double d4 = 0.0d;
        int i4 = 0;
        while (true) {
            i2 = i3 + 1;
            if (i4 >= i2) {
                break;
            }
            dArr[i4] = (1.0d / Math.sqrt(Math.pow(d3, 2.0d) * 6.283185307179586d)) * Math.exp((-Math.pow((double) i4, 2.0d)) / (Math.pow(d3, 2.0d) * 2.0d));
            if (i4 == 0) {
                d2 = dArr[i4];
            } else {
                d2 = dArr[i4] * 2.0d;
            }
            d4 += d2;
            i4++;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            dArr[i5] = dArr[i5] / d4;
        }
        int min = Math.min((i3 / 2) + (i3 % 2), 7);
        double[] dArr2 = new double[min];
        for (int i6 = 0; i6 < min; i6++) {
            int i7 = i6 * 2;
            int i8 = i7 + 1;
            double d5 = dArr[i8];
            int i9 = i7 + 2;
            double d6 = dArr[i9];
            double d7 = (double) i8;
            Double.isNaN(d7);
            double d8 = (double) i9;
            Double.isNaN(d8);
            dArr2[i6] = ((d5 * d7) + (d6 * d8)) / (d5 + d6);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvarying vec2 blurCoordinates[%d];\nvoid main(){\n\tgl_Position = aPosition;\n\tvec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n", new Object[]{Integer.valueOf((min * 2) + 1)}));
        sb.append("\tblurCoordinates[0] = aTextureCoord.xy;\n");
        for (int i10 = 0; i10 < min; i10++) {
            int i11 = i10 * 2;
            sb.append(String.format("\tblurCoordinates[%d] = aTextureCoord.xy + singleStepOffset * %f;\n\tblurCoordinates[%d] = aTextureCoord.xy - singleStepOffset * %f;\n", new Object[]{Integer.valueOf(i11 + 1), Double.valueOf(dArr2[i10]), Integer.valueOf(i11 + 2), Double.valueOf(dArr2[i10])}));
        }
        sb.append("}\n");
        return sb.toString();
    }

    private static String generateCustomizedGaussianBlurFragmentShader(int i, double d) {
        int i2;
        double d2;
        int i3 = i;
        double d3 = d;
        if (i3 < 1) {
            return "";
        }
        double[] dArr = new double[(i3 + 2)];
        double d4 = 0.0d;
        int i4 = 0;
        while (true) {
            i2 = i3 + 1;
            if (i4 >= i2) {
                break;
            }
            dArr[i4] = (1.0d / Math.sqrt(Math.pow(d3, 2.0d) * 6.283185307179586d)) * Math.exp((-Math.pow((double) i4, 2.0d)) / (Math.pow(d3, 2.0d) * 2.0d));
            if (i4 == 0) {
                d2 = dArr[i4];
            } else {
                d2 = dArr[i4] * 2.0d;
            }
            d4 += d2;
            i4++;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            dArr[i5] = dArr[i5] / d4;
        }
        int i6 = 2;
        int i7 = (i3 / 2) + (i3 % 2);
        int min = Math.min(i7, 7);
        StringBuilder sb = new StringBuilder();
        sb.append("uniform sampler2D sTexture;\nuniform highp float texelWidthOffset;\nuniform highp float texelHeightOffset;\n");
        sb.append(String.format("varying highp vec2 blurCoordinates[%d];\n", new Object[]{Integer.valueOf((min * 2) + 1)}));
        sb.append("void main(){\n");
        sb.append("\tlowp vec4 sum = vec4(0.0);\n");
        sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0]) * %f;\n", new Object[]{Double.valueOf(dArr[0])}));
        for (int i8 = 0; i8 < min; i8++) {
            int i9 = i8 * 2;
            int i10 = i9 + 1;
            int i11 = i9 + 2;
            double d5 = dArr[i10] + dArr[i11];
            sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n", new Object[]{Integer.valueOf(i10), Double.valueOf(d5), Integer.valueOf(i11), Double.valueOf(d5)}));
        }
        if (i7 > min) {
            sb.append("\thighp vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n");
            while (min < i7) {
                int i12 = min * 2;
                int i13 = i12 + 1;
                double d6 = dArr[i13];
                int i14 = i12 + i6;
                double d7 = dArr[i14];
                double d8 = d6 + d7;
                double d9 = (double) i13;
                Double.isNaN(d9);
                double d10 = d6 * d9;
                double d11 = (double) i14;
                Double.isNaN(d11);
                double d12 = (d10 + (d7 * d11)) / d8;
                sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0] + singleStepOffset * %f) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[0] - singleStepOffset * %f) * %f;\n", new Object[]{Double.valueOf(d12), Double.valueOf(d8), Double.valueOf(d12), Double.valueOf(d8)}));
                min++;
                i6 = 2;
            }
        }
        sb.append("\tgl_FragColor = sum;\n");
        sb.append("}\n");
        return sb.toString();
    }

    public static CustomizedGaussianBlurFilter initWithBlurRadiusInPixels(int i) {
        int i2;
        if (i >= 1) {
            double d = (double) i;
            int floor = (int) Math.floor(Math.sqrt(Math.pow(d, 2.0d) * -2.0d * Math.log(Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d) * 0.00390625d)));
            i2 = floor + (floor % 2);
        } else {
            i2 = 0;
        }
        return new CustomizedGaussianBlurFilter(i2, (double) i);
    }
}

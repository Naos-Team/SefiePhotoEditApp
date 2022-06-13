package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.imgproc;

import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.AbsFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLSimpleProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class CustomizedBoxBlurFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;
    private boolean scale = false;
    private float texelHeightOffset = 0.0f;
    private float texelWidthOffset = 0.0f;

    public CustomizedBoxBlurFilter(int i) {
        this.glSimpleProgram = new GLSimpleProgram(generateCustomizedBoxBlurVertexShader(i), generateCustomizedBoxBlurFragmentShader(i));
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

    public CustomizedBoxBlurFilter setTexelHeightOffset(float f) {
        this.texelHeightOffset = f;
        return this;
    }

    public CustomizedBoxBlurFilter setTexelWidthOffset(float f) {
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

    public CustomizedBoxBlurFilter setScale(boolean z) {
        this.scale = z;
        return this;
    }

    public static String generateCustomizedBoxBlurVertexShader(int i) {
        if (i < 1) {
            return "";
        }
        int min = Math.min((i / 2) + (i % 2), 7);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvarying vec2 blurCoordinates[%d];\nvoid main(){\n\tgl_Position = aPosition;\n\tvec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n", new Object[]{Integer.valueOf((min * 2) + 1)}));
        sb.append("\tblurCoordinates[0] = aTextureCoord.xy;\n");
        for (int i2 = 0; i2 < min; i2++) {
            int i3 = i2 * 2;
            double d = (double) i3;
            Double.isNaN(d);
            double d2 = d + 1.5d;
            sb.append(String.format("\tblurCoordinates[%d] = aTextureCoord.xy + singleStepOffset * %f;\n\tblurCoordinates[%d] = aTextureCoord.xy - singleStepOffset * %f;\n", new Object[]{Integer.valueOf(i3 + 1), Double.valueOf(d2), Integer.valueOf(i3 + 2), Double.valueOf(d2)}));
        }
        sb.append("}\n");
        return sb.toString();
    }

    public static String generateCustomizedBoxBlurFragmentShader(int i) {
        int i2 = i;
        if (i2 < 1) {
            return "";
        }
        int i3 = (i2 / 2) + (i2 % 2);
        int min = Math.min(i3, 7);
        StringBuilder sb = new StringBuilder();
        double d = (double) ((i2 * 2) + 1);
        Double.isNaN(d);
        double d2 = 1.0d / d;
        sb.append("uniform sampler2D sTexture;\nuniform highp float texelWidthOffset;\nuniform highp float texelHeightOffset;\n");
        sb.append(String.format("varying highp vec2 blurCoordinates[%d];\n", new Object[]{Integer.valueOf((min * 2) + 1)}));
        sb.append("void main(){\n");
        sb.append("\tlowp vec4 sum = vec4(0.0);\n");
        sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0]) * %f;\n", new Object[]{Double.valueOf(d2)}));
        for (int i4 = 0; i4 < min; i4++) {
            int i5 = i4 * 2;
            double d3 = 2.0d * d2;
            sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n", new Object[]{Integer.valueOf(i5 + 1), Double.valueOf(d3), Integer.valueOf(i5 + 2), Double.valueOf(d3)}));
        }
        if (i3 > min) {
            sb.append("\thighp vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n");
            while (min < i3) {
                double d4 = (double) (min * 2);
                Double.isNaN(d4);
                double d5 = d4 + 1.5d;
                double d6 = d2 * 2.0d;
                sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0] + singleStepOffset * %f) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[0] - singleStepOffset * %f) * %f;\n", new Object[]{Double.valueOf(d5), Double.valueOf(d6), Double.valueOf(d5), Double.valueOf(d6)}));
                min++;
            }
        }
        sb.append("\tgl_FragColor = sum;\n");
        sb.append("}\n");
        return sb.toString();
    }
}

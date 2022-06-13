package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.content.Context;
import android.opengl.GLES20;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.program.GLSimpleProgram;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.TextureUtils;

public class SimpleFragmentShaderFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;

    public SimpleFragmentShaderFilter(Context context, String str) {
        this.glSimpleProgram = new GLSimpleProgram(context, "filter/vsh/base/simple.glsl", str);
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
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}

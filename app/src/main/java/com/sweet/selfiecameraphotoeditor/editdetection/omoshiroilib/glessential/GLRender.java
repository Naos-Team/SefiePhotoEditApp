package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.camera.CameraEngine;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec.MediaAudioEncoder;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec.MediaEncoder;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec.MediaMuxerWrapper;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec.MediaVideoEncoder;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.GlobalConfig;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.encoder.MediaCodecUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.encoder.gles.EGLFilterDispatcher;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.encoder.gles.GLTextureSaver;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.AbsFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.FBO;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.FilterGroup;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.OESFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.OrthoFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterFactory;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterType;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRender implements GLSurfaceView.Renderer {
    public static final boolean DEBUG = true;
    private static final String TAG = "GLRender";

    public CameraEngine cameraEngine;

    public Context context;
    private FilterType currentFilterType = FilterType.NONE;
    private FilterGroup customizedFilters;
    private FBO fbo;
    FileUtils.FileSavedCallback fileSavedCallback;
    private FilterGroup filterGroup;

    public boolean isCameraFacingFront;

    public GLTextureSaver lastProcessFilter;
    private final MediaEncoder.MediaEncoderListener mMediaEncoderListener = new MediaEncoder.MediaEncoderListener() {
        public void onPrepared(MediaEncoder mediaEncoder) {
            Log.v(GLRender.TAG, "onPrepared:encoder=" + mediaEncoder);
            if (mediaEncoder instanceof MediaVideoEncoder) {
                GLRender.this.setVideoEncoder((MediaVideoEncoder) mediaEncoder);
            }
        }

        public void onStopped(MediaEncoder mediaEncoder) {
            Log.v(GLRender.TAG, "onStopped:encoder=" + mediaEncoder);
            if (mediaEncoder instanceof MediaVideoEncoder) {
                GLRender.this.setVideoEncoder((MediaVideoEncoder) null);
            }
        }
    };
    private MediaMuxerWrapper mMuxer;
    private final LinkedList<Runnable> mPostDrawTaskList;

    public MediaVideoEncoder mVideoEncoder;
    private OESFilter oesFilter;
    private OrthoFilter orthoFilter;
    private String outputPath;
    private FilterGroup postProcessFilters;
    private PassThroughFilter screenDrawer;
    private int surfaceHeight;
    private int surfaceWidth;

    public void onResume() {
    }

    public GLRender(Context context2, CameraEngine cameraEngine2) {
        this.context = context2;
        this.cameraEngine = cameraEngine2;
        this.filterGroup = new FilterGroup();
        this.postProcessFilters = new FilterGroup();
        this.oesFilter = new OESFilter(context2);
        this.filterGroup.addFilter(this.oesFilter);
        this.orthoFilter = new OrthoFilter(context2);
        this.filterGroup.addFilter(this.orthoFilter);
        this.customizedFilters = new FilterGroup();
        this.customizedFilters.addFilter(FilterFactory.createFilter(this.currentFilterType, context2));
        this.postProcessFilters.addFilter(new PassThroughFilter(context2));
        this.lastProcessFilter = new GLTextureSaver(context2);
        this.postProcessFilters.addFilter(this.lastProcessFilter);
        this.filterGroup.addFilter(this.customizedFilters);
        this.filterGroup.addFilter(this.postProcessFilters);
        this.screenDrawer = new PassThroughFilter(context2);
        this.isCameraFacingFront = true;
        this.mPostDrawTaskList = new LinkedList<>();
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.filterGroup.init();
        this.screenDrawer.init();
    }

    public void onDrawFrame(GL10 gl10) {
        this.cameraEngine.doTextureUpdate(this.oesFilter.getSTMatrix());
        this.filterGroup.onDrawFrame(this.oesFilter.getGlOESTexture().getTextureId());
        if (this.mVideoEncoder != null) {
            Log.d(TAG, "onDrawFrame: " + this.mVideoEncoder.toString());
            this.mVideoEncoder.frameAvailableSoon();
        }
        runPostDrawTasks();
    }


    @TargetApi(18)
    public EGLContext getSharedContext() {
        return EGL14.eglGetCurrentContext();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        Log.d(TAG, "onSurfaceChanged: " + i + " " + i2);
        this.surfaceWidth = i;
        this.surfaceHeight = i2;
        GLES20.glViewport(0, 0, i, i2);
        this.filterGroup.onFilterChanged(i, i2);
        this.fbo = FBO.newInstance().create(this.surfaceWidth, this.surfaceHeight);
        if (this.cameraEngine.isCameraOpened()) {
            this.cameraEngine.stopPreview();
            this.cameraEngine.releaseCamera();
        }
        this.cameraEngine.setTexture(this.oesFilter.getGlOESTexture().getTextureId());
        this.cameraEngine.openCamera(this.isCameraFacingFront);
        this.cameraEngine.startPreview();
    }

    public void onPause() {
        if (this.cameraEngine.isCameraOpened()) {
            this.cameraEngine.stopPreview();
            this.cameraEngine.releaseCamera();
        }
    }

    public void onDestroy() {
        if (this.cameraEngine.isCameraOpened()) {
            this.cameraEngine.releaseCamera();
        }
    }

    public void switchCamera() {
        this.filterGroup.addPreDrawTask(new Runnable() {
            public void run() {
                GLRender gLRender = GLRender.this;
                boolean unused = gLRender.isCameraFacingFront = !gLRender.isCameraFacingFront;
                GLRender.this.cameraEngine.switchCamera(GLRender.this.isCameraFacingFront);
            }
        });
    }

    public void switchLastFilterOfCustomizedFilters(FilterType filterType) {
        if (filterType != null) {
            this.currentFilterType = filterType;
            this.customizedFilters.switchLastFilter(FilterFactory.createFilter(filterType, this.context));
        }
    }

    public void switchFilterOfPostProcessAtPos(AbsFilter absFilter, int i) {
        if (absFilter != null) {
            this.postProcessFilters.switchFilterAt(absFilter, i);
        }
    }

    public FilterGroup getFilterGroup() {
        return this.filterGroup;
    }

    public OrthoFilter getOrthoFilter() {
        return this.orthoFilter;
    }

    public void startRecording() {
        try {
            File cacheDir = GlobalConfig.context.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            this.outputPath = cacheDir.getAbsolutePath() + FileUtils.getVidName();
            this.mMuxer = new MediaMuxerWrapper(this.outputPath);
            new MediaVideoEncoder(this.mMuxer, this.mMediaEncoderListener, MediaCodecUtils.TEST_HEIGHT, MediaCodecUtils.TEST_WIDTH);
            new MediaAudioEncoder(this.mMuxer, this.mMediaEncoderListener);
            this.mMuxer.prepare();
            this.mMuxer.startRecording();
        } catch (IOException e) {
            Log.e(TAG, "startCapture:", e);
        }
    }

    public void stopRecording() {
        MediaMuxerWrapper mediaMuxerWrapper = this.mMuxer;
        if (mediaMuxerWrapper != null) {
            mediaMuxerWrapper.stopRecording();
            this.mMuxer = null;
            this.mVideoEncoder = null;
            FileUtils.FileSavedCallback fileSavedCallback2 = this.fileSavedCallback;
            if (fileSavedCallback2 != null) {
                fileSavedCallback2.onFileSaved(this.outputPath);
            }
        }
    }

    public void setVideoEncoder(final MediaVideoEncoder mediaVideoEncoder) {
        this.filterGroup.addPreDrawTask(new Runnable() {
            public void run() {
                MediaVideoEncoder mediaVideoEncoder = null;
                if (mediaVideoEncoder != null) {
                    mediaVideoEncoder.getRenderHandler().setEglDrawer(new EGLFilterDispatcher(GLRender.this.context));
                    mediaVideoEncoder.setEglContext(GLRender.this.getSharedContext(), GLRender.this.lastProcessFilter.getSavedTextureId());
                    MediaVideoEncoder unused = GLRender.this.mVideoEncoder = mediaVideoEncoder;
                }
            }
        });
    }

    public void setFileSavedCallback(FileUtils.FileSavedCallback fileSavedCallback2) {
        this.fileSavedCallback = fileSavedCallback2;
    }

    public void runPostDrawTasks() {
        while (!this.mPostDrawTaskList.isEmpty()) {
            this.mPostDrawTaskList.removeFirst().run();
        }
    }

    public void addPostDrawTask(Runnable runnable) {
        synchronized (this.mPostDrawTaskList) {
            this.mPostDrawTaskList.addLast(runnable);
        }
    }
}

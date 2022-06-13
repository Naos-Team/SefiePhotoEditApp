package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;

@TargetApi(18)
public class MediaVideoEncoder extends MediaEncoder {
     static final float BPP = 0.25f;
     static final boolean DEBUG = true;
     static final int FRAME_RATE = 25;
     static final String MIME_TYPE = "video/avc";
     static final String TAG = "MediaVideoEncoder";
    protected static int[] recognizedFormats = {2130708361};
      int mHeight;
     int mWidth;

     RenderHandler mRenderHandler = RenderHandler.createHandler(TAG, mWidth, mHeight);
     Surface mSurface;


    public MediaVideoEncoder(MediaMuxerWrapper mediaMuxerWrapper, MediaEncoderListener mediaEncoderListener, int i, int i2) {
        super(mediaMuxerWrapper, mediaEncoderListener);
        Log.i(TAG, "MediaVideoEncoder: ");
        this.mWidth = i;
        this.mHeight = i2;
    }

    public boolean frameAvailableSoon() {
        boolean frameAvailableSoon = super.frameAvailableSoon();
        if (frameAvailableSoon) {
            this.mRenderHandler.draw();
        }
        Log.d(TAG, "frameAvailableSoon: " + frameAvailableSoon);
        return frameAvailableSoon;
    }


    public void prepare() throws IOException {
        Log.i(TAG, "prepare: ");
        this.mTrackIndex = -1;
        this.mIsEOS = false;
        this.mMuxerStarted = false;
        MediaCodecInfo selectVideoCodec = selectVideoCodec("video/avc");
        if (selectVideoCodec == null) {
            Log.e(TAG, "Unable to find an appropriate codec for video/avc");
            return;
        }
        Log.i(TAG, "selected codec: " + selectVideoCodec.getName());
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", this.mWidth, this.mHeight);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("bitrate", calcBitRate());
        createVideoFormat.setInteger("frame-rate", 25);
        createVideoFormat.setInteger("i-frame-interval", 10);
        Log.i(TAG, "format: " + createVideoFormat);
        this.mMediaCodec = MediaCodec.createEncoderByType("video/avc");
        this.mMediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        this.mSurface = this.mMediaCodec.createInputSurface();
        this.mMediaCodec.start();
        Log.i(TAG, "prepare finishing");
        if (this.mListener != null) {
            try {
                this.mListener.onPrepared(this);
            } catch (Exception e) {
                Log.e(TAG, "prepare:", e);
            }
        }
    }

    public void setEglContext(EGLContext eGLContext, int i) {
        this.mRenderHandler.setEglContext(eGLContext, i, this.mSurface, true);
    }


    public void release() {
        Log.i(TAG, "release:");
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
            this.mSurface = null;
        }
        RenderHandler renderHandler = this.mRenderHandler;
        if (renderHandler != null) {
            renderHandler.release();
            this.mRenderHandler = null;
        }
        super.release();
    }

     int calcBitRate() {
        int i = (int) (((float) this.mWidth) * 6.25f * ((float) this.mHeight));
        Log.i(TAG, String.format("bitrate=%5.2f[Mbps]", new Object[]{Float.valueOf((((float) i) / 1024.0f) / 1024.0f)}));
        return i;
    }

    protected static final MediaCodecInfo selectVideoCodec(String str) {
        Log.v(TAG, "selectVideoCodec:");
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (int i2 = 0; i2 < supportedTypes.length; i2++) {
                    if (supportedTypes[i2].equalsIgnoreCase(str)) {
                        Log.i(TAG, "codec:" + codecInfoAt.getName() + ",MIME=" + supportedTypes[i2]);
                        if (selectColorFormat(codecInfoAt, str) > 0) {
                            return codecInfoAt;
                        }
                    }
                }
                continue;
            }
        }
        return null;
    }

    /* JADX INFO: finally extract failed */
    protected static final int selectColorFormat(MediaCodecInfo mediaCodecInfo, String str) {
        Log.i(TAG, "selectColorFormat: ");
        try {
            Thread.currentThread().setPriority(10);
            MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
            Thread.currentThread().setPriority(5);
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= capabilitiesForType.colorFormats.length) {
                    break;
                }
                int i3 = capabilitiesForType.colorFormats[i2];
                if (isRecognizedViewoFormat(i3)) {
                    i = i3;
                    break;
                }
                i2++;
            }
            if (i == 0) {
                Log.e(TAG, "couldn't find a good color format for " + mediaCodecInfo.getName() + " / " + str);
            }
            return i;
        } catch (Throwable th) {
            Thread.currentThread().setPriority(5);
            throw th;
        }
    }

     static final boolean isRecognizedViewoFormat(int i) {
        Log.i(TAG, "isRecognizedViewoFormat:colorFormat=" + i);
        int[] iArr = recognizedFormats;
        int length = iArr != null ? iArr.length : 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (recognizedFormats[i2] == i) {
                return true;
            }
        }
        return false;
    }


    public void signalEndOfInputStream() {
        Log.d(TAG, "sending EOS to encoder");
        this.mMediaCodec.signalEndOfInputStream();
        this.mIsEOS = true;
    }

    public RenderHandler getRenderHandler() {
        return this.mRenderHandler;
    }
}

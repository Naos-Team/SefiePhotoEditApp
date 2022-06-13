package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Locale;

@TargetApi(18)
public class MediaMuxerWrapper {
     static final boolean DEBUG = true;
     static final String DIR_NAME = "AVRecSample";
     static final String TAG = "MediaMuxerWrapper";
     static final SimpleDateFormat mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
     MediaEncoder mAudioEncoder;
     int mEncoderCount = 0;
     boolean mIsStarted = false;
     final MediaMuxer mMediaMuxer = new MediaMuxer(this.mOutputPath, 0);
     String mOutputPath;
     int mStatredCount = 0;
     MediaEncoder mVideoEncoder;

    public MediaMuxerWrapper(String str) throws IOException {
        this.mOutputPath = str;
    }

    public String getOutputPath() {
        return this.mOutputPath;
    }

    public void prepare() throws IOException {
        MediaEncoder mediaEncoder = this.mVideoEncoder;
        if (mediaEncoder != null) {
            mediaEncoder.prepare();
        }
        MediaEncoder mediaEncoder2 = this.mAudioEncoder;
        if (mediaEncoder2 != null) {
            mediaEncoder2.prepare();
        }
    }

    public void startRecording() {
        MediaEncoder mediaEncoder = this.mVideoEncoder;
        if (mediaEncoder != null) {
            mediaEncoder.startRecording();
        }
        MediaEncoder mediaEncoder2 = this.mAudioEncoder;
        if (mediaEncoder2 != null) {
            mediaEncoder2.startRecording();
        }
    }

    public void stopRecording() {
        MediaEncoder mediaEncoder = this.mVideoEncoder;
        if (mediaEncoder != null) {
            mediaEncoder.stopRecording();
        }
        this.mVideoEncoder = null;
        MediaEncoder mediaEncoder2 = this.mAudioEncoder;
        if (mediaEncoder2 != null) {
            mediaEncoder2.stopRecording();
        }
        this.mAudioEncoder = null;
    }

    public synchronized boolean isStarted() {
        return this.mIsStarted;
    }


    public void addEncoder(MediaEncoder mediaEncoder) {
        if (mediaEncoder instanceof MediaVideoEncoder) {
            if (this.mVideoEncoder == null) {
                this.mVideoEncoder = mediaEncoder;
            } else {
                throw new IllegalArgumentException("Video encoder already added.");
            }
        } else if (!(mediaEncoder instanceof MediaAudioEncoder)) {
            throw new IllegalArgumentException("unsupported encoder");
        } else if (this.mAudioEncoder == null) {
            this.mAudioEncoder = mediaEncoder;
        } else {
            throw new IllegalArgumentException("Video encoder already added.");
        }
        int i = 1;
        int i2 = this.mVideoEncoder != null ? 1 : 0;
        if (this.mAudioEncoder == null) {
            i = 0;
        }
        this.mEncoderCount = i2 + i;
    }


    public synchronized boolean start() {
        Log.v(TAG, "start:");
        this.mStatredCount++;
        if (this.mEncoderCount > 0 && this.mStatredCount == this.mEncoderCount) {
            this.mMediaMuxer.start();
            this.mIsStarted = true;
            notifyAll();
            Log.v(TAG, "MediaMuxer started:");
        }
        return this.mIsStarted;
    }


    public synchronized void stop() {
        Log.v(TAG, "stop:mStatredCount=" + this.mStatredCount);
        this.mStatredCount = this.mStatredCount + -1;
        if (this.mEncoderCount > 0 && this.mStatredCount <= 0) {
            this.mMediaMuxer.stop();
            this.mMediaMuxer.release();
            this.mIsStarted = false;
            Log.v(TAG, "MediaMuxer stopped:");
        }
    }


    public synchronized int addTrack(MediaFormat mediaFormat) {
        int addTrack;
        if (!this.mIsStarted) {
            addTrack = this.mMediaMuxer.addTrack(mediaFormat);
            Log.i(TAG, "addTrack:trackNum=" + this.mEncoderCount + ",trackIx=" + addTrack + ",format=" + mediaFormat);
        } else {
            throw new IllegalStateException("muxer already started");
        }
        return addTrack;
    }


    public synchronized void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.mStatredCount > 0) {
            this.mMediaMuxer.writeSampleData(i, byteBuffer, bufferInfo);
        }
    }
}

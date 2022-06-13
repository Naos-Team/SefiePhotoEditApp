package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

@TargetApi(18)
public abstract class MediaEncoder implements Runnable {
     static final boolean DEBUG = true;
    protected static final int MSG_FRAME_AVAILABLE = 1;
    protected static final int MSG_STOP_RECORDING = 9;
     static final String TAG = "MediaEncoder";
    protected static final int TIMEOUT_USEC = 10000;
     MediaCodec.BufferInfo mBufferInfo;
    protected volatile boolean mIsCapturing;
    protected boolean mIsEOS;
    protected final MediaEncoderListener mListener;
    protected MediaCodec mMediaCodec;
    protected boolean mMuxerStarted;
     int mRequestDrain;
    protected volatile boolean mRequestStop;
    protected final Object mSync = new Object();
    protected int mTrackIndex;
    protected final WeakReference<MediaMuxerWrapper> mWeakMuxer;
     long prevOutputPTSUs = 0;

    public interface MediaEncoderListener {
        void onPrepared(MediaEncoder mediaEncoder);

        void onStopped(MediaEncoder mediaEncoder);
    }


    public abstract void prepare() throws IOException;

    public MediaEncoder(MediaMuxerWrapper mediaMuxerWrapper, MediaEncoderListener mediaEncoderListener) {
        if (mediaEncoderListener == null) {
            throw new NullPointerException("MediaEncoderListener is null");
        } else if (mediaMuxerWrapper != null) {
            this.mWeakMuxer = new WeakReference<>(mediaMuxerWrapper);
            mediaMuxerWrapper.addEncoder(this);
            this.mListener = mediaEncoderListener;
            synchronized (this.mSync) {
                this.mBufferInfo = new MediaCodec.BufferInfo();
                new Thread(this, getClass().getSimpleName()).start();
                try {
                    this.mSync.wait();
                } catch (InterruptedException unused) {
                }
            }
        } else {
            throw new NullPointerException("MediaMuxerWrapper is null");
        }
    }


    public String getOutputPath() {
        MediaMuxerWrapper mediaMuxerWrapper = (MediaMuxerWrapper) this.mWeakMuxer.get();
        if (mediaMuxerWrapper != null) {
            return mediaMuxerWrapper.getOutputPath();
        }
        return null;
    }

    public boolean frameAvailableSoon() {
        synchronized (this.mSync) {
            if (this.mIsCapturing) {
                if (!this.mRequestStop) {
                    this.mRequestDrain++;
                    this.mSync.notifyAll();
                    return true;
                }
            }
            return false;
        }
    }

    public void run() {
        boolean z;
        boolean z2;
        synchronized (this.mSync) {
            this.mRequestStop = false;
            this.mRequestDrain = 0;
            this.mSync.notify();
        }
        while (true) {
            synchronized (this.mSync) {
                z = this.mRequestStop;
                z2 = this.mRequestDrain > 0;
                if (z2) {
                    this.mRequestDrain--;
                }
            }
            if (z) {
                drain();
                signalEndOfInputStream();
                drain();
                release();
                break;
            } else if (z2) {
                drain();
            } else {
                synchronized (this.mSync) {
                    try {
                        this.mSync.wait();
                        try {
                        } finally {
                            while (true) {
                            }
                        }
                    } catch (InterruptedException unused) {
                        Log.d(TAG, "Encoder thread exiting");
                        synchronized (this.mSync) {
                            this.mRequestStop = true;
                            this.mIsCapturing = false;
                        }
                        return;
                    }
                }
            }
        }
        while (true) {
        }
    }


    public void startRecording() {
        Log.v(TAG, "startRecording");
        synchronized (this.mSync) {
            this.mIsCapturing = true;
            this.mRequestStop = false;
            this.mSync.notifyAll();
        }
    }


    void stopRecording() {
        Log.v(TAG, "stopRecording");
        synchronized (mSync) {
            if (!mIsCapturing || mRequestStop) {
                return;
            }
            mRequestStop = true;    // for rejecting newer frame
            mSync.notifyAll();
            // We can not know when the encoding and writing finish.
            // so we return immediately after request to avoid delay of caller thread
        }
    }


    public void release() {
        Log.d(TAG, "release:");
        try {
            this.mListener.onStopped(this);
        } catch (Exception e) {
            Log.e(TAG, "failed onStopped", e);
        }
        this.mIsCapturing = false;
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            try {
                mediaCodec.stop();
                this.mMediaCodec.release();
                this.mMediaCodec = null;
            } catch (Exception e2) {
                Log.e(TAG, "failed releasing MediaCodec", e2);
            }
        }
        if (this.mMuxerStarted) {
            WeakReference<MediaMuxerWrapper> weakReference = this.mWeakMuxer;
            MediaMuxerWrapper mediaMuxerWrapper = weakReference != null ? (MediaMuxerWrapper) weakReference.get() : null;
            if (mediaMuxerWrapper != null) {
                try {
                    mediaMuxerWrapper.stop();
                } catch (Exception e3) {
                    Log.e(TAG, "failed stopping muxer", e3);
                }
            }
        }
        this.mBufferInfo = null;
    }


    public void signalEndOfInputStream() {
        Log.d(TAG, "sending EOS to encoder");
        encode((ByteBuffer) null, 0, getPTSUs());
    }


    public void encode(ByteBuffer byteBuffer, int i, long j) {
        Log.d(TAG, "encode: " + getClass().getSimpleName() + " " + this.mIsCapturing);
        if (this.mIsCapturing) {
            ByteBuffer[] inputBuffers = this.mMediaCodec.getInputBuffers();
            while (this.mIsCapturing) {
                int dequeueInputBuffer = this.mMediaCodec.dequeueInputBuffer(10000);
                Log.d(TAG, "encode: " + getClass().getSimpleName() + " " + dequeueInputBuffer);
                if (dequeueInputBuffer >= 0) {
                    ByteBuffer byteBuffer2 = inputBuffers[dequeueInputBuffer];
                    byteBuffer2.clear();
                    if (byteBuffer != null) {
                        byteBuffer2.put(byteBuffer);
                    }
                    if (i <= 0) {
                        this.mIsEOS = true;
                        Log.i(TAG, "send BUFFER_FLAG_END_OF_STREAM");
                        this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, 0, j, 4);
                        return;
                    }
                    this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, i, j, 0);
                    return;
                }
            }
        }
    }

    public void drain() {
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
            MediaMuxerWrapper mediaMuxerWrapper = this.mWeakMuxer.get();
            if (mediaMuxerWrapper == null) {
                Log.w(TAG, "muxer is unexpectedly null");
                return;
            }
            ByteBuffer[] byteBufferArr = outputBuffers;
            int i = 0;
            while (this.mIsCapturing) {
                int dequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(this.mBufferInfo, 10000);
                if (dequeueOutputBuffer == -1) {
                    if (!this.mIsEOS && (i = i + 1) > 5) {
                        return;
                    }
                } else if (dequeueOutputBuffer == -3) {
                    Log.v(TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
                    byteBufferArr = this.mMediaCodec.getOutputBuffers();
                } else if (dequeueOutputBuffer == -2) {
                    Log.v(TAG, "INFO_OUTPUT_FORMAT_CHANGED");
                    if (!this.mMuxerStarted) {
                        this.mTrackIndex = mediaMuxerWrapper.addTrack(this.mMediaCodec.getOutputFormat());
                        this.mMuxerStarted = true;
                        if (!mediaMuxerWrapper.start()) {
                            synchronized (mediaMuxerWrapper) {
                                while (!mediaMuxerWrapper.isStarted()) {
                                    try {
                                        mediaMuxerWrapper.wait(100);
                                    } catch (InterruptedException unused) {
                                        return;
                                    }
                                }
                            }
                        } else {
                            continue;
                        }
                    } else {
                        throw new RuntimeException("format changed twice");
                    }
                } else if (dequeueOutputBuffer < 0) {
                    Log.w(TAG, "drain:unexpected result from encoder#dequeueOutputBuffer: " + dequeueOutputBuffer);
                } else {
                    ByteBuffer byteBuffer = byteBufferArr[dequeueOutputBuffer];
                    if (byteBuffer != null) {
                        if ((this.mBufferInfo.flags & 2) != 0) {
                            Log.d(TAG, "drain:BUFFER_FLAG_CODEC_CONFIG");
                            this.mBufferInfo.size = 0;
                        }
                        if (this.mBufferInfo.size != 0) {
                            if (this.mMuxerStarted) {
                                this.mBufferInfo.presentationTimeUs = getPTSUs();
                                mediaMuxerWrapper.writeSampleData(this.mTrackIndex, byteBuffer, this.mBufferInfo);
                                this.prevOutputPTSUs = this.mBufferInfo.presentationTimeUs;
                                i = 0;
                            } else {
                                throw new RuntimeException("drain:muxer hasn't started");
                            }
                        }
                        this.mMediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                        if ((this.mBufferInfo.flags & 4) != 0) {
                            this.mIsCapturing = false;
                            return;
                        }
                    } else {
                        throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null");
                    }
                }
            }
        }
    }


    public long getPTSUs() {
        long nanoTime = System.nanoTime() / 1000;
        long j = this.prevOutputPTSUs;
        return nanoTime < j ? nanoTime + (j - nanoTime) : nanoTime;
    }
}

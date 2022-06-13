package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.codec;

import android.annotation.TargetApi;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(18)
public class MediaAudioEncoder extends MediaEncoder {

    public static final int[] AUDIO_SOURCES = {1, 0, 5, 7, 6};
     static final int BIT_RATE = 64000;
     static final boolean DEBUG = false;
    public static final int FRAMES_PER_BUFFER = 25;
     static final String MIME_TYPE = "audio/mp4a-latm";
    public static final int SAMPLES_PER_FRAME = 1024;
     static final int SAMPLE_RATE = 44100;
     static final String TAG = "MediaAudioEncoder";
     AudioThread mAudioThread = null;

    public MediaAudioEncoder(MediaMuxerWrapper mediaMuxerWrapper, MediaEncoderListener mediaEncoderListener) {
        super(mediaMuxerWrapper, mediaEncoderListener);
    }


    public void prepare() throws IOException {
        this.mTrackIndex = -1;
        this.mIsEOS = false;
        this.mMuxerStarted = false;
        if (selectAudioCodec("audio/mp4a-latm") == null) {
            Log.e(TAG, "Unable to find an appropriate codec for audio/mp4a-latm");
            return;
        }
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", 44100, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("channel-mask", 16);
        createAudioFormat.setInteger("bitrate", 64000);
        createAudioFormat.setInteger("channel-count", 1);
        this.mMediaCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
        this.mMediaCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        this.mMediaCodec.start();
        if (this.mListener != null) {
            try {
                this.mListener.onPrepared(this);
            } catch (Exception e) {
                Log.e(TAG, "prepare:", e);
            }
        }
    }


    public void startRecording() {
        super.startRecording();
        if (this.mAudioThread == null) {
            this.mAudioThread = new AudioThread();
            this.mAudioThread.start();
        }
    }


    public void release() {
        this.mAudioThread = null;
        super.release();
    }

     class AudioThread extends Thread {
         AudioThread() {
        }

         @Override
        public void run() {
            android.os.Process.setThreadPriority(-19);
            try {
                int minBufferSize = AudioRecord.getMinBufferSize(44100, 16, 2);
                int i = 25600;
                if (25600 < minBufferSize) {
                    i = ((minBufferSize / 1024) + 1) * 1024 * 2;
                }
                AudioRecord audioRecord = null;
                for (int audioRecord2 : MediaAudioEncoder.AUDIO_SOURCES) {
                    try {
                        AudioRecord audioRecord3 = new AudioRecord(audioRecord2, 44100, 16, 2, i);
                        if (audioRecord3.getState() != 1) {
                            audioRecord3 = null;
                        }
                        audioRecord = audioRecord3;
                    } catch (Exception unused) {
                        audioRecord = null;
                    }
                    if (audioRecord != null) {
                        break;
                    }
                }
                if (audioRecord != null) {
                    try {
                        if (MediaAudioEncoder.this.mIsCapturing) {
                            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1024);
                            audioRecord.startRecording();
                            while (MediaAudioEncoder.this.mIsCapturing && !MediaAudioEncoder.this.mRequestStop && !MediaAudioEncoder.this.mIsEOS) {
                                allocateDirect.clear();
                                int read = audioRecord.read(allocateDirect, 1024);
                                if (read > 0) {
                                    allocateDirect.position(read);
                                    allocateDirect.flip();
                                    MediaAudioEncoder.this.encode(allocateDirect, read, MediaAudioEncoder.this.getPTSUs());
                                    MediaAudioEncoder.this.frameAvailableSoon();
                                }
                            }
                            MediaAudioEncoder.this.frameAvailableSoon();
                            audioRecord.stop();
                        }
                        audioRecord.release();
                    } catch (Throwable th) {
                        audioRecord.release();
                        throw th;
                    }
                } else {
                    Log.e(MediaAudioEncoder.TAG, "failed to initialize AudioRecord");
                }
            } catch (Exception e) {
                Log.e(MediaAudioEncoder.TAG, "AudioThread#run", e);
            }
        }
    }

     static final MediaCodecInfo selectAudioCodec(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (String equalsIgnoreCase : supportedTypes) {
                    if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }
}

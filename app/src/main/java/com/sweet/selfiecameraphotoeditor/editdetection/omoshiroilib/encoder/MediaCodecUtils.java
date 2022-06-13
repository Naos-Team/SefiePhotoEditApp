package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.encoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

public class MediaCodecUtils {
    public static final int CODEC_ERROR = -1;
    public static final int CODEC_REQ_API_NOT_SATISFIED = -2;
    public static final int CODEC_SUPPORTED = 1;
    public static final int FRAMES_PER_BUFFER = 25;
    public static final String MIME_TYPE_AUDIO = "audio/mp4a-latm";
    public static final String MIME_TYPE_VIDEO = "video/avc";
    public static final int MIN_API_LEVEL_AUDIO = 16;
    public static final int MIN_API_LEVEL_VIDEO = 18;
    public static final int SAMPLES_PER_FRAME = 1024;
    private static final String TAG = "MediaCodecUtils";
    public static final int TEST_AUDIO_BIT_RATE = 64000;
    public static final int TEST_FRAME_RATE = 30;
    public static final int TEST_HEIGHT = 720;
    public static final int TEST_IFRAME_INTERVAL = 5;
    public static final int TEST_SAMPLE_RATE = 44100;
    public static final int TEST_VIDEO_BIT_RATE = 1000000;
    public static final int TEST_WIDTH = 1280;

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    @TargetApi(18)
    public static int checkMediaCodecVideoEncoderSupport() {
        if (getApiLevel() < 18) {
            Log.d(TAG, "checkMediaCodecVideoEncoderSupport: Min API is 18");
            return -2;
        }
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(MIME_TYPE_VIDEO, TEST_WIDTH, TEST_HEIGHT);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("bitrate", TEST_VIDEO_BIT_RATE);
        createVideoFormat.setInteger("frame-rate", 30);
        createVideoFormat.setInteger("i-frame-interval", 5);
        try {
            MediaCodec createEncoderByType = MediaCodec.createEncoderByType(MIME_TYPE_VIDEO);
            createEncoderByType.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
            createEncoderByType.createInputSurface();
            createEncoderByType.start();
            createEncoderByType.stop();
            createEncoderByType.release();
            return 1;
        } catch (Exception e) {
            Log.e(TAG, "Failed on creation of codec #", e);
            return -1;
        }
    }

    @TargetApi(16)
    public static int checkMediaCodecAudioEncoderSupport() {
        if (getApiLevel() < 16) {
            Log.d(TAG, "checkMediaCodecAudioEncoderSupport: Min API is 16");
            return -2;
        }
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(MIME_TYPE_AUDIO, TEST_SAMPLE_RATE, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("channel-mask", 16);
        createAudioFormat.setInteger("bitrate", TEST_AUDIO_BIT_RATE);
        createAudioFormat.setInteger("channel-count", 1);
        try {
            MediaCodec createEncoderByType = MediaCodec.createEncoderByType(MIME_TYPE_AUDIO);
            createEncoderByType.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
            createEncoderByType.start();
            createEncoderByType.stop();
            createEncoderByType.release();
            return 1;
        } catch (Exception e) {
            Log.e(TAG, "Failed on creation of codec #", e);
            return -1;
        }
    }
}

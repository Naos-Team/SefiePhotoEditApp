package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.core.view.ViewCompat;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.camera.IWorkerCallback;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.GlobalConfig;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.PixelBuffer;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterType;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.GLImageRender;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    public static Bitmap getScreenShot(int i, int i2) {
        int i3 = i * i2;
        IntBuffer allocate = IntBuffer.allocate(i3);
        GLES20.glReadPixels(0, 0, i, i2, 6408, 5121, allocate);
        int[] iArr = new int[i3];
        int[] array = allocate.array();
        for (int i4 = 0; i4 < i2; i4++) {
            for (int i5 = 0; i5 < i; i5++) {
                iArr[(((i2 - i4) - 1) * i) + i5] = array[(i4 * i) + i5];
            }
        }
        return Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
    }

    public static void sendImage(int i, int i2, Context context, FileUtils.FileSavedCallback fileSavedCallback) {
        IntBuffer allocate = IntBuffer.allocate(i * i2);
        long nanoTime = System.nanoTime();
        GLES20.glReadPixels(0, 0, i, i2, 6408, 5121, allocate);
        long nanoTime2 = System.nanoTime();
        Log.d(TAG, "glReadPixels time: " + ((nanoTime2 - nanoTime) / 1000000) + " ms");
        new SaveBitmapTask(allocate, i, i2, context, fileSavedCallback).execute(new Void[0]);
    }

    private static class SaveBitmapTask extends AsyncTask<Void, Integer, Boolean> {
        Context context;
        String filePath;
        FileUtils.FileSavedCallback fileSavedCallback;
        int height;
        IntBuffer rgbaBuf;
        long start;
        int width;

        public SaveBitmapTask(IntBuffer intBuffer, int i, int i2, Context context2, FileUtils.FileSavedCallback fileSavedCallback2) {
            this.rgbaBuf = intBuffer;
            this.width = i;
            this.height = i2;
            this.context = context2;
            File cacheDir = GlobalConfig.context.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            this.filePath = cacheDir.getAbsolutePath() + FileUtils.getPicName();
            this.fileSavedCallback = fileSavedCallback2;
        }


        @Override
        public void onPreExecute() {
            this.start = System.nanoTime();
            super.onPreExecute();
        }


        public Boolean doInBackground(Void... voidArr) {
            try {
                BitmapUtils.saveIntBufferAsBitmap(this.rgbaBuf, this.filePath, this.width, this.height);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean bool) {
            Log.d(BitmapUtils.TAG, "saveBitmap time: " + ((System.nanoTime() - this.start) / 1000000) + " ms");
            super.onPostExecute(bool);
            this.fileSavedCallback.onFileSaved(this.filePath);
        }
    }


    public static void saveIntBufferAsBitmap(IntBuffer intBuffer, String str, int i, int i2) throws Throwable {
        Throwable th;
        IOException e;
        mkDirs(str);
        int[] iArr = new int[(i * i2)];
        Log.d(TAG, "Creating " + str);
        BufferedOutputStream bufferedOutputStream = null;
        try {
            int[] array = intBuffer.array();
            for (int i3 = 0; i3 < i2; i3++) {
                for (int i4 = 0; i4 < i; i4++) {
                    iArr[(((i2 - i3) - 1) * i) + i4] = array[(i3 * i) + i4];
                }
            }
            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(str));
            try {
                Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                createBitmap.copyPixelsFromBuffer(IntBuffer.wrap(iArr));
                createBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bufferedOutputStream2);
                createBitmap.recycle();
                try {
                    bufferedOutputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (Throwable th3) {
                bufferedOutputStream = bufferedOutputStream2;
                th = th3;
                if (bufferedOutputStream != null) {
                    Log.d("","");
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            e.printStackTrace();
            if (bufferedOutputStream == null) {
                bufferedOutputStream.close();
            }
        }
    }

    public static void saveBitmap(Bitmap bitmap, String str, IWorkerCallback iWorkerCallback) {
        Throwable th;
        IOException e;
        BufferedOutputStream bufferedOutputStream;
        mkDirs(str);
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bufferedOutputStream);
            try {
                bufferedOutputStream.close();
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e5) {
            e = e5;
            bufferedOutputStream = null;
            e.printStackTrace();
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e6) {
                    e = e6;
                }
            }
            if (iWorkerCallback == null) {
            }
        } catch (Throwable th3) {
            th = th3;
            if (bufferedOutputStream2 != null) {
            }
            try {
                throw th;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        if (iWorkerCallback == null) {
            iWorkerCallback.onPostExecute(null);
            return;
        }
        return;


    }

    public static void saveByteArray(final byte[] bArr, final String str, final IWorkerCallback iWorkerCallback, final Handler handler) {
        mkDirs(str);
        FakeThreadUtils.postTask(new Runnable() {
            public void run() {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(str);
                    fileOutputStream.write(bArr);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    handler.post(new Runnable() {
                        public void run() {
                            if (iWorkerCallback != null) {
                                iWorkerCallback.onPostExecute((Exception) null);
                            }
                        }
                    });
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        public void run() {
                            if (iWorkerCallback != null) {
                                iWorkerCallback.onPostExecute(e);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void saveBitmapWithFilterApplied(Context context, FilterType filterType, Bitmap bitmap, String str, IWorkerCallback iWorkerCallback) {
        final Context context2 = context;
        final Bitmap bitmap2 = bitmap;
        final FilterType filterType2 = filterType;
        final String str2 = str;
        final IWorkerCallback iWorkerCallback2 = iWorkerCallback;
        FakeThreadUtils.postTask(new Runnable() {
            public void run() {
                Logger.updateCurrentTime();
                GLImageRender gLImageRender = new GLImageRender(context2, bitmap2, filterType2);
                PixelBuffer pixelBuffer = new PixelBuffer(bitmap2.getWidth(), bitmap2.getHeight());
                Logger.logPassedTime("new PixelBuffer");
                pixelBuffer.setRenderer(gLImageRender);
                Bitmap bitmap = pixelBuffer.getBitmap();
                bitmap2.recycle();
                Logger.logPassedTime("getBitmap");
                pixelBuffer.destroy();
                BitmapUtils.saveBitmap(bitmap, str2, iWorkerCallback2);
                bitmap.recycle();
                System.gc();
            }
        });
    }

    public static Bitmap loadBitmapFromFile(String str) {
        new BitmapFactory.Options().inScaled = false;
        return BitmapFactory.decodeFile(str);
    }

    public static Bitmap loadBitmapFromAssets(Context context, String str) {
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open(str);
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = null;
        }
        if (inputStream == null) {
            return null;
        }
        new BitmapFactory.Options().inScaled = false;
        return BitmapFactory.decodeStream(inputStream);
    }

    public static Bitmap loadBitmapFromRaw(Context context, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        return BitmapFactory.decodeResource(context.getResources(), i, options);
    }

    public static void mkDirs(String str) {
        File file = new File(str);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }



    public static Bitmap loadARGBBitmapFromRGBAByteArray(byte[] bArr, int i, int i2, boolean z) {
        int i3 = i * i2;
        if (bArr.length == i3 * 4) {
            int[] iArr = new int[i3];
            int i4 = 0;
            int i5 = 0;
            while (i4 < i3) {
                iArr[i4] = 0;
                iArr[i4] = iArr[i4] | ((bArr[i5 + 0] & 255) << 16);
                iArr[i4] = iArr[i4] | ((bArr[i5 + 1] & 255) << 8);
                iArr[i4] = iArr[i4] | ((bArr[i5 + 2] & 255) << 0);
                iArr[i4] = iArr[i4] | (z ? ViewCompat.MEASURED_STATE_MASK : (bArr[i5 + 3] & 255) << 24);
                i4++;
                i5 += 4;
            }
            return Bitmap.createBitmap(iArr, i, i2, Bitmap.Config.ARGB_8888);
        }
        throw new RuntimeException("Illegal argument");
    }
}

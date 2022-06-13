package com.sweet.selfiecameraphotoeditor.stickerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.util.TypedValue;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class Eva {
    public static Bitmap merge(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap2, new Matrix(), (Paint) null);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap getBitmapFromAsset(String str, Context context) {
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(str);
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = null;
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    public static long calculateLength(CharSequence charSequence) {
        double d = 0.0d;
        for (int i = 0; i < charSequence.length(); i++) {
            char charAt = charSequence.charAt(i);
            d += (charAt <= 0 || charAt >= 127) ? 1.0d : 0.5d;
        }
        return Math.round(d);
    }

    public static void unzip(File file, String str) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    File file2 = new File(str, nextEntry.getName());
                    File parentFile = nextEntry.isDirectory() ? file2 : file2.getParentFile();
                    if (!parentFile.isDirectory()) {
                        if (!parentFile.mkdirs()) {
                            throw new FileNotFoundException("Failed to ensure directory: " + parentFile.getAbsolutePath());
                        }
                    }
                    if (!nextEntry.isDirectory()) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        while (true) {
                            int read = zipInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.close();
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (Throwable unused) {
            zipInputStream.close();
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
        double height = (double) bitmap.getHeight();
        double d = (double) i;
        double width = (double) bitmap.getWidth();
        Double.isNaN(d);
        Double.isNaN(width);
        Double.isNaN(d);
        Double.isNaN(width);
        Double.isNaN(height);
        Double.isNaN(height);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, (int) Math.floor(height * (d / width)), true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        createScaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static int convertPixelsToDp(int i, Context context) {
        return i / (context.getResources().getDisplayMetrics().densityDpi / 160);
    }

    public static int convertDpToPixel(int i, Context context) {
        return i * (context.getResources().getDisplayMetrics().densityDpi / 160);
    }

    public static int getPixelsFromDPs(int i, Context context) {
        return (int) TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
    }

    public static Uri getUriFromBitmap(Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/.share/");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, "share.png");
        try {
            if (file2.exists()) {
                file2.delete();
                try {
                    File file3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/.share/share.png");
                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.close();
                    return Uri.fromFile(file3);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                try {
                    File file4 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/.share/share.png");
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file4);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    fileOutputStream2.close();
                    return Uri.fromFile(file4);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    private static final String TAG = "FileUtils";

    public interface FileSavedCallback {
        void onFileSaved(String str);
    }

    public static void copyFileFromAssets(Context context, String str, String str2, String str3) {
        File file = new File(str, str2);
        Log.d(TAG, "copyFileFromAssets: " + file.getAbsolutePath());
        try {
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
                InputStream open = context.getResources().getAssets().open(str3);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = open.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        open.close();
                        fileOutputStream.close();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFileFromTo(String str, String str2, String str3) {
        File file = new File(str, str2);
        Log.d(TAG, "copyFileFromTo: " + file.getAbsolutePath());
        try {
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
                FileInputStream fileInputStream = new FileInputStream(new File(str3, str2));
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        fileOutputStream.close();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File makeTempFile(String str, String str2, String str3) {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File file = new File(str);
        file.mkdirs();
        return new File(file, str2 + format + str3);
    }

    public static void upZipFile(Context context, String str, String str2) {
        File file = new File(str2);
        Log.e("aaaaaaaa","222222222222222222");

        if (!file.isDirectory()) {
            file.mkdirs();
        }
        try {
            Log.e("aaaaaaaa","33333333333333333");

            ZipInputStream zipInputStream = new ZipInputStream(context.getResources().getAssets().open(str));
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    Log.d(TAG, "upZipFile: " + nextEntry.getName());
                    if (nextEntry.isDirectory()) {
                        File file2 = new File(str2, nextEntry.getName());
                        if (!file2.isDirectory()) {
                            file2.mkdirs();
                        }
                    } else {
                        File file3 = new File(str2 + "/" + nextEntry.getName());
                        if (!file3.exists()) {
                            FileOutputStream fileOutputStream = new FileOutputStream(file3);
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = zipInputStream.read(bArr);
                                if (read <= 0) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                            zipInputStream.closeEntry();
                            fileOutputStream.close();
                        }
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getFileOnSDCard(String str) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), str);
    }

    public static String getPicName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return "/Pic_" + simpleDateFormat.format(new Date()) + ".jpg";
    }

    public static String getVidName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return "/Vid_" + simpleDateFormat.format(new Date()) + ".mp4";
    }
}

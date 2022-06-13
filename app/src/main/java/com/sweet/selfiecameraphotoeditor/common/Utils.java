package com.sweet.selfiecameraphotoeditor.common;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;

public class Utils {
    public static final String HIDDEN_PREFIX = ".";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String SAVED_IMG_PATH = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/temp/");

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isLocalStorageDocument(Uri uri) {
        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
    }

    public static boolean isLocal(String str) {
        return str != null && !str.startsWith("http://") && !str.startsWith("https://");
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Throwable th;
        Cursor cursor;
        String str2 = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        str2 = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                        if (cursor != null) {
                            cursor.close();
                        }
                        return str2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (cursor != null) {
                cursor.close();
            }
            return str2;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
            }
            try {
                throw th;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return str2;
    }

    @TargetApi(19)
    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, (String) null, (String[]) null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            } else {
                return null;
            }
        } else if (isLocalStorageDocument(uri)) {
            return DocumentsContract.getDocumentId(uri);
        } else {
            String[] strArr = new String[0];
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if (!"primary".equalsIgnoreCase(split[0])) {
                    return null;
                }
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), (String) null, (String[]) null);
            } else {
                if (!isMediaDocument(uri)) {
                    return null;
                }
                String str = DocumentsContract.getDocumentId(uri).split(":")[0];
                if ("image".equals(str)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, uri2, "_id=?", new String[]{strArr[1]});
            }
        }
    }

    public static File getFile(Context context, Uri uri) {
        String path;
        if (uri == null || (path = getPath(context, uri)) == null || !isLocal(path)) {
            return null;
        }
        return new File(path);
    }
}

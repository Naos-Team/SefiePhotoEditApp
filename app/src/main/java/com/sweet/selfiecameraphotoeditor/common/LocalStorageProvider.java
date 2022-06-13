package com.sweet.selfiecameraphotoeditor.common;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@TargetApi(19)
public class LocalStorageProvider extends DocumentsProvider {
    public static final String AUTHORITY = "com.ianhanniballake.localstorage.documents";
    private static final String[] DEFAULT_DOCUMENT_PROJECTION = {"document_id", "_display_name", "flags", "mime_type", "_size", "last_modified"};
    private static final String[] DEFAULT_ROOT_PROJECTION = {"root_id", "flags", "title", "document_id", "icon", "available_bytes"};

    public boolean onCreate() {
        return true;
    }

    @TargetApi(19)
    public Cursor queryRoots(String[] strArr) throws FileNotFoundException {
        if (strArr == null) {
            strArr = DEFAULT_ROOT_PROJECTION;
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
        newRow.add("root_id", externalStorageDirectory.getAbsolutePath());
        newRow.add("document_id", externalStorageDirectory.getAbsolutePath());
        newRow.add("title", "Internal storage");
        newRow.add("flags", 3);
        newRow.add("available_bytes", Long.valueOf(externalStorageDirectory.getFreeSpace()));
        return matrixCursor;
    }

    @Override
    public String createDocument(String str, String str2, String str3) throws FileNotFoundException {
        File file = new File(str, str3);
        try {
            file.createNewFile();
            return file.getAbsolutePath();
        } catch (IOException unused) {
            String simpleName = LocalStorageProvider.class.getSimpleName();
            Log.e(simpleName, "Error creating new file " + file);
            return null;
        }
    }

    public Cursor queryChildDocuments(String str, String[] strArr, String str2) throws FileNotFoundException {
        if (strArr == null) {
            strArr = DEFAULT_DOCUMENT_PROJECTION;
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        for (File file : new File(str).listFiles()) {
            if (!file.getName().startsWith(Utils.HIDDEN_PREFIX)) {
                includeFile(matrixCursor, file);
            }
        }
        return matrixCursor;
    }

    public Cursor queryDocument(String str, String[] strArr) throws FileNotFoundException {
        if (strArr == null) {
            strArr = DEFAULT_DOCUMENT_PROJECTION;
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        includeFile(matrixCursor, new File(str));
        return matrixCursor;
    }

    @TargetApi(19)
    private void includeFile(MatrixCursor matrixCursor, File file) throws FileNotFoundException {
        MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
        newRow.add("document_id", file.getAbsolutePath());
        newRow.add("_display_name", file.getName());
        String documentType = getDocumentType(file.getAbsolutePath());
        newRow.add("mime_type", documentType);
        int i = file.canWrite() ? 6 : 0;
        if (documentType.startsWith("image/")) {
            i |= 1;
        }
        newRow.add("flags", Integer.valueOf(i));
        newRow.add("_size", Long.valueOf(file.length()));
        newRow.add("last_modified", Long.valueOf(file.lastModified()));
    }


    @Override
    public String getDocumentType(String str) throws FileNotFoundException {
        String mimeTypeFromExtension;
        File file = new File(str);
        if (file.isDirectory()) {
            return "vnd.android.document/directory";
        }
        int lastIndexOf = file.getName().lastIndexOf(46);
        return (lastIndexOf < 0 || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(lastIndexOf + 1))) == null) ? "application/octet-stream" : mimeTypeFromExtension;
    }

    @Override

    public void deleteDocument(String str) throws FileNotFoundException {
        new File(str).delete();
    }

    public ParcelFileDescriptor openDocument(String str, String str2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        File file = new File(str);
        if (str2.indexOf(119) != -1) {
            return ParcelFileDescriptor.open(file, 805306368);
        }
        return null;
    }
}

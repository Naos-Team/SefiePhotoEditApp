package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.camera.IWorkerCallback;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BitmapUtils;

import java.io.File;

public class FilterResourceHelper {
    private static final String[] ORIGIN_THUMBS = {"filter/thumbs/origin_thumb_1.jpg", "filter/thumbs/origin_thumb_2.jpg", "filter/thumbs/origin_thumb_3.jpg", "filter/thumbs/origin_thumb_4.jpg", "filter/thumbs/origin_thumb_5.jpg", "filter/thumbs/origin_thumb_6.jpg"};
    private static final String TAG = "FilterResourceHelper";

    public static void logAllFilters() {
        for (FilterType filterType : FilterType.values()) {
            Log.d(TAG, "logAllFilters: " + filterType.name().toLowerCase());
        }
    }

    private static String getThumbName(int i) {
        String[] strArr = ORIGIN_THUMBS;
        return strArr[(i / 3) % strArr.length];
    }

    @Deprecated
    public static void generateFilterThumbs(Context context, boolean z) {
        File file;
        if (z) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Omoshiroi/thumbs");
        } else {
            file = new File(context.getFilesDir().getAbsolutePath(), "thumbs");
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        int i = 0;
        for (FilterType filterType : FilterType.values()) {
            Bitmap loadBitmapFromAssets = BitmapUtils.loadBitmapFromAssets(context, getThumbName(i));
            File file2 = new File(file.getAbsolutePath(), filterType.name().toLowerCase() + ".jpg");
            Log.d(TAG, "generateFilterThumbs: saving to " + file2.getAbsolutePath());
            BitmapUtils.saveBitmapWithFilterApplied(context, filterType, loadBitmapFromAssets, file2.getAbsolutePath(), (IWorkerCallback) null);
            i++;
        }
        Toast.makeText(context, "Finished generating filter thumbs", 1).show();
    }

    public static String getSimpleName(FilterType filterType) {
        String replaceAll = filterType.name().toLowerCase().replaceAll("filter", "");
        if (replaceAll.endsWith("_")) {
            replaceAll = replaceAll.substring(0, replaceAll.length() - 1);
        }
        return replaceAll.toUpperCase();
    }

    public static Bitmap getFilterThumbFromFile(Context context, FilterType filterType) {
        return BitmapUtils.loadBitmapFromFile(new File(context.getFilesDir().getAbsolutePath(), "thumbs").getAbsolutePath() + "/" + filterType.name().toLowerCase() + ".jpg");
    }

    public static Bitmap getFilterThumbFromAssets(Context context, FilterType filterType) {
        return BitmapUtils.loadBitmapFromAssets(context, "filter/thumbs/" + filterType.name().toLowerCase() + ".jpg");
    }

    public static int getTotalFilterSize() {
        return FilterType.values().length;
    }
}

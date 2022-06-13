package com.sweet.selfiecameraphotoeditor.cameraview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.Utils;

import java.io.File;

public class ConstantData {
    @SuppressLint({"WrongConstant"})
    public static void shareFile(String str, Context context, String str2) {
        File file = new File(str);
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            if (!str2.equalsIgnoreCase("")) {
                intent.setPackage(str2);
            }
            intent.putExtra("android.intent.extra.TEXT", context.getResources().getString(R.string.app_name) + " \n" + " https://play.google.com/store/apps/details?id=" + context.getPackageName());
            if (Build.VERSION.SDK_INT < 24) {
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(str));
            } else {
                intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file));
            }
            intent.setType(Utils.MIME_TYPE_IMAGE);
            intent.addFlags(1);
            context.startActivity(Intent.createChooser(intent, "Share Photo..."));
        } catch (Exception unused) {
            Toast.makeText(context, "App Not Install", 1).show();
        }
    }
}

package com.sweet.selfiecameraphotoeditor.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class Constant {
    public static final int[] COLORS = {-1, ViewCompat.MEASURED_STATE_MASK, -2097391, -10027264, -63256, -130639, -4324355, -65523, -12535, -258508, -16647738, -65473, -15732484, -232195, -14550003, -10092289, -3342592, -51965, -64368, -4259585, -1703936, -11141121, -7405327, -1024, -16187640, -12544, -125805, -43691, -228313, -16712193, -3342590, -60929, -16459265, -26317, -114333, -12976364, -130662, -4451330, -63686, -3145980, -65451};
    public static final String SAVED_IMG_PATH = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/temp/");
    public static String[] funname = {"Filters", "Text", "Emoji", "Sticker", "Blender", "Frame", "Draw", "Body", "Crop", "Gallery", "Camera", "Add Photo"};
    private static Constant mInstance;
    public static MyCallback myCallback;
    public File file;
    public Uri uri;

    public interface MyCallback {
        void callbackCall(boolean z);
    }

    public static Constant getInstance() {
        if (mInstance == null) {
            mInstance = new Constant();
        }
        return mInstance;
    }

    public static void requestPermission(final Activity activity) {
        Dexter.withActivity(activity).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                multiplePermissionsReport.areAllPermissionsGranted();
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle((CharSequence) "Change Permissions in Settings");
                    builder.setMessage((CharSequence) "\nClick SETTINGS to Manually Set\nPermissions to use this app").setCancelable(false).setPositiveButton((CharSequence) "SETTINGS", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", activity.getPackageName(), (String) null));
                            activity.startActivityForResult(intent, 1000);
                        }
                    });
                    builder.create().show();
                }
            }

            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).onSameThread().check();
    }
}

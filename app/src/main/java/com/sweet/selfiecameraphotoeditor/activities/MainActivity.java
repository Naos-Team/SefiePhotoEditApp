package com.sweet.selfiecameraphotoeditor.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.common.Utils;
import com.sweet.selfiecameraphotoeditor.common.mApplication;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.view.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static final int SELECT_FILE = 222;
    ImageView btnCamera;
    ImageView btnCollage;
    ImageView btnEditor;
    ImageView btnMycreation;
    File f81f;
    ImageView imgRateus;
    ImageView imgPrivacy;
    ImageView imgShareapp;
    mApplication mGlobal;
    String mImagename;
    Bitmap m_bitmap1;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        getWindow().setFlags(1024, 1024);
        this.mGlobal = (mApplication) getApplication();
        if (Build.VERSION.SDK_INT >= 23 && !(ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0)) {
            getPermission();
        }
        this.btnCamera = (ImageView) findViewById(R.id.img_bg1);
        this.btnEditor = (ImageView) findViewById(R.id.img_bg2);
        this.btnCollage = (ImageView) findViewById(R.id.img_bg3);
        this.btnMycreation = (ImageView) findViewById(R.id.img_bg4);
//        this.imgShareapp = (ImageView) findViewById(R.id.img_shareapp);
//        this.imgRateus = (ImageView) findViewById(R.id.img_rateus);
//        this.imgPrivacy = (ImageView) findViewById(R.id.img_privacy);
        this.btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Ad_class.showInterstitial(MainActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), CameraStartActivity.class));
                    }
                });
            }
        });
        this.btnEditor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Ad_class.showInterstitial(MainActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType(Utils.MIME_TYPE_IMAGE);
                        MainActivity.this.startActivityForResult(Intent.createChooser(intent, "Select File"), MainActivity.SELECT_FILE);
                    }
                });
            }
        });
        this.btnCollage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Ad_class.showInterstitial(MainActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        Intent intent = new Intent(MainActivity.this, PhotoSelectActivity.class);
                        intent.putExtra(PhotoSelectActivity.KEY_LIMIT_MAX_IMAGE, 10);
                        intent.putExtra(PhotoSelectActivity.KEY_LIMIT_MIN_IMAGE, 1);
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        });
        this.btnMycreation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Ad_class.showInterstitial(MainActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), MYCreationActivity.class));
                    }
                });
            }
        });
//        this.imgShareapp.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.SEND");
//                intent.putExtra("android.intent.extra.TEXT", "Capture Your Best Memories With Sweet Selfie Camera : https://play.google.com/store/apps/details?id=com.sweet.selfiecameraphotoeditor");
//                intent.setType("text/plain");
//                MainActivity.this.startActivity(intent);
//            }
//        });
//        this.imgRateus.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                MainActivity mainActivity = MainActivity.this;
//                mainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getApplicationContext().getPackageName())));
//            }
//        });
//
//        this.imgPrivacy.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Uri uri = Uri.parse("https://www.google.com");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//
//            }
//        });
    }

    @TargetApi(23)
    public void getPermission() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!addPermission(arrayList2, "android.permission.READ_EXTERNAL_STORAGE")) {
            arrayList.add("Read Storage");
        }
        if (!addPermission(arrayList2, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            arrayList.add("Write Storage");
        }
        if (!addPermission(arrayList2, "android.permission.CAMERA")) {
            arrayList.add("Camera");
        }
        if (arrayList2.size() <= 0) {
            return;
        }
        if (arrayList.size() > 0) {
            for (int i = 0; i < 1; i++) {
                requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 124);
            }
            return;
        }
        requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 124);
    }

    @SuppressLint({"WrongConstant"})
    @TargetApi(23)
    private boolean addPermission(List<String> list, String str) {
        if (checkSelfPermission(str) == 0) {
            return true;
        }
        list.add(str);
        return shouldShowRequestPermissionRationale(str);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 111) {
                this.f81f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
                File[] listFiles = this.f81f.listFiles();
                int length = listFiles.length;
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        break;
                    }
                    File file = listFiles[i4];
                    if (file.getName().equals("temp.jpg")) {
                        this.f81f = file;
                        break;
                    }
                    i4++;
                }
                try {
                    int attributeInt = new ExifInterface(this.f81f.getAbsolutePath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
                    if (attributeInt == 3) {
                        i3 = 180;
                    } else if (attributeInt == 6) {
                        i3 = 90;
                    } else if (attributeInt == View.GONE) {
                        i3 = 270;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    this.m_bitmap1 = BitmapFactory.decodeFile(this.f81f.getAbsolutePath());
                    Bitmap bitmap = this.m_bitmap1;
                    double height = (double) this.m_bitmap1.getHeight();
                    double width = (double) this.m_bitmap1.getWidth();
                    Double.isNaN(width);
                    Double.isNaN(height);
                    this.m_bitmap1 = Bitmap.createScaledBitmap(bitmap, CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION, (int) (height * (500.0d / width)), true);
                    Matrix matrix = new Matrix();
                    matrix.postRotate((float) i3);
                    this.mGlobal.setImage(Bitmap.createBitmap(this.m_bitmap1, 0, 0, this.m_bitmap1.getWidth(), this.m_bitmap1.getHeight(), matrix, true));
                    startCropActivity(Uri.fromFile(this.f81f));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else if (i == SELECT_FILE) {
                try {
                    this.f81f = Utils.getFile(this, intent.getData());
                    Glide.with(getApplicationContext()).asBitmap().load(this.f81f).into(new SimpleTarget<Bitmap>(CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION, CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION) {
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            MainActivity.this.mGlobal.setImage(bitmap);
                            MainActivity mainActivity = MainActivity.this;
                            mainActivity.startCropActivity(Uri.fromFile(mainActivity.f81f));
                        }
                    });
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } else if (i == 69) {
                handleCropResult(intent);
            } else if (i == 2) {
                Log.w(NotificationCompat.CATEGORY_MESSAGE, "requestCode 2");
            }
        }
        if (i2 == 96) {
            handleCropError(intent);
        }
    }

    public void startCropActivity(@NonNull Uri uri) {
        File file = new File(Utils.SAVED_IMG_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.mImagename = "image" + Calendar.getInstance().getTimeInMillis() + ".png";
        UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), "SampleCropImage.png"))).start(this);
    }

    @SuppressLint({"WrongConstant"})
    private void handleCropError(@NonNull Intent intent) {
        Throwable error = UCrop.getError(intent);
        if (error != null) {
            Toast.makeText(this, error.getMessage(), 1).show();
        } else {
            Toast.makeText(this, "Error", 1).show();
        }
    }

    @SuppressLint({"WrongConstant"})
    private void handleCropResult(@NonNull Intent intent) {
        Uri output = UCrop.getOutput(intent);
        if (output != null) {
            saveCroppedImage(output);
        } else {
            Toast.makeText(this, "Crop", 1).show();
        }
    }

    @SuppressLint({"WrongConstant"})
    private void saveCroppedImage(Uri uri) {
        if (uri == null || !uri.getScheme().equals("file")) {
            Toast.makeText(this, "Error", 1).show();
            return;
        }
        try {
            copyFileToDownloads(uri);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), 1).show();
        }
    }

    private void copyFileToDownloads(Uri uri) throws Exception {
        File file = new File(Utils.SAVED_IMG_PATH, this.mImagename);
        if (file.exists()) {
            file.delete();
        }
        FileInputStream fileInputStream = new FileInputStream(new File(uri.getPath()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        channel.transferTo(0, channel.size(), fileOutputStream.getChannel());
        fileInputStream.close();
        fileOutputStream.close();
        if (Build.VERSION.SDK_INT >= 19) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            sendBroadcast(intent);
        } else {
            sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(file.getAbsolutePath()))));
        Intent intent2 = new Intent(this, PhotoEditorActivity.class);
        intent2.putExtra("img", this.mImagename);
        intent2.putExtra("iseffect", true);
        startActivityForResult(intent2, 2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Ad_class.loadAd(this);
    }
    @Override
    public void onBackPressed() {
        exitdialog();
    }

    public void exitdialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(false);
        ((CustomTextView) dialog.findViewById(R.id.txt_no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((CustomTextView) dialog.findViewById(R.id.txt_yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.finish();
                MainActivity.this.finishAffinity();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

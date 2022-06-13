package com.sweet.selfiecameraphotoeditor.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class CameraStickerPhotoSaveActivity extends Activity {
    Bitmap back;
    ImageView cameraImage;
    ImageView frame;
    Bitmap front;
    ImageView imgBack;
    ImageView imgSave;
    RelativeLayout saveLayout;
    Uri savedimageuri;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.camerastickerphotosave_activity);
        getWindow().setFlags(1024, 1024);

        FrameLayout frameLayout = findViewById(R.id.native_frame);
        Ad_class.refreshAd(frameLayout, CameraStickerPhotoSaveActivity.this);


        this.imgSave = (ImageView) findViewById(R.id.img_save);
        this.saveLayout = (RelativeLayout) findViewById(R.id.save_layout);
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraStickerPhotoSaveActivity.this.finish();
            }
        });
        this.cameraImage = (ImageView) findViewById(R.id.cameraImage);
        this.frame = (ImageView) findViewById(R.id.frame);
        this.back = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Temporary/temp_1.png");
        this.front = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Temporary/temp_2.png");
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        sb.append("/Temporary/temp_1.png");
        this.frame.setImageBitmap(this.front);
        this.cameraImage.setImageBitmap(this.back);
        this.imgSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Ad_class.showInterstitial(CameraStickerPhotoSaveActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        CameraStickerPhotoSaveActivity.this.savecameraimage();
                    }
                });
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        Ad_class.loadAd(this);
    }

    public void savecameraimage() {
        saveBitmapImage(getScreenShot());
        Intent intent = new Intent(this, CameraPhotoShareActivity.class);
        intent.putExtra("imageToShare-uri", this.savedimageuri.toString());
        startActivityForResult(intent, 5);
    }

    public Bitmap getScreenShot() {
        this.saveLayout.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(this.saveLayout.getDrawingCache());
        this.saveLayout.setDrawingCacheEnabled(false);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        this.saveLayout.draw(new Canvas(createBitmap2));
        return createBitmap2;
    }


    public void saveBitmapImage(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + getResources().getString(R.string.app_name) + "/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, String.format("%s_%d.png", "CameraPlus", Integer.valueOf(new Random().nextInt(1000))));
            if (file2.exists() && file2.delete()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ContentValues contentValues = new ContentValues(3);
            contentValues.put("title", "ManHairMustachePro");
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("_data", file2.getAbsolutePath());
            this.savedimageuri = Uri.fromFile(file2.getAbsoluteFile());
            getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }

}

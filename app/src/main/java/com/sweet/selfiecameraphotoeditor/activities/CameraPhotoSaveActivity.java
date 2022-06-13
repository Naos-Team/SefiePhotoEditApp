package com.sweet.selfiecameraphotoeditor.activities;

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
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.TouchImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class CameraPhotoSaveActivity extends AppCompatActivity {
    Bitmap cameraBitamp;
    TouchImageView cameraImage;
    ImageView imgBack;
    ImageView imgSave;
    String string;
    Uri savedImageUri;
    Uri path;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.cameraphotosave_activity);
        getWindow().setFlags(1024, 1024);
        this.imgSave = (ImageView) findViewById(R.id.img_save);
        this.cameraImage = (TouchImageView) findViewById(R.id.cameraImage);
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraPhotoSaveActivity.this.finish();
            }
        });
        path= Uri.parse(getIntent().getStringExtra("path"));


//        try {
//            if(  Uri.parse(path)!=null   ){
//                this.cameraBitamp = MediaStore.Images.Media.getBitmap(getContentResolver() , Uri.parse(path));
//            }
//        }
//        catch (Exception e) {
//            //handle exception
//        }
//        this.cameraBitamp = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Temp/"+path);
//        this.string = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Temp/"+path;
        this.cameraImage.setImageURI(path);
        this.imgSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraPhotoSaveActivity.this.savecameraimage();
            }
        });
    }


    public void savecameraimage() {
        saveBitmapImage(getScreenShot());
        Intent intent = new Intent(this, CameraPhotoShareActivity.class);
        intent.putExtra("imageToShare-uri", this.savedImageUri.toString());
        startActivityForResult(intent, 5);
    }

    public Bitmap getScreenShot() {
        this.cameraImage.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(this.cameraImage.getDrawingCache());
        this.cameraImage.setDrawingCacheEnabled(false);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        this.cameraImage.draw(new Canvas(createBitmap2));
        return createBitmap2;
    }

    public void saveBitmapImage(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + getResources().getString(R.string.app_name) + "/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, String.format("%s_%d.png", new Object[]{"CameraPlus", Integer.valueOf(new Random().nextInt(1000))}));
            if (file2.exists() && file2.delete()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
            } catch (Exception unused) {
                ContentValues contentValues = new ContentValues(3);
                contentValues.put("title", "ManHairMustachePro");
                contentValues.put("mime_type", "image/jpeg");
                contentValues.put("_data", file2.getAbsolutePath());
                this.savedImageUri = Uri.fromFile(file2.getAbsoluteFile());
                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } catch (Throwable th) {
                try {
                    th.printStackTrace();
                } catch (Exception unused2) {
                    ContentValues contentValues2 = new ContentValues(3);
                    contentValues2.put("title", "ManHairMustachePro");
                    contentValues2.put("mime_type", "image/jpeg");
                    contentValues2.put("_data", file2.getAbsolutePath());
                    this.savedImageUri = Uri.fromFile(file2.getAbsoluteFile());
                    getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues2);
                }
            }
            ContentValues contentValues3 = new ContentValues(3);
            contentValues3.put("title", "ManHairMustachePro");
            contentValues3.put("mime_type", "image/jpeg");
            contentValues3.put("_data", file2.getAbsolutePath());
            this.savedImageUri = Uri.fromFile(file2.getAbsoluteFile());
            getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues3);
        }
    }
}

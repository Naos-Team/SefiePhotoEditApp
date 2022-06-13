package com.sweet.selfiecameraphotoeditor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.cameraview.ConstantData;

import java.io.FileNotFoundException;

public class CameraPhotoShareActivity extends AppCompatActivity {
    LinearLayout btnFbshare;
    LinearLayout btnInstashare;
    LinearLayout btnMoreshare;
    LinearLayout btnWhatshare;
    ImageView cameraimageshare;
    ImageView imgCamerasub;
    ImageView imgCreation;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.cameraphotoshare_activity);
        getWindow().setFlags(1024, 1024);

        FrameLayout frameLayout = findViewById(R.id.native_frame);
        Ad_class.refreshAd(frameLayout, CameraPhotoShareActivity.this);

        this.cameraimageshare = (ImageView) findViewById(R.id.cameraImageShare);
        this.btnFbshare = (LinearLayout) findViewById(R.id.btn_fbshare);
        this.btnWhatshare = (LinearLayout) findViewById(R.id.btn_whatshare);
        this.btnInstashare = (LinearLayout) findViewById(R.id.btn_instashare);
        this.btnMoreshare = (LinearLayout) findViewById(R.id.btn_moreshare);
        this.imgCamerasub = (ImageView) findViewById(R.id.img_camerasub);
        this.imgCamerasub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraPhotoShareActivity.this.onBackPressed();
            }
        });
        this.imgCreation = (ImageView) findViewById(R.id.img_creation);
        this.imgCreation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraPhotoShareActivity cameraPhotoShareActivity = CameraPhotoShareActivity.this;
                cameraPhotoShareActivity.startActivity(new Intent(cameraPhotoShareActivity.getApplicationContext(), MYCreationActivity.class));
            }
        });
        Uri parse = Uri.parse(getIntent().getStringExtra("imageToShare-uri"));
        this.cameraimageshare.setImageURI(parse);
        setResult(-1, new Intent());
        try {
            getContentResolver().openInputStream(parse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final String path = parse.getPath();
        this.btnFbshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConstantData.shareFile(path, CameraPhotoShareActivity.this, "com.facebook.katana");
            }
        });
        this.btnWhatshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConstantData.shareFile(path, CameraPhotoShareActivity.this, "com.whatsapp");
            }
        });
        this.btnInstashare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConstantData.shareFile(path, CameraPhotoShareActivity.this, "com.instagram.android");
            }
        });
        this.btnMoreshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConstantData.shareFile(path, CameraPhotoShareActivity.this, "");
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SweetCameraActivity.class));
    }
}

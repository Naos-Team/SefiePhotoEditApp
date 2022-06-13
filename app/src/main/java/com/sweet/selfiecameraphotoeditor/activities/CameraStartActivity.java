package com.sweet.selfiecameraphotoeditor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;

public class CameraStartActivity extends AppCompatActivity {
    public static boolean isCheckCameraFilter = false;
    public static boolean isCheckCameraSticker = false;
    CardView btnCamerafilter;
    CardView btnCamerasticker;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.camerastart_activity);

        FrameLayout frameLayout = findViewById(R.id.native_frame);
        Ad_class.refreshAd(frameLayout, CameraStartActivity.this);


        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getWindow().setFlags(1024, 1024);
        this.btnCamerasticker = (CardView) findViewById(R.id.btn_camerasticker);
        this.btnCamerafilter = (CardView) findViewById(R.id.btn_camerafilter);
        this.btnCamerasticker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraStartActivity.isCheckCameraSticker = true;
                CameraStartActivity.isCheckCameraFilter = false;
                CameraStartActivity cameraStartActivity = CameraStartActivity.this;
                cameraStartActivity.startActivity(new Intent(cameraStartActivity.getApplicationContext(), SweetCameraActivity.class));
            }
        });
        this.btnCamerafilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraStartActivity.isCheckCameraSticker = false;
                CameraStartActivity.isCheckCameraFilter = true;
                CameraStartActivity cameraStartActivity = CameraStartActivity.this;
                cameraStartActivity.startActivity(new Intent(cameraStartActivity.getApplicationContext(), SweetCameraActivity.class));
            }
        });

          }

          @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}

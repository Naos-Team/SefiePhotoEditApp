package com.sweet.selfiecameraphotoeditor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.Utils;

import java.io.File;

public class PhotoShareActivity extends AppCompatActivity {
    ImageView finalSavedImage;
    String path;
    Uri uri;


@Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.photoshare_activity);
        getWindow().setFlags(1024, 1024);


        FrameLayout frameLayout = findViewById(R.id.native_frame);
        Ad_class.refreshAd(frameLayout, PhotoShareActivity.this);

         findViewById(R.id.img_home).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoShareActivity.this.onBackPressed();
            }
        });
        findViewById(R.id.img_mycreation).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoShareActivity photoShareActivity = PhotoShareActivity.this;
                photoShareActivity.startActivity(new Intent(photoShareActivity.getApplicationContext(), MYCreationActivity.class));
            }
        });
        Bundle extras = getIntent().getExtras();
        this.path = getIntent().getStringExtra("FinalURI");
        this.uri = Uri.fromFile(new File(extras.get("FinalURI").toString()));
        this.finalSavedImage = (ImageView) findViewById(R.id.finalSavedImage);
        Glide.with(getApplicationContext()).load(this.uri).into(this.finalSavedImage);
        findViewById(R.id.btn_fbshare).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    PhotoShareActivity photoShareActivity = PhotoShareActivity.this;
                    Uri uriForFile = FileProvider.getUriForFile(photoShareActivity, PhotoShareActivity.this.getPackageName() + ".provider", new File(PhotoShareActivity.this.path));
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("*/*");
                    intent.setPackage("com.facebook.katana");
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    PhotoShareActivity.this.startActivity(Intent.createChooser(intent, "Share Photo With"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_whatshare).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    PhotoShareActivity photoShareActivity = PhotoShareActivity.this;
                    Uri uriForFile = FileProvider.getUriForFile(photoShareActivity, PhotoShareActivity.this.getPackageName() + ".provider", new File(PhotoShareActivity.this.path));
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("*/*");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    PhotoShareActivity.this.startActivity(Intent.createChooser(intent, "Share Photo With"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_instashare).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    PhotoShareActivity photoShareActivity = PhotoShareActivity.this;
                    Uri uriForFile = FileProvider.getUriForFile(photoShareActivity, PhotoShareActivity.this.getPackageName() + ".provider", new File(PhotoShareActivity.this.path));
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("*/*");
                    intent.setPackage("com.instagram.android");
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    PhotoShareActivity.this.startActivity(Intent.createChooser(intent, "Share Photo With"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_moreshare).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    PhotoShareActivity photoShareActivity = PhotoShareActivity.this;
                    Uri uriForFile = FileProvider.getUriForFile(photoShareActivity, PhotoShareActivity.this.getPackageName() + ".provider", new File(PhotoShareActivity.this.path));
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType(Utils.MIME_TYPE_IMAGE);
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    PhotoShareActivity.this.startActivity(Intent.createChooser(intent, "Share Photo With"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}

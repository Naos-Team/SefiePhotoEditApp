package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.flyu.hardcode.HardCodeData;

public class SplashActivity extends AppCompatActivity {
    private static int requestCamera = 1;

    public static int requestGallery = 2;
    private static final String TAG = "SplashActivity";


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.content_splash);
        int random = ((int) (Math.random() * 2.147483647E9d)) % 3;
        ImageView imageView = (ImageView) findViewById(R.id.back_ground);
        if (random == 0) {
            imageView.setImageResource(R.drawable.landing1);
        } else if (random == 1) {
            imageView.setImageResource(R.drawable.landing2);
        } else if (random == 2) {
            imageView.setImageResource(R.drawable.landing3);
        }
        setLsnForImageView(R.id.take, new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity splashActivity = SplashActivity.this;
                splashActivity.startActivity(new Intent(splashActivity, CameraPreviewActivity.class));
            }
        });
        setLsnForImageView(R.id.choose, new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), SplashActivity.requestGallery);
            }
        });
        setLsnForImageView(R.id.collage, new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(SplashActivity.this, "不存在的~", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLsnForImageView(int i, View.OnClickListener onClickListener) {
        ((ImageView) findViewById(i)).setOnClickListener(onClickListener);
    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            return;
        }
        if (i == requestCamera) {
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
            Log.d(TAG, "onActivityResult: " + bitmap.getWidth() + " " + bitmap.getHeight());
        } else if (i == requestGallery) {
            String[] strArr = {"_data"};
            Cursor query = getContentResolver().query(intent.getData(), strArr, (String) null, (String[]) null, (String) null);
            String str = null;
            while (query.moveToNext()) {
                str = query.getString(query.getColumnIndex(strArr[0]));
            }
            query.close();
            Log.d(TAG, "onActivityResult: " + str);
            Toast.makeText(this, "This function is not quiet ready.", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(this, EditActivity.class);
            intent2.putExtra(HardCodeData.IMAGE_PATH, str);
            startActivity(intent2);
        }
    }
}

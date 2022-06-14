package com.sweet.selfiecameraphotoeditor.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.model.Image;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView iv_icon;
    TextView textView;
    ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.splashscreen_activity);

        Ad_class.loadAd(SplashScreenActivity.this);

        iv_icon = findViewById(R.id.ic_app);
        textView = findViewById(R.id.tv_title);
        progressBar = findViewById(R.id.progress_circular);

        progressBar.setIndeterminateDrawable(new DoubleBounce());

        getWindow().setFlags(1024, 1024);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());



    }

    @SuppressLint({"WrongConstant"})
    public boolean needPermissionCheck() {
        return (Build.VERSION.SDK_INT >= 23 && getApplicationContext().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) || (Build.VERSION.SDK_INT >= 23 && getApplicationContext().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) || (Build.VERSION.SDK_INT >= 23 && getApplicationContext().checkSelfPermission("android.permission.CAMERA") != 0);
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
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CAMERA") == 0) {

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    if (!SplashScreenActivity.this.needPermissionCheck()) {

                        SplashScreenActivity splashScreenActivity = SplashScreenActivity.this;
                        splashScreenActivity.startActivity(new Intent(splashScreenActivity.getApplicationContext(), MainActivity.class));
                    }
                }
            }, 3500);
        } else {
            PermissionDialog();
        }
    }


    public void PermissionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_permission);
        dialog.setCancelable(false);
        ((CustomTextView) dialog.findViewById(R.id.txt_yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SplashScreenActivity.this.getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(SplashScreenActivity.this.getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(SplashScreenActivity.this.getApplicationContext(), "android.permission.CAMERA") == 0) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (!SplashScreenActivity.this.needPermissionCheck()) {
                                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this.getApplicationContext(), MainActivity.class));
                            }
                        }
                    }, 3000);
                } else {
                    SplashScreenActivity.this.getPermission();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

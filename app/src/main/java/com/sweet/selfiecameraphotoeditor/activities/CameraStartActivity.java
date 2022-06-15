package com.sweet.selfiecameraphotoeditor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;


import com.makeramen.roundedimageview.RoundedImageView;
import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.adapter.ImageSlideItemAdapter;
import com.sweet.selfiecameraphotoeditor.model.ImageSlideItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class CameraStartActivity extends AppCompatActivity {
    public static boolean isCheckCameraFilter = false;
    public static boolean isCheckCameraSticker = false;
    private ImageView btnCamerafilter;
    private ImageView btnCamerasticker;
    private RoundedImageView imageSlider;
    private ViewPager viewPager;
    private ImageSlideItemAdapter imageSlideItemAdapter;
    private CircleIndicator circleIndicator;
    private List<ImageSlideItem> mImageList;
    private Timer timer;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.camerastart_activity);

        //FrameLayout frameLayout = findViewById(R.id.native_frame);
        //Ad_class.refreshAd(frameLayout, CameraStartActivity.this);

        viewPager = findViewById(R.id.image_slider);
        circleIndicator = findViewById(R.id.circle_indicator);
        mImageList = getListPhoto();
        imageSlideItemAdapter = new ImageSlideItemAdapter(this, mImageList);
        viewPager.setAdapter(imageSlideItemAdapter);
        circleIndicator.setViewPager(viewPager);
        imageSlideItemAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImage();

        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getWindow().setFlags(1024, 1024);
        this.btnCamerasticker = (ImageView) findViewById(R.id.btn_camerasticker);
        this.btnCamerafilter = (ImageView) findViewById(R.id.btn_camerafilter);
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

    private List<ImageSlideItem> getListPhoto() {
        List<ImageSlideItem> imageList = new ArrayList<ImageSlideItem>();
        imageList.add(new ImageSlideItem(R.drawable.pic1));
        imageList.add(new ImageSlideItem(R.drawable.pic2));
        imageList.add(new ImageSlideItem(R.drawable.pic3));
        imageList.add(new ImageSlideItem(R.drawable.pic4));
        imageList.add(new ImageSlideItem(R.drawable.pic5));
        imageList.add(new ImageSlideItem(R.drawable.pic6));
        return imageList;
    }

    private void autoSlideImage(){
        if (mImageList == null || mImageList.isEmpty() || viewPager == null){
            return;
        }
        if (timer==null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mImageList.size()-1;
                        if (currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }
                        else{
                            viewPager.setCurrentItem(0);
                        }

                    }
                });
            }
        },500,3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer = null;
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}

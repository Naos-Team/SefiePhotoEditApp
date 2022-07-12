package com.sweet.selfiecameraphotoeditor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.adapter.ScaleItemAdapter;
import com.sweet.selfiecameraphotoeditor.model.ScaleItem;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yalantis.ucrop.view.CropImageView;

import java.util.ArrayList;


public class CropImageActivity extends AppCompatActivity {

    private CropImageView cropImageView;
    private ImageView btn_save;
    private RecyclerView rv_scale;
    private ArrayList<ScaleItem> scaleItems;
    private ScaleItemAdapter scaleItemAdapter;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        bindView();

        if(getIntent() != null){
            mUri = Uri.parse(getIntent().getStringExtra("uri_img"));
        }

        setUpCropView();
        setUpScaleList();

    }

    private void setUpCropView() {
        cropImageView.setImageUriAsync(mUri);
        cropImageView.setAspectRatio(9,16);
        cropImageView.setFixedAspectRatio(false);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap cropped = cropImageView.getCroppedImage();
            }
        });
    }

    private void bindView() {
        btn_save = findViewById(R.id.btn_save);
        cropImageView = findViewById(R.id.cropImageView);
        rv_scale = findViewById(R.id.rv_scale);
    }

    private void setUpScaleList() {
        scaleItems = new ArrayList<>();
        scaleItems.add(new ScaleItem(0, "Custom", 1, 1, false));
        scaleItems.add(new ScaleItem(1, "1:1", 1, 1, false));
        scaleItems.add(new ScaleItem(2, "3:4", 3, 4, false));
        scaleItems.add(new ScaleItem(3, "4:3", 4, 3, false));
        scaleItems.add(new ScaleItem(4, "16:9", 16, 9, false));
        scaleItems.add(new ScaleItem(5, "9:16", 9, 16, false));



        rv_scale.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_scale.setItemAnimator(new DefaultItemAnimator());

        scaleItemAdapter = new ScaleItemAdapter(scaleItems, new ScaleItemAdapter.ScaleItemListener() {
            @Override
            public void onClick(ScaleItem item) {
                if(item.getId() == 0){
                    cropImageView.setFixedAspectRatio(false);
                }else{
                    cropImageView.setFixedAspectRatio(true);
                    cropImageView.setAspectRatio(item.getScaleX(), item.getScaleY());
                }
            }
        });

        rv_scale.setAdapter(scaleItemAdapter);


    }

}
package com.sweet.selfiecameraphotoeditor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.model.ScaleItem;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;


public class CropImageActivity extends AppCompatActivity {

    private CropImageView cropImageView;
    private ImageView btn_save;
    private RecyclerView rv_scale;
    private ArrayList<ScaleItem> scaleItems;
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
        scaleItems.add(new ScaleItem(0, "Free", 0, 0, R.drawable.ic_nav_crop,false));
        scaleItems.add(new ScaleItem(1, "1:1", 1, 1, R.drawable.ic_nav_effect,false));
        scaleItems.add(new ScaleItem(2, "3:4", 3, 4, R.drawable.ic_nav_filters,false));
        scaleItems.add(new ScaleItem(3, "5:4", 5, 4, R.drawable.ic_nav_text,false));
        scaleItems.add(new ScaleItem(4, "9:16", 9, 16, R.drawable.ic_nav_paint,false));

    }


}
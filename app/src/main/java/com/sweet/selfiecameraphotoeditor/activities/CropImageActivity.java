package com.sweet.selfiecameraphotoeditor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sweet.selfiecameraphotoeditor.R;
import com.theartofdev.edmodo.cropper.CropImageView;


public class CropImageActivity extends AppCompatActivity {

    private CropImageView cropImageView;
    private Button btn_save;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        btn_save = findViewById(R.id.btn_save);
        cropImageView = findViewById(R.id.cropImageView);

        if(getIntent() != null){
            mUri = Uri.parse(getIntent().getStringExtra("uri_img"));
        }

        cropImageView.setImageUriAsync(mUri);

//        binding.cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
//            @Override
//            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
//                Uri resultUri = result.getUri();
//            }
//        });
//        binding.cropImageView.getCroppedImageAsync();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap cropped = cropImageView.getCroppedImage();
            }
        });

    }


}
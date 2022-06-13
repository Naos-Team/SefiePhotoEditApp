package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

//import com.cameraforselfie.sweetselfiecamera.common.Utils;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.Utils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.GlobalConfig;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.anim.RotateLoading;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.module.EffectsButton;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.AnimationUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BitmapUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.FakeThreadUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.FileUtils;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;

public class DecorateActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    public static final String RAW_MEDIA = "raw_media";
    public static final String SAVED_MEDIA_FILE = "saved_media_file";
    public static final String SAVED_MEDIA_TYPE = "saved_media_type";
    private static final String TAG = "DecorateActivity";
    private RelativeLayout decorateTool;
    private String desFolder;

    public String filePath;
    private ImageView imagePreview;
    private File mediaFile;

    public int mediaType;

    public String outputFilePath;

    public RotateLoading rotateLoading;

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }


    public void saveFile(final Runnable runnable) {
        this.rotateLoading.start();
        this.outputFilePath = new File(this.desFolder, this.mediaFile.getName()).getAbsolutePath();
        new FakeThreadUtils.SaveFileTask(this.desFolder, this.mediaFile.getName(), this.mediaFile.getParent(), new FileUtils.FileSavedCallback() {
            public void onFileSaved(String str) {
                DecorateActivity.this.rotateLoading.stop();
                runnable.run();
            }
        }).execute(new Void[0]);
    }

    private void init() {
        String str;
        getWindow().setFlags(1024, 1024);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.frag_decorate_picture);
        this.rotateLoading = (RotateLoading) findViewById(R.id.rotate_loading);
        this.imagePreview = (ImageView) findViewById(R.id.img_preview);
        this.decorateTool = (RelativeLayout) findViewById(R.id.rl_frag_decorate_tool);
        this.filePath = getIntent().getStringExtra(SAVED_MEDIA_FILE);
        this.mediaType = getIntent().getIntExtra(SAVED_MEDIA_TYPE, -1);
        this.mediaFile = new File(this.filePath);
        if (this.mediaType == 1) {
            str = FileUtils.getFileOnSDCard(GlobalConfig.IN_77_CAMERA_PHOTO_PATH).getAbsolutePath();
        } else {
            str = FileUtils.getFileOnSDCard(GlobalConfig.IN_77_CAMERA_VIDEO_PATH).getAbsolutePath();
        }
        this.desFolder = str;
        if (this.mediaType < 0) {
            finish();
        }
        TextureView textureView = (TextureView) findViewById(R.id.video_view);
        textureView.setSurfaceTextureListener(this);
        int i = this.mediaType;
        if (i == 1) {
            textureView.setVisibility(View.VISIBLE);
            this.imagePreview.setImageBitmap(BitmapUtils.loadBitmapFromFile(this.filePath));
        } else if (i == 0) {
            this.imagePreview.setVisibility(View.GONE);
            textureView.setVisibility(View.VISIBLE);
        }
        this.decorateTool.bringToFront();
        AnimationUtils.displayAnim(this.decorateTool, this, R.anim.fadein, 0);
        ((EffectsButton) findViewById(R.id.btn_frag_decorate_save)).setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                DecorateActivity.this.saveFile(new Runnable() {
                    public void run() {
                        TastyToast.makeText(DecorateActivity.this.getApplicationContext(), "已保存至SD卡", 0, 1);
                    }
                });
            }
        });
        ((EffectsButton) findViewById(R.id.btn_frag_decorate_cancel)).setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                File file = new File(DecorateActivity.this.filePath);
                if (file.exists()) {
                    file.delete();
                }
                DecorateActivity.this.finish();
            }
        });
        ((EffectsButton) findViewById(R.id.btn_frag_decorate_share)).setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                DecorateActivity.this.saveFile(new Runnable() {
                    public void run() {
                        Intent intent = new Intent("android.intent.action.SEND");
                        if (DecorateActivity.this.mediaType == 1) {
                            intent.setType(Utils.MIME_TYPE_IMAGE);
                        } else if (DecorateActivity.this.mediaType == 0) {
                            intent.setType("video/*");
                        }
                        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(DecorateActivity.this.outputFilePath)));
                        intent.addFlags(268435456);
                        DecorateActivity.this.startActivity(Intent.createChooser(intent, "分享给朋友"));
                    }
                });
            }
        });
        findViewById(R.id.btn_frag_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DecorateActivity.this.finish();
            }
        });
    }


    public void onDestroy() {
        super.onDestroy();
        File file = this.mediaFile;
        if (file != null && file.exists()) {
            this.mediaFile.delete();
        }
    }


    public void onPause() {
        super.onPause();
    }


    public void onResume() {
        super.onResume();
    }
}

package com.sweet.selfiecameraphotoeditor.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdView;
import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.adapter.EffectListAdapter;
import com.sweet.selfiecameraphotoeditor.adapter.StickerListAdapter;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.common.DrawingView;
import com.sweet.selfiecameraphotoeditor.common.Utils;
import com.sweet.selfiecameraphotoeditor.common.mApplication;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.EditActivity;
import com.sweet.selfiecameraphotoeditor.filtercommom;
import com.sweet.selfiecameraphotoeditor.stickerview.AutoTextRel;
import com.sweet.selfiecameraphotoeditor.stickerview.Color_Module;
import com.sweet.selfiecameraphotoeditor.stickerview.RecyclerItemClickListener;
import com.sweet.selfiecameraphotoeditor.stickerview.TextInfo;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2738b;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2745c;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2794d;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.view.CropImageView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import it.chengdazhi.styleimageview.StyleImageView;
import yuku.ambilwarna.AmbilWarnaDialog;


public class PhotoEditorActivity extends AppCompatActivity implements C2745c.C2638f, AutoTextRel.TouchEventListener, EffectListAdapter.CallBackData {
    protected static final int SELECT_FILE = 222;
    private static final int TEXT_ACTIVITY = 909;
    RelativeLayout rlLayout;
    LinearLayout adjustLayout;
    ImageView adjustSelect;
    ArrayList<Color_Module> arraySticker = new ArrayList<>();
    int bgAlpha = 0;
    int bgColor = 0;
    String bgDrawable = "0";
    LinearLayout btnAdjust;
    LinearLayout btnBrightness;
    LinearLayout btnCamera;
    LinearLayout btnContrast;
    LinearLayout btnDrawingcolor;
    LinearLayout btnDrawingerase;
    LinearLayout btnDrawingredo;
    LinearLayout btnDrawingstroke;
    LinearLayout btnDrawingundo;
    LinearLayout btnEffect;
    LinearLayout btnFilter;
    LinearLayout btnGallery;
    LinearLayout btnOpacity;
    LinearLayout btnPaint;
    LinearLayout btnRotate;
    LinearLayout btnSituration;
    LinearLayout btnSticker;
    LinearLayout btnText;
    LinearLayout ll_empty_photo, ll_load_photo;
    ImageView btnAddPhoto;
    ImageView cameraSelect;
    ProgressDialog dialog;
    private boolean editMode = false;
    int[] effectImageArray = {R.drawable.overlay01, R.drawable.overlay02, R.drawable.overlay03, R.drawable.overlay04, R.drawable.overlay05, R.drawable.overlay06, R.drawable.overlay07, R.drawable.overlay08, R.drawable.overlay09, R.drawable.overlay10, R.drawable.overlay11};
    ImageView effectSelect;
    RecyclerView effectrecycler;
    SeekBar effectseekbar;
    public static File f81f;
    ImageView filterSelect;
    String fontName = "";
    ImageView galleryselect;
    int height;
    ImageView imageoverlay;
    ImageView imgBack;
    ImageView imgSave;
    ImageView imgStrokeclose;
    int initialColor = -1;
    ImageView ivDrawImage;
    StyleImageView ivimg;
    mApplication mGlobal;
    String mImagename;
    Bitmap mBitmap1;
    DrawingView mainDrawingView;
    Bitmap original;
    SeekBar overlaymainimgseekbar;
    ImageView paintSelect;
    LinearLayout rlDrawingView;
    float rotation = 0.0f;
    DiscreteSeekBar seekbarBrightness;
    DiscreteSeekBar seekbarContrast;
    DiscreteSeekBar seekbarSaturation;
    RelativeLayout seekbarbrightnessclose;
    RelativeLayout seekbarbrightnesslayout;
    RelativeLayout seekbarcontrastclose;
    RelativeLayout seekbarcontrastlayout;
    RelativeLayout seekbardrawingstroke;
    RelativeLayout seekbarmainimgopacityclose;
    RelativeLayout seekbarmainopacitylayout;
    RelativeLayout seekbaropacitylayout;
    RelativeLayout seekbarsaturationclose;
    RelativeLayout seekbarsaturationlayout;
    int shadowColor = ViewCompat.MEASURED_STATE_MASK;
    int shadowProg = 0;
    ImageView stickerSelect;
    RelativeLayout stickerclose;
    RecyclerView stickerrecycler;
    SeekBar strokeseekbar;
    int tAlpha = 100;
    int tColor = -1;
    File tempfilepath;
    ImageView textSelect;
    boolean touchChange = false;
    boolean is_photo = false;
    private CustomTextView txtremoveoverlay;
    int width;

    public void mo2684a(View view, Uri uri) {
        Log.d("", "");
    }

    public void mo2685l() {
        Log.d("", "");
    }

    public void onEdit(View view, Uri uri) {
        Log.d("", "");
    }

    public void onRotateDown(View view) {

        Log.d("", "");
    }

    public void onRotateMove(View view) {
        Log.d("", "");
    }

    public void onRotateUp(View view) {
        Log.d("", "");
    }

    public void onScaleDown(View view) {
        Log.d("", "");
    }

    public void onScaleMove(View view) {
        Log.d("", "");
    }

    public void onScaleUp(View view) {
        Log.d("", "");
    }

    public void onTouchMove(View view) {
        Log.d("", "");
    }

    public void onTouchUp(View view) {
        Log.d("", "");
    }


    @Override
    @SuppressLint({"WrongConstant"})
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.photoeditor_activity);
        getWindow().setFlags(1024, 1024);
        this.mGlobal = (mApplication) getApplication();


        AdView mAdView = findViewById(R.id.adView);
        Ad_class.Show_banner(mAdView);

        ll_empty_photo = findViewById(R.id.ll_empty_photo);
        ll_load_photo = findViewById(R.id.ll_load_photo);

        this.mImagename = getIntent().getStringExtra("img");
        RequestBuilder<Bitmap> asBitmap = Glide.with((FragmentActivity) this).asBitmap();
        ((RequestBuilder) ((RequestBuilder) asBitmap.load(Utils.SAVED_IMG_PATH + "" + this.mImagename).diskCacheStrategy(DiskCacheStrategy.NONE)).skipMemoryCache(true)).into(new C13331());

        btnAddPhoto = findViewById(R.id.btn_add_image);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(Utils.MIME_TYPE_IMAGE);
                PhotoEditorActivity.this.startActivityForResult(Intent.createChooser(intent, "Select File"), PhotoEditorActivity.SELECT_FILE);
            }
        });

        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.onBackPressed();
            }
        });
        this.rlLayout = (RelativeLayout) findViewById(R.id.Rl_Layout);
        this.ivimg = (StyleImageView) findViewById(R.id.ivimg);
        this.mainDrawingView = (DrawingView) findViewById(R.id.main_drawing_view);
        this.imageoverlay = (ImageView) findViewById(R.id.imageoverlay);
        this.ivDrawImage = (ImageView) findViewById(R.id.ivDrawImage);
        this.imgStrokeclose = (ImageView) findViewById(R.id.img_strokeclose);
        this.adjustLayout = (LinearLayout) findViewById(R.id.adjust_layout);
        this.rlDrawingView = (LinearLayout) findViewById(R.id.rlDrawingView);
        this.imgSave = (ImageView) findViewById(R.id.img_save);
        this.btnCamera = (LinearLayout) findViewById(R.id.btn_camera);
        this.btnGallery = (LinearLayout) findViewById(R.id.btn_gallery);
        this.btnFilter = (LinearLayout) findViewById(R.id.btn_filter);
        this.btnEffect = (LinearLayout) findViewById(R.id.btn_effect);
        this.btnAdjust = (LinearLayout) findViewById(R.id.btn_adjust);
        this.btnSticker = (LinearLayout) findViewById(R.id.btn_sticker);
        this.btnText = (LinearLayout) findViewById(R.id.btn_text);
        this.btnPaint = (LinearLayout) findViewById(R.id.btn_paint);
        this.btnDrawingcolor = (LinearLayout) findViewById(R.id.btn_drawingcolor);
        this.btnDrawingstroke = (LinearLayout) findViewById(R.id.btn_drawingstroke);
        this.btnDrawingundo = (LinearLayout) findViewById(R.id.btn_drawingundo);
        this.btnDrawingredo = (LinearLayout) findViewById(R.id.btn_drawingredo);
        this.btnDrawingerase = (LinearLayout) findViewById(R.id.btn_drawingerase);
//        this.cameraSelect = (ImageView) findViewById(R.id.camera_select);
//        this.galleryselect = (ImageView) findViewById(R.id.gallery_select);
//        this.filterSelect = (ImageView) findViewById(R.id.filter_select);
//        this.effectSelect = (ImageView) findViewById(R.id.effect_select);
//        this.adjustSelect = (ImageView) findViewById(R.id.adjust_select);
        this.stickerSelect = (ImageView) findViewById(R.id.sticker_select);
        this.textSelect = (ImageView) findViewById(R.id.text_select);
        this.paintSelect = (ImageView) findViewById(R.id.paint_select);
        this.btnRotate = (LinearLayout) findViewById(R.id.btn_rotate);
        this.btnOpacity = (LinearLayout) findViewById(R.id.btn_opacity);
        this.btnBrightness = (LinearLayout) findViewById(R.id.btn_brightness);
        this.btnContrast = (LinearLayout) findViewById(R.id.btn_contrast);
        this.btnSituration = (LinearLayout) findViewById(R.id.btn_situration);
        this.txtremoveoverlay = (CustomTextView) findViewById(R.id.txtremoveoverlay);
        this.seekbardrawingstroke = (RelativeLayout) findViewById(R.id.seekbardrawingstroke);
        this.seekbarmainopacitylayout = (RelativeLayout) findViewById(R.id.seekbarmainopacitylayout);
        this.stickerclose = (RelativeLayout) findViewById(R.id.stickerclose);
        this.seekbarmainimgopacityclose = (RelativeLayout) findViewById(R.id.seekbarmainimgopacityclose);
        this.seekbarsaturationlayout = (RelativeLayout) findViewById(R.id.seekbarsaturationlayout);
        this.seekbarcontrastlayout = (RelativeLayout) findViewById(R.id.seekbarcontrastlayout);
        this.seekbarbrightnesslayout = (RelativeLayout) findViewById(R.id.seekbarbrightnesslayout);
        this.seekbaropacitylayout = (RelativeLayout) findViewById(R.id.seekbaropacitylayout);
        this.seekbarsaturationclose = (RelativeLayout) findViewById(R.id.seekbarsaturationclose);
        this.seekbarcontrastclose = (RelativeLayout) findViewById(R.id.seekbarcontrastclose);
        this.seekbarbrightnessclose = (RelativeLayout) findViewById(R.id.seekbarbrightnessclose);
        this.effectrecycler = (RecyclerView) findViewById(R.id.effectrecycler);
        EffectListAdapter effectListAdapter = new EffectListAdapter(this.effectImageArray, this);
        this.effectrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.effectrecycler.setAdapter(effectListAdapter);
        this.stickerrecycler = (RecyclerView) findViewById(R.id.stickerrecycler);
        this.stickerrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        bindsticker();
        this.strokeseekbar = (SeekBar) findViewById(R.id.strokeseekbar);
        this.strokeseekbar.setMax(50);
        this.strokeseekbar.setProgress(10);
        this.strokeseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("", "");
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("", "");

            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                PhotoEditorActivity.this.mainDrawingView.setPaintStrokeWidth(i);
            }
        });
        this.seekbarSaturation = (DiscreteSeekBar) findViewById(R.id.seekbarSaturation);
        this.seekbarSaturation.setProgress(115);
        this.seekbarSaturation.setOnProgressChangeListener(new C12155());
        this.seekbarContrast = (DiscreteSeekBar) findViewById(R.id.seekbarContrast);
        this.seekbarContrast.setProgress((int) (this.ivimg.getContrast() * 130.0f));
        this.seekbarContrast.setOnProgressChangeListener(new C12144());
        this.seekbarBrightness = (DiscreteSeekBar) findViewById(R.id.seekbarBrightness);
        this.seekbarBrightness.setProgress(this.ivimg.getBrightness() + 280);
        this.seekbarBrightness.setOnProgressChangeListener(new C12133());
        this.overlaymainimgseekbar = (SeekBar) findViewById(R.id.overlaymainimgseekbar);
        this.overlaymainimgseekbar.setProgress(100);
        this.overlaymainimgseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("", "");
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("", "");
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                PhotoEditorActivity.this.ivimg.setAlpha((seekBar.getProgress() * 255) / 100);
            }
        });
        this.effectseekbar = (SeekBar) findViewById(R.id.overlayseekbar);
        this.effectseekbar.setProgress(50);
        this.effectseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("", "");
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("", "");
            }


            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                PhotoEditorActivity.this.imageoverlay.setAlpha((seekBar.getProgress() * 255) / 100);
            }
        });
        RecyclerView recyclerView = this.stickerrecycler;
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemLongClick(View view, int i) {
                Log.d("", "");
            }

            public void onItemClick(View view, int i) {
                PhotoEditorActivity.this.m18062n();
                PhotoEditorActivity.this.m18039a(i - 1, PhotoEditorActivity.getBitmapFromAsset(PhotoEditorActivity.this.arraySticker.get(i).getDirName() + PhotoEditorActivity.this.arraySticker.get(i).getFileName(), PhotoEditorActivity.this));
            }
        }));
        this.btnCamera.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {

                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                PhotoEditorActivity.this.f81f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.jpg");
                if (Build.VERSION.SDK_INT < 24) {
                    intent.putExtra("output", Uri.fromFile(PhotoEditorActivity.this.f81f));
                } else {
                    Context applicationContext = PhotoEditorActivity.this.getApplicationContext();
                    intent.putExtra("output", FileProvider.getUriForFile(applicationContext, PhotoEditorActivity.this.getPackageName() + ".provider", PhotoEditorActivity.this.f81f));
                }
                intent.addFlags(1);
                if (intent.resolveActivity(PhotoEditorActivity.this.getPackageManager()) != null) {
                    PhotoEditorActivity.this.startActivityForResult(intent, 111);
                }
            }
        });
        this.btnGallery.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {

                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(Utils.MIME_TYPE_IMAGE);
                PhotoEditorActivity.this.startActivityForResult(Intent.createChooser(intent, "Select File"), PhotoEditorActivity.SELECT_FILE);
            }
        });
        this.btnFilter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                
                if(!is_photo){
                    Toast.makeText(PhotoEditorActivity.this, getResources().getString(R.string.require_photo), Toast.LENGTH_SHORT).show();
                    return;
                }
                

                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);

                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }

                PhotoEditorActivity.this.saveImageProgress();
                new DownloadFile().execute(PhotoEditorActivity.this.original);
            }
        });
        this.btnEffect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {

                if(!is_photo){
                    Toast.makeText(PhotoEditorActivity.this, getResources().getString(R.string.require_photo), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (PhotoEditorActivity.this.effectrecycler.getVisibility() == View.GONE) {
                    PhotoEditorActivity.this.effectrecycler.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.VISIBLE);

                    PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                    PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                    PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                    PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                    if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                        PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);

                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnAdjust.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                if (PhotoEditorActivity.this.adjustLayout.getVisibility() == View.GONE) {

                    if(!is_photo){
                        Toast.makeText(PhotoEditorActivity.this, getResources().getString(R.string.require_photo), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    PhotoEditorActivity.this.adjustLayout.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                    PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                    PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                    PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                    if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                        PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }

                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnSticker.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                if (PhotoEditorActivity.this.stickerrecycler.getVisibility() == View.GONE) {

                    if(!is_photo){
                        Toast.makeText(PhotoEditorActivity.this, getResources().getString(R.string.require_photo), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.stickerrecycler.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.stickerclose.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                    PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                    if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                        PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
//                PhotoEditorActivity.this.cameraSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.galleryselect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.filterSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.effectSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.adjustSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.stickerSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.textSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.paintSelect.setVisibility(View.GONE);
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {

                if(!is_photo){
                    Toast.makeText(PhotoEditorActivity.this, getResources().getString(R.string.require_photo), Toast.LENGTH_SHORT).show();
                    return;
                }

                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }
                PhotoEditorActivity.this.m18062n();
                PhotoEditorActivity.this.openTextActivity();
            }
        });
        this.btnPaint.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                if (PhotoEditorActivity.this.rlDrawingView.getVisibility() == View.GONE) {

                    if(!is_photo){
                        Toast.makeText(PhotoEditorActivity.this, getResources().getString(R.string.require_photo), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                    PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                    PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                    PhotoEditorActivity.this.rlDrawingView.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                    if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                        PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
//                PhotoEditorActivity.this.cameraSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.galleryselect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.filterSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.effectSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.adjustSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.stickerSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.textSelect.setVisibility(View.GONE);
//                PhotoEditorActivity.this.paintSelect.setVisibility(View.GONE);
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.effectrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbaropacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.mainDrawingView.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                if (PhotoEditorActivity.this.mainDrawingView.getWidth() > 0 || PhotoEditorActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoEditorActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoEditorActivity.this.mainDrawingView.layout(0, 0, PhotoEditorActivity.this.mainDrawingView.getWidth(), PhotoEditorActivity.this.mainDrawingView.getHeight());
                    PhotoEditorActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoEditorActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoEditorActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnDrawingcolor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoEditorActivity photoEditorActivity = PhotoEditorActivity.this;
                new AmbilWarnaDialog(photoEditorActivity, photoEditorActivity.initialColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                        Log.d("", "");
                    }

                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                        PhotoEditorActivity.this.initialColor = i;
                        PhotoEditorActivity.this.mainDrawingView.setPaintColor(i);
                    }
                }).show();
            }
        });
        this.btnDrawingstroke.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                if (PhotoEditorActivity.this.seekbardrawingstroke.getVisibility() == View.GONE) {
                    PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.VISIBLE);
                    PhotoEditorActivity.this.rlDrawingView.setVisibility(View.GONE);
                    return;
                }
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.VISIBLE);
            }
        });
        this.btnDrawingundo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoEditorActivity.this.mainDrawingView.undo();
            }
        });
        this.btnDrawingredo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoEditorActivity.this.mainDrawingView.redo();
            }
        });
        this.btnDrawingerase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoEditorActivity.this.erasedialog();
            }
        });
        this.btnRotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.ivimg.setRotation(PhotoEditorActivity.this.ivimg.getRotation() + 90.0f);
                PhotoEditorActivity.this.imageoverlay.setRotation(PhotoEditorActivity.this.imageoverlay.getRotation() + 90.0f);
            }
        });
        this.btnOpacity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.VISIBLE);
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
            }
        });
        this.btnBrightness.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.VISIBLE);
            }
        });
        this.btnContrast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.VISIBLE);
            }
        });
        this.btnSituration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.VISIBLE);
            }
        });
        this.seekbarmainimgopacityclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.VISIBLE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
            }
        });
        this.seekbarsaturationclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.VISIBLE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
            }
        });
        this.seekbarcontrastclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.VISIBLE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
            }
        });
        this.seekbarbrightnessclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.adjustLayout.setVisibility(View.VISIBLE);
                PhotoEditorActivity.this.seekbarmainopacitylayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarbrightnesslayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarcontrastlayout.setVisibility(View.GONE);
                PhotoEditorActivity.this.seekbarsaturationlayout.setVisibility(View.GONE);
            }
        });
        this.txtremoveoverlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.imageoverlay.setImageBitmap((Bitmap) null);
            }
        });
        this.stickerclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoEditorActivity.this.stickerSelect.setVisibility(View.GONE);
            }
        });
        this.imgStrokeclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoEditorActivity.this.rlDrawingView.setVisibility(View.VISIBLE);
            }
        });
        this.imgSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Ad_class.showInterstitial(PhotoEditorActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        PhotoEditorActivity.this.m18062n();
                        new SaveImage().execute(new Object[0]);
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Ad_class.loadAd(this);
    }
    public void imageget(int i) {
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), this.effectImageArray[i]);
        this.effectseekbar.setProgress(50);
        this.imageoverlay.setAlpha(120);
        try {
            if (this.width == 0) {
                if (this.height == 0) {
                    View inflate = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    ((CustomTextView) inflate.findViewById(R.id.txt_toast)).setText("Please Select Photo ! After You Can Use Effect");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(80, 0, 150);
                    toast.setView(inflate);
                    toast.show();
                    return;
                }
            }
            this.imageoverlay.setImageBitmap(Bitmap.createScaledBitmap(decodeResource, this.width, this.height, false));
        } catch (Exception e) {
            View inflate2 = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            ((CustomTextView) inflate2.findViewById(R.id.txt_toast)).setText("Please Select Photo ! After You Can Use Effect");
            Toast toast2 = new Toast(getApplicationContext());
            toast2.setDuration(Toast.LENGTH_LONG);
            toast2.setGravity(80, 0, 150);
            toast2.setView(inflate2);
            toast2.show();
            e.printStackTrace();
        }
    }

    public void onDelete() {
        this.touchChange = false;
    }

    public void onDoubleTap() {
        doubleTabPrass();
    }

    public void onTouchDown(View view) {
        this.touchChange = false;
        m18062n();
    }

    class C12155 implements DiscreteSeekBar.OnProgressChangeListener {
        public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            Log.d("", "");
        }

        public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            Log.d("", "");
        }

        C12155() {
        }

        public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean z) {
            PhotoEditorActivity.this.ivimg.setMode(0).updateStyle();
            PhotoEditorActivity.this.ivimg.setSaturation(((float) i) / 100.0f).updateStyle();
        }
    }

    class C12144 implements DiscreteSeekBar.OnProgressChangeListener {
        public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            Log.d("", "");
        }

        public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            Log.d("", "");
        }

        C12144() {
        }

        public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean z) {
            PhotoEditorActivity.this.ivimg.setContrast(((float) i) / 100.0f).updateStyle();
        }
    }

    class C12133 implements DiscreteSeekBar.OnProgressChangeListener {
        public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            Log.d("","");
        }

        public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            Log.d("","");
        }

        C12133() {
        }

        public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean z) {
            Log.e("ddddddddd", "dddddddd" + i);


            PhotoEditorActivity.this.ivimg.setBrightness(i - 255).updateStyle();
        }
    }

    private class DownloadFile extends AsyncTask<Bitmap, Integer, String> {
        private DownloadFile() {
        }


        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }


        public String doInBackground(Bitmap... bitmapArr) {
            try {
                String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
                File file2 = new File(file + "/saved_images");
                file2.mkdirs();
                File file3 = new File(file2, "temp.jpg");
                if (file3.exists()) {
                    file3.delete();
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                    bitmapArr[0].compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PhotoEditorActivity.this.tempfilepath = file3;
                filtercommom.getInstance().path = file3.getAbsolutePath();


                Log.e("hhhhhhh", filtercommom.getInstance().path);
                return filtercommom.getInstance().path;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }


        @Override
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            try {


                Intent intent = new Intent(PhotoEditorActivity.this, EditActivity.class);
                intent.putExtra("path", filtercommom.getInstance().path);


                PhotoEditorActivity.this.startActivityForResult(intent, 86);
                PhotoEditorActivity.this.dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        PhotoEditorActivity.this.ivimg.setDrawingCacheEnabled(false);
                    }
                }, 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveImageProgress() {
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("Just Wait ! Photo Saving");
        this.dialog.setIndeterminate(false);
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
    }


    int i3;

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {

        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 111) {

                Log.e("hello", "kjfddsfjdshfksdhkfj");

                try {
                    this.mBitmap1 = BitmapFactory.decodeFile(this.f81f.getAbsolutePath());
                    Bitmap bitmap = this.mBitmap1;
                    double height2 = (double) this.mBitmap1.getHeight();
                    double width2 = (double) this.mBitmap1.getWidth();
                    Double.isNaN(width2);
                    Double.isNaN(height2);
                    this.mBitmap1 = Bitmap.createScaledBitmap(bitmap, CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION, (int) (height2 * (500.0d / width2)), true);
                    Matrix matrix = new Matrix();
                    matrix.postRotate((float) i3);
                    this.mGlobal.setImage(Bitmap.createBitmap(this.mBitmap1, 0, 0, this.mBitmap1.getWidth(), this.mBitmap1.getHeight(), matrix, true));
                    startCropActivity(Uri.fromFile(this.f81f));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } else if (i == SELECT_FILE) {
                try {
                    this.f81f = Utils.getFile(this, intent.getData());
                    Glide.with(getApplicationContext()).asBitmap().load(this.f81f).into(new SimpleTarget<Bitmap>(CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION, CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION) {


                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            PhotoEditorActivity.this.mGlobal.setImage(bitmap);
                            PhotoEditorActivity photoEditorActivity = PhotoEditorActivity.this;
                            photoEditorActivity.startCropActivity(Uri.fromFile(photoEditorActivity.f81f));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (i == 69) {
                handleCropResult(intent);
            } else if (i == 2) {
                Log.w(NotificationCompat.CATEGORY_MESSAGE, "requestCode 2");
            }
        }
        if (i2 == 96) {
            handleCropError(intent);
        }

        if (i == 86) {


            try {
                this.mBitmap1 = BitmapFactory.decodeFile(this.f81f.getAbsolutePath());
                Bitmap bitmap = this.mBitmap1;
                double height2 = (double) this.mBitmap1.getHeight();
                double width2 = (double) this.mBitmap1.getWidth();
                Double.isNaN(width2);
                Double.isNaN(height2);
                this.mBitmap1 = Bitmap.createScaledBitmap(bitmap, CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION, (int) (height2 * (500.0d / width2)), true);
                Matrix matrix = new Matrix();
                matrix.postRotate((float) i3);
                this.mGlobal.setImage(Bitmap.createBitmap(this.mBitmap1, 0, 0, this.mBitmap1.getWidth(), this.mBitmap1.getHeight(), matrix, true));
                startCropActivity(Uri.fromFile(this.f81f));
            } catch (Exception e3) {
                e3.printStackTrace();
            }


        }

        if (i == TEXT_ACTIVITY) {
            try {
                Bundle extras = intent.getExtras();
                TextInfo textInfo = new TextInfo();
                textInfo.setPOS_X((float) extras.getInt("X", 0));
                textInfo.setPOS_Y((float) extras.getInt("Y", 0));
                textInfo.setWIDTH(extras.getInt("wi", C2794d.m18425a(this, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION)));
                textInfo.setHEIGHT(extras.getInt("he", C2794d.m18425a(this, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION)));
                textInfo.setTEXT(extras.getString("text", ""));
                textInfo.setFONT_NAME(extras.getString("fontName", ""));
                textInfo.setTEXT_COLOR(extras.getInt("tColor", Color.parseColor("#4149b6")));
                textInfo.setTEXT_ALPHA(extras.getInt("tAlpha", 100));
                textInfo.setSHADOW_COLOR(extras.getInt("shadowColor", Color.parseColor("#7641b6")));
                textInfo.setSHADOW_PROG(extras.getInt("shadowProg", 5));
                textInfo.setBG_COLOR(extras.getInt("bgColor", 0));
                textInfo.setBG_DRAWABLE(extras.getString("bgDrawable", "0"));
                textInfo.setBG_ALPHA(extras.getInt("bgAlpha", 255));
                textInfo.setROTATION(extras.getFloat("rotation", 0.0f));
                textInfo.setFIELD_TWO(extras.getString("field_two", ""));
                this.fontName = extras.getString("fontName", "");
                this.tColor = extras.getInt("tColor", Color.parseColor("#4149b6"));
                this.shadowColor = extras.getInt("shadowColor", Color.parseColor("#7641b6"));
                this.shadowProg = extras.getInt("shadowProg", 0);
                this.tAlpha = extras.getInt("tAlpha", 100);
                this.bgDrawable = extras.getString("bgDrawable", "0");
                this.bgAlpha = extras.getInt("bgAlpha", 255);
                this.rotation = extras.getFloat("rotation", 0.0f);
                this.bgColor = extras.getInt("bgColor", 0);
                if (this.editMode) {
                    this.touchChange = false;
                    RelativeLayout relativeLayout = this.rlLayout;
                    ((AutoTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).setTextInfo(textInfo, false);
                    RelativeLayout relativeLayout2 = this.rlLayout;
                    ((AutoTextRel) relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1)).setBorderVisibility(true);
                    this.editMode = false;
                    return;
                }
                this.touchChange = true;
                AutoTextRel autoTextRel = new AutoTextRel(this);
                this.rlLayout.addView(autoTextRel);
                autoTextRel.setTextInfo(textInfo, false);
                autoTextRel.setOnTouchCallbackListener(this);
                autoTextRel.setBorderVisibility(true);


                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }

        } else {


        }

    }

    public void startCropActivity(@NonNull Uri uri) {
        File file = new File(Utils.SAVED_IMG_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.mImagename = "temp" + ".png";
        UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), "SampleCropImage.png"))).start(this);
    }

    @SuppressLint({"WrongConstant"})
    private void handleCropError(@NonNull Intent intent) {
        Throwable error = UCrop.getError(intent);
        if (error != null) {
            Toast.makeText(this, error.getMessage(), 1).show();
        } else {
            Toast.makeText(this, "Error", 1).show();
        }
    }

    @SuppressLint({"WrongConstant"})
    private void handleCropResult(@NonNull Intent intent) {
        Uri output = UCrop.getOutput(intent);
        if (output != null) {
            saveCroppedImage(output);
        } else {
            Toast.makeText(this, "Crop", 1).show();
        }
    }

    @SuppressLint({"WrongConstant"})
    private void saveCroppedImage(Uri uri) {
        if (uri == null || !uri.getScheme().equals("file")) {
            Toast.makeText(this, "Error", 1).show();
            return;
        }
        try {
            copyFileToDownloads(uri);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), 1).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            if (this.tempfilepath.exists()) {
                this.tempfilepath.delete();
            }
            if (filtercommom.getInstance().bitmap != null) {
                this.ivimg.setImageBitmap(filtercommom.getInstance().bitmap);
                filtercommom.getInstance().bitmap = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyFileToDownloads(Uri uri) throws Exception {
        File file = new File(Utils.SAVED_IMG_PATH, this.mImagename);
        if (file.exists()) {
            file.delete();
        }
        FileInputStream fileInputStream = new FileInputStream(new File(uri.getPath()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        channel.transferTo(0, channel.size(), fileOutputStream.getChannel());
        fileInputStream.close();
        fileOutputStream.close();
        if (Build.VERSION.SDK_INT >= 19) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)));
            sendBroadcast(intent);
        } else {
            sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))));
        }
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(file.getAbsolutePath()))));
        Intent intent2 = new Intent(this, PhotoEditorActivity.class);
        intent2.putExtra("img", this.mImagename);
        intent2.putExtra("iseffect", true);
        startActivityForResult(intent2, 2);
    }

    class C13331 extends SimpleTarget<Bitmap> {
        C13331() {
        }

        @Override
        public void onStart() {
            ll_load_photo.setVisibility(View.VISIBLE);
            super.onStart();
        }

        @Override
        public void onLoadStarted(@Nullable Drawable placeholder) {
            ll_load_photo.setVisibility(View.VISIBLE);
            super.onLoadStarted(placeholder);
        }

        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
            ll_empty_photo.setVisibility(View.GONE);
            ll_load_photo.setVisibility(View.GONE);
            is_photo = true;
            PhotoEditorActivity.this.ivimg.setImageBitmap(bitmap);
            PhotoEditorActivity photoEditorActivity = PhotoEditorActivity.this;
            photoEditorActivity.original = bitmap;
            photoEditorActivity.width = photoEditorActivity.original.getWidth();
            PhotoEditorActivity photoEditorActivity2 = PhotoEditorActivity.this;
            photoEditorActivity2.height = photoEditorActivity2.original.getHeight();
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            ll_empty_photo.setVisibility(View.VISIBLE);
            ll_load_photo.setVisibility(View.GONE);
            is_photo = false;
            super.onLoadFailed(errorDrawable);
        }
    }


    private class SaveImage extends AsyncTask<Object, Integer, String> {
        private SaveImage() {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            PhotoEditorActivity.this.saveImageProgress();
        }

        public String doInBackground(Object... objArr) {
            try {
                PhotoEditorActivity.this.mergeAndSave();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            PhotoEditorActivity.this.dialog.dismiss();
        }
    }

    private int generateRandomName(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    public void mergeAndSave() {
        Bitmap loadBitmapFromView = loadBitmapFromView(this.rlLayout);
        try {
            saveImageToSD(loadBitmapFromView, "photox_" + generateRandomName(1000000, 5000000) + ".png", Bitmap.CompressFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap loadBitmapFromView(View view) {
        try {
            if (view.getMeasuredHeight() <= 0) {
                view.measure(-2, -2);
                Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                view.draw(canvas);
                return createBitmap;
            }
            Bitmap createBitmap2 = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap2);
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            view.draw(canvas2);
            return createBitmap2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String saveImageToSD(Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat) {
        FileOutputStream fileOutputStream;
        try {
            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(compressFormat, 100, byteArrayOutputStream);
            File file2 = new File(file + "/SelfieCameraPhotoEditor/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            File file3 = new File(file2, str);
            try {
                file3.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream = new FileOutputStream(file3);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                fileOutputStream = null;
            }
            try {
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
                fileOutputStream.close();
                Log.e("Success", "Final Image Saved - " + str);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            try {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
                Intent intent = new Intent(this, PhotoShareActivity.class);
                intent.putExtra("FinalURI", "" + file + "/SelfieCameraPhotoEditor/" + str);
                startActivity(intent);
                ContentValues contentValues = new ContentValues();
                String format = new SimpleDateFormat("MMMM dd, yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse("04/05/2010"));
                contentValues.put("title", str);
                contentValues.put("_display_name", str);
                contentValues.put("description", str);
                contentValues.put("date_added", format);
                contentValues.put("datetaken", "");
                contentValues.put("date_modified", "");
                contentValues.put("mime_type", Utils.MIME_TYPE_IMAGE);
                contentValues.put("orientation", 0);
                File parentFile = file3.getParentFile();
                String lowerCase = parentFile.toString().toLowerCase();
                String lowerCase2 = parentFile.getName().toLowerCase();
                contentValues.put("bucket_id", Integer.valueOf(lowerCase.hashCode()));
                contentValues.put("bucket_display_name", lowerCase2);
                contentValues.put("_size", Long.valueOf(file3.length()));
                contentValues.put("_data", file3.getAbsolutePath());
                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                finish();
                return file3.getPath().toString();
            } catch (NullPointerException unused) {
                Log.e("error", "SAve to disk");
                return "";
            } catch (ParseException e4) {
                e4.printStackTrace();
                return "";
            }
        } catch (NullPointerException unused2) {
            Log.e("error", "SAve to disk");
            return "";
        }
    }

    public void m18039a(int i, Bitmap bitmap) {
        C2738b c2738b = new C2738b();
        c2738b.m18222a((float) ((this.rlLayout.getWidth() / 2) - C2794d.m18425a(this, 70)));
        c2738b.m18228b((float) ((this.rlLayout.getHeight() / 2) - C2794d.m18425a(this, 70)));
        c2738b.m18229b(C2794d.m18425a(this, 140));
        c2738b.m18233c(C2794d.m18425a(this, 140));
        c2738b.m18232c(0.0f);
        c2738b.m18224a(bitmap);
        c2738b.m18230b("white");
        c2738b.m18226a("STICKER");
        C2745c c2745c = new C2745c(this);
        c2745c.setComponentInfo(c2738b);
        this.rlLayout.addView(c2745c);
        c2745c.m18265a(this);
        c2745c.setBorderVisibility(true);
    }

    public void m18062n() {
        int childCount = this.rlLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.rlLayout.getChildAt(i);
            if (childAt instanceof AutoTextRel) {
                ((AutoTextRel) childAt).setBorderVisibility(false);
            }
            if (childAt instanceof C2745c) {
                ((C2745c) childAt).setBorderVisibility(false);
            }
        }
    }

    private void bindsticker() {
        try {
            String[] list = getApplicationContext().getResources().getAssets().list("neonsticker");
            if (list != null) {
                for (String color_Module : list) {
                    this.arraySticker.add(new Color_Module("neonsticker/", color_Module));
                }
                this.stickerrecycler.setAdapter(new StickerListAdapter(getApplicationContext(), this.arraySticker) {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openTextActivity() {
        Intent intent = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("X", (this.rlLayout.getWidth() / 2) - C2794d.m18425a(this, 100));
        bundle.putInt("Y", (this.rlLayout.getHeight() / 2) - C2794d.m18425a(this, 100));
        bundle.putInt("wi", C2794d.m18425a(this, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        bundle.putInt("he", C2794d.m18425a(this, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        bundle.putString("text", "");
        bundle.putString("fontName", this.fontName);
        bundle.putInt("tAlpha", this.tAlpha);
        bundle.putInt("shadowColor", this.shadowColor);
        bundle.putInt("shadowProg", this.shadowProg);
        bundle.putString("bgDrawable", this.bgDrawable);
        bundle.putInt("bgColor", this.bgColor);
        bundle.putInt("bgAlpha", this.bgAlpha);
        bundle.putFloat("rotation", this.rotation);
        bundle.putString("view", "mosaic");
        intent.putExtras(bundle);
        startActivityForResult(intent, TEXT_ACTIVITY);
    }

    private void doubleTabPrass() {
        removeImageViewControll();
        this.editMode = true;
        RelativeLayout relativeLayout = this.rlLayout;
        TextInfo textInfo = ((AutoTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).getTextInfo();
        Intent intent = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("X", (int) textInfo.getPOS_X());
        bundle.putInt("Y", (int) textInfo.getPOS_Y());
        bundle.putInt("wi", textInfo.getWIDTH());
        bundle.putInt("he", textInfo.getHEIGHT());
        bundle.putString("text", textInfo.getTEXT());
        bundle.putString("fontName", textInfo.getFONT_NAME());
        bundle.putInt("tColor", textInfo.getTEXT_COLOR());
        bundle.putInt("tAlpha", textInfo.getTEXT_ALPHA());
        bundle.putInt("shadowColor", textInfo.getSHADOW_COLOR());
        bundle.putInt("shadowProg", textInfo.getSHADOW_PROG());
        bundle.putString("bgDrawable", textInfo.getBG_DRAWABLE());
        bundle.putInt("bgColor", textInfo.getBG_COLOR());
        bundle.putInt("bgAlpha", textInfo.getBG_ALPHA());
        bundle.putFloat("rotation", textInfo.getROTATION());
        intent.putExtras(bundle);
        startActivityForResult(intent, TEXT_ACTIVITY);
    }

    public void removeImageViewControll() {
        int childCount = this.rlLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.rlLayout.getChildAt(i);
            if (childAt instanceof AutoTextRel) {
                ((AutoTextRel) childAt).setBorderVisibility(false);
            }
        }
    }

    public static Bitmap getBitmapFromAsset(String str, Context context) {
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(str);
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = null;
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    public void erasedialog() {
        final Dialog dialog2 = new Dialog(this);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setContentView(R.layout.erase_dialog);
        ((CustomTextView) dialog2.findViewById(R.id.txt_no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        ((CustomTextView) dialog2.findViewById(R.id.txt_yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity.this.mainDrawingView.clearCanvas();
                PhotoEditorActivity.this.ivDrawImage.setImageBitmap((Bitmap) null);
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    @Override
    public void onBackPressed() {
        backdialog();
    }

    public void backdialog() {
        final Dialog dialog2 = new Dialog(this);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setContentView(R.layout.dialog_back);
        dialog2.setCancelable(false);
        ((CustomTextView) dialog2.findViewById(R.id.txt_no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        ((CustomTextView) dialog2.findViewById(R.id.txt_yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoEditorActivity photoEditorActivity = PhotoEditorActivity.this;
                photoEditorActivity.startActivity(new Intent(photoEditorActivity.getApplicationContext(), MainActivity.class));
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }
}

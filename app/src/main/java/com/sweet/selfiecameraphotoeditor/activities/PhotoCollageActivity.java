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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.adapter.BackgroundAdapter;
import com.sweet.selfiecameraphotoeditor.adapter.CollageLayoutAdapter;
import com.sweet.selfiecameraphotoeditor.adapter.ColorAdapter;
import com.sweet.selfiecameraphotoeditor.adapter.StickerListAdapter;
import com.sweet.selfiecameraphotoeditor.collageutils.ImageUtils;
import com.sweet.selfiecameraphotoeditor.collageutils.PhotoItem;
import com.sweet.selfiecameraphotoeditor.collageutils.TemplateItem;
import com.sweet.selfiecameraphotoeditor.collageutils.collageviews.FrameImageUtils;
import com.sweet.selfiecameraphotoeditor.collageutils.custom.PhotoView;
import com.sweet.selfiecameraphotoeditor.collageutils.layouts.FramePhotoLayout;
import com.sweet.selfiecameraphotoeditor.common.Constant;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.common.DrawingView;
import com.sweet.selfiecameraphotoeditor.common.Utils;
import com.sweet.selfiecameraphotoeditor.common.mApplication;
import com.sweet.selfiecameraphotoeditor.model.Drawables;
import com.sweet.selfiecameraphotoeditor.stickerview.AutoTextRel;
import com.sweet.selfiecameraphotoeditor.stickerview.Color_Module;
import com.sweet.selfiecameraphotoeditor.stickerview.RecyclerItemClickListener;
import com.sweet.selfiecameraphotoeditor.stickerview.TextInfo;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2738b;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2745c;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2794d;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import io.reactivex.functions.Consumer;
import yuku.ambilwarna.AmbilWarnaDialog;

public class PhotoCollageActivity extends AppCompatActivity implements CollageLayoutAdapter.OnPreviewTemplateClickListener, C2745c.C2638f, AutoTextRel.TouchEventListener, ColorAdapter.AdapterCallback, BackgroundAdapter.AdapterfilterCallback {
    private static final float DEFAULT_SPACE = ImageUtils.pxFromDp(mApplication.getContext(), 2.0f);

    public static final float MAX_CORNER = ImageUtils.pxFromDp(mApplication.getContext(), 60.0f);
    private static final float MAX_CORNER_PROGRESS = 200.0f;

    public static final float MAX_SPACE = ImageUtils.pxFromDp(mApplication.getContext(), 30.0f);
    private static final float MAX_SPACE_PROGRESS = 300.0f;
    protected static final int RATIO_GOLDEN = 2;
    protected static final int RATIO_SQUARE = 0;
    private static final int TEXT_ACTIVITY = 909;
    int[] colors;
    ArrayList<Color_Module> arraySticker = new ArrayList<>();
    int backPos = 0;
    ArrayList<Drawables> backgroundList = new ArrayList<>();
    RelativeLayout backgroundLayout;
    ImageView backgroundSelect;
    RelativeLayout backgroundlistclose;
    int bgAlpha = 0;
    int bgColor = 0;
    String bgDrawable = "0";
    LinearLayout btnBackground;
    LinearLayout btnControl;
    LinearLayout btnDrawingcolor;
    LinearLayout btnDrawingerase;
    LinearLayout btnDrawingredo;
    LinearLayout btnDrawingstroke;
    LinearLayout btnDrawingundo;
    LinearLayout btnLayout;
    LinearLayout btnPaint;
    LinearLayout btnSticker;
    LinearLayout btnText;
    RelativeLayout colorlistclose;
    ImageView controlSelect;
    SeekBar cornerseekbar;
    ProgressDialog dialog;
    private boolean editMode = false;
    String fontName = "";
    ImageView imageView;
    ImageView imgBackgroundlist1;
    ImageView imgBackgroundlist2;
    ImageView imgBackgroundlist3;
    ImageView imgBackgroundlist4;
    ImageView imgBackgroundlist5;
    ImageView imgBackgroundlist6;
    ImageView imgcolorlist;
    ImageView imgnobackground;
    ImageView imgpickimage;
    ImageView imgsave;
    ImageView imgStrokeclose;
    int initialColor = -1;
    ImageView ivDrawImage;
    ImageView layoutSelect;
    private ArrayList<TemplateItem> mAllTemplateItemList = new ArrayList<>();
    RelativeLayout mContainerLayout;

    public float mCorner = 0.0f;

    public FramePhotoLayout mFramePhotoLayout;
    private int mImageInTemplateCount = 0;
    protected int mLayoutRatio = 0;
    protected float mOutputScale = 1.0f;
    protected PhotoView mPhotoView;
    private Bundle mSavedInstanceState;
    protected TemplateItem mSelectedTemplateItem;

    public float mSpace = DEFAULT_SPACE;
    CollageLayoutAdapter mTemplateAdapter;
    private ArrayList<TemplateItem> mTemplateItemList = new ArrayList<>();
    DrawingView mainDrawingView;
    ImageView paintSelect;
    ArrayList<String> pathList = new ArrayList<>();
    LinearLayout rlDrawingView;
    RelativeLayout rlMainScreen;
    float rotation = 0.0f;
    RecyclerView rvBackgroundList;
    RecyclerView rvCollegeList;
    RecyclerView rvColorList;
    LinearLayout seekbarcontrollayout;
    RelativeLayout seekbardrawingstroke;
    private int selectedItemIndex = 0;
    int shadowColor = ViewCompat.MEASURED_STATE_MASK;
    int shadowProg = 0;
    SeekBar spaceseekbar;
    ImageView stickerselect;
    RelativeLayout stickerclose;
    RecyclerView stickerrecycler;
    SeekBar strokeseekbar;
    int tAlpha = 100;
    int tColor = -1;
    ImageView textSelect;
    boolean touchChange = false;

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
        setContentView((int) R.layout.photocollage_activity);
        getWindow().setFlags(1024, 1024);


        AdView mAdView = findViewById(R.id.adView);
        Ad_class.Show_banner(mAdView);

        this.mSavedInstanceState = bundle;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.pathList = extras.getStringArrayList("imageList");
            this.mImageInTemplateCount = this.pathList.size();
        }
        this.imageView = (ImageView) findViewById(R.id.img_back);
        this.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.onBackPressed();
            }
        });
        Constant.getInstance();
        this.colors = Constant.COLORS;
        this.rlMainScreen = (RelativeLayout) findViewById(R.id.rlMainScreen);
        this.mContainerLayout = (RelativeLayout) findViewById(R.id.mContainerLayout);
        this.mAllTemplateItemList.addAll(FrameImageUtils.loadFrameImages(this));
        this.mTemplateItemList.clear();
        if (this.mImageInTemplateCount > 0) {
            Iterator<TemplateItem> it2 = this.mAllTemplateItemList.iterator();
            while (it2.hasNext()) {
                TemplateItem next = it2.next();
                if (next.getPhotoItemList().size() == this.mImageInTemplateCount) {
                    this.mTemplateItemList.add(next);
                }
            }
        } else {
            this.mTemplateItemList.addAll(this.mAllTemplateItemList);
        }
        this.mSelectedTemplateItem = this.mTemplateItemList.get(this.selectedItemIndex);
        this.mSelectedTemplateItem.setSelected(true);
        ArrayList<String> arrayList = this.pathList;
        if (arrayList != null) {
            int min = Math.min(arrayList.size(), this.mSelectedTemplateItem.getPhotoItemList().size());
            for (int i = 0; i < min; i++) {
                this.mSelectedTemplateItem.getPhotoItemList().get(i).imagePath = this.pathList.get(i);
            }
        }
        this.mainDrawingView = (DrawingView) findViewById(R.id.main_drawing_view);
        this.mPhotoView = new PhotoView(this);
        this.rvCollegeList = (RecyclerView) findViewById(R.id.rvCollegeList);
        this.rvColorList = (RecyclerView) findViewById(R.id.rvColorList);
        this.rvBackgroundList = (RecyclerView) findViewById(R.id.rvBackgroundList);
        this.rvBackgroundList.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.rvBackgroundList.setHasFixedSize(true);
        this.rvBackgroundList.setAdapter(new BackgroundAdapter(this, backgroundListOne(), "Background"));
        this.seekbardrawingstroke = (RelativeLayout) findViewById(R.id.seekbardrawingstroke);
        this.stickerrecycler = (RecyclerView) findViewById(R.id.stickerrecycler);
        this.stickerrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.stickerclose = (RelativeLayout) findViewById(R.id.stickerclose);
        this.colorlistclose = (RelativeLayout) findViewById(R.id.colorlistclose);
        this.backgroundlistclose = (RelativeLayout) findViewById(R.id.backgroundlistclose);
        bindsticker();
        this.mTemplateAdapter = new CollageLayoutAdapter(this.mTemplateItemList, this);
        this.rvCollegeList.setHasFixedSize(true);
        this.rvCollegeList.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.rvCollegeList.setAdapter(this.mTemplateAdapter);
        this.mContainerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                photoCollageActivity.mOutputScale = ImageUtils.calculateOutputScaleFactor(photoCollageActivity.mContainerLayout.getWidth(), PhotoCollageActivity.this.mContainerLayout.getHeight());
                PhotoCollageActivity photoCollageActivity2 = PhotoCollageActivity.this;
                photoCollageActivity2.buildLayout(photoCollageActivity2.mSelectedTemplateItem);
                if (Build.VERSION.SDK_INT >= 16) {
                    PhotoCollageActivity.this.mContainerLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    PhotoCollageActivity.this.mContainerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        RecyclerView recyclerView = this.stickerrecycler;
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemLongClick(View view, int i) {
                Log.d("","");
            }

            public void onItemClick(View view, int i) {
                PhotoCollageActivity.this.m18062n();
                PhotoCollageActivity.this.m18039a(i - 1, PhotoCollageActivity.getBitmapFromAsset(PhotoCollageActivity.this.arraySticker.get(i).getDirName() + PhotoCollageActivity.this.arraySticker.get(i).getFileName(), PhotoCollageActivity.this));
            }
        }));
        this.imgsave = (ImageView) findViewById(R.id.img_save);
        this.rlDrawingView = (LinearLayout) findViewById(R.id.rlDrawingView);
        this.ivDrawImage = (ImageView) findViewById(R.id.ivDrawImage);
        this.imgStrokeclose = (ImageView) findViewById(R.id.img_strokeclose);
        this.seekbarcontrollayout = (LinearLayout) findViewById(R.id.seekbarcontrollayout);
        this.cornerseekbar = (SeekBar) findViewById(R.id.cornerseekbar);
        this.cornerseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("","");
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("","");
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
           PhotoCollageActivity.this.mCorner = (PhotoCollageActivity.MAX_CORNER * ((float) seekBar.getProgress())) / PhotoCollageActivity.MAX_CORNER_PROGRESS;
                if (PhotoCollageActivity.this.mFramePhotoLayout != null) {
                    PhotoCollageActivity.this.mFramePhotoLayout.setSpace(PhotoCollageActivity.this.mSpace, PhotoCollageActivity.this.mCorner);
                }
            }
        });
        this.spaceseekbar = (SeekBar) findViewById(R.id.spaceseekbar);
        this.spaceseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("","");
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("","");
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                 PhotoCollageActivity.this.mSpace = (PhotoCollageActivity.MAX_SPACE * ((float) seekBar.getProgress())) / PhotoCollageActivity.MAX_SPACE_PROGRESS;
                if (PhotoCollageActivity.this.mFramePhotoLayout != null) {
                    PhotoCollageActivity.this.mFramePhotoLayout.setSpace(PhotoCollageActivity.this.mSpace, PhotoCollageActivity.this.mCorner);
                }
            }
        });
        this.strokeseekbar = (SeekBar) findViewById(R.id.strokeseekbar);
        this.strokeseekbar.setMax(50);
        this.strokeseekbar.setProgress(10);
        this.strokeseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("","");
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("","");
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                PhotoCollageActivity.this.mainDrawingView.setPaintStrokeWidth(i);
            }
        });
        this.backgroundLayout = (RelativeLayout) findViewById(R.id.background_layout);
        this.btnLayout = (LinearLayout) findViewById(R.id.btn_layout);
        this.btnControl = (LinearLayout) findViewById(R.id.btn_control);
        this.btnBackground = (LinearLayout) findViewById(R.id.btn_background);
        this.btnSticker = (LinearLayout) findViewById(R.id.btn_sticker);
        this.btnText = (LinearLayout) findViewById(R.id.btn_text);
        this.btnPaint = (LinearLayout) findViewById(R.id.btn_paint);
        this.layoutSelect = (ImageView) findViewById(R.id.layout_select);
        this.controlSelect = (ImageView) findViewById(R.id.control_select);
        this.backgroundSelect = (ImageView) findViewById(R.id.background_select);
        this.stickerselect = (ImageView) findViewById(R.id.sticker_select);
        this.textSelect = (ImageView) findViewById(R.id.text_select);
        this.paintSelect = (ImageView) findViewById(R.id.paint_select);
        if (this.rvCollegeList.getVisibility() == View.GONE) {
            this.rvCollegeList.setVisibility(View.VISIBLE);
            this.layoutSelect.setVisibility(View.VISIBLE);
            this.controlSelect.setVisibility(View.GONE);
            this.backgroundSelect.setVisibility(View.GONE);
            this.stickerselect.setVisibility(View.GONE);
            this.textSelect.setVisibility(View.GONE);
            this.paintSelect.setVisibility(View.GONE);
        }
        this.btnDrawingcolor = (LinearLayout) findViewById(R.id.btn_drawingcolor);
        this.btnDrawingstroke = (LinearLayout) findViewById(R.id.btn_drawingstroke);
        this.btnDrawingundo = (LinearLayout) findViewById(R.id.btn_drawingundo);
        this.btnDrawingredo = (LinearLayout) findViewById(R.id.btn_drawingredo);
        this.btnDrawingerase = (LinearLayout) findViewById(R.id.btn_drawingerase);
        this.btnLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (PhotoCollageActivity.this.rvCollegeList.getVisibility() == View.GONE) {
                    PhotoCollageActivity.this.rvCollegeList.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.layoutSelect.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                    if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                        PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
                PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                    PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnControl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (PhotoCollageActivity.this.seekbarcontrollayout.getVisibility() == View.GONE) {
                    PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.controlSelect.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                    if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                        PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
                PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                    PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnBackground.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (PhotoCollageActivity.this.backgroundLayout.getVisibility() == View.GONE) {
                    PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundLayout.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundSelect.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                    if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                        PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
                PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                    PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnSticker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (PhotoCollageActivity.this.stickerrecycler.getVisibility() == View.GONE) {
                    PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerrecycler.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.stickerclose.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerselect.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                    if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                        PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
                PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                    PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.btnText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                    PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                }
                PhotoCollageActivity.this.m18062n();
                PhotoCollageActivity.this.openTextActivity();
            }
        });
        this.btnPaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (PhotoCollageActivity.this.rlDrawingView.getVisibility() == View.GONE) {
                    PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rlDrawingView.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.mainDrawingView.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                    PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                    PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                    PhotoCollageActivity.this.paintSelect.setVisibility(View.VISIBLE);
                    if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                        PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                        PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                        PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                        PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                        PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                        return;
                    }
                    return;
                }
                PhotoCollageActivity.this.rvCollegeList.setVisibility(View.GONE);
                PhotoCollageActivity.this.seekbarcontrollayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.mainDrawingView.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.layoutSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.controlSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
                PhotoCollageActivity.this.textSelect.setVisibility(View.GONE);
                PhotoCollageActivity.this.paintSelect.setVisibility(View.GONE);
                if (PhotoCollageActivity.this.mainDrawingView.getWidth() > 0 || PhotoCollageActivity.this.mainDrawingView.getHeight() > 0) {
                    PhotoCollageActivity.this.ivDrawImage.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.mainDrawingView.setDrawingCacheEnabled(true);
                    PhotoCollageActivity.this.mainDrawingView.layout(0, 0, PhotoCollageActivity.this.mainDrawingView.getWidth(), PhotoCollageActivity.this.mainDrawingView.getHeight());
                    PhotoCollageActivity.this.mainDrawingView.buildDrawingCache();
                    PhotoCollageActivity.this.ivDrawImage.setImageBitmap(Bitmap.createBitmap(PhotoCollageActivity.this.mainDrawingView.getDrawingCache()));
                }
            }
        });
        this.imgpickimage = (ImageView) findViewById(R.id.img_pickimage);
        this.imgnobackground = (ImageView) findViewById(R.id.img_nobackground);
        this.imgcolorlist = (ImageView) findViewById(R.id.img_colorlist);
        this.imgBackgroundlist1 = (ImageView) findViewById(R.id.img_backgroundlist1);
        this.imgBackgroundlist2 = (ImageView) findViewById(R.id.img_backgroundlist2);
        this.imgBackgroundlist3 = (ImageView) findViewById(R.id.img_backgroundlist3);
        this.imgBackgroundlist4 = (ImageView) findViewById(R.id.img_backgroundlist4);
        this.imgBackgroundlist5 = (ImageView) findViewById(R.id.img_backgroundlist5);
        this.imgBackgroundlist6 = (ImageView) findViewById(R.id.img_backgroundlist6);
        this.imgpickimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RxImagePicker.with(PhotoCollageActivity.this).requestImage(Sources.GALLERY).subscribe(new Consumer<Uri>() {
                    public void accept(@NonNull Uri uri) throws Exception {
                        try {
                            PhotoCollageActivity.this.mContainerLayout.setBackground(new BitmapDrawable(PhotoCollageActivity.this.getResources(), MediaStore.Images.Media.getBitmap(PhotoCollageActivity.this.getContentResolver(), uri)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        this.imgnobackground.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.mContainerLayout.invalidate();
                PhotoCollageActivity.this.mContainerLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            }
        });
        this.imgcolorlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.rvColorList.setLayoutManager(new LinearLayoutManager(PhotoCollageActivity.this, 0, false));
                PhotoCollageActivity.this.rvColorList.setHasFixedSize(true);
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                PhotoCollageActivity.this.rvColorList.setAdapter(new ColorAdapter(photoCollageActivity, photoCollageActivity.colors));
                PhotoCollageActivity.this.rvColorList.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
            }
        });
        this.imgBackgroundlist1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundList.clear();
                PhotoCollageActivity.this.backgroundList.addAll(PhotoCollageActivity.this.backgroundListOne());
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                PhotoCollageActivity.this.rvBackgroundList.setAdapter(new BackgroundAdapter(photoCollageActivity, photoCollageActivity.backgroundList, "Background"));
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
            }
        });
        this.imgBackgroundlist2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundList.clear();
                PhotoCollageActivity.this.backgroundList.addAll(PhotoCollageActivity.this.backgroundListTwo());
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                PhotoCollageActivity.this.rvBackgroundList.setAdapter(new BackgroundAdapter(photoCollageActivity, photoCollageActivity.backgroundList, "Background"));
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
            }
        });
        this.imgBackgroundlist3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundList.clear();
                PhotoCollageActivity.this.backgroundList.addAll(PhotoCollageActivity.this.backgroundListThree());
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                PhotoCollageActivity.this.rvBackgroundList.setAdapter(new BackgroundAdapter(photoCollageActivity, photoCollageActivity.backgroundList, "Background"));
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
            }
        });
        this.imgBackgroundlist4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundList.clear();
                PhotoCollageActivity.this.backgroundList.addAll(PhotoCollageActivity.this.backgroundListFour());
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                PhotoCollageActivity.this.rvBackgroundList.setAdapter(new BackgroundAdapter(photoCollageActivity, photoCollageActivity.backgroundList, "Background"));
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
            }
        });
        this.imgBackgroundlist5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundList.clear();
                PhotoCollageActivity.this.backgroundList.addAll(PhotoCollageActivity.this.backgroundListFive());
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                PhotoCollageActivity.this.rvBackgroundList.setAdapter(new BackgroundAdapter(photoCollageActivity, photoCollageActivity.backgroundList, "Background"));
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
            }
        });
        this.imgBackgroundlist6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundList.clear();
                PhotoCollageActivity.this.backgroundList.addAll(PhotoCollageActivity.this.backgroundListSix());
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                PhotoCollageActivity.this.rvBackgroundList.setAdapter(new BackgroundAdapter(photoCollageActivity, photoCollageActivity.backgroundList, "Background"));
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.VISIBLE);
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
            }
        });
        this.btnDrawingcolor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                new AmbilWarnaDialog(photoCollageActivity, photoCollageActivity.initialColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                        Log.d("","");
                    }

                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                        PhotoCollageActivity.this.initialColor = i;
                        PhotoCollageActivity.this.mainDrawingView.setPaintColor(i);
                    }
                }).show();
            }
        });
        this.btnDrawingstroke.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                if (PhotoCollageActivity.this.seekbardrawingstroke.getVisibility() == View.GONE) {
                    PhotoCollageActivity.this.seekbardrawingstroke.setVisibility(View.VISIBLE);
                    PhotoCollageActivity.this.rlDrawingView.setVisibility(View.GONE);
                    return;
                }
                PhotoCollageActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.VISIBLE);
            }
        });
        this.btnDrawingundo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoCollageActivity.this.mainDrawingView.undo();
            }
        });
        this.btnDrawingredo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoCollageActivity.this.mainDrawingView.redo();
            }
        });
        this.btnDrawingerase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                PhotoCollageActivity.this.erasedialog();
            }
        });
        this.stickerclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.stickerrecycler.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.stickerselect.setVisibility(View.GONE);
            }
        });
        this.colorlistclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.rvColorList.setVisibility(View.GONE);
                PhotoCollageActivity.this.colorlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.VISIBLE);
            }
        });
        this.backgroundlistclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.rvBackgroundList.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundlistclose.setVisibility(View.GONE);
                PhotoCollageActivity.this.backgroundLayout.setVisibility(View.VISIBLE);
            }
        });
        this.imgStrokeclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoCollageActivity.this.seekbardrawingstroke.setVisibility(View.GONE);
                PhotoCollageActivity.this.rlDrawingView.setVisibility(View.VISIBLE);
            }
        });
        this.imgsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Ad_class.showInterstitial(PhotoCollageActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        PhotoCollageActivity.this.m18062n();
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
    public ArrayList<Drawables> backgroundListOne() {
        ArrayList<Drawables> arrayList = new ArrayList<>();
        arrayList.add(new Drawables(R.drawable.pattern_g1_01));
        arrayList.add(new Drawables(R.drawable.pattern_g1_02));
        arrayList.add(new Drawables(R.drawable.pattern_g1_03));
        arrayList.add(new Drawables(R.drawable.pattern_g1_04));
        arrayList.add(new Drawables(R.drawable.pattern_g1_05));
        arrayList.add(new Drawables(R.drawable.pattern_g1_06));
        arrayList.add(new Drawables(R.drawable.pattern_g1_07));
        arrayList.add(new Drawables(R.drawable.pattern_g1_08));
        arrayList.add(new Drawables(R.drawable.pattern_g1_09));
        arrayList.add(new Drawables(R.drawable.pattern_g1_10));
        arrayList.add(new Drawables(R.drawable.pattern_g1_11));
        arrayList.add(new Drawables(R.drawable.pattern_g1_12));
        arrayList.add(new Drawables(R.drawable.pattern_g1_13));
        arrayList.add(new Drawables(R.drawable.pattern_g1_14));
        arrayList.add(new Drawables(R.drawable.pattern_g1_15));
        arrayList.add(new Drawables(R.drawable.pattern_g1_16));
        arrayList.get(0).setSelected(true);
        return arrayList;
    }

    public ArrayList<Drawables> backgroundListTwo() {
        ArrayList<Drawables> arrayList = new ArrayList<>();
        arrayList.add(new Drawables(R.drawable.pattern_g2_01));
        arrayList.add(new Drawables(R.drawable.pattern_g2_02));
        arrayList.add(new Drawables(R.drawable.pattern_g2_03));
        arrayList.add(new Drawables(R.drawable.pattern_g2_04));
        arrayList.add(new Drawables(R.drawable.pattern_g2_05));
        arrayList.add(new Drawables(R.drawable.pattern_g2_06));
        arrayList.add(new Drawables(R.drawable.pattern_g2_07));
        arrayList.add(new Drawables(R.drawable.pattern_g2_08));
        arrayList.add(new Drawables(R.drawable.pattern_g2_09));
        arrayList.add(new Drawables(R.drawable.pattern_g2_10));
        arrayList.add(new Drawables(R.drawable.pattern_g2_11));
        arrayList.add(new Drawables(R.drawable.pattern_g2_12));
        arrayList.get(0).setSelected(true);
        return arrayList;
    }

    public ArrayList<Drawables> backgroundListThree() {
        ArrayList<Drawables> arrayList = new ArrayList<>();
        arrayList.add(new Drawables(R.drawable.pattern_g3_01));
        arrayList.add(new Drawables(R.drawable.pattern_g3_02));
        arrayList.add(new Drawables(R.drawable.pattern_g3_03));
        arrayList.add(new Drawables(R.drawable.pattern_g3_04));
        arrayList.add(new Drawables(R.drawable.pattern_g3_05));
        arrayList.add(new Drawables(R.drawable.pattern_g3_06));
        arrayList.add(new Drawables(R.drawable.pattern_g3_07));
        arrayList.add(new Drawables(R.drawable.pattern_g3_08));
        arrayList.add(new Drawables(R.drawable.pattern_g3_09));
        arrayList.add(new Drawables(R.drawable.pattern_g3_10));
        arrayList.add(new Drawables(R.drawable.pattern_g3_11));
        arrayList.add(new Drawables(R.drawable.pattern_g3_12));
        arrayList.get(0).setSelected(true);
        return arrayList;
    }

    public ArrayList<Drawables> backgroundListFour() {
        ArrayList<Drawables> arrayList = new ArrayList<>();
        arrayList.add(new Drawables(R.drawable.pattern_g4_01));
        arrayList.add(new Drawables(R.drawable.pattern_g4_02));
        arrayList.add(new Drawables(R.drawable.pattern_g4_03));
        arrayList.add(new Drawables(R.drawable.pattern_g4_04));
        arrayList.add(new Drawables(R.drawable.pattern_g4_05));
        arrayList.add(new Drawables(R.drawable.pattern_g4_06));
        arrayList.add(new Drawables(R.drawable.pattern_g4_07));
        arrayList.add(new Drawables(R.drawable.pattern_g4_08));
        arrayList.add(new Drawables(R.drawable.pattern_g4_09));
        arrayList.add(new Drawables(R.drawable.pattern_g4_10));
        arrayList.add(new Drawables(R.drawable.pattern_g4_11));
        arrayList.add(new Drawables(R.drawable.pattern_g4_12));
        arrayList.get(0).setSelected(true);
        return arrayList;
    }

    public ArrayList<Drawables> backgroundListFive() {
        ArrayList<Drawables> arrayList = new ArrayList<>();
        arrayList.add(new Drawables(R.drawable.pattern_g5_01));
        arrayList.add(new Drawables(R.drawable.pattern_g5_02));
        arrayList.add(new Drawables(R.drawable.pattern_g5_03));
        arrayList.add(new Drawables(R.drawable.pattern_g5_04));
        arrayList.add(new Drawables(R.drawable.pattern_g5_05));
        arrayList.add(new Drawables(R.drawable.pattern_g5_06));
        arrayList.add(new Drawables(R.drawable.pattern_g5_07));
        arrayList.add(new Drawables(R.drawable.pattern_g5_08));
        arrayList.add(new Drawables(R.drawable.pattern_g5_09));
        arrayList.add(new Drawables(R.drawable.pattern_g5_10));
        arrayList.add(new Drawables(R.drawable.pattern_g5_11));
        arrayList.add(new Drawables(R.drawable.pattern_g5_12));
        arrayList.get(0).setSelected(true);
        return arrayList;
    }

    public ArrayList<Drawables> backgroundListSix() {
        ArrayList<Drawables> arrayList = new ArrayList<>();
        arrayList.add(new Drawables(R.drawable.pattern_g6_01));
        arrayList.add(new Drawables(R.drawable.pattern_g6_02));
        arrayList.add(new Drawables(R.drawable.pattern_g6_03));
        arrayList.add(new Drawables(R.drawable.pattern_g6_04));
        arrayList.add(new Drawables(R.drawable.pattern_g6_05));
        arrayList.add(new Drawables(R.drawable.pattern_g6_06));
        arrayList.add(new Drawables(R.drawable.pattern_g6_07));
        arrayList.add(new Drawables(R.drawable.pattern_g6_08));
        arrayList.add(new Drawables(R.drawable.pattern_g6_09));
        arrayList.add(new Drawables(R.drawable.pattern_g6_10));
        arrayList.add(new Drawables(R.drawable.pattern_g6_11));
        arrayList.add(new Drawables(R.drawable.pattern_g6_12));
        arrayList.get(0).setSelected(true);
        return arrayList;
    }

    public void onPreviewTemplateClick(TemplateItem templateItem) {
        this.mSelectedTemplateItem.setSelected(false);
        for (int i = 0; i < this.mSelectedTemplateItem.getPhotoItemList().size(); i++) {
            PhotoItem photoItem = this.mSelectedTemplateItem.getPhotoItemList().get(i);
            if (photoItem.imagePath != null && photoItem.imagePath.length() > 0) {
                if (i < this.pathList.size()) {
                    this.pathList.add(i, photoItem.imagePath);
                } else {
                    this.pathList.add(photoItem.imagePath);
                }
            }
        }
        int min = Math.min(this.pathList.size(), templateItem.getPhotoItemList().size());
        for (int i2 = 0; i2 < min; i2++) {
            PhotoItem photoItem2 = templateItem.getPhotoItemList().get(i2);
            if (photoItem2.imagePath == null || photoItem2.imagePath.length() < 1) {
                photoItem2.imagePath = this.pathList.get(i2);
            }
        }
        this.mSelectedTemplateItem = templateItem;
        this.mSelectedTemplateItem.setSelected(true);
        this.mTemplateAdapter.notifyDataSetChanged();
        buildLayout(templateItem);
    }

    public void buildLayout(TemplateItem templateItem) {
        this.mFramePhotoLayout = new FramePhotoLayout(this, templateItem.getPhotoItemList());
        int width = this.mContainerLayout.getWidth();
        int height = this.mContainerLayout.getHeight();
        int i = this.mLayoutRatio;
        if (i == 0) {
            if (width > height) {
                width = height;
            } else {
                height = width;
            }
        } else if (i == 2) {
            if (width <= height) {
                double d = (double) width;
                Double.isNaN(d);
                double d2 = d * 1.61803398875d;
                double d3 = (double) height;
                if (d2 >= d3) {
                    Double.isNaN(d3);
                    width = (int) (d3 / 1.61803398875d);
                } else {
                    height = (int) d2;
                }
            } else if (height <= width) {
                double d4 = (double) height;
                Double.isNaN(d4);
                double d5 = d4 * 1.61803398875d;
                double d6 = (double) width;
                if (d5 >= d6) {
                    Double.isNaN(d6);
                    height = (int) (d6 / 1.61803398875d);
                } else {
                    width = (int) d5;
                }
            }
        }
        this.mOutputScale = ImageUtils.calculateOutputScaleFactor(width, height);
        this.mFramePhotoLayout.build(width, height, this.mOutputScale, this.mSpace, this.mCorner);
        Bundle bundle = this.mSavedInstanceState;
        if (bundle != null) {
            this.mFramePhotoLayout.restoreInstanceState(bundle);
            this.mSavedInstanceState = null;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(13);
        this.mContainerLayout.removeAllViews();
        this.mContainerLayout.addView(this.mFramePhotoLayout, layoutParams);
        this.mContainerLayout.removeView(this.mPhotoView);
        this.mContainerLayout.addView(this.mPhotoView, layoutParams);
        this.spaceseekbar.setProgress((int) ((this.mSpace * MAX_SPACE_PROGRESS) / MAX_SPACE));
        this.cornerseekbar.setProgress((int) ((this.mCorner * MAX_CORNER_PROGRESS) / MAX_CORNER));
    }

    public void m18039a(int i, Bitmap bitmap) {
        C2738b c2738b = new C2738b();
        c2738b.m18222a((float) ((this.rlMainScreen.getWidth() / 2) - C2794d.m18425a(this, 70)));
        c2738b.m18228b((float) ((this.rlMainScreen.getHeight() / 2) - C2794d.m18425a(this, 70)));
        c2738b.m18229b(C2794d.m18425a(this, 140));
        c2738b.m18233c(C2794d.m18425a(this, 140));
        c2738b.m18232c(0.0f);
        c2738b.m18224a(bitmap);
        c2738b.m18230b("white");
        c2738b.m18226a("STICKER");
        C2745c c2745c = new C2745c(this);
        c2745c.setComponentInfo(c2738b);
        this.rlMainScreen.addView(c2745c);
        c2745c.m18265a(this);
        c2745c.setBorderVisibility(true);
    }

    public void m18062n() {
        int childCount = this.rlMainScreen.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.rlMainScreen.getChildAt(i);
            if (childAt instanceof AutoTextRel) {
                ((AutoTextRel) childAt).setBorderVisibility(false);
            }
            if (childAt instanceof C2745c) {
                ((C2745c) childAt).setBorderVisibility(false);
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

    public void openTextActivity() {
        Intent intent = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("X", (this.rlMainScreen.getWidth() / 2) - C2794d.m18425a(this, 100));
        bundle.putInt("Y", (this.rlMainScreen.getHeight() / 2) - C2794d.m18425a(this, 100));
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
        RelativeLayout relativeLayout = this.rlMainScreen;
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
        int childCount = this.rlMainScreen.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.rlMainScreen.getChildAt(i);
            if (childAt instanceof AutoTextRel) {
                ((AutoTextRel) childAt).setBorderVisibility(false);
            }
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
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
                    RelativeLayout relativeLayout = this.rlMainScreen;
                    ((AutoTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).setTextInfo(textInfo, false);
                    RelativeLayout relativeLayout2 = this.rlMainScreen;
                    ((AutoTextRel) relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1)).setBorderVisibility(true);
                    this.editMode = false;
                    return;
                }
                this.touchChange = true;
                AutoTextRel autoTextRel = new AutoTextRel(this);
                this.rlMainScreen.addView(autoTextRel);
                autoTextRel.setTextInfo(textInfo, false);
                autoTextRel.setOnTouchCallbackListener(this);
                autoTextRel.setBorderVisibility(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                PhotoCollageActivity.this.mainDrawingView.clearCanvas();
                PhotoCollageActivity.this.ivDrawImage.setImageBitmap((Bitmap) null);
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
                PhotoCollageActivity photoCollageActivity = PhotoCollageActivity.this;
                photoCollageActivity.startActivity(new Intent(photoCollageActivity.getApplicationContext(), MainActivity.class));
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    public void oncolorBackgroundCallback(int i) {
        this.mContainerLayout.setBackgroundColor(this.colors[i]);
    }

    public void onMethodCallbackforfilter(int i, String str) {
        this.backPos = i;
        this.mContainerLayout.setBackgroundResource(this.backgroundList.get(i).image);
    }

    public void saveImageProgress() {
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("Just Wait ! Photo Saving");
        this.dialog.setIndeterminate(false);
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
    }

    private class SaveImage extends AsyncTask<Object, Integer, String> {
        private SaveImage() {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            PhotoCollageActivity.this.saveImageProgress();
        }

        public String doInBackground(Object... objArr) {
            try {
                PhotoCollageActivity.this.mergeAndSave();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            PhotoCollageActivity.this.dialog.dismiss();
        }
    }

    private int generateRandomName(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    public void mergeAndSave() {
        Bitmap loadBitmapFromView = loadBitmapFromView(this.rlMainScreen);
        try {
            saveImageToSD(loadBitmapFromView, "photox_" + generateRandomName(1000000, 5000000) + ".jpg", Bitmap.CompressFormat.JPEG);
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
}

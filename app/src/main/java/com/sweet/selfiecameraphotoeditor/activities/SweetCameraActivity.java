package com.sweet.selfiecameraphotoeditor.activities;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.cameraview.CameraHelper;
import com.sweet.selfiecameraphotoeditor.cameraview.CameraSourcePreview;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Antique;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Calm;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Cool;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Emerald;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.EverGreen;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Healthy;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Latte;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Nostalgia;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.RedCat;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Romance;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Sakura;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Sunrise;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Sunset;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Sweety;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Tender;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.Warm;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.WhiteCat;
import com.sweet.selfiecameraphotoeditor.cameraview.filters.WhiteSkin;
import com.sweet.selfiecameraphotoeditor.cameraview.gputoolutils.GPUImageFilterTools;
import com.sweet.selfiecameraphotoeditor.cameraview.json.HttpHandler;
import com.sweet.selfiecameraphotoeditor.cameraview.json.JsonData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;

public class SweetCameraActivity extends AppCompatActivity {
    private static final int RC_HANDLE_GMS = 9001;
    private static final String TAG = "FaceTracker";
    static Boolean animalBtn = false;
    static Bitmap bitmap = null;
    static boolean hat = false;
    public static int imgId;
    int animCount;
    ArrayList<String> animalList;
    HorizontalScrollView blenderList;
    RelativeLayout btnBlender;
    RelativeLayout btnCameraswitch;
    ImageView btnCapture;
    RelativeLayout btnFilter;
    RelativeLayout btnGradient;
    GLSurfaceView camerasurfaceview;
    HorizontalScrollView filterList;
    GetURL getURL;
    HorizontalScrollView gradientList;
    ImageView imgSwitchcamera;

    public CameraLoader mCamera;
    public CameraHelper mCameraHelper;
    private CameraSource mCameraSource = null;
    private GPUImageFilter mFilter;
    GPUImageFilterTools.FilterAdjuster mFilterAdjuster;
    public GPUImage mGPUImage;
    public GraphicOverlay mGraphicOverlay;
    public CameraSourcePreview mPreview;
    public int passHeight = 0;
    public int passWidth = 0;
    LinearLayout photosavingLayout;
    Bitmap rBitmap;
    HorizontalScrollView stickerList;
    LinearLayout topcontrolLayout;

    private class CameraLoader {
        public Camera mCameraInstance;
        private int mCurrentCameraId;

        private CameraLoader() {
            this.mCurrentCameraId = 1;
        }

        public void onResume() {
            setUpCamera(this.mCurrentCameraId);
            SweetCameraActivity.this.startCameraSource();
        }

        public void onPause() {
            releaseCamera();
        }

        public void switchCamera() {
            releaseCamera();
            this.mCurrentCameraId = (this.mCurrentCameraId + 1) % SweetCameraActivity.this.mCameraHelper.getNumberOfCameras();
            setUpCamera(this.mCurrentCameraId);
        }

        private void setUpCamera(int i) {
            this.mCameraInstance = getCameraInstance(i);
            Camera.Parameters parameters = this.mCameraInstance.getParameters();
            if (parameters.getSupportedFocusModes().contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
            }
            this.mCameraInstance.setParameters(parameters);
            int cameraDisplayOrientation = SweetCameraActivity.this.mCameraHelper.getCameraDisplayOrientation(SweetCameraActivity.this, this.mCurrentCameraId);
            CameraHelper.CameraInfo2 cameraInfo2 = new CameraHelper.CameraInfo2();
            SweetCameraActivity.this.mCameraHelper.getCameraInfo(this.mCurrentCameraId, cameraInfo2);
            boolean z = true;
            if (cameraInfo2.facing != 1) {
                z = false;
            }
            SweetCameraActivity.this.mGPUImage.setUpCamera(this.mCameraInstance, cameraDisplayOrientation, z, false);
        }

        private Camera getCameraInstance(int i) {
            try {
                return SweetCameraActivity.this.mCameraHelper.openCamera(i);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void releaseCamera() {
            this.mCameraInstance.setPreviewCallback((Camera.PreviewCallback) null);
            this.mCameraInstance.release();
            this.mCameraInstance = null;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.sweetcamera_activity);
        getWindow().setFlags(1024, 1024);
        this.mGPUImage = new GPUImage(this);
        this.camerasurfaceview = (GLSurfaceView) findViewById(R.id.camerasurfaceview);
        this.mGPUImage.setGLSurfaceView(this.camerasurfaceview);
        BitmapFactory.decodeResource(getResources(), R.drawable.fairy_tale);
        this.mCameraHelper = new CameraHelper(this);
        this.mCamera = new CameraLoader();
        this.topcontrolLayout = (LinearLayout) findViewById(R.id.topcontrol_layout);
        this.photosavingLayout = (LinearLayout) findViewById(R.id.photosaving_layout);
        this.blenderList = (HorizontalScrollView) findViewById(R.id.blender_list);
        this.gradientList = (HorizontalScrollView) findViewById(R.id.gradient_list);
        this.filterList = (HorizontalScrollView) findViewById(R.id.filter_list);
        this.stickerList = (HorizontalScrollView) findViewById(R.id.sticker_list);
        this.imgSwitchcamera = (ImageView) findViewById(R.id.img_switchcamera);
        this.btnCapture = (ImageView) findViewById(R.id.btn_capture);
        this.btnCameraswitch = (RelativeLayout) findViewById(R.id.btn_cameraswitch);
        this.btnBlender = (RelativeLayout) findViewById(R.id.btn_blander);
        this.btnGradient = (RelativeLayout) findViewById(R.id.btn_gradient);
        this.btnFilter = (RelativeLayout) findViewById(R.id.btn_filter);
        this.animalList = new ArrayList<>();
        this.getURL = new GetURL();
        this.getURL.execute(new Void[0]);
        createCameraSource();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.transparent);
        this.animCount = 5;
        new DownloadImageFromJson().cancel(true);
        this.mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        this.mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
        this.mPreview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    SweetCameraActivity.this.mPreview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    SweetCameraActivity.this.mPreview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                SweetCameraActivity sweetCameraActivity = SweetCameraActivity.this;
                sweetCameraActivity.passWidth = sweetCameraActivity.mPreview.getMeasuredWidth();
                SweetCameraActivity sweetCameraActivity2 = SweetCameraActivity.this;
                sweetCameraActivity2.passHeight = sweetCameraActivity2.mPreview.getMeasuredHeight();
            }
        });
        if (CameraStartActivity.isCheckCameraSticker) {
            this.mPreview.setVisibility(View.VISIBLE);
            this.topcontrolLayout.setVisibility(View.GONE);
            this.mGraphicOverlay.setVisibility(View.VISIBLE);
            this.camerasurfaceview.setVisibility(View.GONE);
            this.btnCameraswitch.setVisibility(View.GONE);
            this.stickerList.setVisibility(View.VISIBLE);
            this.filterList.setVisibility(View.GONE);
            this.gradientList.setVisibility(View.GONE);
            this.blenderList.setVisibility(View.GONE);
        } else if (CameraStartActivity.isCheckCameraFilter) {
            this.mPreview.setVisibility(View.GONE);
            this.topcontrolLayout.setVisibility(View.VISIBLE);
            this.mGraphicOverlay.setVisibility(View.GONE);
            this.camerasurfaceview.setVisibility(View.VISIBLE);
            this.btnCameraswitch.setVisibility(View.VISIBLE);
            this.stickerList.setVisibility(View.GONE);
            this.filterList.setVisibility(View.VISIBLE);
            this.gradientList.setVisibility(View.GONE);
            this.blenderList.setVisibility(View.GONE);
        } else {
            this.mPreview.setVisibility(View.GONE);
            this.topcontrolLayout.setVisibility(View.VISIBLE);
            this.mGraphicOverlay.setVisibility(View.GONE);
            this.camerasurfaceview.setVisibility(View.VISIBLE);
            this.stickerList.setVisibility(View.GONE);
            this.filterList.setVisibility(View.VISIBLE);
            this.gradientList.setVisibility(View.GONE);
            this.blenderList.setVisibility(View.GONE);
        }
        this.btnCapture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SweetCameraActivity.this.mPreview.getVisibility() == View.VISIBLE) {
                    SweetCameraActivity.this.photosavingLayout.setVisibility(View.VISIBLE);
                    SweetCameraActivity.this.mainFunction();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            SweetCameraActivity.this.photosavingLayout.setVisibility(View.GONE);
                        }
                    }, 2000);
                    return;
                }
                SweetCameraActivity.this.photosavingLayout.setVisibility(View.VISIBLE);
                if (SweetCameraActivity.this.mCamera.mCameraInstance.getParameters().getFocusMode().equals("continuous-picture")) {
                    SweetCameraActivity.this.takePicture();
                } else {
                    SweetCameraActivity.this.mCamera.mCameraInstance.autoFocus(new Camera.AutoFocusCallback() {
                        public void onAutoFocus(boolean z, Camera camera) {
                            SweetCameraActivity.this.takePicture();
                        }
                    });
                }
            }
        });
        this.btnCameraswitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SweetCameraActivity.this.flipAnimation();
                SweetCameraActivity.this.mCamera.switchCamera();
            }
        });
        this.btnBlender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SweetCameraActivity.this.mPreview.setVisibility(View.GONE);
                SweetCameraActivity.this.mGraphicOverlay.setVisibility(View.GONE);
                SweetCameraActivity.this.camerasurfaceview.setVisibility(View.VISIBLE);
                SweetCameraActivity.this.btnCameraswitch.setVisibility(View.VISIBLE);
                if (SweetCameraActivity.this.blenderList.getVisibility() == View.GONE) {
                    SweetCameraActivity.this.blenderList.setVisibility(View.VISIBLE);
                    SweetCameraActivity.this.gradientList.setVisibility(View.GONE);
                    SweetCameraActivity.this.filterList.setVisibility(View.GONE);
                    return;
                }
                SweetCameraActivity.this.blenderList.setVisibility(View.GONE);
                SweetCameraActivity.this.gradientList.setVisibility(View.GONE);
                SweetCameraActivity.this.filterList.setVisibility(View.GONE);
            }
        });
        this.btnGradient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SweetCameraActivity.this.mPreview.setVisibility(View.GONE);
                SweetCameraActivity.this.mGraphicOverlay.setVisibility(View.GONE);
                SweetCameraActivity.this.camerasurfaceview.setVisibility(View.VISIBLE);
                SweetCameraActivity.this.btnCameraswitch.setVisibility(View.VISIBLE);
                if (SweetCameraActivity.this.gradientList.getVisibility() == View.GONE) {
                    SweetCameraActivity.this.gradientList.setVisibility(View.VISIBLE);
                    SweetCameraActivity.this.blenderList.setVisibility(View.GONE);
                    SweetCameraActivity.this.filterList.setVisibility(View.GONE);
                    return;
                }
                SweetCameraActivity.this.gradientList.setVisibility(View.GONE);
                SweetCameraActivity.this.blenderList.setVisibility(View.GONE);
                SweetCameraActivity.this.filterList.setVisibility(View.GONE);
            }
        });
        this.btnFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SweetCameraActivity.this.mPreview.setVisibility(View.GONE);
                SweetCameraActivity.this.mGraphicOverlay.setVisibility(View.GONE);
                SweetCameraActivity.this.camerasurfaceview.setVisibility(View.VISIBLE);
                SweetCameraActivity.this.btnCameraswitch.setVisibility(View.VISIBLE);
                if (SweetCameraActivity.this.filterList.getVisibility() == View.GONE) {
                    SweetCameraActivity.this.filterList.setVisibility(View.VISIBLE);
                    SweetCameraActivity.this.gradientList.setVisibility(View.GONE);
                    SweetCameraActivity.this.blenderList.setVisibility(View.GONE);
                    return;
                }
                SweetCameraActivity.this.filterList.setVisibility(View.GONE);
                SweetCameraActivity.this.gradientList.setVisibility(View.GONE);
                SweetCameraActivity.this.blenderList.setVisibility(View.GONE);
            }
        });
    }

    public void takePicture() {
        Camera.Parameters parameters = this.mCamera.mCameraInstance.getParameters();
        parameters.setRotation(90);
        this.mCamera.mCameraInstance.setParameters(parameters);
        for (Camera.Size next : parameters.getSupportedPictureSizes()) {
            Log.i("ASDF", "Supported: " + next.width + "x" + next.height);
        }
        this.mCamera.mCameraInstance.takePicture((Camera.ShutterCallback) null, (Camera.PictureCallback) null, new Camera.PictureCallback() {
            public void onPictureTaken(byte[] bArr, Camera camera) {
                final File outputMediaFile = SweetCameraActivity.getOutputMediaFile(1);
                if (outputMediaFile == null) {
                    Log.d("ASDF", "Error creating media file, check storage permissions");
                    return;
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(outputMediaFile);
                    fileOutputStream.write(bArr);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    Log.d("ASDF", "File not found: " + e.getMessage());
                } catch (IOException e2) {
                    Log.d("ASDF", "Error accessing file: " + e2.getMessage());
                }
                Bitmap decodeFile = BitmapFactory.decodeFile(outputMediaFile.getAbsolutePath());
                if (decodeFile.getWidth() > decodeFile.getHeight()) {
                    SweetCameraActivity.this.rBitmap = SweetCameraActivity.rotateImage(decodeFile, 90.0f);
                } else {
                    SweetCameraActivity.this.rBitmap = decodeFile;
                }
                final GLSurfaceView gLSurfaceView = (GLSurfaceView) SweetCameraActivity.this.findViewById(R.id.camerasurfaceview);
                gLSurfaceView.setRenderMode(0);
                Date currentTime = Calendar.getInstance().getTime();
                String date=currentTime.getTime()+"temp_3.jpg";
                SweetCameraActivity.this.mGPUImage.saveToPictures(SweetCameraActivity.this.rBitmap, "Temp", date, new GPUImage.OnPictureSavedListener() {
                    public void onPictureSaved(Uri uri) {
                        outputMediaFile.delete();
                        Log.e("aaaaaaaaa",uri.toString());
                        gLSurfaceView.setRenderMode(1);
                        SweetCameraActivity.this.photosavingLayout.setVisibility(View.GONE);
                        Intent intent=new Intent(SweetCameraActivity.this,CameraPhotoSaveActivity.class);
                        intent.putExtra("path",uri.toString());
                        startActivity(intent);

//                        SweetCameraActivity.this.startActivity(new Intent(SweetCameraActivity.this, CameraPhotoSaveActivity.class));
                    }
                });
            }
        });
    }

    public void mainFunction() {
        this.mCameraSource.takePicture(null, new CameraSource.PictureCallback() {


            @Override
            public void onPictureTaken(final byte[] bArr) {
                new Handler().postDelayed(new Runnable() {


                    public void run() {
                        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
                        Matrix matrix = new Matrix();
                        matrix.setScale(-1, 1);
                        matrix.postTranslate( decodeByteArray.getWidth(),0);
                        decodeByteArray = Bitmap.createBitmap( decodeByteArray, 0, 0,
                                decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, true);
                        Bitmap screenShot = SweetCameraActivity.this.getScreenShot();
                        SweetCameraActivity.this.saveBitmapImage(SweetCameraActivity.this.mirrirImage(decodeByteArray));
                        SweetCameraActivity.this.saveBitmapImage1(screenShot);
                        PrintStream printStream = System.out;
                        printStream.println("BITMAP" + decodeByteArray.getWidth() + "x" + decodeByteArray.getHeight());
                        SweetCameraActivity.this.startActivity(new Intent(SweetCameraActivity.this, CameraStickerPhotoSaveActivity.class));
                    }
                }, 1000);
            }
        });
    }

    public Bitmap getScreenShot() {
        View findViewById = findViewById(R.id.faceOverlay);
        findViewById.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(findViewById.getDrawingCache());
        findViewById.setDrawingCacheEnabled(false);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        findViewById.draw(new Canvas(createBitmap2));
        return createBitmap2;
    }

    public void saveBitmapImage(Bitmap bitmap2) {
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Temporary");
            if (!file.exists()) {
                file.mkdirs();
            }
            new Random();
            File file2 = new File(file, String.format("%s_%d.png", new Object[]{"temp", 1}));
            file2.toString();
            if (file2.exists() && file2.delete()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
                file2.toString();
            } catch (Exception unused) {
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }


    public void saveBitmapImage1(Bitmap bitmap2) {
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Temporary");
            if (!file.exists()) {
                file.mkdirs();
            }
            new Random();
            File file2 = new File(file, String.format("%s_%d.png", "temp", 2));
            if (file2.exists() && file2.delete()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap mirrirImage(Bitmap bitmap2) {
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), null, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mCamera.onResume();
        startCameraSource();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mPreview.stop();
    }

    @Override
    public void onStop() {
        if (CameraStartActivity.isCheckCameraSticker) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "mCamera Not Pause");
        } else {
            this.mCamera.onPause();
        }
        super.onStop();
    }

    public static File getOutputMediaFile(int i) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraPlus");
        if (!file.exists() && !file.mkdirs()) {
            return null;
        }
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if (i == 1) {
            return new File(file.getPath() + File.separator + "IMG_" + format + ".jpg");
        } else if (i != 2) {
            return null;
        } else {
            return new File(file.getPath() + File.separator + "VID_" + format + ".mp4");
        }
    }

    public static Bitmap rotateImage(Bitmap bitmap2, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
    }


    public void flipAnimation() {
        AnimatorSet objectAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flipstage2);
        objectAnimator.setTarget(this.imgSwitchcamera);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }


    public void switchFilterTo(GPUImageFilter gPUImageFilter) {
        GPUImageFilter gPUImageFilter2 = this.mFilter;
        if (gPUImageFilter2 == null || (gPUImageFilter != null && !gPUImageFilter2.getClass().equals(gPUImageFilter.getClass()))) {
            this.mFilter = gPUImageFilter;
            this.mGPUImage.setFilter(this.mFilter);
            this.mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(this.mFilter);
        }
    }

    public void blander(View view) {
        switch (view.getId()) {
            case R.id.img_blender_1:
                imgId = 1;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_10:
                imgId = 10;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_11:
                imgId = 11;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_12:
                imgId = 12;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_13:
                imgId = 13;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_14:
                imgId = 14;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_15:
                imgId = 15;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_16:
                imgId = 16;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_2:
                imgId = 2;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_3:
                imgId = 3;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_4:
                imgId = 4;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_5:
                imgId = 5;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_6:
                imgId = 6;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_7:
                imgId = 7;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_8:
                imgId = 8;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_9:
                imgId = 9;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_blender_no:
                imgId = 0;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createBlenderFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            default:
                return;
        }
    }

    public void gradient(View view) {
        switch (view.getId()) {
            case R.id.img_gradient_1 /*2131362182*/:
                imgId = 1;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_10 /*2131362183*/:
                imgId = 10;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_11 /*2131362184*/:
                imgId = 11;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_12 /*2131362185*/:
                imgId = 12;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_13 /*2131362186*/:
                imgId = 13;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_14 /*2131362187*/:
                imgId = 14;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_15 /*2131362188*/:
                imgId = 15;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_16 /*2131362189*/:
                imgId = 16;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_2 /*2131362190*/:
                imgId = 2;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_3 /*2131362191*/:
                imgId = 3;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_4 /*2131362192*/:
                imgId = 4;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_5 /*2131362193*/:
                imgId = 5;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_6 /*2131362194*/:
                imgId = 6;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_7 /*2131362195*/:
                imgId = 7;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_8 /*2131362196*/:
                imgId = 8;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_9 /*2131362197*/:
                imgId = 9;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            case R.id.img_gradient_no /*2131362198*/:
                imgId = 0;
                switchFilterTo(new Healthy());
                switchFilterTo(GPUImageFilterTools.createGradientFilter(getApplicationContext(), GPUImageMultiplyBlendFilter.class));
                return;
            default:
                return;
        }
    }

    public void filter(View view) {
        switch (view.getId()) {
            case R.id.img_filter_1 /*2131362133*/:
                switchFilterTo(new Sunrise());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_10 /*2131362134*/:
                switchFilterTo(new Antique());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_11 /*2131362135*/:
                switchFilterTo(new Nostalgia());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_12 /*2131362136*/:
                switchFilterTo(new Calm());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_13 /*2131362137*/:
                switchFilterTo(new Latte());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_14 /*2131362138*/:
                switchFilterTo(new Tender());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_15 /*2131362139*/:
                switchFilterTo(new Cool());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_16 /*2131362140*/:
                switchFilterTo(new Emerald());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_17 /*2131362141*/:
                switchFilterTo(new EverGreen());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_18 /*2131362142*/:
                switchFilterTo(new Sakura());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_19 /*2131362143*/:
                GPUImageLookupFilter gPUImageLookupFilter = new GPUImageLookupFilter();
                gPUImageLookupFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_amatorka));
                switchFilterTo(gPUImageLookupFilter);
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_2 /*2131362144*/:
                switchFilterTo(new Sunset());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_20 /*2131362145*/:
                switchFilterTo(new GPUImageGrayscaleFilter());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_21 /*2131362146*/:
                switchFilterTo(new GPUImageHueFilter());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_22 /*2131362147*/:
                switchFilterTo(new GPUImageMonochromeFilter());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_23 /*2131362148*/:
                switchFilterTo(new GPUImageColorInvertFilter());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.VISIBLE);
                return;
            case R.id.img_filter_3 /*2131362149*/:
                switchFilterTo(new WhiteCat());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_4 /*2131362150*/:
                switchFilterTo(new RedCat());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_5 /*2131362151*/:
                switchFilterTo(new Romance());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_6 /*2131362152*/:
                switchFilterTo(new WhiteSkin());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_7 /*2131362153*/:
                switchFilterTo(new Healthy());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_8 /*2131362154*/:
                switchFilterTo(new Sweety());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_9 /*2131362155*/:
                switchFilterTo(new Warm());
                findViewById(R.id.img_filterselect_no).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            case R.id.img_filter_no /*2131362156*/:
                switchFilterTo(new GPUImageSharpenFilter());
                findViewById(R.id.img_filterselect_no).setVisibility(View.VISIBLE);
                findViewById(R.id.img_filterselect_1).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_2).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_3).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_4).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_5).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_6).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_7).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_8).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_9).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_10).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_11).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_12).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_13).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_14).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_15).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_16).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_17).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_18).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_19).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_20).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_21).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_22).setVisibility(View.GONE);
                findViewById(R.id.img_filterselect_23).setVisibility(View.GONE);
                return;
            default:
                return;
        }
    }

    public void facefilter(View view) {
        switch (view.getId()) {
            case R.id.img_sticker_1 /*2131362208*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_01);
                return;
            case R.id.img_sticker_10 /*2131362209*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_10);
                return;
            case R.id.img_sticker_11 /*2131362210*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_11);
                return;
            case R.id.img_sticker_12 /*2131362211*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_12);
                return;
            case R.id.img_sticker_13 /*2131362212*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_13);
                return;
            case R.id.img_sticker_14 /*2131362213*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_14);
                return;
            case R.id.img_sticker_15 /*2131362214*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_15);
                return;
            case R.id.img_sticker_16 /*2131362215*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_16);
                return;
            case R.id.img_sticker_17 /*2131362216*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_17);
                return;
            case R.id.img_sticker_18 /*2131362217*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_18);
                return;
            case R.id.img_sticker_19 /*2131362218*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_19);
                return;
            case R.id.img_sticker_2 /*2131362219*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_02);
                return;
            case R.id.img_sticker_20 /*2131362220*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_20);
                return;
            case R.id.img_sticker_21 /*2131362221*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_21);
                return;
            case R.id.img_sticker_22 /*2131362222*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_22);
                return;
            case R.id.img_sticker_23 /*2131362223*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_23);
                return;
            case R.id.img_sticker_24 /*2131362224*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_24);
                return;
            case R.id.img_sticker_25 /*2131362225*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_25);
                return;
            case R.id.img_sticker_26 /*2131362226*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_26);
                return;
            case R.id.img_sticker_27 /*2131362227*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_27);
                return;
            case R.id.img_sticker_28 /*2131362228*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_28);
                return;
            case R.id.img_sticker_29 /*2131362229*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_29);
                return;
            case R.id.img_sticker_3 /*2131362230*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_03);
                return;
            case R.id.img_sticker_30 /*2131362231*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_30);
                return;
            case R.id.img_sticker_31 /*2131362232*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_31);
                return;
            case R.id.img_sticker_32 /*2131362233*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_32);
                return;
            case R.id.img_sticker_33 /*2131362234*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_33);
                return;
            case R.id.img_sticker_34 /*2131362235*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_34);
                return;
            case R.id.img_sticker_35 /*2131362236*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_35);
                return;
            case R.id.img_sticker_36 /*2131362237*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_36);
                return;
            case R.id.img_sticker_37 /*2131362238*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_37);
                return;
            case R.id.img_sticker_38 /*2131362239*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_38);
                return;
            case R.id.img_sticker_39 /*2131362240*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_39);
                return;
            case R.id.img_sticker_4 /*2131362241*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_04);
                return;
            case R.id.img_sticker_40 /*2131362242*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_40);
                return;
            case R.id.img_sticker_41 /*2131362243*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_41);
                return;
            case R.id.img_sticker_42 /*2131362244*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_42);
                return;
            case R.id.img_sticker_43 /*2131362245*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_43);
                return;
            case R.id.img_sticker_44 /*2131362246*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_44);
                return;
            case R.id.img_sticker_45 /*2131362247*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_45);
                return;
            case R.id.img_sticker_46 /*2131362248*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_46);
                return;
            case R.id.img_sticker_47 /*2131362249*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_47);
                return;
            case R.id.img_sticker_48 /*2131362250*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_48);
                return;
            case R.id.img_sticker_49 /*2131362251*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_49);
                return;
            case R.id.img_sticker_5 /*2131362252*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_05);
                return;
            case R.id.img_sticker_50 /*2131362253*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_50);
                return;
            case R.id.img_sticker_51 /*2131362254*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_51);
                return;
            case R.id.img_sticker_52 /*2131362255*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_52);
                return;
            case R.id.img_sticker_6 /*2131362256*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_06);
                return;
            case R.id.img_sticker_7 /*2131362257*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_07);
                return;
            case R.id.img_sticker_8 /*2131362258*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_08);
                return;
            case R.id.img_sticker_9 /*2131362259*/:
                bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sticker_09);
                return;
            default:
                return;
        }
    }

    private class DownloadImageFromJson extends AsyncTask<String, Void, Bitmap> {
        private DownloadImageFromJson() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Bitmap doInBackground(String... strArr) {
            try {
                return BitmapFactory.decodeStream(new URL(strArr[0]).openStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Bitmap bitmap) {
            if (SweetCameraActivity.animalBtn.booleanValue()) {
                SweetCameraActivity.this.DownloadedAnimalImage(bitmap);
            }
        }

        public void onCancelled() {
            super.onCancelled();
        }
    }

    public void DownloadedAnimalImage(Bitmap bitmap2) {
        this.animCount++;
        System.out.println("Image Saved anim_" + this.animCount);
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/.Testing");
            if (!file.exists()) {
                file.mkdirs();
            }
            new Random().nextInt(1000);
            File file2 = new File(file, String.format("%s_%d.png", new Object[]{"animal", Integer.valueOf(this.animCount)}));
            if (file2.exists() && file2.delete()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private class GetURL extends AsyncTask<Void, Void, Void> {
        private GetURL() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Void doInBackground(Void... voidArr) {
            new HttpHandler();
            try {
                try {
                    JSONArray jSONArray = new JSONObject(JsonData.JSONFILE).getJSONArray("animal");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        SweetCameraActivity.this.animalList.add(jSONArray.getJSONObject(i).getString("image_url"));
                    }
                    return null;
                } catch (JSONException e) {
                    Log.e(SweetCameraActivity.TAG, "Json parsing error: " + e.getMessage());
                    SweetCameraActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Context applicationContext = SweetCameraActivity.this.getApplicationContext();
                            Toast.makeText(applicationContext, "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    return null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            for (int i = 0; i < SweetCameraActivity.this.animalList.size(); i++) {
                PrintStream printStream = System.out;
                printStream.println("@@@@" + SweetCameraActivity.this.animalList.get(i));
            }
        }
    }

    private void createCameraSource() {
        Context applicationContext = getApplicationContext();
        FaceDetector build = new FaceDetector.Builder(applicationContext).setClassificationType(1).build();
        build.setProcessor(new MultiProcessor.Builder(new GraphicFaceTrackerFactory()).build());
        if (!build.isOperational()) {
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }
        this.mCameraSource = new CameraSource.Builder(applicationContext, build).setAutoFocusEnabled(true).setRequestedPreviewSize(1280, 960).setFacing(1).setRequestedFps(30.0f).build();
    }


    @SuppressLint({"NewApi"})
    public void startCameraSource() {
        int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());
        if (isGooglePlayServicesAvailable != 0) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, isGooglePlayServicesAvailable, 9001).show();
        }
        CameraSource cameraSource = this.mCameraSource;
        if (cameraSource != null) {
            try {
                if (checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED) {
                    this.mPreview.start(cameraSource, this.mGraphicOverlay);
                }
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                this.mCameraSource.release();
                this.mCameraSource = null;
            }
        }
    }

    private class GraphicFaceTracker extends Tracker<Face> {
        private FaceGraphic mFaceGraphic;
        private GraphicOverlay mOverlay;

        GraphicFaceTracker(GraphicOverlay graphicOverlay) {
            this.mOverlay = graphicOverlay;
            this.mFaceGraphic = new FaceGraphic(graphicOverlay);
        }

        public void onNewItem(int i, Face face) {
            this.mFaceGraphic.setId(i);
        }

        public void onUpdate(Detector.Detections<Face> detections, Face face) {
            this.mOverlay.add(this.mFaceGraphic);
            this.mFaceGraphic.updateFace(face);
        }

        public void onMissing(Detector.Detections<Face> detections) {
            this.mOverlay.remove(this.mFaceGraphic);
        }

        public void onDone() {
            this.mOverlay.remove(this.mFaceGraphic);
        }
    }

    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        private GraphicFaceTrackerFactory() {
        }

        public Tracker<Face> create(Face face) {
            SweetCameraActivity sweetCameraActivity = SweetCameraActivity.this;
            return new GraphicFaceTracker(sweetCameraActivity.mGraphicOverlay);
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), CameraStartActivity.class));
    }
}

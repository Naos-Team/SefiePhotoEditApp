package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.GlobalConfig;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.encoder.MediaCodecUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base.PassThroughFilter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.ext.BlurredFrameEffect;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterType;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.flyu.EffectAdapter;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.flyu.hardcode.HardCodeData;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.CameraView;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.GLRootView;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.module.EffectsButton;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.module.RecordButton;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.AnimationUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BitmapUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.DisplayUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.FileUtils;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class CameraPreviewActivity extends AppCompatActivity {
    private static final boolean DEBUG = false;
    private static final String TAG = "CameraPreviewActivity";

    public EffectsButton appSettingBtn;

    public RelativeLayout bottomControlPanel;
    private ImageView cameraActionTip;

    public ImageView cameraFocusView;

    public CameraSettingBtn cameraLightBtn;

    public CameraSettingBtn cameraPictureTypeBtn;

    public EffectsButton cameraSettingBtn;
    private RelativeLayout cameraSettingsFrag;

    public CameraSettingBtn cameraTimeLapseBtn;

    public CameraSettingBtn cameraTouchBtn;

    public CameraView cameraView;

    public boolean canUseMediaCodec;

    public CaptureAnimation captureAnimation;
    private RecyclerView effectListView;
    private RecyclerView filterListView;
    private GLRootView glRootView;

    public boolean isRecording = false;
    private RecordButton recordButton;

    public int surfaceHeight;

    public int surfaceWidth;

    public EffectsButton switchCameraBtn;

    public EffectsButton switchFaceBtn;

    public EffectsButton switchFilterBtn;

    public int timeCountDown;
    final Handler timeCountDownHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                CameraPreviewActivity.access$3110(CameraPreviewActivity.this);
                if (CameraPreviewActivity.this.timeCountDown > 0) {
                    TextView access$3200 = CameraPreviewActivity.this.timeCountDownText;
                    access$3200.setText("" + CameraPreviewActivity.this.timeCountDown);
                    AnimationUtils.displayAnim(CameraPreviewActivity.this.timeCountDownText, GlobalConfig.context, R.anim.anim_text_scale, 0);
                    CameraPreviewActivity.this.timeCountDownHandler.sendMessageDelayed(CameraPreviewActivity.this.timeCountDownHandler.obtainMessage(1), 1000);
                } else {
                    CameraPreviewActivity.this.timeCountDownText.setVisibility(View.GONE);
                    CameraPreviewActivity.this.takePic();
                }
            }
            super.handleMessage(message);
        }
    };

    public TextView timeCountDownText;

    static  int access$3110(CameraPreviewActivity cameraPreviewActivity) {
        int i = cameraPreviewActivity.timeCountDown;
        cameraPreviewActivity.timeCountDown = i - 1;
        return i;
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }

    private void init() {
        GlobalConfig.context = this;
        FileUtils.upZipFile(this, "filter/thumbs/thumbs.zip", getFilesDir().getAbsolutePath());
        getWindow().setFlags(1024, 1024);
        boolean z = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.camera_preview);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= 128;
        getWindow().setAttributes(attributes);
        this.glRootView = (GLRootView) findViewById(R.id.camera_view);
        this.cameraView = new CameraView(this, this.glRootView);
        this.cameraView.setScreenSizeChangedListener(new CameraView.ScreenSizeChangedListener() {
            public void updateScreenSize(int i, int i2) {
                Log.d(CameraPreviewActivity.TAG, "updateScreenSize: " + i + " " + i2);
                int unused = CameraPreviewActivity.this.surfaceWidth = i;
                int unused2 = CameraPreviewActivity.this.surfaceHeight = i2;
                CameraPreviewActivity.this.captureAnimation.resetAnimationSize(i, i2);
            }
        });
        this.captureAnimation = (CaptureAnimation) findViewById(R.id.capture_animation_view);
        findViewById(R.id.record_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CameraPreviewActivity.this.isRecording) {
                    CameraPreviewActivity.this.cameraView.getCameraEngine().releaseRecorder();
                    CameraPreviewActivity.this.showHint("stop record");
                } else {
                    CameraPreviewActivity.this.cameraView.getCameraEngine().startRecordingVideo();
                    CameraPreviewActivity.this.showHint("start record");
                }
                CameraPreviewActivity cameraPreviewActivity = CameraPreviewActivity.this;
                boolean unused = cameraPreviewActivity.isRecording = !cameraPreviewActivity.isRecording;
            }
        });
        this.filterListView = (RecyclerView) findViewById(R.id.filter_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        this.filterListView.setLayoutManager(linearLayoutManager);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < FilterType.values().length; i++) {
            arrayList.add(FilterType.values()[i]);
            if (i == 0) {
                arrayList.add(FilterType.NONE);
            }
        }
        FilterAdapter filterAdapter = new FilterAdapter(this, arrayList);
        this.filterListView.setAdapter(filterAdapter);
        filterAdapter.setOnFilterChangeListener(new FilterAdapter.OnFilterChangeListener() {
            public void onFilterChanged(FilterType filterType) {
                CameraPreviewActivity.this.cameraView.getGlRender().switchLastFilterOfCustomizedFilters(filterType);
            }
        });
        initButtons();
        this.cameraSettingsFrag = (RelativeLayout) findViewById(R.id.rl_camera_setting_content);
        this.cameraSettingsFrag.setVisibility(View.GONE);
        this.cameraActionTip = (ImageView) findViewById(R.id.iv_frag_camera_action_tip);
        this.bottomControlPanel = (RelativeLayout) findViewById(R.id.bottom_control_panel);
        if (!(MediaCodecUtils.checkMediaCodecVideoEncoderSupport() == 1 && MediaCodecUtils.checkMediaCodecAudioEncoderSupport() == 1)) {
            z = false;
        }
        this.canUseMediaCodec = z;
        this.recordButton.setRecordable(this.canUseMediaCodec);
        this.timeCountDownText = (TextView) findViewById(R.id.tv_frag_camera_time_lapse_number);
        this.effectListView = (RecyclerView) findViewById(R.id.effect_list);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        this.effectListView.setLayoutManager(linearLayoutManager2);
        HardCodeData.initHardCodeData();
        EffectAdapter effectAdapter = new EffectAdapter(this, HardCodeData.itemList);
        this.effectListView.setAdapter(effectAdapter);
        effectAdapter.setOnEffectChangeListener(new EffectAdapter.OnEffectChangeListener() {
            public void onFilterChanged(HardCodeData.EffectItem effectItem) {
            }
        });
    }


    public void hideAllControlBtn() {
        AnimationUtils.displayAnim(this.switchFilterBtn, GlobalConfig.context, R.anim.fast_faded_out, 8);
        AnimationUtils.displayAnim(this.switchFaceBtn, GlobalConfig.context, R.anim.fast_faded_out, 8);
        AnimationUtils.displayAnim(this.cameraSettingBtn, GlobalConfig.context, R.anim.fast_faded_out, 8);
        AnimationUtils.displayAnim(this.switchCameraBtn, GlobalConfig.context, R.anim.fast_faded_out, 8);
        AnimationUtils.displayAnim(this.appSettingBtn, GlobalConfig.context, R.anim.fast_faded_out, 8);
        requestHideCameraSettingsFrag();
    }


    public void showAllControlBtn() {
        AnimationUtils.displayAnim(this.switchFilterBtn, GlobalConfig.context, R.anim.fast_faded_in, 0);
        AnimationUtils.displayAnim(this.switchFaceBtn, GlobalConfig.context, R.anim.fast_faded_in, 0);
        AnimationUtils.displayAnim(this.cameraSettingBtn, GlobalConfig.context, R.anim.fast_faded_in, 0);
        AnimationUtils.displayAnim(this.switchCameraBtn, GlobalConfig.context, R.anim.fast_faded_in, 0);
    }

    private void initButtons() {
        this.switchFilterBtn = getEffectsBtn(R.id.btn_switch_filter);
        this.switchFilterBtn.setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.hideTips();
                CameraPreviewActivity.this.switchFilterBtn.setSelected(false);
                CameraPreviewActivity.this.requestShowFilterView();
            }
        });
        this.switchFaceBtn = getEffectsBtn(R.id.btn_switch_face);
        this.switchFaceBtn.setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.hideTips();
                CameraPreviewActivity.this.switchFaceBtn.setSelected(false);
                CameraPreviewActivity.this.requestShowEffectView();
            }
        });
        this.switchCameraBtn = getEffectsBtn(R.id.btn_switch_camera);
        this.switchCameraBtn.setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.switchCameraBtn.setSelected(!CameraPreviewActivity.this.switchCameraBtn.isSelected());
                CameraPreviewActivity.this.cameraView.getGlRender().switchCamera();
            }
        });
        this.cameraSettingBtn = getEffectsBtn(R.id.btn_camera_setting);
        this.cameraSettingBtn.setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                if (CameraPreviewActivity.this.cameraSettingBtn.isSelected()) {
                    CameraPreviewActivity.this.requestHideCameraSettingsFrag();
                } else {
                    CameraPreviewActivity.this.requestShowCameraSettingsFrag();
                }
            }
        });
        this.cameraView.setRootViewClickListener(new CameraView.RootViewClickListener() {
            public void onRootViewTouched(MotionEvent motionEvent) {
                CameraPreviewActivity.this.displayFocusAnim(motionEvent);
            }

            public void onRootViewClicked() {
                CameraPreviewActivity.this.requestHideCameraSettingsFrag();
                CameraPreviewActivity.this.hideTips();
                if (CameraPreviewActivity.this.bottomControlPanel.getVisibility() == 8) {
                    CameraPreviewActivity.this.requestHideFilterAndEffectView();
                    AnimationUtils.displayAnim(CameraPreviewActivity.this.bottomControlPanel, GlobalConfig.context, R.anim.fast_faded_in, 0);
                }
            }

            public void onRootViewLongClicked() {
                if (CameraPreviewActivity.this.cameraTouchBtn.isSelected()) {
                    CameraPreviewActivity.this.requestTakePicture();
                }
            }
        });
        this.cameraTouchBtn = new CameraSettingBtn(R.id.btn_camera_touch, R.id.tv_camera_touch).register(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.cameraTouchBtn.changeState();
            }
        });
        this.cameraTimeLapseBtn = new CameraSettingBtn(R.id.btn_camera_time_lapse, R.id.tv_camera_time_lapse).register(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.cameraTimeLapseBtn.changeState();
            }
        });
        this.cameraLightBtn = new CameraSettingBtn(R.id.btn_camera_light, R.id.tv_camera_light).register(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.cameraLightBtn.changeState();
                if (CameraPreviewActivity.this.cameraLightBtn.isSelected()) {
                    CameraPreviewActivity.this.cameraView.getCameraEngine().requestOpenFlashLight(true);
                } else {
                    CameraPreviewActivity.this.cameraView.getCameraEngine().requestCloseFlashLight();
                }
            }
        });
        this.cameraPictureTypeBtn = new CameraSettingBtn(R.id.btn_camera_picture_type, R.id.tv_camera_picture_type).register(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.cameraPictureTypeBtn.changeState();
                if (CameraPreviewActivity.this.cameraPictureTypeBtn.isSelected()) {
                    CameraPreviewActivity.this.cameraView.getGlRender().switchFilterOfPostProcessAtPos(new BlurredFrameEffect(GlobalConfig.context), 0);
                } else {
                    CameraPreviewActivity.this.cameraView.getGlRender().switchFilterOfPostProcessAtPos(new PassThroughFilter(GlobalConfig.context), 0);
                }
            }
        });
        this.recordButton = (RecordButton) findViewById(R.id.btn_takePicture);
        this.recordButton.setClickListener(new RecordButton.ClickListener() {
            public void onClick() {
                CameraPreviewActivity.this.requestTakePicture();
            }

            public void onLongClickStart() {
                CameraPreviewActivity.this.hideTips();
                CameraPreviewActivity.this.hideAllControlBtn();
                if (CameraPreviewActivity.this.canUseMediaCodec) {
                    CameraPreviewActivity.this.cameraView.getGlRender().setFileSavedCallback(new FileUtils.FileSavedCallback() {
                        public void onFileSaved(String str) {
                            CameraPreviewActivity.this.startDecorateActivity(str, 0);
                        }
                    });
                    CameraPreviewActivity.this.cameraView.getGlRender().startRecording();
                } else {
                    CameraPreviewActivity cameraPreviewActivity = CameraPreviewActivity.this;
                    cameraPreviewActivity.showHint(cameraPreviewActivity.getString(R.string.not_support_media_codec));
                }
                Log.d(CameraPreviewActivity.TAG, "onLongClickStart: ");
            }

            public void onLongClickEnd() {
                CameraPreviewActivity.this.showAllControlBtn();
                if (CameraPreviewActivity.this.canUseMediaCodec) {
                    CameraPreviewActivity.this.cameraView.getGlRender().stopRecording();
                }
                Log.d(CameraPreviewActivity.TAG, "onLongClickEnd: ");
            }
        });
        this.appSettingBtn = getEffectsBtn(R.id.btn_app_setting);
        this.appSettingBtn.setOnClickEffectButtonListener(new EffectsButton.OnClickEffectButtonListener() {
            public void onClickEffectButton() {
                CameraPreviewActivity.this.appSettingBtn.setSelected(!CameraPreviewActivity.this.appSettingBtn.isSelected());
                CameraPreviewActivity.this.showHint("Apps设置还木有做");
            }
        });
        this.cameraFocusView = (ImageView) findViewById(R.id.iv_focus_anim_view);
    }


    public void startDecorateActivity(String str, int i) {
        Intent intent = new Intent(this, DecorateActivity.class);
        intent.putExtra(DecorateActivity.SAVED_MEDIA_FILE, str);
        intent.putExtra(DecorateActivity.SAVED_MEDIA_TYPE, i);
        startActivity(intent);
    }


    public void hideTips() {
        this.cameraActionTip.setVisibility(View.GONE);
    }


    public void displayFocusAnim(MotionEvent motionEvent) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.cameraFocusView.getLayoutParams();
        layoutParams.leftMargin = (int) (motionEvent.getX() - ((float) (DisplayUtils.getRefLength(GlobalConfig.context, 150.0f) / 2)));
        layoutParams.topMargin = (int) (motionEvent.getY() - ((float) (DisplayUtils.getRefLength(GlobalConfig.context, 150.0f) / 2)));
        this.cameraFocusView.setLayoutParams(layoutParams);
        this.cameraFocusView.clearAnimation();
        Animation loadAnimation = android.view.animation.AnimationUtils.loadAnimation(GlobalConfig.context, R.anim.anim_camera_focus);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                CameraPreviewActivity.this.cameraFocusView.setVisibility(View.GONE);
            }
        });
        this.cameraFocusView.setVisibility(View.VISIBLE);
        this.cameraFocusView.startAnimation(loadAnimation);
    }


    public void takePic() {
        hideTips();
        this.captureAnimation.setVisibility(View.VISIBLE);
        this.captureAnimation.startAnimation();
        this.cameraView.getGlRender().addPostDrawTask(new Runnable() {
            public void run() {
                BitmapUtils.sendImage(CameraPreviewActivity.this.surfaceWidth, CameraPreviewActivity.this.surfaceHeight, GlobalConfig.context, new FileUtils.FileSavedCallback() {
                    public void onFileSaved(String str) {
                        CameraPreviewActivity.this.startDecorateActivity(str, 1);
                    }
                });
            }
        });
    }


    public void requestTakePicture() {
        if (this.cameraTimeLapseBtn.isSelected()) {
            this.timeCountDown = 4;
            this.timeCountDownHandler.sendMessage(this.timeCountDownHandler.obtainMessage(1));
            return;
        }
        takePic();
    }


    public void requestShowFilterView() {
        if (!this.switchFilterBtn.isSelected()) {
            AnimationUtils.displayAnim(this.filterListView, GlobalConfig.context, R.anim.anim_gallery_show, 0);
            AnimationUtils.displayAnim(this.bottomControlPanel, GlobalConfig.context, R.anim.fast_faded_out, 8);
            this.switchFilterBtn.setSelected(true);
        }
    }


    public void requestShowEffectView() {
        if (!this.switchFaceBtn.isSelected()) {
            AnimationUtils.displayAnim(this.effectListView, GlobalConfig.context, R.anim.anim_gallery_show, 0);
            AnimationUtils.displayAnim(this.bottomControlPanel, GlobalConfig.context, R.anim.fast_faded_out, 8);
            this.switchFaceBtn.setSelected(true);
        }
    }


    public void requestHideFilterAndEffectView() {
        if (this.switchFilterBtn.isSelected()) {
            AnimationUtils.displayAnim(this.filterListView, GlobalConfig.context, R.anim.anim_gallery_hide, 8);
            this.switchFilterBtn.setSelected(false);
        }
        if (this.switchFaceBtn.isSelected()) {
            AnimationUtils.displayAnim(this.effectListView, GlobalConfig.context, R.anim.anim_gallery_hide, 8);
            this.switchFaceBtn.setSelected(false);
        }
    }


    public void requestShowCameraSettingsFrag() {
        if (!this.cameraSettingBtn.isSelected()) {
            AnimationUtils.displayAnim(this.cameraSettingsFrag, GlobalConfig.context, R.anim.anim_setting_content_show, 0);
            this.cameraSettingBtn.setSelected(true);
        }
    }


    public void requestHideCameraSettingsFrag() {
        if (this.cameraSettingBtn.isSelected()) {
            AnimationUtils.displayAnim(this.cameraSettingsFrag, GlobalConfig.context, R.anim.anim_setting_content_hide, 8);
            this.cameraSettingBtn.setSelected(false);
        }
    }


    public void onPause() {
        super.onPause();
        CameraView cameraView2 = this.cameraView;
        if (cameraView2 != null) {
            cameraView2.onPause();
        }
    }


    public void onResume() {
        super.onResume();
        CameraView cameraView2 = this.cameraView;
        if (cameraView2 != null) {
            cameraView2.onResume();
        }
    }


    public void onDestroy() {
        super.onDestroy();
        CameraView cameraView2 = this.cameraView;
        if (cameraView2 != null) {
            cameraView2.onDestroy();
        }
    }


    public void showHint(final String str) {
        runOnUiThread(new Runnable() {
            public void run() {
                TastyToast.makeText(CameraPreviewActivity.this.getApplicationContext(), str, 1, 4);
            }
        });
    }


    public EffectsButton getEffectsBtn(int i) {
        return (EffectsButton) findViewById(i);
    }


    public TextView getTextView(int i) {
        return (TextView) findViewById(i);
    }

    class CameraSettingBtn {
        EffectsButton effectsButton;
        TextView hintText;

        CameraSettingBtn(int i, int i2) {
            this.effectsButton = CameraPreviewActivity.this.getEffectsBtn(i);
            this.hintText = CameraPreviewActivity.this.getTextView(i2);
        }


        public boolean isSelected() {
            return this.effectsButton.isSelected();
        }


        public void changeState() {
            if (!this.effectsButton.isSelected()) {
                this.effectsButton.setSelected(true);
                this.hintText.setTextColor(GlobalConfig.context.getResources().getColor(R.color.app_color));
                return;
            }
            this.effectsButton.setSelected(false);
            this.hintText.setTextColor(-1);
        }


        public CameraSettingBtn register(EffectsButton.OnClickEffectButtonListener onClickEffectButtonListener) {
            this.effectsButton.setOnClickEffectButtonListener(onClickEffectButtonListener);
            return this;
        }
    }
}

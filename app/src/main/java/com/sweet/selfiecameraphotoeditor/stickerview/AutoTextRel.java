package com.sweet.selfiecameraphotoeditor.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.sweet.selfiecameraphotoeditor.R;

public class AutoTextRel extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    double angle = 0.0d;
    public ImageView backgroundIv;
    int baseh;
    int basew;
    int basex;
    int basey;
     int bgAlpha = 255;
     int bgColor = 0;
    public String bgDrawable = "0";
     ImageView borderIv;
     Context context;
    double dAngle = 0.0d;
     ImageView deleteIv;
    public int f23s;
    float f2473cX = 0.0f;
    float f2474cY = 0.0f;
     GestureDetector f2475gd = null;
    public int f2476he;
    public int f2477wi;
     String fieldFour = "";
     int fieldOne = 0;
     String fieldThree = "";
    public String fieldTwo = "0,0";
     String fontName = "";
     boolean isBorderVisible = false;
    public int leftMargin = 0;
    public TouchEventListener listener = null;
     OnTouchListener mTouchListener1 = new C06073();
    int margl;
    int margt;
     int progress = 0;
     OnTouchListener rTouchListener = new C06062();
     ImageView rotateIv;
     float rotation;
    Animation scale;
     ImageView scale_iv;
     int shadowColor = 0;
     int shadowProg = 0;
     int tAlpha = 100;
    double tAngle = 0.0d;
     int tColor = ViewCompat.MEASURED_STATE_MASK;
     String text = "";
    public AutoResizeTextView text_iv;
    public int topMargin = 0;
    double vAngle = 0.0d;
     int xRotateProg = 0;
     int yRotateProg = 0;
     int zRotateProg = 0;
    Animation zoomInScale;
    Animation zoomOutScale;

    public interface TouchEventListener {
        void onDelete();

        void onDoubleTap();

        void onEdit(View view, Uri uri);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    class C06051 implements OnClickListener {
        C06051() {
        }

        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) AutoTextRel.this.getParent();
            AutoTextRel.this.zoomInScale.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                    Log.d("","");
                }

                public void onAnimationStart(Animation animation) {
                    Log.d("","");
                }

                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(AutoTextRel.this);
                }
            });
            AutoTextRel.this.text_iv.startAnimation(AutoTextRel.this.zoomInScale);
            AutoTextRel.this.backgroundIv.startAnimation(AutoTextRel.this.zoomInScale);
            AutoTextRel.this.setBorderVisibility(false);
            if (AutoTextRel.this.listener != null) {
                AutoTextRel.this.listener.onDelete();
            }
        }
    }

    class C06062 implements OnTouchListener {
        C06062() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                if (AutoTextRel.this.listener != null) {
                    AutoTextRel.this.listener.onRotateDown(view);
                }
                Rect rect = new Rect();
                ((View) view.getParent()).getGlobalVisibleRect(rect);
                AutoTextRel.this.f2473cX = rect.exactCenterX();
                AutoTextRel.this.f2474cY = rect.exactCenterY();
                AutoTextRel.this.vAngle = (double) ((View) view.getParent()).getRotation();
                AutoTextRel autoTextRel = AutoTextRel.this;
                autoTextRel.tAngle = (Math.atan2((double) (autoTextRel.f2474cY - motionEvent.getRawY()), (double) (AutoTextRel.this.f2473cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                AutoTextRel autoTextRel2 = AutoTextRel.this;
                autoTextRel2.dAngle = autoTextRel2.vAngle - AutoTextRel.this.tAngle;
            } else if (action != 1) {
                if (action == 2) {
                    if (AutoTextRel.this.listener != null) {
                        AutoTextRel.this.listener.onRotateMove(view);
                    }
                    AutoTextRel autoTextRel3 = AutoTextRel.this;
                    autoTextRel3.angle = (Math.atan2((double) (autoTextRel3.f2474cY - motionEvent.getRawY()), (double) (AutoTextRel.this.f2473cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (AutoTextRel.this.angle + AutoTextRel.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                }
            } else if (AutoTextRel.this.listener != null) {
                AutoTextRel.this.listener.onRotateUp(view);
            }
            return true;
        }
    }

    class C06073 implements OnTouchListener {
        C06073() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutoTextRel.this.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (AutoTextRel.this.listener != null) {
                    AutoTextRel.this.listener.onScaleDown(view);
                }
                AutoTextRel.this.invalidate();
                AutoTextRel autoTextRel = AutoTextRel.this;
                autoTextRel.basex = rawX;
                autoTextRel.basey = rawY;
                autoTextRel.basew = autoTextRel.getWidth();
                AutoTextRel autoTextRel2 = AutoTextRel.this;
                autoTextRel2.baseh = autoTextRel2.getHeight();
                AutoTextRel.this.getLocationOnScreen(new int[2]);
                AutoTextRel.this.margl = layoutParams.leftMargin;
                AutoTextRel.this.margt = layoutParams.topMargin;
            } else if (action == 1) {
                AutoTextRel autoTextRel3 = AutoTextRel.this;
                autoTextRel3.f2477wi = autoTextRel3.getLayoutParams().width;
                AutoTextRel autoTextRel4 = AutoTextRel.this;
                autoTextRel4.f2476he = autoTextRel4.getLayoutParams().height;
                AutoTextRel autoTextRel5 = AutoTextRel.this;
                autoTextRel5.leftMargin = ((LayoutParams) autoTextRel5.getLayoutParams()).leftMargin;
                AutoTextRel autoTextRel6 = AutoTextRel.this;
                autoTextRel6.topMargin = ((LayoutParams) autoTextRel6.getLayoutParams()).topMargin;
                AutoTextRel autoTextRel7 = AutoTextRel.this;
                autoTextRel7.fieldTwo = String.valueOf(AutoTextRel.this.leftMargin) + "," + String.valueOf(AutoTextRel.this.topMargin);
                if (AutoTextRel.this.listener != null) {
                    AutoTextRel.this.listener.onScaleUp(view);
                }
            } else if (action == 2) {
                if (AutoTextRel.this.listener != null) {
                    AutoTextRel.this.listener.onScaleMove(view);
                }
                float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - AutoTextRel.this.basey), (double) (rawX - AutoTextRel.this.basex)));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - AutoTextRel.this.basex;
                int i2 = rawY - AutoTextRel.this.basey;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - AutoTextRel.this.getRotation()))));
                int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - AutoTextRel.this.getRotation()))));
                int i4 = (sqrt * 2) + AutoTextRel.this.basew;
                int i5 = (sqrt2 * 2) + AutoTextRel.this.baseh;
                if (i4 > AutoTextRel.this.f23s) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = AutoTextRel.this.margl - sqrt;
                }
                if (i5 > AutoTextRel.this.f23s) {
                    layoutParams.height = i5;
                    layoutParams.topMargin = AutoTextRel.this.margt - sqrt2;
                }
                AutoTextRel.this.setLayoutParams(layoutParams);
                if (!AutoTextRel.this.bgDrawable.equals("0")) {
                    AutoTextRel autoTextRel8 = AutoTextRel.this;
                    autoTextRel8.f2477wi = autoTextRel8.getLayoutParams().width;
                    AutoTextRel autoTextRel9 = AutoTextRel.this;
                    autoTextRel9.f2476he = autoTextRel9.getLayoutParams().height;
                    AutoTextRel autoTextRel10 = AutoTextRel.this;
                    autoTextRel10.setBgDrawable(autoTextRel10.bgDrawable);
                }
            }
            return true;
        }
    }



    class C06084 extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        C06084() {
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (AutoTextRel.this.listener == null) {
                return true;
            }
            AutoTextRel.this.listener.onDoubleTap();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }
    }

    public AutoTextRel setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        return this;
    }

    public AutoTextRel(Context context2) {
        super(context2);
        init(context2);
    }

    public AutoTextRel(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2);
    }

    public AutoTextRel(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        init(context2);
    }

    public void init(Context context2) {
        this.context = context2;
        this.text_iv = new AutoResizeTextView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.borderIv = new ImageView(this.context);
        this.backgroundIv = new ImageView(this.context);
        this.deleteIv = new ImageView(this.context);
        this.rotateIv = new ImageView(this.context);
        this.f23s = dpToPx(this.context, 30);
        this.f2477wi = dpToPx(this.context, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.f2476he = dpToPx(this.context, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.scale_iv.setImageResource(R.drawable.resize);
        this.backgroundIv.setImageResource(0);
        this.rotateIv.setImageResource(R.drawable.rotate);
        this.deleteIv.setImageResource(R.drawable.close);
        LayoutParams layoutParams = new LayoutParams(this.f2477wi, this.f2476he);
        int i = this.f23s;
        LayoutParams layoutParams2 = new LayoutParams(i, i);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        layoutParams2.setMargins(5, 5, 5, 5);
        int i2 = this.f23s;
        LayoutParams layoutParams3 = new LayoutParams(i2, i2);
        layoutParams3.addRule(12);
        layoutParams3.addRule(9);
        layoutParams3.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams4 = new LayoutParams(-1, -1);
        layoutParams4.setMargins(5, 5, 5, 5);
        layoutParams4.addRule(17);
        int i3 = this.f23s;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(10);
        layoutParams5.addRule(9);
        layoutParams5.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.sticker_border);
        addView(this.backgroundIv);
        this.backgroundIv.setLayoutParams(layoutParams7);
        this.backgroundIv.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(this.borderIv);
        this.borderIv.setLayoutParams(layoutParams6);
        this.borderIv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(400.0f);
        this.text_iv.setLayoutParams(layoutParams4);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(10.0f);
        addView(this.deleteIv);
        this.deleteIv.setLayoutParams(layoutParams5);
        this.deleteIv.setOnClickListener(new C06051());
        addView(this.rotateIv);
        this.rotateIv.setLayoutParams(layoutParams3);
        this.rotateIv.setOnTouchListener(this.rTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams2);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in);
        initGD();
        setDefaultTouchListener();
    }

    public void setDefaultTouchListener() {
        setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.f2475gd));
    }

    @SuppressLint({"WrongConstant"})
    public void setBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.borderIv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.deleteIv.setVisibility(View.GONE);
            this.rotateIv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.borderIv.getVisibility() != 0) {
            this.borderIv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.deleteIv.setVisibility(View.VISIBLE);
            this.rotateIv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.sticker_border);
            this.text_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setText(String str) {
        this.text_iv.setText(str);
        this.text = str;
        this.text_iv.startAnimation(this.zoomOutScale);
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setTextFont(String str) {
        try {
            AutoResizeTextView autoResizeTextView = this.text_iv;
            AssetManager assets = this.context.getAssets();
            autoResizeTextView.setTypeface(Typeface.createFromAsset(assets, "fonts/" + str));
            this.fontName = str;
        } catch (Exception unused) {
            Log.d("","");
        }
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setTextColor(int i) {
        this.text_iv.setTextColor(i);
        this.tColor = i;
    }

    public int getTextColor() {
        return this.tColor;
    }

    public void setTextAlpha(int i) {
        this.text_iv.setAlpha(((float) i) / 100.0f);
        this.tAlpha = i;
    }

    public int getTextAlpha() {
        return this.tAlpha;
    }

    public void setTextShadowColor(int i) {
        this.shadowColor = i;
        this.text_iv.setShadowLayer((float) this.shadowProg, 0.0f, 0.0f, this.shadowColor);
    }

    public int getTextShadowColor() {
        return this.shadowColor;
    }

    public void setTextShadowProg(int i) {
        this.shadowProg = i;
        this.text_iv.setShadowLayer((float) this.shadowProg, 0.0f, 0.0f, this.shadowColor);
    }

    public int getTextShadowProg() {
        return this.shadowProg;
    }

    public void setBgDrawable(String str) {
        this.bgDrawable = str;
        this.bgColor = 0;
        this.backgroundIv.setImageBitmap(getTiledBitmap(this.context, getResources().getIdentifier(str, "drawable", this.context.getPackageName()), this.f2477wi, this.f2476he));
        this.backgroundIv.setBackgroundColor(this.bgColor);
    }

    public String getBgDrawable() {
        return this.bgDrawable;
    }

    public void setBgColor(int i) {
        this.bgDrawable = "0";
        this.bgColor = i;
        this.backgroundIv.setImageBitmap((Bitmap) null);
        this.backgroundIv.setBackgroundColor(i);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgAlpha(int i) {
        this.backgroundIv.setAlpha(((float) i) / 255.0f);
        this.bgAlpha = i;
    }

    public int getBgAlpha() {
        return this.bgAlpha;
    }

    public TextInfo getTextInfo() {
        TextInfo textInfo = new TextInfo();
        textInfo.setPOS_X(getX());
        textInfo.setPOS_Y(getY());
        textInfo.setWIDTH(this.f2477wi);
        textInfo.setHEIGHT(this.f2476he);
        textInfo.setTEXT(this.text);
        textInfo.setFONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setSHADOW_COLOR(this.shadowColor);
        textInfo.setSHADOW_PROG(this.shadowProg);
        textInfo.setBG_COLOR(this.bgColor);
        textInfo.setBG_DRAWABLE(this.bgDrawable);
        textInfo.setBG_ALPHA(this.bgAlpha);
        textInfo.setROTATION(getRotation());
        textInfo.setXRotateProg(this.xRotateProg);
        textInfo.setYRotateProg(this.yRotateProg);
        textInfo.setZRotateProg(this.zRotateProg);
        textInfo.setCurveRotateProg(this.progress);
        textInfo.setFIELD_ONE(this.fieldOne);
        textInfo.setFIELD_TWO(this.fieldTwo);
        textInfo.setFIELD_THREE(this.fieldThree);
        textInfo.setFIELD_FOUR(this.fieldFour);
        return textInfo;
    }

    public void setTextInfo(TextInfo textInfo, boolean z) {
        this.f2477wi = textInfo.getWIDTH();
        this.f2476he = textInfo.getHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.getFONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.shadowColor = textInfo.getSHADOW_COLOR();
        this.shadowProg = textInfo.getSHADOW_PROG();
        this.bgColor = textInfo.getBG_COLOR();
        this.bgDrawable = textInfo.getBG_DRAWABLE();
        this.bgAlpha = textInfo.getBG_ALPHA();
        this.rotation = textInfo.getROTATION();
        this.fieldTwo = textInfo.getFIELD_TWO();
        setText(this.text);
        setTextFont(this.fontName);
        setTextColor(this.tColor);
        setTextAlpha(this.tAlpha);
        setTextShadowColor(this.shadowColor);
        setTextShadowProg(this.shadowProg);
        int i = this.bgColor;
        if (i != 0) {
            setBgColor(i);
        } else {
            this.backgroundIv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.backgroundIv.setImageBitmap((Bitmap) null);
        } else {
            setBgDrawable(this.bgDrawable);
        }
        setBgAlpha(this.bgAlpha);
        setRotation(textInfo.getROTATION());
        if (this.fieldTwo.equals("")) {
            getLayoutParams().width = this.f2477wi;
            getLayoutParams().height = this.f2476he;
            setX(textInfo.getPOS_X());
            setY(textInfo.getPOS_Y());
            return;
        }
        String[] split = this.fieldTwo.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
        ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
        getLayoutParams().width = this.f2477wi;
        getLayoutParams().height = this.f2476he;
        setX(textInfo.getPOS_X() + ((float) (parseInt * -1)));
        setY(textInfo.getPOS_Y() + ((float) (parseInt2 * -1)));
    }

    public void optimize(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (((float) this.f2477wi) * f);
        getLayoutParams().height = (int) (((float) this.f2476he) * f2);
    }

    public void incrX() {
        setX(getX() + 1.0f);
    }

    public void decX() {
        setX(getX() - 1.0f);
    }

    public void incrY() {
        setY(getY() + 1.0f);
    }

    public void decY() {
        setY(getY() - 1.0f);
    }

    public int dpToPx(Context context2, int i) {
        context2.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * ((float) i));
    }

     Bitmap getTiledBitmap(Context context2, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context2.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

     void initGD() {
        this.f2475gd = new GestureDetector(this.context, new C06084());
    }

    public void onTouchCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchDown(view);
        }
    }

    public void onTouchUpCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view);
        }
    }

    public void onTouchMoveCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view);
        }
    }
}

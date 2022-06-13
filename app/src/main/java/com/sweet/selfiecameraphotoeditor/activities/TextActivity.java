package com.sweet.selfiecameraphotoeditor.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.stickerview.AutoEditText;
import com.sweet.selfiecameraphotoeditor.stickerview.HorizontalListView;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2775a;
import com.sweet.selfiecameraphotoeditor.stickerview.view.C2785d;

import yuku.ambilwarna.AmbilWarnaDialog;

public class TextActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    RelativeLayout f12445A;
    LinearLayout f12447C;
    LinearLayout f12448D;
    LinearLayout f12449E;
    LinearLayout f12450F;
    SeekBar f12451G;
    SeekBar f12452H;
    int f12454J = Color.parseColor("#7641b6");
    int f12455K = 5;
    RelativeLayout f12456L;
    int f12457M = 100;
    int f12458N = Color.parseColor("#4149b6");
    String f12459O = "";
    View f12461a;
    boolean f12462b = true;
    String f12463c = "";
    String f12464d = "";
    String[] f12466f = {"#4182b6", "#4149b6", "#7641b6", "#b741a7", "#c54657", "#d1694a", "#24352a", "#b8c847", "#67bb43", "#41b691", "#293b2f", "#1c0101", "#420a09", "#b4794b", "#4b86b4", "#93b6d2", "#72aa52", "#67aa59", "#fa7916", "#16a1fa", "#165efa", "#1697fa"};
    int f12467g = 100;
    int f12468h = 0;
    int f12469i = 0;
    AutoEditText f12470j;
    int f12471k = 255;
    int f12472l = 0;
    String f12473m = "0";
    ImageView f12475o;
    Bundle f12477q;
    RelativeLayout f12478r;
    String f12479s = "";
    RelativeLayout f12480t;
    GridView f12481u;
    CustomTextView f12482v;
    InputMethodManager f12484x;
    boolean f12485y = true;
    ImageView f12486z;
    ImageView imgNext;
    ImageView imgTextadd;
    ImageView imgTextcolor;
    ImageView imgTextshadow;
    ImageView imgTextstyle;
    RelativeLayout layoutkeyboardclose;

    public void m18332a(int i) {
        Log.d("", "");
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d("", "");
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d("", "");
    }

    class C2755a implements Runnable {
        final TextActivity f12435a;

        C2755a(TextActivity textActivity) {
            this.f12435a = textActivity;
        }

        public void run() {
            this.f12435a.m18323c();
            this.f12435a.f12450F.performClick();
        }
    }

    class C2756b implements ViewTreeObserver.OnGlobalLayoutListener {
        final TextActivity f12436a;

        C2756b(TextActivity textActivity) {
            this.f12436a = textActivity;
        }

        @SuppressLint({"WrongConstant"})
        public void onGlobalLayout() {
            TextActivity textActivity = this.f12436a;
            if (textActivity.m18317a(textActivity.f12470j.getRootView())) {
                this.f12436a.f12445A.setVisibility(4);
                this.f12436a.m18332a(R.id.laykeyboard);
                this.f12436a.f12462b = false;
            } else if (!this.f12436a.f12462b) {
                TextActivity textActivity2 = this.f12436a;
                textActivity2.m18332a(textActivity2.f12461a.getId());
                this.f12436a.f12461a.performClick();
            }
        }
    }

    class C2757c implements TextWatcher {
        final TextActivity f12437a;

        public void afterTextChanged(Editable editable) {
            Log.d("", "");
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Log.d("", "");
        }

        C2757c(TextActivity textActivity) {
            this.f12437a = textActivity;
        }

        @SuppressLint({"WrongConstant"})
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.length() == 0) {
                this.f12437a.f12482v.setVisibility(View.GONE);
            } else {
                this.f12437a.f12482v.setVisibility(View.GONE);
            }
        }
    }


    class C2762f implements View.OnClickListener {
        final TextActivity f12442a;

        class C2761a implements AmbilWarnaDialog.OnAmbilWarnaListener {
            final C2762f f12441a;

            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.d("", "");
            }

            C2761a(C2762f c2762f) {
                this.f12441a = c2762f;
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                this.f12441a.f12442a.m18314a(i, 2);
            }
        }

        C2762f(TextActivity textActivity) {
            this.f12442a = textActivity;
        }

        public void onClick(View view) {
            TextActivity textActivity = this.f12442a;
            new AmbilWarnaDialog(textActivity, textActivity.f12454J, new C2761a(this)).show();
        }
    }

    class C2764g implements View.OnClickListener {
        final TextActivity f12444a;

        class C2763a implements AmbilWarnaDialog.OnAmbilWarnaListener {
            final C2764g f12443a;

            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.d("","");
            }

            C2763a(C2764g c2764g) {
                this.f12443a = c2764g;
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                this.f12443a.f12444a.m18314a(i, 1);
            }
        }

        C2764g(TextActivity textActivity) {
            this.f12444a = textActivity;
        }

        public void onClick(View view) {
            TextActivity textActivity = this.f12444a;
            new AmbilWarnaDialog(textActivity, textActivity.f12458N, new C2763a(this)).show();
        }
    }

    public TextActivity() {
        Log.d("", "");
    }

    public Bitmap m18310a(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    @SuppressLint({"WrongConstant"})
    private void m18313a() {
        this.f12481u = (GridView) findViewById(R.id.font_gridview);
        this.f12470j = (AutoEditText) findViewById(R.id.auto_fit_edit_text);
        this.f12486z = (ImageView) findViewById(R.id.lay_back_txt);
        this.f12475o = (ImageView) findViewById(R.id.img_back);
        this.imgNext = (ImageView) findViewById(R.id.img_next);
        this.layoutkeyboardclose = (RelativeLayout) findViewById(R.id.layoutkeyboardclose);
        this.layoutkeyboardclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((InputMethodManager) TextActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(TextActivity.this.f12470j.getWindowToken(), 0);
            }
        });
        this.f12482v = (CustomTextView) findViewById(R.id.hint_txt);
        this.f12445A = (RelativeLayout) findViewById(R.id.lay_below);
        this.f12450F = (LinearLayout) findViewById(R.id.laykeyboard);
        this.f12448D = (LinearLayout) findViewById(R.id.lay_txtfont);
        this.f12447C = (LinearLayout) findViewById(R.id.lay_txtcolor);
        this.f12449E = (LinearLayout) findViewById(R.id.lay_txtshadow);
        this.f12480t = (RelativeLayout) findViewById(R.id.font_grid_rel);
        this.f12478r = (RelativeLayout) findViewById(R.id.color_rel);
        this.f12456L = (RelativeLayout) findViewById(R.id.shadow_rel);
        this.f12461a = this.f12448D;
        this.f12451G = (SeekBar) findViewById(R.id.seekBar1);
        this.f12452H = (SeekBar) findViewById(R.id.seekBar2);
        this.f12451G.setProgress(this.f12467g);
        this.f12470j.addTextChangedListener(new C2757c(this));
        findViewById(R.id.color_picker2).setOnClickListener(new C2762f(this));
        findViewById(R.id.color_picker1).setOnClickListener(new C2764g(this));
        HorizontalListView horizontalListView = (HorizontalListView) findViewById(R.id.color_listview2);
        final C2785d c2785d = new C2785d(this, this.f12466f);
        horizontalListView.setAdapter((ListAdapter) c2785d);
        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                TextActivity.this.m18314a(((Integer) c2785d.getItem(i)).intValue(), 2);
            }
        });
        HorizontalListView horizontalListView2 = (HorizontalListView) findViewById(R.id.color_listview1);
        horizontalListView2.setAdapter((ListAdapter) new C2785d(this, this.f12466f));
        horizontalListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                TextActivity.this.m18314a(((Integer) c2785d.getItem(i)).intValue(), 1);
            }
        });
        this.f12475o.setOnClickListener(this);
        this.imgNext.setOnClickListener(this);
        this.f12450F.setOnClickListener(this);
        this.f12448D.setOnClickListener(this);
        this.f12447C.setOnClickListener(this);
        this.f12449E.setOnClickListener(this);
        this.f12451G.setOnSeekBarChangeListener(this);
        this.f12452H.setOnSeekBarChangeListener(this);
        this.f12452H.setProgress(5);
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.f12470j, 0);
    }

    public void m18314a(int i, int i2) {
        if (i2 == 1) {
            this.f12458N = i;
            this.f12463c = Integer.toHexString(i);
            AutoEditText autoEditText = this.f12470j;
            autoEditText.setTextColor(Color.parseColor("#" + this.f12463c));
        } else if (i2 == 2) {
            this.f12454J = i;
            int progress = this.f12452H.getProgress();
            this.f12464d = Integer.toHexString(i);
            this.f12470j.setShadowLayer((float) progress, 0.0f, 0.0f, Color.parseColor("#" + this.f12464d));
        }
    }

    public boolean m18317a(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return ((float) (view.getBottom() - rect.bottom)) > view.getResources().getDisplayMetrics().density * 128.0f;
    }

    private void m18321b() {
        final C2775a c2775a = new C2775a(this, getResources().getStringArray(R.array.fonts_array));
        this.f12481u = (GridView) findViewById(R.id.font_gridview);
        this.f12481u.setAdapter(c2775a);
        this.f12481u.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                TextActivity.this.f12479s = (String) c2775a.getItem(i);
                Editable text = TextActivity.this.f12470j.getText();
                AutoEditText autoEditText = TextActivity.this.f12470j;
                AssetManager assets = TextActivity.this.getAssets();
                autoEditText.setTypeface(Typeface.createFromAsset(assets, "fonts/" + TextActivity.this.f12479s));
                TextActivity.this.f12470j.setText(text);
                TextActivity.this.f12470j.invalidate();
            }
        });
    }

    @SuppressLint({"WrongConstant"})
    public void m18323c() {
        this.f12477q = getIntent().getExtras();
        Bundle bundle = this.f12477q;
        if (bundle != null) {
            this.f12459O = bundle.getString("text", "");
            this.f12479s = this.f12477q.getString("fontName", "");
            this.f12458N = this.f12477q.getInt("tColor", Color.parseColor("#4149b6"));
            this.f12457M = this.f12477q.getInt("tAlpha", 100);
            this.f12454J = this.f12477q.getInt("shadowColor", Color.parseColor("#7641b6"));
            this.f12455K = this.f12477q.getInt("shadowProg", 5);
            this.f12473m = this.f12477q.getString("bgDrawable", "0");
            this.f12472l = this.f12477q.getInt("bgColor", 0);
            this.f12471k = this.f12477q.getInt("bgAlpha", 255);
            this.f12470j.setText(this.f12459O);
            this.f12451G.setProgress(this.f12457M);
            this.f12452H.setProgress(this.f12455K);
            m18314a(this.f12458N, 1);
            m18314a(this.f12454J, 2);
            if (!this.f12473m.equals("0")) {
                this.f12486z.setImageBitmap(m18310a(this, getResources().getIdentifier(this.f12473m, "drawable", getPackageName()), this.f12470j.getWidth(), this.f12470j.getHeight()));
                this.f12486z.setVisibility(View.VISIBLE);
                this.f12486z.postInvalidate();
                this.f12486z.requestLayout();
            }
            int i = this.f12472l;
            if (i != 0) {
                this.f12486z.setBackgroundColor(i);
                this.f12486z.setVisibility(View.VISIBLE);
            }
            try {
                this.f12470j.setTypeface(Typeface.createFromAsset(getAssets(), this.f12479s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public Bundle m18324d() {
        if (this.f12477q == null) {
            this.f12477q = new Bundle();
        }
        this.f12459O = this.f12470j.getText().toString().trim().replace("\n", " s");
        this.f12477q.putString("text", this.f12459O);
        this.f12477q.putString("fontName", this.f12479s);
        this.f12477q.putInt("tColor", this.f12458N);
        this.f12477q.putInt("tAlpha", this.f12451G.getProgress());
        this.f12477q.putInt("shadowColor", this.f12454J);
        this.f12477q.putInt("shadowProg", this.f12452H.getProgress());
        this.f12477q.putString("bgDrawable", this.f12473m);
        this.f12477q.putInt("bgColor", this.f12472l);
        return this.f12477q;
    }

    @Override
    @SuppressLint({"WrongConstant"})
    public void onBackPressed() {
        backdialog();
    }

    public void backdialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_back);
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.f12470j.getWindowToken(), 0);
        ((CustomTextView) dialog.findViewById(R.id.txt_no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((CustomTextView) dialog.findViewById(R.id.txt_yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextActivity.super.onBackPressed();
            }
        });
        dialog.show();
    }

    @SuppressLint({"WrongConstant"})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.img_next) {
            if (this.f12470j.getText().toString().trim().length() > 0) {
                Ad_class.showInterstitial(TextActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        ((InputMethodManager) TextActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(TextActivity.this.f12470j.getWindowToken(), 0);
                        Intent intent = new Intent();
                        intent.putExtras(TextActivity.this.m18324d());
                        TextActivity.this.setResult(-1, intent);
                        TextActivity.this.finish();
                    }
                });
                return;
            }
            View inflate = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            ((CustomTextView) inflate.findViewById(R.id.txt_toast)).setText("Please Enter Text");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(1);
            toast.setGravity(80, 0, 150);
            toast.setView(inflate);
            toast.show();
        } else if (id == R.id.laykeyboard) {
            this.imgTextadd.setVisibility(View.VISIBLE);
            this.imgTextstyle.setVisibility(View.GONE);
            this.imgTextcolor.setVisibility(View.GONE);
            this.imgTextshadow.setVisibility(View.GONE);
            this.f12485y = true;
            this.f12462b = true;
            m18332a(view.getId());
            this.f12484x.showSoftInput(this.f12470j, 0);
        } else if (id == R.id.lay_txtfont) {
            this.imgTextadd.setVisibility(View.GONE);
            this.imgTextstyle.setVisibility(View.VISIBLE);
            this.imgTextcolor.setVisibility(View.GONE);
            this.imgTextshadow.setVisibility(View.GONE);
            m18332a(view.getId());
            this.f12461a = view;
            this.f12480t.setVisibility(View.VISIBLE);
            this.f12478r.setVisibility(View.GONE);
            this.f12456L.setVisibility(View.GONE);
            this.f12445A.setVisibility(View.VISIBLE);
            this.f12484x.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } else if (id == R.id.lay_txtcolor) {
            this.imgTextadd.setVisibility(View.GONE);
            this.imgTextstyle.setVisibility(View.GONE);
            this.imgTextcolor.setVisibility(View.VISIBLE);
            this.imgTextshadow.setVisibility(View.GONE);
            m18332a(view.getId());
            this.f12461a = view;
            this.f12480t.setVisibility(View.GONE);
            this.f12478r.setVisibility(View.VISIBLE);
            this.f12456L.setVisibility(View.GONE);
            this.f12445A.setVisibility(View.VISIBLE);
            this.f12484x.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } else if (id == R.id.lay_txtshadow) {
            this.imgTextadd.setVisibility(View.GONE);
            this.imgTextstyle.setVisibility(View.GONE);
            this.imgTextcolor.setVisibility(View.GONE);
            this.imgTextshadow.setVisibility(View.VISIBLE);
            m18332a(view.getId());
            this.f12461a = view;
            this.f12480t.setVisibility(View.GONE);
            this.f12478r.setVisibility(View.GONE);
            this.f12456L.setVisibility(View.VISIBLE);
            this.f12445A.setVisibility(View.VISIBLE);
            this.f12484x.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Ad_class.loadAd(this);
    }
    @Override
    @SuppressLint({"WrongConstant"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.text_activity);

        AdView mAdView = findViewById(R.id.adView);
        Ad_class.Show_banner(mAdView);

        getWindow().setFlags(1024, 1024);
        this.imgTextadd = (ImageView) findViewById(R.id.img_textadd);
        this.imgTextstyle = (ImageView) findViewById(R.id.img_textstyle);
        this.imgTextcolor = (ImageView) findViewById(R.id.img_textcolor);
        this.imgTextshadow = (ImageView) findViewById(R.id.img_textshadow);


        this.f12484x = (InputMethodManager) getSystemService("input_method");
        m18313a();
        m18321b();
        this.f12486z.post(new C2755a(this));
        this.f12470j.getViewTreeObserver().addOnGlobalLayoutListener(new C2756b(this));
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        this.f12467g = i;
        this.f12468h = i;
        int id = seekBar.getId();
        if (id == R.id.seekBar1) {
            this.f12470j.setAlpha(((float) seekBar.getProgress()) / ((float) seekBar.getMax()));
        } else if (id != R.id.seekBar2) {
            Log.d("","");
        } else {
            if (this.f12464d.equals("")) {
                this.f12470j.setShadowLayer((float) i, 0.0f, 0.0f, Color.parseColor("#fdab52"));
                return;
            }
            this.f12470j.setShadowLayer((float) i, 0.0f, 0.0f, Color.parseColor("#" + this.f12464d));
        }
    }
}

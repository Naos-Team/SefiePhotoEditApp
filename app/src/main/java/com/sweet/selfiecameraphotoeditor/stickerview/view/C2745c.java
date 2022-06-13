package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.sweet.selfiecameraphotoeditor.R;

public class C2745c extends RelativeLayout implements C2734a.C2732b {
     boolean f12346A = true;
     boolean f12347B = false;
    public C2638f f12348C = null;
     OnTouchListener f12349D = new C2743d(this);
     OnTouchListener f12350E = new C2744e(this);
    public Uri f12351F = null;
     float f12352G;
    public int f12353H;
     ImageView f12354I;
    public double f12355J = -1.0d;
    public double f12356K = -1.0d;
    public float f12357L = -1.0f;
    public float f12358M = -1.0f;
    public float f12359N = -1.0f;
    public float f12360O = -1.0f;
    public int f12361P;
     float f12362Q;
    int f12363a;
    int f12364b;
    int f12365c;
    int f12366d;
    public ImageView f12367e;
    int f12368f;
    int f12369g;
    Animation f12370h;
    Animation f12371i;
    Animation f12372j;
     int f12373k = 0;
     ImageView f12374l;
     Bitmap f12375m = null;
    public double f12376n;
    public double f12377o;
     String f12378p = "color";
     Context f12379q;
     ImageView f12380r;
     int f12381s;
     ImageView f12382t;
     ImageView f12383u;
    public int f12384v;
     int f12385w = 0;
     int f12386x = 0;
     boolean f12387y = false;
     boolean f12388z = false;

    public interface C2638f {
        void mo2684a(View view, Uri uri);

        void mo2685l();

        void onTouchDown(View view);

        void onTouchUp(View view);
    }

    class C2739a implements OnClickListener {
        final C2745c f12339a;

        C2739a(C2745c c2745c) {
            this.f12339a = c2745c;
        }

        public void onClick(View view) {
            ImageView imageView = this.f12339a.f12367e;
            float f = -180.0f;
            if (this.f12339a.f12367e.getRotationY() == -180.0f) {
                f = 0.0f;
            }
            imageView.setRotationY(f);
            this.f12339a.f12367e.invalidate();
            this.f12339a.requestLayout();
        }
    }

    class C2740b implements OnClickListener {
        final C2745c f12340a;

        C2740b(C2745c c2745c) {
            this.f12340a = c2745c;
        }

        public void onClick(View view) {
            if (this.f12340a.f12348C != null) {
                C2638f c2638f = this.f12340a.f12348C;
                C2745c c2745c = this.f12340a;
                c2638f.mo2684a(c2745c, c2745c.f12351F);
            }
        }
    }

    class C2742c implements OnClickListener {
        final C2745c f12343a;

        C2742c(C2745c c2745c) {
            this.f12343a = c2745c;
        }

        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) this.f12343a.getParent();
            this.f12343a.f12371i.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                    Log.d("","");
                }

                public void onAnimationStart(Animation animation) {
                    Log.d("","");
                }

                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(C2742c.this.f12343a);
                }
            });
            this.f12343a.f12367e.startAnimation(this.f12343a.f12371i);
            this.f12343a.setBorderVisibility(false);
            if (this.f12343a.f12348C != null) {
                this.f12343a.f12348C.mo2685l();
            }
        }
    }

    class C2743d implements OnTouchListener {
        final C2745c f12344a;

        C2743d(C2745c c2745c) {
            this.f12344a = c2745c;
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int i;
            int action = motionEvent.getAction();
            if (action == 0) {
                C2745c c2745c = this.f12344a;
                c2745c.f12359N = c2745c.getX();
                C2745c c2745c2 = this.f12344a;
                c2745c2.f12360O = c2745c2.getY();
                this.f12344a.f12357L = motionEvent.getRawX();
                this.f12344a.f12358M = motionEvent.getRawY();
                C2745c c2745c3 = this.f12344a;
                c2745c3.f12356K = (double) c2745c3.getLayoutParams().width;
                C2745c c2745c4 = this.f12344a;
                c2745c4.f12355J = (double) c2745c4.getLayoutParams().height;
                C2745c c2745c5 = this.f12344a;
                c2745c5.f12376n = (double) (((View) c2745c5.getParent()).getX() + this.f12344a.getX() + (((float) this.f12344a.getWidth()) / 2.0f));
                int i2 = 0;
                int identifier = this.f12344a.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (identifier > 0) {
                    i2 = this.f12344a.getResources().getDimensionPixelSize(identifier);
                }
                C2745c c2745c6 = this.f12344a;
                double d = (double) i2;
                double y = (double) (((View) c2745c6.getParent()).getY() + this.f12344a.getY());
                Double.isNaN(d);
                Double.isNaN(y);
                Double.isNaN(d);
                Double.isNaN(y);
                double height = (double) (((float) this.f12344a.getHeight()) / 2.0f);
                Double.isNaN(height);
                Double.isNaN(height);
                c2745c6.f12377o = d + y + height;
                return true;
            }
            if (action == 1) {
                C2745c c2745c7 = this.f12344a;
                c2745c7.f12361P = c2745c7.getLayoutParams().width;
                C2745c c2745c8 = this.f12344a;
                c2745c8.f12384v = c2745c8.getLayoutParams().height;
            } else if (action == 2) {
                double atan2 = Math.atan2((double) (motionEvent.getRawY() - this.f12344a.f12358M), (double) (motionEvent.getRawX() - this.f12344a.f12357L));
                double d2 = (double) this.f12344a.f12358M;
                double d3 = this.f12344a.f12377o;
                Double.isNaN(d2);
                Double.isNaN(d2);
                double d4 = d2 - d3;
                double d5 = (double) this.f12344a.f12357L;
                double d6 = this.f12344a.f12376n;
                Double.isNaN(d5);
                Double.isNaN(d5);
                double abs = (Math.abs(atan2 - Math.atan2(d4, d5 - d6)) * 180.0d) / 3.141592653589793d;
                Log.v("ResizableStickerView", "angle_diff: " + abs);
                C2745c c2745c9 = this.f12344a;
                double m18245a = c2745c9.m18245a(c2745c9.f12376n, this.f12344a.f12377o, (double) this.f12344a.f12357L, (double) this.f12344a.f12358M);
                C2745c c2745c10 = this.f12344a;
                double m18245a2 = c2745c10.m18245a(c2745c10.f12376n, this.f12344a.f12377o, (double) motionEvent.getRawX(), (double) motionEvent.getRawY());
                C2745c c2745c11 = this.f12344a;
                int m18264a = c2745c11.m18264a(c2745c11.getContext(), 30);
                if (m18245a2 > m18245a && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                    double round = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - this.f12344a.f12357L), (double) Math.abs(motionEvent.getRawY() - this.f12344a.f12358M)));
                    LayoutParams layoutParams = (LayoutParams) this.f12344a.getLayoutParams();
                    double d7 = (double) layoutParams.width;
                    Double.isNaN(d7);
                    Double.isNaN(round);
                    Double.isNaN(d7);
                    Double.isNaN(round);
                    layoutParams.width = (int) (d7 + round);
                    LayoutParams layoutParams2 = (LayoutParams) this.f12344a.getLayoutParams();
                    double d8 = (double) layoutParams2.height;
                    Double.isNaN(round);
                    Double.isNaN(d8);
                    Double.isNaN(round);
                    Double.isNaN(d8);
                    layoutParams2.height = (int) (round + d8);
                } else if (m18245a2 < m18245a && ((abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d) && this.f12344a.getLayoutParams().width > (i = m18264a / 2) && this.f12344a.getLayoutParams().height > i)) {
                    double round2 = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - this.f12344a.f12357L), (double) Math.abs(motionEvent.getRawY() - this.f12344a.f12358M)));
                    LayoutParams layoutParams3 = (LayoutParams) this.f12344a.getLayoutParams();
                    double d9 = (double) layoutParams3.width;
                    Double.isNaN(d9);
                    Double.isNaN(round2);
                    Double.isNaN(d9);
                    Double.isNaN(round2);
                    layoutParams3.width = (int) (d9 - round2);
                    LayoutParams layoutParams4 = (LayoutParams) this.f12344a.getLayoutParams();
                    double d10 = (double) layoutParams4.height;
                    Double.isNaN(d10);
                    Double.isNaN(round2);
                    Double.isNaN(d10);
                    Double.isNaN(round2);
                    layoutParams4.height = (int) (d10 - round2);
                }
                this.f12344a.f12357L = motionEvent.getRawX();
                this.f12344a.f12358M = motionEvent.getRawY();
                this.f12344a.postInvalidate();
                this.f12344a.requestLayout();
                return true;
            }
            return true;
        }
    }

    class C2744e implements OnTouchListener {
        final C2745c f12345a;

        C2744e(C2745c c2745c) {
            this.f12345a = c2745c;
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) this.f12345a.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                this.f12345a.invalidate();
                C2745c c2745c = this.f12345a;
                c2745c.f12365c = rawX;
                c2745c.f12366d = rawY;
                c2745c.f12364b = c2745c.getWidth();
                C2745c c2745c2 = this.f12345a;
                c2745c2.f12363a = c2745c2.getHeight();
                this.f12345a.getLocationOnScreen(new int[2]);
                this.f12345a.f12368f = layoutParams.leftMargin;
                this.f12345a.f12369g = layoutParams.topMargin;
            } else if (action == 1) {
                C2745c c2745c3 = this.f12345a;
                c2745c3.f12361P = c2745c3.getLayoutParams().width;
                C2745c c2745c4 = this.f12345a;
                c2745c4.f12384v = c2745c4.getLayoutParams().height;
            } else if (action == 2) {
                float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - this.f12345a.f12366d), (double) (rawX - this.f12345a.f12365c)));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - this.f12345a.f12365c;
                int i2 = rawY - this.f12345a.f12366d;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - this.f12345a.getRotation()))));
                int sqrt2 = (int) (Math.sqrt((double) (i3 + (sqrt * sqrt))) * Math.sin(Math.toRadians((double) (degrees - this.f12345a.getRotation()))));
                int i4 = (sqrt * 2) + this.f12345a.f12364b;
                int i5 = (sqrt2 * 2) + this.f12345a.f12363a;
                if (i4 > this.f12345a.f12353H) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = this.f12345a.f12368f - sqrt;
                }
                if (i5 > this.f12345a.f12353H) {
                    layoutParams.height = i5;
                    layoutParams.topMargin = this.f12345a.f12369g - sqrt2;
                }
                this.f12345a.setLayoutParams(layoutParams);
                this.f12345a.performLongClick();
            }
            return true;
        }
    }

    public C2745c(Context context) {
        super(context);
        m18267a(context);
    }

    public double m18245a(double d, double d2, double d3, double d4) {
        return Math.sqrt(Math.pow(d4 - d2, 2.0d) + Math.pow(d3 - d, 2.0d));
    }

    public int m18264a(Context context, int i) {
        context.getResources();
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public C2745c m18265a(C2638f c2638f) {
        this.f12348C = c2638f;
        return this;
    }

    public void m18266a() {
        setOnTouchListener(new C2734a().m18206a(true).m18205a(this));
    }

    @SuppressLint({"WrongConstant"})
    public void m18267a(Context context) {
        this.f12379q = context;
        this.f12367e = new ImageView(this.f12379q);
        this.f12354I = new ImageView(this.f12379q);
        this.f12374l = new ImageView(this.f12379q);
        this.f12383u = new ImageView(this.f12379q);
        this.f12382t = new ImageView(this.f12379q);
        this.f12380r = new ImageView(this.f12379q);
        this.f12353H = m18264a(this.f12379q, 25);
        this.f12361P = m18264a(this.f12379q, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.f12384v = m18264a(this.f12379q, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.f12354I.setImageResource(R.drawable.resize);
        this.f12374l.setImageResource(R.drawable.sticker_border);
        this.f12383u.setVisibility(View.GONE);
        this.f12383u.setImageResource(R.drawable.flip);
        this.f12380r.setImageResource(R.drawable.close);
        LayoutParams layoutParams = new LayoutParams(this.f12361P, this.f12384v);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.setMargins(5, 5, 5, 5);
        layoutParams2.addRule(17);
        int i = this.f12353H;
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(12);
        layoutParams3.addRule(11);
        layoutParams3.setMargins(5, 5, 5, 5);
        int i2 = this.f12353H;
        LayoutParams layoutParams4 = new LayoutParams(i2, i2);
        layoutParams4.addRule(10);
        layoutParams4.addRule(11);
        layoutParams4.setMargins(5, 5, 5, 5);
        int i3 = this.f12353H;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(12);
        layoutParams5.addRule(9);
        layoutParams5.setMargins(5, 5, 5, 5);
        int i4 = this.f12353H;
        LayoutParams layoutParams6 = new LayoutParams(i4, i4);
        layoutParams6.addRule(10);
        layoutParams6.addRule(9);
        layoutParams6.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        addView(this.f12374l);
        this.f12374l.setLayoutParams(layoutParams7);
        this.f12374l.setScaleType(ImageView.ScaleType.FIT_XY);
        this.f12374l.setTag("border_iv");
        addView(this.f12367e);
        this.f12367e.setLayoutParams(layoutParams2);
        this.f12367e.setTag("main_iv");
        addView(this.f12383u);
        this.f12383u.setLayoutParams(layoutParams4);
        this.f12383u.setOnClickListener(new C2739a(this));
        addView(this.f12382t);
        this.f12382t.setLayoutParams(layoutParams5);
        this.f12382t.setOnClickListener(new C2740b(this));
        addView(this.f12380r);
        this.f12380r.setLayoutParams(layoutParams6);
        this.f12380r.setOnClickListener(new C2742c(this));
        addView(this.f12354I);
        this.f12354I.setLayoutParams(layoutParams3);
        this.f12354I.setOnTouchListener(this.f12350E);
        this.f12354I.setTag("scale_iv");
        this.f12352G = getRotation();
        this.f12370h = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_anim);
        this.f12372j = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out);
        this.f12371i = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in);
        m18266a();
    }

    public void mo2700a(View view) {
        C2638f c2638f = this.f12348C;
        if (c2638f != null) {
            c2638f.onTouchDown(view);
        }
    }

    public void mo2701b(View view) {
        C2638f c2638f = this.f12348C;
        if (c2638f != null) {
            c2638f.onTouchUp(view);
        }
    }

    public int getAlphaProg() {
        return this.f12373k;
    }

    public boolean getBorderVisbilty() {
        return this.f12387y;
    }

    public int getColor() {
        return this.f12386x;
    }

    public String getColorType() {
        return this.f12378p;
    }

    public C2738b getComponentInfo() {
        C2738b c2738b = new C2738b();
        c2738b.m18222a(getX());
        c2738b.m18228b(getY());
        c2738b.m18229b(this.f12361P);
        c2738b.m18233c(this.f12384v);
        c2738b.m18223a(this.f12381s);
        c2738b.m18236d(this.f12386x);
        c2738b.m18225a(this.f12351F);
        c2738b.m18230b(this.f12378p);
        c2738b.m18224a(this.f12375m);
        c2738b.m18232c(getRotation());
        c2738b.m18235d(this.f12367e.getRotationY());
        return c2738b;
    }

    public int getHueProg() {
        return this.f12385w;
    }

    public Bitmap getMainImageBitmap() {
        return this.f12375m;
    }

    public Uri getMainImageUri() {
        return this.f12351F;
    }

    public void setAlphaProg(int i) {
        this.f12373k = i;
        if (Build.VERSION.SDK_INT >= 16) {
            this.f12367e.setImageAlpha(255 - i);
        }
        this.f12367e.setAlpha(255 - i);
    }

    public void setBgDrawable(int i) {
        this.f12367e.setImageResource(i);
        this.f12381s = i;
        this.f12367e.startAnimation(this.f12372j);
    }

    @SuppressLint({"WrongConstant"})
    public void setBorderVisibility(boolean z) {
        this.f12387y = z;
        if (!z) {
            this.f12374l.setVisibility(View.GONE);
            this.f12354I.setVisibility(View.GONE);
            this.f12383u.setVisibility(View.GONE);
            this.f12382t.setVisibility(View.GONE);
            this.f12380r.setVisibility(View.GONE);
            setBackgroundResource(0);
            if (this.f12388z) {
                this.f12367e.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.f12374l.getVisibility() != 0) {
            this.f12374l.setVisibility(View.VISIBLE);
            this.f12354I.setVisibility(View.VISIBLE);
            if (this.f12346A) {
                this.f12383u.setVisibility(View.VISIBLE);
            }
            if (this.f12347B) {
                this.f12382t.setVisibility(View.VISIBLE);
            }
            this.f12380r.setVisibility(View.VISIBLE);
            this.f12367e.startAnimation(this.f12370h);
        }
    }

    public void setColor(int i) {
        try {
            this.f12367e.setColorFilter(i);
            this.f12386x = i;
        } catch (Exception unused) {
        }
    }

    public void setColorType(String str) {
        this.f12378p = str;
    }

    @SuppressLint({"WrongConstant"})
    public void setComponentInfo(C2738b c2738b) {
        this.f12361P = c2738b.m18238f();
        this.f12384v = c2738b.m18239g();
        this.f12381s = c2738b.m18231c();
        this.f12351F = c2738b.m18241i();
        this.f12375m = c2738b.m18242j();
        this.f12386x = c2738b.m18243k();
        this.f12352G = c2738b.m18237e();
        this.f12362Q = c2738b.m18240h();
        this.f12378p = c2738b.m18244l();
        setX(c2738b.m18221a());
        setY(c2738b.m18227b());
        int i = this.f12381s;
        if (i == 0) {
            this.f12367e.setImageBitmap(this.f12375m);
        } else {
            setBgDrawable(i);
        }
        setRotation(this.f12352G);
        setColor(this.f12386x);
        setColorType(this.f12378p);
        getLayoutParams().width = this.f12361P;
        getLayoutParams().height = this.f12384v;
        if (c2738b.m18234d() == "SHAPE") {
            this.f12383u.setVisibility(View.GONE);
            this.f12346A = false;
        }
        if (c2738b.m18234d() == "STICKER") {
            this.f12383u.setVisibility(View.VISIBLE);
            this.f12346A = true;
        }
        this.f12367e.setRotationY(this.f12362Q);
    }

    public void setHueProg(int i) {
        this.f12385w = i;
        this.f12367e.setColorFilter(C2737a.m18219a((float) i));
    }

    public void setMainImageBitmap(Bitmap bitmap) {
        this.f12367e.setImageBitmap(bitmap);
    }

    public void setMainImageUri(Uri uri) {
        this.f12351F = uri;
        this.f12367e.setImageURI(this.f12351F);
    }
}

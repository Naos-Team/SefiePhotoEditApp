package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class C2734a implements View.OnTouchListener {
    Bitmap f12294a = null;
    boolean f12295b = false;
    public boolean f12296c = true;
    public boolean f12297d = false;
    public boolean f12298e = true;
    public float f12299f = 8.0f;
    public float f12300g = 0.5f;
    private C2732b f12301h = null;
    private int f12302i = -1;
    public float f12303j;
    public float f12304k;
    private C2735b f12305l = new C2735b(new C2731a(this));

    public interface C2732b {
        void mo2700a(View view);

        void mo2701b(View view);
    }

    private static float m18199a(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    private class C2731a extends C2735b.C2730b {
        final C2734a f12282a;
        private float f12283b;
        private float f12284c;
        private C2736c f12285d;

        private C2731a(C2734a c2734a) {
            this.f12282a = c2734a;
            this.f12285d = new C2736c(c2734a.f12303j, c2734a.f12304k);
        }

        public boolean mo2697a(View view, C2735b c2735b) {
            this.f12283b = c2735b.m18213b();
            this.f12284c = c2735b.m18214c();
            this.f12285d.set(c2735b.m18215d());
            return true;
        }

        public boolean mo2698b(View view, C2735b c2735b) {
            C2733c c2733c = new C2733c();
            float f = 0.0f;
            c2733c.f12286a = this.f12282a.f12296c ? C2736c.m18216a(this.f12285d, c2735b.m18215d()) : 0.0f;
            c2733c.f12287b = this.f12282a.f12298e ? c2735b.m18213b() - this.f12283b : 0.0f;
            if (this.f12282a.f12298e) {
                f = c2735b.m18214c() - this.f12284c;
            }
            c2733c.f12288c = f;
            c2733c.f12291f = this.f12283b;
            c2733c.f12292g = this.f12284c;
            c2733c.f12290e = this.f12282a.f12300g;
            c2733c.f12289d = this.f12282a.f12299f;
            this.f12282a.m18202a(view, c2733c);
            return false;
        }
    }

    private class C2733c {
        public float f12286a;
        public float f12287b;
        public float f12288c;
        public float f12289d;
        public float f12290e;
        public float f12291f;
        public float f12292g;
        C2734a f12293h;

        private C2733c(C2734a c2734a) {
            this.f12293h = c2734a;
        }

        public C2733c() {
        }
    }

    private static void m18201a(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(fArr[1] + view.getTranslationY());
    }

    public void m18202a(View view, C2733c c2733c) {
        if (this.f12297d) {
            view.setRotation(m18199a(view.getRotation() + c2733c.f12286a));
        }
    }

    public C2734a m18205a(C2732b c2732b) {
        this.f12301h = c2732b;
        return this;
    }

    public C2734a m18206a(boolean z) {
        this.f12297d = z;
        return this;
    }

    public boolean m18207a(View view, MotionEvent motionEvent) {
        try {
            if (((C2745c) view).getBorderVisbilty()) {
                return false;
            }
            boolean z = true;
            if (motionEvent.getAction() == 2 && this.f12295b) {
                return true;
            }
            if (motionEvent.getAction() == 1) {
                if (this.f12295b) {
                    this.f12295b = false;
                    if (this.f12294a != null) {
                        this.f12294a.recycle();
                    }
                    return true;
                }
            }
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int rawY = (int) (motionEvent.getRawY() - ((float) iArr[1]));
            float rotation = view.getRotation();
            Matrix matrix = new Matrix();
            matrix.postRotate(-rotation);
            float[] fArr = {(float) ((int) (motionEvent.getRawX() - ((float) iArr[0]))), (float) rawY};
            matrix.mapPoints(fArr);
            int i = (int) fArr[0];
            int i2 = (int) fArr[1];
            if (motionEvent.getAction() == 0) {
                this.f12295b = false;
                view.setDrawingCacheEnabled(true);
                this.f12294a = Bitmap.createBitmap(view.getDrawingCache());
                i = (int) (((float) i) * (((float) this.f12294a.getWidth()) / (((float) this.f12294a.getWidth()) * view.getScaleX())));
                i2 = (int) (((float) i2) * (((float) this.f12294a.getHeight()) / (((float) this.f12294a.getHeight()) * view.getScaleX())));
                view.setDrawingCacheEnabled(false);
            }
            if (i < 0 || i2 < 0 || i > this.f12294a.getWidth() || i2 > this.f12294a.getHeight()) {
                return false;
            }
            if (this.f12294a.getPixel(i, i2) != 0) {
                z = false;
            }
            if (motionEvent.getAction() != 0) {
                return z;
            }
            this.f12295b = z;
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.f12305l.m18212a(view, motionEvent);
        int i = 0;
        if (m18207a(view, motionEvent)) {
            return false;
        }
        if (!this.f12298e) {
            return true;
        }
        int action = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked() & action;
        if (actionMasked == 0) {
            C2732b c2732b = this.f12301h;
            if (c2732b != null) {
                c2732b.mo2700a(view);
            }
            view.bringToFront();
            if (view instanceof C2745c) {
                ((C2745c) view).setBorderVisibility(true);
            }
            this.f12303j = motionEvent.getX();
            this.f12304k = motionEvent.getY();
            this.f12302i = motionEvent.getPointerId(0);
            return true;
        } else if (actionMasked == 1) {
            this.f12302i = -1;
            C2732b c2732b2 = this.f12301h;
            if (c2732b2 != null) {
                c2732b2.mo2701b(view);
            }
            float rotation = view.getRotation();
            if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                rotation = rotation > 0.0f ? 90.0f : -90.0f;
            }
            if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                rotation = rotation > 0.0f ? 0.0f : -0.0f;
            }
            if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                rotation = rotation > 0.0f ? 180.0f : -180.0f;
            }
            view.setRotation(rotation);
            Log.i("testing", "Final Rotation : " + rotation);
            return true;
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.f12302i);
            if (findPointerIndex == -1) {
                return true;
            }
            float x = motionEvent.getX(findPointerIndex);
            float y = motionEvent.getY(findPointerIndex);
            if (this.f12305l.m18211a()) {
                return true;
            }
            m18201a(view, x - this.f12303j, y - this.f12304k);
            return true;
        } else if (actionMasked == 3) {
            this.f12302i = -1;
            return true;
        } else if (actionMasked != 6) {
            return true;
        } else {
            int i2 = (65280 & action) >> 8;
            if (motionEvent.getPointerId(i2) != this.f12302i) {
                return true;
            }
            if (i2 == 0) {
                i = 1;
            }
            this.f12303j = motionEvent.getX(i);
            this.f12304k = motionEvent.getY(i);
            this.f12302i = motionEvent.getPointerId(i);
            return true;
        }
    }
}

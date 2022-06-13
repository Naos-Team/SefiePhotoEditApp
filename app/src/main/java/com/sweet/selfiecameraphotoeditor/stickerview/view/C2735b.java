package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class C2735b {
     boolean f12306a;
     int f12307b;
     int f12308c;
     MotionEvent f12309d;
     float f12310e;
     float f12311f;
     float f12312g;
     float f12313h;
     float f12314i;
     float f12315j;
     C2736c f12316k = new C2736c(this.f12314i, this.f12315j);
     boolean f12317l;
     boolean f12318m;
     final C2729a f12319n;
     MotionEvent f12320o;
     float f12321p;
     float f12322q;
     float f12323r;
     float f12324s;
     float f12325t;
     long f12326u;

    public interface C2729a {
        boolean mo2697a(View view, C2735b c2735b);

        boolean mo2698b(View view, C2735b c2735b);

        void mo2699c(View view, C2735b c2735b);
    }

    public static class C2730b implements C2729a {
        public boolean mo2697a(View view, C2735b c2735b) {
            return true;
        }

        public boolean mo2698b(View view, C2735b c2735b) {
            return false;
        }

        public void mo2699c(View view, C2735b c2735b) {
        }
    }

    public C2735b(C2729a c2729a) {
        this.f12319n = c2729a;
    }

     int m18208a(MotionEvent motionEvent, int i, int i2) {
        int pointerCount = motionEvent.getPointerCount();
        int findPointerIndex = motionEvent.findPointerIndex(i);
        for (int i3 = 0; i3 < pointerCount; i3++) {
            if (i3 != i2 && i3 != findPointerIndex) {
                return i3;
            }
        }
        return -1;
    }

     void m18209b(View view, MotionEvent motionEvent) {
        MotionEvent motionEvent2 = this.f12309d;
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        this.f12309d = MotionEvent.obtain(motionEvent);
        this.f12312g = -1.0f;
        this.f12323r = -1.0f;
        this.f12325t = -1.0f;
        this.f12316k.set(0.0f, 0.0f);
        MotionEvent motionEvent3 = this.f12320o;
        int findPointerIndex = motionEvent3.findPointerIndex(this.f12307b);
        int findPointerIndex2 = motionEvent3.findPointerIndex(this.f12308c);
        int findPointerIndex3 = motionEvent.findPointerIndex(this.f12307b);
        int findPointerIndex4 = motionEvent.findPointerIndex(this.f12308c);
        if (findPointerIndex < 0 || findPointerIndex2 < 0 || findPointerIndex3 < 0 || findPointerIndex4 < 0) {
            this.f12318m = true;
            Log.e("ScaleGestureDetector", "Invalid MotionEvent stream detected.", new Throwable());
            if (this.f12317l) {
                this.f12319n.mo2699c(view, this);
                return;
            }
            return;
        }
        float x = motionEvent3.getX(findPointerIndex);
        float y = motionEvent3.getY(findPointerIndex);
        float x2 = motionEvent3.getX(findPointerIndex2);
        float y2 = motionEvent3.getY(findPointerIndex2);
        float x3 = motionEvent.getX(findPointerIndex3);
        float y3 = motionEvent.getY(findPointerIndex3);
        float f = x2 - x;
        float f2 = y2 - y;
        float x4 = motionEvent.getX(findPointerIndex4) - x3;
        float y4 = motionEvent.getY(findPointerIndex4) - y3;
        this.f12316k.set(x4, y4);
        this.f12321p = f;
        this.f12322q = f2;
        this.f12310e = x4;
        this.f12311f = y4;
        this.f12314i = (x4 * 0.5f) + x3;
        this.f12315j = (y4 * 0.5f) + y3;
        this.f12326u = motionEvent.getEventTime() - motionEvent3.getEventTime();
        this.f12313h = motionEvent.getPressure(findPointerIndex3) + motionEvent.getPressure(findPointerIndex4);
        this.f12324s = motionEvent3.getPressure(findPointerIndex2) + motionEvent3.getPressure(findPointerIndex);
    }

     void m18210e() {
        MotionEvent motionEvent = this.f12320o;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.f12320o = null;
        }
        MotionEvent motionEvent2 = this.f12309d;
        if (motionEvent2 != null) {
            motionEvent2.recycle();
            this.f12309d = null;
        }
        this.f12317l = false;
        this.f12307b = -1;
        this.f12308c = -1;
        this.f12318m = false;
    }

    public boolean m18211a() {
        return this.f12317l;
    }

    public boolean m18212a(View view, MotionEvent motionEvent) {
        int m18208a;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            m18210e();
        }
        if (this.f12318m) {
            return false;
        }
        if (this.f12317l) {
            if (actionMasked == 1) {
                m18210e();
                return true;
            } else if (actionMasked == 2) {
                m18209b(view, motionEvent);
                if (this.f12313h / this.f12324s > 0.67f && this.f12319n.mo2698b(view, this)) {
                    this.f12320o.recycle();
                    this.f12320o = MotionEvent.obtain(motionEvent);
                }
                return true;
            } else if (actionMasked == 3) {
                this.f12319n.mo2699c(view, this);
                m18210e();
                return true;
            } else if (actionMasked == 5) {
                this.f12319n.mo2699c(view, this);
                int i = this.f12307b;
                int i2 = this.f12308c;
                m18210e();
                this.f12320o = MotionEvent.obtain(motionEvent);
                if (!this.f12306a) {
                    i = i2;
                }
                this.f12307b = i;
                this.f12308c = motionEvent.getPointerId(motionEvent.getActionIndex());
                this.f12306a = false;
                if (motionEvent.findPointerIndex(this.f12307b) < 0 || this.f12307b == this.f12308c) {
                    this.f12307b = motionEvent.getPointerId(m18208a(motionEvent, this.f12308c, -1));
                }
                m18209b(view, motionEvent);
                this.f12317l = this.f12319n.mo2697a(view, this);
                return true;
            } else if (actionMasked != 6) {
                return true;
            } else {
                int pointerCount = motionEvent.getPointerCount();
                int actionIndex = motionEvent.getActionIndex();
                int pointerId = motionEvent.getPointerId(actionIndex);
                if (pointerCount > 2) {
                    int i3 = this.f12307b;
                    if (pointerId == i3) {
                        int m18208a2 = m18208a(motionEvent, this.f12308c, actionIndex);
                        if (m18208a2 >= 0) {
                            this.f12319n.mo2699c(view, this);
                            this.f12307b = motionEvent.getPointerId(m18208a2);
                            this.f12306a = true;
                            this.f12320o = MotionEvent.obtain(motionEvent);
                            m18209b(view, motionEvent);
                            this.f12317l = this.f12319n.mo2697a(view, this);
                            this.f12320o.recycle();
                            this.f12320o = MotionEvent.obtain(motionEvent);
                            m18209b(view, motionEvent);
                        }
                    } else {
                        if (pointerId == this.f12308c && (m18208a = m18208a(motionEvent, i3, actionIndex)) >= 0) {
                            this.f12319n.mo2699c(view, this);
                            this.f12308c = motionEvent.getPointerId(m18208a);
                            this.f12306a = false;
                            this.f12320o = MotionEvent.obtain(motionEvent);
                            m18209b(view, motionEvent);
                            this.f12317l = this.f12319n.mo2697a(view, this);
                        }
                        this.f12320o.recycle();
                        this.f12320o = MotionEvent.obtain(motionEvent);
                        m18209b(view, motionEvent);
                    }
                    this.f12320o.recycle();
                    this.f12320o = MotionEvent.obtain(motionEvent);
                    m18209b(view, motionEvent);
                }
                m18209b(view, motionEvent);
                int i4 = this.f12307b;
                if (pointerId == i4) {
                    i4 = this.f12308c;
                }
                int findPointerIndex = motionEvent.findPointerIndex(i4);
                this.f12314i = motionEvent.getX(findPointerIndex);
                this.f12315j = motionEvent.getY(findPointerIndex);
                this.f12319n.mo2699c(view, this);
                m18210e();
                this.f12307b = i4;
                this.f12306a = true;
                return true;
            }
        } else if (actionMasked == 0) {
            this.f12307b = motionEvent.getPointerId(0);
            this.f12306a = true;
            return true;
        } else if (actionMasked == 1) {
            m18210e();
            return true;
        } else if (actionMasked != 5) {
            return true;
        } else {
            MotionEvent motionEvent2 = this.f12320o;
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            this.f12320o = MotionEvent.obtain(motionEvent);
            this.f12326u = 0;
            int actionIndex2 = motionEvent.getActionIndex();
            int findPointerIndex2 = motionEvent.findPointerIndex(this.f12307b);
            this.f12308c = motionEvent.getPointerId(actionIndex2);
            if (findPointerIndex2 < 0 || findPointerIndex2 == actionIndex2) {
                this.f12307b = motionEvent.getPointerId(m18208a(motionEvent, this.f12308c, -1));
            }
            this.f12306a = false;
            m18209b(view, motionEvent);
            this.f12317l = this.f12319n.mo2697a(view, this);
            return true;
        }
    }

    public float m18213b() {
        return this.f12314i;
    }

    public float m18214c() {
        return this.f12315j;
    }

    public C2736c m18215d() {
        return this.f12316k;
    }
}

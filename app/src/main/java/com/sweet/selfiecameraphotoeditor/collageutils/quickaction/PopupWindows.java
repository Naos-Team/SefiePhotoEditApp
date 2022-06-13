package com.sweet.selfiecameraphotoeditor.collageutils.quickaction;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupWindows {
    protected Drawable mBackground = null;
    protected Context mContext;
    protected View mRootView;
    protected PopupWindow mWindow;
    protected WindowManager mWindowManager;


    public void onDismiss() {
        Log.d("","");
    }


    public void onShow() {
        Log.d("","");
    }

    public PopupWindows(Context context) {
        this.mContext = context;
        this.mWindow = new PopupWindow(context);
        this.mWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 4) {
                    return false;
                }
                PopupWindows.this.mWindow.dismiss();
                return true;
            }
        });
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }


    public void preShow() {
        if (this.mRootView != null) {
            onShow();
            Drawable drawable = this.mBackground;
            if (drawable == null) {
                this.mWindow.setBackgroundDrawable(new BitmapDrawable());
            } else {
                this.mWindow.setBackgroundDrawable(drawable);
            }
            this.mWindow.setWidth(-2);
            this.mWindow.setHeight(-2);
            this.mWindow.setTouchable(true);
            this.mWindow.setFocusable(true);
            this.mWindow.setOutsideTouchable(true);
            this.mWindow.setContentView(this.mRootView);
            return;
        }
        throw new IllegalStateException("setContentView was not called with a view to display.");
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackground = drawable;
    }

    public void setContentView(View view) {
        this.mRootView = view;
        this.mWindow.setContentView(view);
    }

    public void setContentView(int i) {
        setContentView(((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(i, (ViewGroup) null));
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mWindow.setOnDismissListener(onDismissListener);
    }

    public void dismiss() {
        this.mWindow.dismiss();
    }
}

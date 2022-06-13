package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.module;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.appcompat.widget.AppCompatButton;

public class EffectsButton extends AppCompatButton {

     Animation.AnimationListener animationListener;
     boolean clickable;
     ScaleAnimation downAnim;
     int[] locationOnScreen;

    public OnClickEffectButtonListener onClickEffectButtonListener;
     int preX;
     int preY;
     boolean shouldAbortAnim;
     ScaleAnimation tmpUpAnim;
     ScaleAnimation upAnimation;

    public interface OnClickEffectButtonListener {
        void onClickEffectButton();
    }

    public EffectsButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public EffectsButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EffectsButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.clickable = true;
        this.downAnim = createDownAnim();
        this.upAnimation = createUpAnim();
        this.tmpUpAnim = createUpAnim();
        this.animationListener = new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
                Log.d("","");
            }

            public void onAnimationStart(Animation animation) {
                Log.d("","");
            }

            public void onAnimationEnd(Animation animation) {
                EffectsButton.this.clearAnimation();
                if (EffectsButton.this.onClickEffectButtonListener != null) {
                    EffectsButton.this.onClickEffectButtonListener.onClickEffectButton();
                }
            }
        };
        this.upAnimation.setAnimationListener(this.animationListener);
        this.locationOnScreen = new int[2];
        setGravity(17);
    }


    public ScaleAnimation createUpAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(50);
        scaleAnimation.setFillEnabled(true);
        scaleAnimation.setFillEnabled(false);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }


    public ScaleAnimation createDownAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(50);
        scaleAnimation.setFillEnabled(true);
        scaleAnimation.setFillBefore(false);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (!this.clickable) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            clearAnimation();
            startAnimation(this.downAnim);
            this.shouldAbortAnim = false;
            getLocationOnScreen(this.locationOnScreen);
            this.preX = this.locationOnScreen[0] + (getWidth() / 2);
            this.preY = this.locationOnScreen[1] + (getHeight() / 2);
        }
        if (motionEvent.getAction() == 1) {
            clearAnimation();
            if (!this.shouldAbortAnim) {
                startAnimation(this.upAnimation);
            }
            this.shouldAbortAnim = false;
        } else if (motionEvent.getAction() == 3) {
            clearAnimation();
            startAnimation(this.tmpUpAnim);
            this.shouldAbortAnim = false;
        } else if (motionEvent.getAction() == 2 && !this.shouldAbortAnim && !checkPos(motionEvent.getRawX(), motionEvent.getRawY())) {
            this.shouldAbortAnim = true;
            clearAnimation();
            startAnimation(this.tmpUpAnim);
        }
        return true;
    }


    public boolean checkPos(float f, float f2) {
        return Math.abs(f - ((float) this.preX)) <= ((float) (getWidth() / 2)) && Math.abs(f2 - ((float) this.preY)) <= ((float) (getHeight() / 2));
    }

    public void setClickable(boolean z) {
        this.clickable = z;
    }

    public void setOnClickEffectButtonListener(OnClickEffectButtonListener onClickEffectButtonListener2) {
        this.onClickEffectButtonListener = onClickEffectButtonListener2;
    }
}

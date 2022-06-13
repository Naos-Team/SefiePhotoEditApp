package com.sweet.selfiecameraphotoeditor.collageutils.quickaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sweet.selfiecameraphotoeditor.R;

import java.util.ArrayList;
import java.util.List;

public class QuickAction extends PopupWindows implements PopupWindow.OnDismissListener {
    public static final int ANIM_AUTO = 5;
    public static final int ANIM_GROW_FROM_CENTER = 3;
    public static final int ANIM_GROW_FROM_LEFT = 1;
    public static final int ANIM_GROW_FROM_RIGHT = 2;
    public static final int ANIM_REFLECT = 4;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
     View mAnchorView;
     int mAnimStyle;
     ImageView mArrowDown;
     ImageView mArrowUp;
     int mChildPos;

    public boolean mDidAction;
     OnDismissListener mDismissListener;
     LayoutInflater mInflater;
     int mInsertPos;

    public OnActionItemClickListener mItemClickListener;
     int mOrientation;
     List<QuickActionItem> mQuickActionItems;
     View mRootView;
     ScrollView mScroller;
     ViewGroup mTrack;
     int rootWidth;

    public interface OnActionItemClickListener {
        void onItemClick(QuickAction quickAction, int i, int i2);
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public QuickAction(Context context) {
        this(context, 1);
    }

    public QuickAction(Context context, int i) {
        super(context);
        this.mQuickActionItems = new ArrayList();
        this.rootWidth = 0;
        this.mOrientation = i;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (this.mOrientation == 0) {
            setRootViewId(R.layout.popup_horizontal);
        } else {
            setRootViewId(R.layout.popup_vertical);
        }
        this.mAnimStyle = 5;
        this.mChildPos = 0;
    }

    public QuickActionItem getActionItem(int i) {
        return this.mQuickActionItems.get(i);
    }

    public View getAnchorView() {
        return this.mAnchorView;
    }

    public void setPopupBackgroundColor(int i) {
        this.mScroller.setBackgroundColor(i);
    }

    public void setPopupBackground(Drawable drawable) {
        if (this.mBackground == null) {
            this.mScroller.setBackgroundDrawable(new BitmapDrawable());
        } else {
            this.mScroller.setBackgroundDrawable(this.mBackground);
        }
    }

    public void setRootViewId(int i) {
        this.mRootView = (ViewGroup) this.mInflater.inflate(i, (ViewGroup) null);
        this.mTrack = (ViewGroup) this.mRootView.findViewById(R.id.tracks);
        this.mArrowDown = (ImageView) this.mRootView.findViewById(R.id.arrow_down);
        this.mArrowUp = (ImageView) this.mRootView.findViewById(R.id.arrow_up);
        this.mScroller = (ScrollView) this.mRootView.findViewById(R.id.scroller);
        this.mRootView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        setContentView(this.mRootView);
    }

    public void setAnimStyle(int i) {
        this.mAnimStyle = i;
    }

    public void setOnActionItemClickListener(OnActionItemClickListener onActionItemClickListener) {
        this.mItemClickListener = onActionItemClickListener;
    }

    public void addActionItem(QuickActionItem quickActionItem) {
        View view;
        this.mQuickActionItems.add(quickActionItem);
        String title = quickActionItem.getTitle();
        Drawable icon = quickActionItem.getIcon();
        if (this.mOrientation == 0) {
            view = this.mInflater.inflate(R.layout.action_item_horizontal, (ViewGroup) null);
        } else {
            view = this.mInflater.inflate(R.layout.action_item_vertical, (ViewGroup) null);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        if (icon != null) {
            imageView.setImageDrawable(icon);
        } else {
            imageView.setVisibility(View.GONE);
        }
        if (title != null) {
            textView.setText(title);
        } else {
            textView.setVisibility(View.GONE);
        }
        final int i = this.mChildPos;
        final int actionId = quickActionItem.getActionId();
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (QuickAction.this.mItemClickListener != null) {
                    QuickAction.this.mItemClickListener.onItemClick(QuickAction.this, i, actionId);
                }
                if (!QuickAction.this.getActionItem(i).isSticky()) {
                    boolean unused = QuickAction.this.mDidAction = true;
                    QuickAction.this.dismiss();
                }
            }
        });
        view.setFocusable(true);
        view.setClickable(true);
        if (this.mOrientation == 0 && this.mChildPos != 0) {
            View inflate = this.mInflater.inflate(R.layout.horiz_separator, (ViewGroup) null);
            inflate.setLayoutParams(new RelativeLayout.LayoutParams(-2, -1));
            inflate.setPadding(5, 0, 5, 0);
            this.mTrack.addView(inflate, this.mInsertPos);
            this.mInsertPos++;
        }
        this.mTrack.addView(view, this.mInsertPos);
        this.mChildPos++;
        this.mInsertPos++;
    }

    int i3;
    int i4;
    int i5;
    int i6;

    public void show(View view, int i, int i2) {

        this.mAnchorView = view;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i7 = i + iArr[0];
        boolean z = true;
        int i8 = i2 + iArr[1];
        int measuredWidth = this.mArrowDown.getMeasuredWidth();
        int measuredHeight = this.mArrowDown.getMeasuredHeight();
        if (measuredWidth < 1) {
            Bitmap decodeResource = BitmapFactory.decodeResource(view.getResources(), R.drawable.app_setting);
            if (measuredWidth < 1) {
                measuredWidth = decodeResource.getWidth();
            }
            if (measuredHeight < 1) {
                measuredHeight = decodeResource.getHeight();
            }
            decodeResource.recycle();
        }
        if (this.mOrientation == 0) {
            i7 -= measuredWidth / 2;
        } else {
            i8 -= measuredHeight / 2;
        }
        preShow();
        this.mDidAction = false;
        int[] iArr2 = {i7, i8};
        Rect rect = new Rect(iArr2[0], iArr2[1], iArr2[0] + measuredWidth, iArr2[1] + measuredHeight);
        this.mRootView.measure(-2, -2);
        int measuredHeight2 = this.mRootView.getMeasuredHeight();
        if (this.rootWidth == 0) {
            this.rootWidth = this.mRootView.getMeasuredWidth();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int i9 = displayMetrics.widthPixels;
        int i10 = displayMetrics.heightPixels;
        int i11 = rect.left;
        int i12 = this.rootWidth;
        if (i11 + i12 > i9) {
            i4 = rect.left - (this.rootWidth - measuredWidth);
            if (i4 < 0) {
                i4 = 0;
            }
            i3 = rect.centerX() - i4;
        } else {
            if (measuredWidth > i12) {
                i6 = rect.centerX() - (this.rootWidth / 2);
            } else {
                i6 = rect.left;
            }
            i3 = rect.centerX() - i4;
        }
        int i13 = rect.top;
        int i14 = i10 - rect.bottom;
        if (i13 <= i14) {
            z = false;
        }
        if (!z) {
            int i15 = rect.bottom;
            if (measuredHeight2 > i14) {
                this.mScroller.getLayoutParams().height = i14;
            }
            i5 = i15;
        } else if (measuredHeight2 > i13) {
            i5 = 15;
            this.mScroller.getLayoutParams().height = i13 - measuredHeight;
        } else {
            i5 = rect.top - measuredHeight2;
        }
        showArrow(z ? R.id.arrow_down : R.id.arrow_up, i3);
        setAnimationStyle(i9, rect.centerX(), z);
        this.mWindow.showAtLocation(view, 0, i4, i5);
    }

    int i;
    int i2;


    public void show(View view) {
        int i3;
        int i4;

        this.mAnchorView = view;
        preShow();
        this.mDidAction = false;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        boolean z = true;
        Rect rect = new Rect(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
        this.mRootView.measure(-2, -2);
        int measuredHeight = this.mRootView.getMeasuredHeight();
        if (this.rootWidth == 0) {
            this.rootWidth = this.mRootView.getMeasuredWidth();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int i5 = displayMetrics.widthPixels;
        int i6 = displayMetrics.heightPixels;
        if (rect.left + this.rootWidth > i5) {
            i2 = rect.left - (this.rootWidth - view.getWidth());
            if (i2 < 0) {
                i2 = 0;
            }
            i = rect.centerX() - i2;
        } else {
            if (view.getWidth() > this.rootWidth) {
                i4 = rect.centerX() - (this.rootWidth / 2);
            } else {
                i4 = rect.left;
            }
            i = rect.centerX() - i2;
        }
        int i7 = rect.top;
        int i8 = i6 - rect.bottom;
        if (i7 <= i8) {
            z = false;
        }
        if (!z) {
            int i9 = rect.bottom;
            if (measuredHeight > i8) {
                this.mScroller.getLayoutParams().height = i8;
            }
            i3 = i9;
        } else if (measuredHeight > i7) {
            i3 = 15;
            this.mScroller.getLayoutParams().height = i7 - view.getHeight();
        } else {
            i3 = rect.top - measuredHeight;
        }
        showArrow(z ? R.id.arrow_down : R.id.arrow_up, i);
        setAnimationStyle(i5, rect.centerX(), z);
        this.mWindow.showAtLocation(view, 0, i2, i3);
    }

     void setAnimationStyle(int i, int i2, boolean z) {
        int measuredWidth = i2 - (this.mArrowUp.getMeasuredWidth() / 2);
        int i3 = this.mAnimStyle;
        int i4 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Center;
        int i5 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopUpMenu_Right;
        int i6 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Left;
        switch (i3) {
            case 1:
                PopupWindow popupWindow = this.mWindow;
                if (!z) {
                    i6 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Left;
                }
                popupWindow.setAnimationStyle(i6);
                return;
            case 2:
                PopupWindow popupWindow2 = this.mWindow;
                if (!z) {
                    i5 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Right;
                }
                popupWindow2.setAnimationStyle(i5);
                return;
            case 3:
                PopupWindow popupWindow3 = this.mWindow;
                if (!z) {
                    i4 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Center;
                }
                popupWindow3.setAnimationStyle(i4);
                return;
            case 4:
                this.mWindow.setAnimationStyle(z ? com.sweet.selfiecameraphotoeditor.R.style.Animations_PopUpMenu_Reflect : com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Reflect);
                return;
            case 5:
                int i7 = i / 4;
                if (measuredWidth <= i7) {
                    PopupWindow popupWindow4 = this.mWindow;
                    if (!z) {
                        i6 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Left;
                    }
                    popupWindow4.setAnimationStyle(i6);
                    return;
                } else if (measuredWidth <= i7 || measuredWidth >= i7 * 3) {
                    PopupWindow popupWindow5 = this.mWindow;
                    if (!z) {
                        i5 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Right;
                    }
                    popupWindow5.setAnimationStyle(i5);
                    return;
                } else {
                    PopupWindow popupWindow6 = this.mWindow;
                    if (!z) {
                        i4 = com.sweet.selfiecameraphotoeditor.R.style.Animations_PopDownMenu_Center;
                    }
                    popupWindow6.setAnimationStyle(i4);
                    return;
                }
            default:
                return;
        }
    }

     void showArrow(int i, int i2) {
        ImageView imageView = i == R.id.arrow_up ? this.mArrowUp : this.mArrowDown;
        ImageView imageView2 = i == R.id.arrow_up ? this.mArrowDown : this.mArrowUp;
        int measuredWidth = this.mArrowUp.getMeasuredWidth();
        imageView.setVisibility(View.VISIBLE);
        ((ViewGroup.MarginLayoutParams) imageView.getLayoutParams()).leftMargin = i2 - (measuredWidth / 2);
        imageView2.setVisibility(View.INVISIBLE);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        setOnDismissListener(this);
        this.mDismissListener = onDismissListener;
    }

    public void onDismiss() {
        OnDismissListener onDismissListener;
        if (!this.mDidAction && (onDismissListener = this.mDismissListener) != null) {
            onDismissListener.onDismiss();
        }
    }
}

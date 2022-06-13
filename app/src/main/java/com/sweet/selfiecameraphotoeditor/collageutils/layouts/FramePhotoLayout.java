package com.sweet.selfiecameraphotoeditor.collageutils.layouts;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.collageutils.ImageDecoder;
import com.sweet.selfiecameraphotoeditor.collageutils.ImageUtils;
import com.sweet.selfiecameraphotoeditor.collageutils.PhotoItem;
import com.sweet.selfiecameraphotoeditor.collageutils.quickaction.QuickAction;
import com.sweet.selfiecameraphotoeditor.collageutils.quickaction.QuickActionItem;

import java.util.ArrayList;
import java.util.List;

public class FramePhotoLayout extends RelativeLayout implements FrameImageView.OnImageClickListener {
    static final int ID_CANCEL = 4;
    static final int ID_CHANGE = 2;
    static final int ID_DELETE = 3;
    static final int ID_EDIT = 1;
    static final String TAG = "FramePhotoLayout";
    List<FrameImageView> mItemImageViews = new ArrayList();
    OnDragListener mOnDragListener = new OnDragListener() {
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 5:
                        Log.i("Drag Event", "Entered: x=" + dragEvent.getX() + ", y=" + dragEvent.getY());
                        return true;
                    case 6:
                        Log.i("Drag Event", "Exited: x=" + dragEvent.getX() + ", y=" + dragEvent.getY());
                        return true;
                    default:
                        return true;
                }
            } else {
                Log.i("Drag Event", "Dropped: x=" + dragEvent.getX() + ", y=" + dragEvent.getY());
                FrameImageView access$000 = FramePhotoLayout.this.getSelectedFrameImageView((FrameImageView) view, dragEvent);
                if (access$000 == null) {
                    return true;
                }
                FrameImageView frameImageView = (FrameImageView) dragEvent.getLocalState();
                if (access$000.getPhotoItem() == null || frameImageView.getPhotoItem() == null) {
                    return true;
                }
                String str = access$000.getPhotoItem().imagePath;
                String str2 = frameImageView.getPhotoItem().imagePath;
                if (str == null) {
                    str = "";
                }
                if (str2 == null) {
                    str2 = "";
                }
                if (str.equals(str2)) {
                    return true;
                }
                access$000.swapImage(frameImageView);
                return true;
            }
        }
    };
    float mOutputScaleRatio = 1.0f;
    List<PhotoItem> mPhotoItems;

    public QuickAction mQuickAction;

    public OnQuickActionClickListener mQuickActionClickListener;
    int mViewHeight;
    int mViewWidth;

    public interface OnQuickActionClickListener {
        void onChangeActionClick(FrameImageView frameImageView);

        void onEditActionClick(FrameImageView frameImageView);
    }

    public FramePhotoLayout(Context context, List<PhotoItem> list) {
        super(context);
        setLayerType(2, (Paint) null);
        createQuickAction();
        this.mPhotoItems = list;
    }


    public FrameImageView getSelectedFrameImageView(FrameImageView frameImageView, DragEvent dragEvent) {
        Log.d(TAG, "getSelectedFrameImageView");
        FrameImageView frameImageView2 = (FrameImageView) dragEvent.getLocalState();
        int i = (int) (((float) this.mViewHeight) * frameImageView.getPhotoItem().bound.top);
        float x = ((float) ((int) (((float) this.mViewWidth) * frameImageView.getPhotoItem().bound.left))) + dragEvent.getX();
        float y = ((float) i) + dragEvent.getY();
        int size = this.mItemImageViews.size();
        while (true) {
            size--;
            if (size < 0) {
                return null;
            }
            FrameImageView frameImageView3 = this.mItemImageViews.get(size);
            if (frameImageView3.isSelected(x - (((float) this.mViewWidth) * frameImageView3.getPhotoItem().bound.left), y - (((float) this.mViewHeight) * frameImageView3.getPhotoItem().bound.top))) {
                if (frameImageView3 == frameImageView2) {
                    return null;
                }
                return frameImageView3;
            }
        }
    }

    public void saveInstanceState(Bundle bundle) {
        List<FrameImageView> list = this.mItemImageViews;
        if (list != null) {
            for (FrameImageView saveInstanceState : list) {
                saveInstanceState.saveInstanceState(bundle);
            }
        }
    }

    public void restoreInstanceState(Bundle bundle) {
        List<FrameImageView> list = this.mItemImageViews;
        if (list != null) {
            for (FrameImageView restoreInstanceState : list) {
                restoreInstanceState.restoreInstanceState(bundle);
            }
        }
    }

    public void setQuickActionClickListener(OnQuickActionClickListener onQuickActionClickListener) {
        this.mQuickActionClickListener = onQuickActionClickListener;
    }

    void createQuickAction() {
        QuickActionItem quickActionItem = new QuickActionItem(1, getContext().getString(R.string.edit), getResources().getDrawable(R.drawable.menu_edit));
        QuickActionItem quickActionItem2 = new QuickActionItem(2, getContext().getString(R.string.change), getResources().getDrawable(R.drawable.menu_change));
        QuickActionItem quickActionItem3 = new QuickActionItem(3, getContext().getString(R.string.delete), getResources().getDrawable(R.drawable.menu_delete));
        QuickActionItem quickActionItem4 = new QuickActionItem(4, getContext().getString(R.string.cancel), getResources().getDrawable(R.drawable.menu_cancel));
        this.mQuickAction = new QuickAction(getContext(), 0);
        this.mQuickAction.addActionItem(quickActionItem2);
        this.mQuickAction.addActionItem(quickActionItem);
        this.mQuickAction.addActionItem(quickActionItem3);
        this.mQuickAction.addActionItem(quickActionItem4);
        this.mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            public void onItemClick(QuickAction quickAction, int i, int i2) {
                FramePhotoLayout.this.mQuickAction.getActionItem(i);
                FramePhotoLayout.this.mQuickAction.dismiss();
                if (i2 == 3) {
                    ((FrameImageView) FramePhotoLayout.this.mQuickAction.getAnchorView()).clearMainImage();
                } else if (i2 == 1) {
                    if (FramePhotoLayout.this.mQuickActionClickListener != null) {
                        FramePhotoLayout.this.mQuickActionClickListener.onEditActionClick((FrameImageView) FramePhotoLayout.this.mQuickAction.getAnchorView());
                    }
                } else if (i2 == 2 && FramePhotoLayout.this.mQuickActionClickListener != null) {
                    FramePhotoLayout.this.mQuickActionClickListener.onChangeActionClick((FrameImageView) FramePhotoLayout.this.mQuickAction.getAnchorView());
                }
            }
        });
        this.mQuickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            public void onDismiss() {
            }
        });
    }

    boolean isNotLargeThan1Gb() {
        ImageUtils.MemoryInfo memoryInfo = ImageUtils.getMemoryInfo(getContext());
        if (memoryInfo.totalMem <= 0) {
            return false;
        }
        double d = (double) memoryInfo.totalMem;
        Double.isNaN(d);
        return d / 1048576.0d <= 1024.0d;
    }

    public void build(int i, int i2, float f, float f2, float f3) {
        if (i >= 1 && i2 >= 1) {
            this.mViewWidth = i;
            this.mViewHeight = i2;
            this.mOutputScaleRatio = f;
            this.mItemImageViews.clear();
            if (this.mPhotoItems.size() > 4 || isNotLargeThan1Gb()) {
                ImageDecoder.SAMPLER_SIZE = 256;
            } else {
                ImageDecoder.SAMPLER_SIZE = 512;
            }
            String str = TAG;
            Log.d(str, "build, SAMPLER_SIZE = " + ImageDecoder.SAMPLER_SIZE);
            for (PhotoItem addPhotoItemView : this.mPhotoItems) {
                this.mItemImageViews.add(addPhotoItemView(addPhotoItemView, this.mOutputScaleRatio, f2, f3));
            }
        }
    }

    public void build(int i, int i2, float f) {
        build(i, i2, f, 0.0f, 0.0f);
    }

    public void setSpace(float f, float f2) {
        for (FrameImageView space : this.mItemImageViews) {
            space.setSpace(f, f2);
        }
    }

    FrameImageView addPhotoItemView(PhotoItem photoItem, float f, float f2, float f3) {
        int i;
        int i2;
        FrameImageView frameImageView = new FrameImageView(getContext(), photoItem);
        int i3 = (int) (((float) this.mViewWidth) * photoItem.bound.left);
        int i4 = (int) (((float) this.mViewHeight) * photoItem.bound.top);
        if (photoItem.bound.right == 1.0f) {
            i = this.mViewWidth - i3;
        } else {
            i = (int) ((((float) this.mViewWidth) * photoItem.bound.width()) + 0.5f);
        }
        if (photoItem.bound.bottom == 1.0f) {
            i2 = this.mViewHeight - i4;
        } else {
            i2 = (int) ((((float) this.mViewHeight) * photoItem.bound.height()) + 0.5f);
        }
        frameImageView.init((float) i, (float) i2, f, f2, f3);
        frameImageView.setOnImageClickListener(this);
        if (this.mPhotoItems.size() > 1) {
            frameImageView.setOnDragListener(this.mOnDragListener);
        }
        LayoutParams layoutParams = new LayoutParams(i, i2);
        layoutParams.leftMargin = i3;
        layoutParams.topMargin = i4;
        frameImageView.setOriginalLayoutParams(layoutParams);
        addView(frameImageView, layoutParams);
        return frameImageView;
    }

    public Bitmap createImage() throws OutOfMemoryError {
        try {
            Bitmap createBitmap = Bitmap.createBitmap((int) (this.mOutputScaleRatio * ((float) this.mViewWidth)), (int) (this.mOutputScaleRatio * ((float) this.mViewHeight)), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            for (FrameImageView next : this.mItemImageViews) {
                if (next.getImage() != null && !next.getImage().isRecycled()) {
                    int left = (int) (((float) next.getLeft()) * this.mOutputScaleRatio);
                    int top = (int) (((float) next.getTop()) * this.mOutputScaleRatio);
                    int width = (int) (((float) next.getWidth()) * this.mOutputScaleRatio);
                    int height = (int) (((float) next.getHeight()) * this.mOutputScaleRatio);
                    float f = (float) left;
                    float f2 = (float) top;
                    canvas.saveLayer(f, f2, (float) (left + width), (float) (top + height), new Paint(), 31);
                    canvas.translate(f, f2);
                    canvas.clipRect(0, 0, width, height);
                    next.drawOutputImage(canvas);
                    canvas.restore();
                }
            }
            return createBitmap;
        } catch (OutOfMemoryError e) {
            throw e;
        }
    }

    public void recycleImages() {
        Log.d(TAG, "recycleImages");
        for (FrameImageView recycleImage : this.mItemImageViews) {
            recycleImage.recycleImage();
        }
        System.gc();
    }

    public void onLongClickImage(FrameImageView frameImageView) {
        if (this.mPhotoItems.size() > 1) {
            frameImageView.setTag("x=" + frameImageView.getPhotoItem().x + ",y=" + frameImageView.getPhotoItem().y + ",path=" + frameImageView.getPhotoItem().imagePath);
            ClipData.Item item = new ClipData.Item((CharSequence) frameImageView.getTag());
            frameImageView.startDrag(new ClipData(frameImageView.getTag().toString(), new String[]{"text/plain"}, item), new DragShadowBuilder(frameImageView), frameImageView, 0);
        }
    }

    public void onDoubleClickImage(FrameImageView frameImageView) {
        OnQuickActionClickListener onQuickActionClickListener;
        if ((frameImageView.getImage() == null || frameImageView.getImage().isRecycled()) && (onQuickActionClickListener = this.mQuickActionClickListener) != null) {
            onQuickActionClickListener.onChangeActionClick((FrameImageView) this.mQuickAction.getAnchorView());
            return;
        }
        PointF centerPolygon = frameImageView.getCenterPolygon();
        if (centerPolygon == null) {
            centerPolygon = new PointF(((float) frameImageView.getWidth()) / 2.0f, ((float) frameImageView.getHeight()) / 2.0f);
        }
        this.mQuickAction.show(frameImageView, (int) centerPolygon.x, (int) centerPolygon.y);
        this.mQuickAction.setAnimStyle(4);
    }
}

package com.sweet.selfiecameraphotoeditor.collageutils;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.collageutils.quickaction.QuickAction;
import com.sweet.selfiecameraphotoeditor.collageutils.quickaction.QuickActionItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhotoLayout extends RelativeLayout implements ItemImageView.OnImageClickListener {
    static final int ID_CANCEL = 4;
    static final int ID_CHANGE = 2;
    static final int ID_DELETE = 3;
    static final int ID_EDIT = 1;
    Bitmap mBackgroundImage;

    public TransitionImageView mBackgroundImageView;

    public QuickAction mBackgroundQuickAction;
    int mImageHeight;
    int mImageWidth;
    float mInternalScaleRatio = 1.0f;
    List<ItemImageView> mItemImageViews;
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
                ItemImageView itemImageView = (ItemImageView) view;
                ItemImageView itemImageView2 = (ItemImageView) dragEvent.getLocalState();
                String str = "";
                String str2 = "";
                if (itemImageView.getPhotoItem() != null) {
                    str = itemImageView.getPhotoItem().imagePath;
                }
                if (itemImageView2.getPhotoItem() != null) {
                    str2 = itemImageView2.getPhotoItem().imagePath;
                }
                if (str == null) {
                    str = "";
                }
                if (str2 == null) {
                    str2 = "";
                }
                if (str.equals(str2)) {
                    return true;
                }
                itemImageView.swapImage(itemImageView2);
                return true;
            }
        }
    };

    public float mOutputScaleRatio = 1.0f;
    List<PhotoItem> mPhotoItems;

    public ProgressBar mProgressBar;

    public QuickAction mQuickAction;

    public OnQuickActionClickListener mQuickActionClickListener;
    Bitmap mTemplateImage;

    public int mViewHeight;

    public int mViewWidth;

    public interface OnQuickActionClickListener {
        void onChangeActionClick(ItemImageView itemImageView);

        void onChangeBackgroundActionClick(TransitionImageView transitionImageView);

        void onEditActionClick(ItemImageView itemImageView);
    }

    public static List<PhotoItem> parseImageTemplate(ImageTemplate imageTemplate) {
        ArrayList arrayList = new ArrayList();
        try {
            String[] split = imageTemplate.getChild().split(";");
            if (split != null) {
                for (String split2 : split) {
                    String[] split3 = split2.split(",");
                    if (split3 != null) {
                        PhotoItem photoItem = new PhotoItem();
                        photoItem.index = Integer.parseInt(split3[0]);
                        photoItem.x = (float) Integer.parseInt(split3[1]);
                        photoItem.y = (float) Integer.parseInt(split3[2]);
                        photoItem.maskPath = split3[3];
                        arrayList.add(photoItem);
                    }
                }
                Collections.sort(arrayList, new Comparator<PhotoItem>() {
                    public int compare(PhotoItem photoItem, PhotoItem photoItem2) {
                        return photoItem2.index - photoItem.index;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public PhotoLayout(Context context, ImageTemplate imageTemplate) {
        super(context);
        init(parseImageTemplate(imageTemplate), PhotoUtils.decodePNGImage(context, imageTemplate.getTemplate()));
    }

    public PhotoLayout(Context context, List<PhotoItem> list, Bitmap bitmap) {
        super(context);
        init(list, bitmap);
    }

    void init(List<PhotoItem> list, Bitmap bitmap) {
        this.mPhotoItems = list;
        this.mTemplateImage = bitmap;
        this.mImageWidth = this.mTemplateImage.getWidth();
        this.mImageHeight = this.mTemplateImage.getHeight();
        this.mItemImageViews = new ArrayList();
        setLayerType(2, (Paint) null);
        createQuickAction();
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
                PhotoLayout.this.mQuickAction.dismiss();
                if (i2 == 3) {
                    ((ItemImageView) PhotoLayout.this.mQuickAction.getAnchorView()).clearMainImage();
                } else if (i2 == 1) {
                    if (PhotoLayout.this.mQuickActionClickListener != null) {
                        PhotoLayout.this.mQuickActionClickListener.onEditActionClick((ItemImageView) PhotoLayout.this.mQuickAction.getAnchorView());
                    }
                } else if (i2 == 2 && PhotoLayout.this.mQuickActionClickListener != null) {
                    PhotoLayout.this.mQuickActionClickListener.onChangeActionClick((ItemImageView) PhotoLayout.this.mQuickAction.getAnchorView());
                }
            }
        });
        this.mQuickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            public void onDismiss() {
            }
        });
        QuickActionItem quickActionItem5 = new QuickActionItem(2, getContext().getString(R.string.change), getResources().getDrawable(R.drawable.close));
        QuickActionItem quickActionItem6 = new QuickActionItem(3, getContext().getString(R.string.delete), getResources().getDrawable(R.drawable.close));
        QuickActionItem quickActionItem7 = new QuickActionItem(4, getContext().getString(R.string.cancel), getResources().getDrawable(R.drawable.close));
        this.mBackgroundQuickAction = new QuickAction(getContext(), 0);
        this.mBackgroundQuickAction.addActionItem(quickActionItem5);
        this.mBackgroundQuickAction.addActionItem(quickActionItem6);
        this.mBackgroundQuickAction.addActionItem(quickActionItem7);
        this.mBackgroundQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            public void onItemClick(QuickAction quickAction, int i, int i2) {
                PhotoLayout.this.mBackgroundQuickAction.getActionItem(i);
                PhotoLayout.this.mBackgroundQuickAction.dismiss();
                if (i2 == 3) {
                    ((TransitionImageView) PhotoLayout.this.mBackgroundQuickAction.getAnchorView()).recycleImages();
                } else if (i2 == 2 && PhotoLayout.this.mQuickActionClickListener != null) {
                    PhotoLayout.this.mQuickActionClickListener.onChangeBackgroundActionClick((TransitionImageView) PhotoLayout.this.mBackgroundQuickAction.getAnchorView());
                }
            }
        });
    }

    void asyncCreateBackgroundImage(final String str) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            public void onPreExecute() {
                super.onPreExecute();
                PhotoLayout.this.mProgressBar.setVisibility(View.VISIBLE);
            }


            @Override
            public Bitmap doInBackground(Void... voidArr) {
                try {
                    Bitmap decodeFileToBitmap = ImageDecoder.decodeFileToBitmap(str);
                    if (decodeFileToBitmap == null) {
                        return null;
                    }
                    Bitmap blurImage = PhotoUtils.blurImage(decodeFileToBitmap, 10.0f);
                    if (decodeFileToBitmap != blurImage) {
                        decodeFileToBitmap.recycle();
                        System.gc();
                    }
                    return blurImage;
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    return null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return null;
                }
            }


            @Override
            public void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                PhotoLayout.this.mProgressBar.setVisibility(View.GONE);
                if (bitmap != null) {
                    PhotoLayout.this.mBackgroundImageView.init(bitmap, PhotoLayout.this.mViewWidth, PhotoLayout.this.mViewHeight, PhotoLayout.this.mOutputScaleRatio);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void build(int i, int i2, float f) {
        if (i >= 1 && i2 >= 1) {
            this.mViewWidth = i;
            this.mViewHeight = i2;
            this.mOutputScaleRatio = f;
            this.mItemImageViews.clear();
            this.mInternalScaleRatio = 1.0f / PhotoUtils.calculateScaleRatio(this.mImageWidth, this.mImageHeight, i, i2);
            for (PhotoItem addPhotoItemView : this.mPhotoItems) {
                this.mItemImageViews.add(addPhotoItemView(addPhotoItemView, this.mInternalScaleRatio, this.mOutputScaleRatio));
            }
            ImageView imageView = new ImageView(getContext());
            if (Build.VERSION.SDK_INT >= 16) {
                imageView.setBackground(new BitmapDrawable(getResources(), this.mTemplateImage));
            } else {
                imageView.setBackgroundDrawable(new BitmapDrawable(getResources(), this.mTemplateImage));
            }
            addView(imageView, new LayoutParams(-1, -1));
            this.mProgressBar = new ProgressBar(getContext());
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.addRule(13);
            this.mProgressBar.setVisibility(View.GONE);
            addView(this.mProgressBar, layoutParams);
            this.mBackgroundImageView = new TransitionImageView(getContext());
            addView(this.mBackgroundImageView, 0, new LayoutParams(-1, -1));
            this.mBackgroundImageView.setOnImageClickListener(new TransitionImageView.OnImageClickListener() {
                public void onLongClickImage(TransitionImageView transitionImageView) {
                }

                public void onDoubleClickImage(TransitionImageView transitionImageView) {
                    if ((transitionImageView.getImage() == null || transitionImageView.getImage().isRecycled()) && PhotoLayout.this.mQuickActionClickListener != null) {
                        PhotoLayout.this.mQuickActionClickListener.onChangeBackgroundActionClick(transitionImageView);
                        return;
                    }
                    QuickAction access$200 = PhotoLayout.this.mBackgroundQuickAction;
                    double width = (double) transitionImageView.getWidth();
                    Double.isNaN(width);
                    double height = (double) transitionImageView.getHeight();
                    Double.isNaN(height);
                    access$200.show(transitionImageView, (int) (width / 2.0d), (int) (height / 2.0d));
                    PhotoLayout.this.mBackgroundQuickAction.setAnimStyle(4);
                }
            });
            Bitmap bitmap = this.mBackgroundImage;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mBackgroundImageView.init(this.mBackgroundImage, this.mViewWidth, this.mViewHeight, this.mOutputScaleRatio);
            } else if (this.mPhotoItems.size() > 0 && this.mPhotoItems.get(0).imagePath != null && this.mPhotoItems.get(0).imagePath.length() > 0) {
                asyncCreateBackgroundImage(this.mPhotoItems.get(0).imagePath);
            }
        }
    }

    ItemImageView addPhotoItemView(PhotoItem photoItem, float f, float f2) {
        if (photoItem == null || photoItem.maskPath == null) {
            return null;
        }
        ItemImageView itemImageView = new ItemImageView(getContext(), photoItem);
        float width = ((float) itemImageView.getMaskImage().getWidth()) * f;
        float height = ((float) itemImageView.getMaskImage().getHeight()) * f;
        itemImageView.init(width, height, f2);
        itemImageView.setOnImageClickListener(this);
        if (this.mPhotoItems.size() > 1) {
            itemImageView.setOnDragListener(this.mOnDragListener);
        }
        LayoutParams layoutParams = new LayoutParams((int) width, (int) height);
        layoutParams.leftMargin = (int) (photoItem.x * f);
        layoutParams.topMargin = (int) (f * photoItem.y);
        itemImageView.setOriginalLayoutParams(layoutParams);
        addView(itemImageView, layoutParams);
        return itemImageView;
    }

    public void onLongClickImage(ItemImageView itemImageView) {
        if (this.mPhotoItems.size() > 1) {
            itemImageView.setTag("x=" + itemImageView.getPhotoItem().x + ",y=" + itemImageView.getPhotoItem().y + ",path=" + itemImageView.getPhotoItem().imagePath);
            ClipData.Item item = new ClipData.Item((CharSequence) itemImageView.getTag());
            itemImageView.startDrag(new ClipData(itemImageView.getTag().toString(), new String[]{"text/plain"}, item), new DragShadowBuilder(itemImageView), itemImageView, 0);
        }
    }

    public void onDoubleClickImage(ItemImageView itemImageView) {
        OnQuickActionClickListener onQuickActionClickListener;
        if ((itemImageView.getImage() == null || itemImageView.getImage().isRecycled()) && (onQuickActionClickListener = this.mQuickActionClickListener) != null) {
            onQuickActionClickListener.onChangeActionClick(itemImageView);
            return;
        }
        this.mQuickAction.show(itemImageView);
        this.mQuickAction.setAnimStyle(4);
    }
}

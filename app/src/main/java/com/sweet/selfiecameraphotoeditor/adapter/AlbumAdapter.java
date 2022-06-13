package com.sweet.selfiecameraphotoeditor.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sweet.selfiecameraphotoeditor.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.model.ImageModel;
import com.sweet.selfiecameraphotoeditor.myinterface.OnAlbum;

import java.io.File;
import java.util.ArrayList;

public class AlbumAdapter extends ArrayAdapter<ImageModel> {
    Context context;
    ArrayList<ImageModel> data = new ArrayList<>();
    int layoutResourceId;
    OnAlbum onItem;
    int pHeightItem = 0;
    int pWHIconNext = 0;

    static class RecordHolder {
        ImageView iconNext;
        ImageView imageItem;
        RelativeLayout layoutRoot;
        CheckBox select;
        CustomTextView txtPath;
        CustomTextView txtTitle;

        RecordHolder() {
        }
    }

    public AlbumAdapter(Context context2, int i, ArrayList<ImageModel> arrayList) {
        super(context2, i, arrayList);
        this.layoutResourceId = i;
        this.context = context2;
        this.data = arrayList;
        this.pHeightItem = getDisplayInfo((Activity) context2).widthPixels / 6;
        this.pWHIconNext = this.pHeightItem / 4;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final RecordHolder recordHolder;
        if (view == null) {
            view = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, viewGroup, false);
            recordHolder = new RecordHolder();
            recordHolder.txtTitle = (CustomTextView) view.findViewById(R.id.name_album);
            recordHolder.txtPath = (CustomTextView) view.findViewById(R.id.path_album);
            recordHolder.imageItem = (ImageView) view.findViewById(R.id.icon_album);
            recordHolder.iconNext = (ImageView) view.findViewById(R.id.iconNext);
            recordHolder.layoutRoot = (RelativeLayout) view.findViewById(R.id.layoutRoot);
            recordHolder.layoutRoot.getLayoutParams().height = this.pHeightItem;
            recordHolder.imageItem.getLayoutParams().width = this.pHeightItem;
            recordHolder.imageItem.getLayoutParams().height = this.pHeightItem;
            recordHolder.iconNext.getLayoutParams().width = this.pWHIconNext;
            recordHolder.iconNext.getLayoutParams().height = this.pWHIconNext;
            recordHolder.select = (CheckBox) view.findViewById(R.id.select);
            view.setTag(recordHolder);
        } else {
            recordHolder = (RecordHolder) view.getTag();
        }
        ImageModel imageModel = this.data.get(i);
        recordHolder.txtTitle.setText(imageModel.getName());
        recordHolder.txtPath.setText(imageModel.getPathFolder());
        ((RequestBuilder) Glide.with(this.context).load(new File(imageModel.getPathFile())).placeholder((int) R.drawable.empty_photo)).into(recordHolder.imageItem);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AlbumAdapter.this.onItem != null) {
                    AlbumAdapter.this.onItem.onitemalbumclick(i);
                }
            }
        });
        if (this.data.get(i).isSelected()) {
            recordHolder.select.setChecked(true);
        } else {
            recordHolder.select.setChecked(false);
        }
        recordHolder.select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AlbumAdapter.this.data.get(i).isSelected()) {
                    AlbumAdapter.this.data.get(i).setSelected(false);
                    recordHolder.select.setChecked(false);
                    AlbumAdapter.this.onItem.onlongalbumclicklist(i, false);
                } else {
                    AlbumAdapter.this.data.get(i).setSelected(true);
                    recordHolder.select.setChecked(true);
                    AlbumAdapter.this.onItem.onlongalbumclicklist(i, true);
                }
                AlbumAdapter.this.notifyDataSetChanged();
            }
        });
        return view;
    }

    public OnAlbum getOnItem() {
        return this.onItem;
    }

    public void setOnItem(OnAlbum onAlbum) {
        this.onItem = onAlbum;
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}

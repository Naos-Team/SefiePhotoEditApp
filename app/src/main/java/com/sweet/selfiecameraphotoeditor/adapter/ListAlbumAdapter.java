package com.sweet.selfiecameraphotoeditor.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sweet.selfiecameraphotoeditor.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.sweet.selfiecameraphotoeditor.model.ImageModel;
import com.sweet.selfiecameraphotoeditor.myinterface.OnListAlbum;

import java.util.ArrayList;

public class ListAlbumAdapter extends ArrayAdapter<ImageModel> {
    Context context;
    ArrayList<ImageModel> data = new ArrayList<>();
    int layoutResourceId;
    OnListAlbum onListAlbum;
    int pHeightItem = 0;

    static class RecordHolder {
        ImageView check;
        ImageView click;
        ImageView imageItem;
        ImageView ivPlay;
        RelativeLayout layoutRoot;

        RecordHolder() {
        }
    }

    public ListAlbumAdapter(Context context2, int i, ArrayList<ImageModel> arrayList) {
        super(context2, i, arrayList);
        this.layoutResourceId = i;
        this.context = context2;
        this.data = arrayList;
        this.pHeightItem = getDisplayInfo((Activity) context2).widthPixels / 3;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordHolder recordHolder;
        if (view == null) {
            view = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, viewGroup, false);
            recordHolder = new RecordHolder();
            recordHolder.imageItem = (ImageView) view.findViewById(R.id.imageItem);
            recordHolder.check = (ImageView) view.findViewById(R.id.check);
            recordHolder.click = (ImageView) view.findViewById(R.id.click);
            recordHolder.layoutRoot = (RelativeLayout) view.findViewById(R.id.layoutRoot);
            recordHolder.ivPlay = (ImageView) view.findViewById(R.id.ivPlay);
            recordHolder.layoutRoot.getLayoutParams().height = this.pHeightItem;
            recordHolder.imageItem.getLayoutParams().width = this.pHeightItem;
            recordHolder.imageItem.getLayoutParams().height = this.pHeightItem;
            recordHolder.click.getLayoutParams().width = this.pHeightItem;
            recordHolder.click.getLayoutParams().height = this.pHeightItem;
            view.setTag(recordHolder);
        } else {
            recordHolder = (RecordHolder) view.getTag();
        }
        final ImageModel imageModel = this.data.get(i);
        if (imageModel.getPathFile().contains("mp4")) {
            recordHolder.ivPlay.setVisibility(View.VISIBLE);
        } else {
            recordHolder.ivPlay.setVisibility(View.GONE);
        }
        ((RequestBuilder) Glide.with(this.context).load(imageModel.getPathFile()).placeholder((int) R.drawable.empty_photo)).into(recordHolder.imageItem);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ListAlbumAdapter.this.onListAlbum != null) {
                    ListAlbumAdapter.this.onListAlbum.onitemlistalbumclick(imageModel);
                }
            }
        });
        return view;
    }

    public OnListAlbum getOnListAlbum() {
        return this.onListAlbum;
    }

    public void setOnListAlbum(OnListAlbum onListAlbum2) {
        this.onListAlbum = onListAlbum2;
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}

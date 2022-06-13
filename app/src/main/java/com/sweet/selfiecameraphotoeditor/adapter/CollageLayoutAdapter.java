package com.sweet.selfiecameraphotoeditor.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.sweet.selfiecameraphotoeditor.R;
import com.bumptech.glide.Glide;
import com.sweet.selfiecameraphotoeditor.collageutils.PhotoUtils;
import com.sweet.selfiecameraphotoeditor.collageutils.TemplateItem;

import java.util.ArrayList;

public class CollageLayoutAdapter extends RecyclerView.Adapter<CollageLayoutAdapter.PreviewTemplateViewHolder> {

    public OnPreviewTemplateClickListener mListener;

    public ArrayList<TemplateItem> mTemplateItems;

    public interface OnPreviewTemplateClickListener {
        void onPreviewTemplateClick(TemplateItem templateItem);
    }

    public static class PreviewTemplateViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;

        PreviewTemplateViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public CollageLayoutAdapter(ArrayList<TemplateItem> arrayList, OnPreviewTemplateClickListener onPreviewTemplateClickListener) {
        this.mTemplateItems = arrayList;
        this.mListener = onPreviewTemplateClickListener;
    }

    public PreviewTemplateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PreviewTemplateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.collagelayout_adapter, viewGroup, false));
    }

    public void onBindViewHolder(PreviewTemplateViewHolder previewTemplateViewHolder, final int i) {
        if (this.mTemplateItems.get(i).isSelected()) {
            previewTemplateViewHolder.mImageView.setBackgroundResource(R.drawable.layout_selected);
        } else {
            previewTemplateViewHolder.mImageView.setBackgroundResource(R.drawable.layout_unselected);
        }
        if (this.mTemplateItems.get(i).getPreview().startsWith(PhotoUtils.ASSET_PREFIX)) {
            Glide.with(previewTemplateViewHolder.mImageView.getContext()).load(Uri.parse("file:///android_asset/".concat(this.mTemplateItems.get(i).getPreview().substring(9)))).into(previewTemplateViewHolder.mImageView);
        }
        previewTemplateViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CollageLayoutAdapter.this.mListener != null) {
                    CollageLayoutAdapter.this.mListener.onPreviewTemplateClick((TemplateItem) CollageLayoutAdapter.this.mTemplateItems.get(i));
                }
            }
        });
    }

    public int getItemCount() {
        return this.mTemplateItems.size();
    }
}

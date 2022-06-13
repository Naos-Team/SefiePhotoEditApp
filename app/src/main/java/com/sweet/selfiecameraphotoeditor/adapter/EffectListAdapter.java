package com.sweet.selfiecameraphotoeditor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.sweet.selfiecameraphotoeditor.R;

public class EffectListAdapter extends RecyclerView.Adapter<EffectListAdapter.ViewHolder> {
    CallBackData callbackdata;
    private int[] listdata;

    public interface CallBackData {
        void imageget(int i);
    }

    public EffectListAdapter(int[] iArr, CallBackData callBackData) {
        this.listdata = iArr;
        this.callbackdata = callBackData;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.effectlist_adapter, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.imageView.setImageResource(this.listdata[i]);
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EffectListAdapter.this.callbackdata.imageget(i);
            }
        });
    }

    public int getItemCount() {
        return this.listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        }
    }
}

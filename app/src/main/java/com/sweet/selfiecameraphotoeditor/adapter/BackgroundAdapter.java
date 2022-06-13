package com.sweet.selfiecameraphotoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.model.Drawables;

import java.util.ArrayList;

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.ReyclerViewHolder> {
    private Context context;
    String filter;
    private LayoutInflater layoutInflater;

    public AdapterfilterCallback mAdapterCallback;
    ArrayList<Drawables> overlayList = new ArrayList<>();

    public interface AdapterfilterCallback {
        void onMethodCallbackforfilter(int i, String str);
    }

    public BackgroundAdapter(Context context2, ArrayList<Drawables> arrayList, String str) {
        this.layoutInflater = LayoutInflater.from(context2);
        this.context = context2;
        this.overlayList = arrayList;
        this.filter = str;
        try {
            this.mAdapterCallback = (AdapterfilterCallback) context2;
        } catch (ClassCastException unused) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    public ReyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ReyclerViewHolder(this.layoutInflater.inflate(R.layout.background_adapter, viewGroup, false));
    }

    public void onBindViewHolder(ReyclerViewHolder reyclerViewHolder, final int i) {
        if (this.filter.equalsIgnoreCase("Background")) {
            Glide.with(this.context).load(Integer.valueOf(this.overlayList.get(i).getImage())).into(reyclerViewHolder.imgShape);
        } else {
            if (i == 0) {
                reyclerViewHolder.imgShape.setImageResource(R.drawable.img_placeholder);
            } else {
                Glide.with(this.context).load(Integer.valueOf(this.overlayList.get(i).getImage())).into(reyclerViewHolder.imgShape);
            }
            if (this.overlayList.get(i).isSelected()) {
                reyclerViewHolder.imgShape.setBackgroundResource(R.drawable.layout_selected);
            } else {
                reyclerViewHolder.imgShape.setBackgroundResource(R.drawable.layout_unselected);
            }
        }
        reyclerViewHolder.imgShape.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                for (int i = 0; i < BackgroundAdapter.this.overlayList.size(); i++) {
                    if (i == i) {
                        BackgroundAdapter.this.overlayList.get(i).setSelected(true);
                    } else {
                        BackgroundAdapter.this.overlayList.get(i).setSelected(false);
                    }
                }
                try {
                    BackgroundAdapter.this.mAdapterCallback.onMethodCallbackforfilter(i, BackgroundAdapter.this.filter);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
                BackgroundAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public int getItemCount() {
        return this.overlayList.size();
    }

    class ReyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgShape;

        private ReyclerViewHolder(View view) {
            super(view);
            this.imgShape = (ImageView) view.findViewById(R.id.imgShape);
        }
    }
}

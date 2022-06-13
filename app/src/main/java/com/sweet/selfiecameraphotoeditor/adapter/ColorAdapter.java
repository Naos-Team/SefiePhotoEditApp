package com.sweet.selfiecameraphotoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sweet.selfiecameraphotoeditor.R;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ReyclerViewHolder> {
    private Context context;
    private int[] items;
    private LayoutInflater layoutInflater;

    public AdapterCallback mAdapterCallback;

    public interface AdapterCallback {
        void oncolorBackgroundCallback(int i);
    }

    public ColorAdapter(Context context2, int[] iArr) {
        this.layoutInflater = LayoutInflater.from(context2);
        this.context = context2;
        this.items = iArr;
        try {
            this.mAdapterCallback = (AdapterCallback) context2;
        } catch (ClassCastException unused) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    public ReyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ReyclerViewHolder(this.layoutInflater.inflate(R.layout.color_adapter, viewGroup, false));
    }

    public void onBindViewHolder(ReyclerViewHolder reyclerViewHolder, final int i) {
        reyclerViewHolder.cardfont.setCardBackgroundColor(this.items[i]);
        reyclerViewHolder.cardfont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    ColorAdapter.this.mAdapterCallback.oncolorBackgroundCallback(i);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int getItemCount() {
        return this.items.length;
    }

    class ReyclerViewHolder extends RecyclerView.ViewHolder {
        CardView cardfont;

        private ReyclerViewHolder(View view) {
            super(view);
            this.cardfont = (CardView) view.findViewById(R.id.cardfont);
        }
    }
}

package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.flyu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;


import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.flyu.hardcode.HardCodeData;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.EffectUtils;

import java.util.List;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.EffectHolder> {
    private Context context;

    public List<HardCodeData.EffectItem> effectItems;

    public OnEffectChangeListener onEffectChangeListener;

    public int selected = 0;

    public interface OnEffectChangeListener {
        void onFilterChanged(HardCodeData.EffectItem effectItem);
    }

    public EffectAdapter(Context context2, List<HardCodeData.EffectItem> list) {
        this.effectItems = list;
        this.context = context2;
    }

    @Override
    public int getItemViewType(int i) {
        return super.getItemViewType(i);
    }

    public EffectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.effect_item_layout, viewGroup, false);
        EffectHolder effectHolder = new EffectHolder(inflate);
        effectHolder.thumbImage = (ImageView) inflate.findViewById(R.id.effect_thumb_image);
        effectHolder.filterRoot = (LinearLayout) inflate.findViewById(R.id.effect_root);
        effectHolder.filterImg = (FrameLayout) inflate.findViewById(R.id.effect_img_panel);
        return effectHolder;
    }

    public void onBindViewHolder(EffectHolder effectHolder, final int i) {
        effectHolder.thumbImage.setImageBitmap(EffectUtils.getEffectThumbFromFile(this.context, this.effectItems.get(i)));
        if (i == this.selected) {
            effectHolder.filterImg.setBackgroundResource(R.drawable.effect_item_selected_bg);
        } else {
            effectHolder.filterImg.setBackgroundResource(0);
        }
        effectHolder.filterRoot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (EffectAdapter.this.selected != i) {
                    int selected = EffectAdapter.this.selected;
                    int unused = EffectAdapter.this.selected = i;
                    EffectAdapter.this.notifyItemChanged(selected);
                    EffectAdapter.this.notifyItemChanged(i);
                    if (EffectAdapter.this.onEffectChangeListener != null) {
                        EffectAdapter.this.onEffectChangeListener.onFilterChanged((HardCodeData.EffectItem) EffectAdapter.this.effectItems.get(i));
                    }
                }
            }
        });
    }

    public int getItemCount() {
        List<HardCodeData.EffectItem> list = this.effectItems;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    class EffectHolder extends RecyclerView.ViewHolder {
        FrameLayout filterImg;
        LinearLayout filterRoot;
        ImageView thumbImage;

        public EffectHolder(View view) {
            super(view);
        }
    }

    public void setOnEffectChangeListener(OnEffectChangeListener onEffectChangeListener2) {
        this.onEffectChangeListener = onEffectChangeListener2;
    }
}

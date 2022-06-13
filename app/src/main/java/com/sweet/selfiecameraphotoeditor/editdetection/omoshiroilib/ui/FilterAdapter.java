package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.GlobalConfig;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterResourceHelper;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterType;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder> {
    private Context context;

    public List<FilterType> filterTypeList;

    public OnFilterChangeListener onFilterChangeListener;

    public int selected = 0;

    public interface OnFilterChangeListener {
        void onFilterChanged(FilterType filterType);
    }

    public FilterAdapter(Context context2, List<FilterType> list) {
        this.filterTypeList = list;
        this.context = context2;
    }

    @Override
    public int getItemViewType(int i) {
        if (i == 1) {
            return -1;
        }
        return super.getItemViewType(i);
    }

    public FilterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == -1) {
            return new FilterHolder(LayoutInflater.from(this.context).inflate(R.layout.filter_division_layout, viewGroup, false));
        }
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.filter_item_layout, viewGroup, false);
        FilterHolder filterHolder = new FilterHolder(inflate);
        filterHolder.thumbImage = (ImageView) inflate.findViewById(R.id.filter_thumb_image);
        filterHolder.filterName = (TextView) inflate.findViewById(R.id.filter_thumb_name);
        filterHolder.filterRoot = (LinearLayout) inflate.findViewById(R.id.filter_root);
        filterHolder.filterImg = (FrameLayout) inflate.findViewById(R.id.filter_img_panel);
        return filterHolder;
    }

    public void onBindViewHolder(FilterHolder filterHolder, final int i) {
        if (i != 1) {
            FilterType filterType = this.filterTypeList.get(i);
            filterHolder.thumbImage.setImageBitmap(FilterResourceHelper.getFilterThumbFromFile(this.context, filterType));
            filterHolder.filterName.setText(FilterResourceHelper.getSimpleName(filterType));
            if (i == this.selected) {
                filterHolder.filterImg.setBackgroundResource(R.drawable.effect_item_selected_bg);
                filterHolder.filterName.setTextColor(GlobalConfig.context.getResources().getColor(R.color.app_color));
            } else {
                filterHolder.filterImg.setBackgroundResource(0);
                filterHolder.filterName.setTextColor(-1);
            }
            filterHolder.filterRoot.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (FilterAdapter.this.selected != i) {
                        int selected1 = FilterAdapter.this.selected;
                        int unused = FilterAdapter.this.selected = i;
                        FilterAdapter.this.notifyItemChanged(selected1);
                        FilterAdapter.this.notifyItemChanged(i);
                        if (FilterAdapter.this.onFilterChangeListener != null) {
                            FilterAdapter.this.onFilterChangeListener.onFilterChanged((FilterType) FilterAdapter.this.filterTypeList.get(i));
                        }
                    }
                }
            });
        }
    }

    public int getItemCount() {
        return this.filterTypeList == null ? 0 : 30;
    }

    class FilterHolder extends RecyclerView.ViewHolder {
        FrameLayout filterImg;
        TextView filterName;
        LinearLayout filterRoot;
        ImageView thumbImage;

        public FilterHolder(View view) {
            super(view);
        }
    }

    public void setOnFilterChangeListener(OnFilterChangeListener onFilterChangeListener2) {
        this.onFilterChangeListener = onFilterChangeListener2;
    }
}

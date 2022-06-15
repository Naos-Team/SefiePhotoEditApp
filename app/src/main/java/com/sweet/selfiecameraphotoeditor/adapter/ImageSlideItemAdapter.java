package com.sweet.selfiecameraphotoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.model.Image;
import com.sweet.selfiecameraphotoeditor.model.ImageSlideItem;

import java.util.List;

public class ImageSlideItemAdapter extends PagerAdapter {
    private Context context;
    private List<ImageSlideItem> itemList;

    public ImageSlideItemAdapter(Context context, List<ImageSlideItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_slider_camera, container, false);
        ImageView imageView = view.findViewById(R.id.img_slider_item);

        ImageSlideItem imageSlideItem = itemList.get(position);
        if (imageSlideItem != null){
            Glide.with(context).load(imageSlideItem.getResourceID()).into(imageView);
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (itemList!=null){
            return itemList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

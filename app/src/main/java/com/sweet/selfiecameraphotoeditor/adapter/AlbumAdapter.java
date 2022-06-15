package com.sweet.selfiecameraphotoeditor.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.model.Image;
import com.sweet.selfiecameraphotoeditor.model.ImageModel;
import com.sweet.selfiecameraphotoeditor.myinterface.OnAlbum;

import java.io.File;
import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumItemHolder>{
    Context context;
    ArrayList<ImageModel> data = new ArrayList<>();
    OnAlbum onItem;

    public AlbumAdapter(Context context, ArrayList<ImageModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AlbumAdapter.AlbumItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piclist_row_album, parent, false);
        return new AlbumAdapter.AlbumItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumItemHolder holder, @SuppressLint("RecyclerView") int position) {
        ImageModel imageModel = this.data.get(position);
        if (imageModel.getName().length()>0){
            holder.tv_name.setText(imageModel.getName());
        }
        else{
            holder.tv_name.setText("Unknown");
        }

        if (Integer.parseInt(imageModel.getCountFile())==1){
            holder.tv_count.setText("1 file");
        }
        else{
            holder.tv_count.setText(imageModel.getCountFile()+" files");
        }

        ((RequestBuilder) Glide.with(this.context).load(new File(imageModel.getPathFile())).placeholder((int) R.drawable.empty_photo)).into(holder.iv_album);
        holder.layout_root.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AlbumAdapter.this.onItem != null) {
                    AlbumAdapter.this.onItem.onitemalbumclick(position);
                    //AlbumAdapter.this.data.get(i).setSelected(true);
                }
            }
        });

        int height = context.getResources().getDisplayMetrics().heightPixels;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int)Math.round(width/3.2), (int)Math.round(height*0.21));
        layoutParams.setMargins(0,8,0,8);
        holder.layout_root.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        if (data!=null){
            return data.size();
        }
        return 0;
    }

        public OnAlbum getOnItem() {
        return this.onItem;
    }

    public void setOnItem(OnAlbum onAlbum) {
        this.onItem = onAlbum;
    }

    class AlbumItemHolder extends RecyclerView.ViewHolder{
        RoundedImageView iv_album;
        CustomTextView tv_name, tv_count;
        ConstraintLayout layout_root;
        public AlbumItemHolder(@NonNull View itemView) {
            super(itemView);
            iv_album = itemView.findViewById(R.id.icon_album);
            tv_name = itemView.findViewById(R.id.name_album);
            tv_count = itemView.findViewById(R.id.path_album);
            layout_root = itemView.findViewById(R.id.layoutRoot);
        }
    }

        public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}

//public class AlbumAdapter extends ArrayAdapter<ImageModel> {
//    Context context;
//    ArrayList<ImageModel> data = new ArrayList<>();
//    int layoutResourceId;
//    OnAlbum onItem;
//    int pHeightItem = 0;
//    int pWHIconNext = 0;
//
//    static class RecordHolder {
//        //ImageView iconNext;
//        ImageView imageItem;
//        ConstraintLayout layoutRoot;
//        //CheckBox select;
//        CustomTextView txtPath;
//        CustomTextView txtTitle;
//
//        RecordHolder() {
//        }
//    }
//
//    public AlbumAdapter(Context context2, int i, ArrayList<ImageModel> arrayList) {
//        super(context2, i, arrayList);
//        this.layoutResourceId = i;
//        this.context = context2;
//        this.data = arrayList;
////        this.pHeightItem = getDisplayInfo((Activity) context2).widthPixels / 6;
////        this.pWHIconNext = this.pHeightItem / 4;
//    }
//
//    @Override
//    public View getView(final int i, View view, ViewGroup viewGroup) {
//        final RecordHolder recordHolder;
//        if (view == null) {
//            view = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, viewGroup, false);
//            recordHolder = new RecordHolder();
//            recordHolder.txtTitle = (CustomTextView) view.findViewById(R.id.name_album);
//            recordHolder.txtPath = (CustomTextView) view.findViewById(R.id.path_album);
//            recordHolder.imageItem = (ImageView) view.findViewById(R.id.icon_album);
//            recordHolder.layoutRoot = (ConstraintLayout) view.findViewById(R.id.layoutRoot);
//            //recordHolder.iconNext = (ImageView) view.findViewById(R.id.iconNext);
//            //recordHolder.select = (CheckBox) view.findViewById(R.id.select);
//
//            //            recordHolder.layoutRoot.getLayoutParams().height = this.pHeightItem;
////            recordHolder.imageItem.getLayoutParams().width = this.pHeightItem;
////            recordHolder.imageItem.getLayoutParams().height = this.pHeightItem;
//            //recordHolder.iconNext.getLayoutParams().width = this.pWHIconNext;
//            //recordHolder.iconNext.getLayoutParams().height = this.pWHIconNext;
//            view.setTag(recordHolder);
//        } else {
//            recordHolder = (RecordHolder) view.getTag();
//        }
//        ImageModel imageModel = this.data.get(i);
//        if (imageModel.getName().length()>0){
//            recordHolder.txtTitle.setText(imageModel.getName());
//        }
//        else{
//            recordHolder.txtTitle.setText("Unknown");
//        }
//
//
//        if (Integer.parseInt(imageModel.getCountFile())==1){
//            recordHolder.txtPath.setText("1 file");
//        }
//        else{
//            recordHolder.txtPath.setText(imageModel.getCountFile()+" files");
//        }
//
//        ((RequestBuilder) Glide.with(this.context).load(new File(imageModel.getPathFile())).placeholder((int) R.drawable.empty_photo)).into(recordHolder.imageItem);
//        view.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (AlbumAdapter.this.onItem != null) {
//                    AlbumAdapter.this.onItem.onitemalbumclick(i);
//                    //AlbumAdapter.this.data.get(i).setSelected(true);
//                }
//            }
//        });
//
//        int height = getContext().getResources().getDisplayMetrics().heightPixels;
//        int width = getContext().getResources().getDisplayMetrics().widthPixels;
//        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int)Math.round(width*0.27), (int)Math.round(height*0.21));
//        layoutParams.setMargins(5,8,5,8);
//        recordHolder.layoutRoot.setLayoutParams(layoutParams);
////        if (this.data.get(i).isSelected()) {
////            recordHolder.select.setChecked(true);
////        } else {
////            recordHolder.select.setChecked(false);
////        }
////        recordHolder.select.setOnClickListener(new View.OnClickListener() {
////            public void onClick(View view) {
////                if (AlbumAdapter.this. data.get(i).isSelected()) {
////                    AlbumAdapter.this.data.get(i).setSelected(false);
////                    recordHolder.select.setChecked(false);
////                    AlbumAdapter.this.onItem.onlongalbumclicklist(i, false);
////                } else {
////                    AlbumAdapter.this.data.get(i).setSelected(true);
////                    recordHolder.select.setChecked(true);
////                    AlbumAdapter.this.onItem.onlongalbumclicklist(i, true);
////                }
////                AlbumAdapter.this.notifyDataSetChanged();
////            }
////        });
//        return view;
//    }
//
//    public OnAlbum getOnItem() {
//        return this.onItem;
//    }
//
//    public void setOnItem(OnAlbum onAlbum) {
//        this.onItem = onAlbum;
//    }
//
//    public static DisplayMetrics getDisplayInfo(Activity activity) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics;
//    }
//}

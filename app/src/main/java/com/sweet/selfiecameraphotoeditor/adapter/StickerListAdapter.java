package com.sweet.selfiecameraphotoeditor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.stickerview.Color_Module;
import com.sweet.selfiecameraphotoeditor.stickerview.Eva;

import java.util.ArrayList;

public class StickerListAdapter extends RecyclerView.Adapter<StickerListAdapter.MyViewHolder> {
    ArrayList<Color_Module> arrayList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public StickerListAdapter(Context context2, ArrayList<Color_Module> arrayList) {
        this.context = context2;
        this.arrayList = arrayList;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stickerlist_adapter, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        try {
            ImageView imageView = myViewHolder.image;
            imageView.setImageBitmap(Eva.getBitmapFromAsset(this.arrayList.get(i).getDirName() + this.arrayList.get(i).getFileName(), this.context));
        } catch (Exception unused) {
            Log.d("","");
        }
    }

    public int getItemCount() {
        return this.arrayList.size();
    }
}

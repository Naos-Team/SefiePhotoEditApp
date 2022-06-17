package com.sweet.selfiecameraphotoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.model.ScaleItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ScaleItemAdapter extends RecyclerView.Adapter<ScaleItemAdapter.MyViewHolder>{

    private ArrayList<ScaleItem> scaleItems;
    private ScaleItemListener listener;
    private Context context;

    public ScaleItemAdapter(ArrayList<ScaleItem> scaleItems, ScaleItemListener listener) {
        this.scaleItems = scaleItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_scale, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScaleItem item = scaleItems.get(position);

        holder.iv_scale.setImageDrawable(context.getResources().getDrawable(item.getThumbnail()));
        holder.tv_title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return scaleItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_scale;
        TextView tv_title;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            iv_scale = itemView.findViewById(R.id.iv_scale);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

    public interface ScaleItemListener{
        void onClick(ScaleItem item);
    }
}

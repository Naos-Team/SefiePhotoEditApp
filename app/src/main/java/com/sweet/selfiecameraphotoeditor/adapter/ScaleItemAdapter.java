package com.sweet.selfiecameraphotoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

        holder.tv_title.setText(item.getTitle());

        int x = item.getScaleX();
        int y = item.getScaleY();


        if(item.getId() == 0){
            holder.iv_custom.setVisibility(View.VISIBLE);
        }

        if(x > y){
            double new_scale = round((double) y/x, 1);
            int new_height = (int) (new_scale * holder.rl_scale_border.getLayoutParams().width);
            holder.rl_scale.getLayoutParams().height = new_height;
            holder.iv_custom.setVisibility(View.GONE);
        }else if(y > x){
            double new_scale = round((double) x/y, 1);
            int new_width = (int) (new_scale * holder.rl_scale_border.getLayoutParams().height);
            holder.rl_scale.getLayoutParams().width = new_width;
            holder.iv_custom.setVisibility(View.GONE);
        }

        holder.rl_scale_border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(item);
            }
        });

        holder.rl_scale.requestLayout();


    }

    @Override
    public int getItemCount() {
        return scaleItems.size();
    }

    public void setData(ArrayList<ScaleItem> arrayList){
        this.scaleItems = arrayList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl_scale, rl_scale_border;
        TextView tv_title;
        ImageView iv_custom;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            rl_scale = itemView.findViewById(R.id.rl_scale);
            tv_title = itemView.findViewById(R.id.tv_title);
            rl_scale_border = itemView.findViewById(R.id.rl_scale_border);
            iv_custom = itemView.findViewById(R.id.iv_custom);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public interface ScaleItemListener{
        void onClick(ScaleItem item);
    }
}

package com.sweet.selfiecameraphotoeditor.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.activities.CameraCreationFragment;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;

import java.io.File;

public class Camera_Creation_Adapter extends BaseAdapter {
    static LayoutInflater mLayoutInflater;
    Context activity;
    String[] fileName;
    String[] filePath;

    public long getItemId(int i) {
        return (long) i;
    }

    static class ViewHolder {
        ImageView deleteBtn;
        ImageView gallryImg;

        ViewHolder() {
        }
    }

    @SuppressLint({"WrongConstant"})
    public Camera_Creation_Adapter(Activity activity2, String[] strArr, String[] strArr2) {
        this.activity = activity2;
        this.filePath = strArr;
        this.fileName = strArr2;
        mLayoutInflater = (LayoutInflater) this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.filePath.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.photocreation_adapter, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.gallryImg = (ImageView) view.findViewById(R.id.img_gallary);
            viewHolder.deleteBtn = (ImageView) view.findViewById(R.id.img_del);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(this.activity).load(this.filePath[i]).into(viewHolder.gallryImg);
        viewHolder.deleteBtn.setTag(Integer.valueOf(i));
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final int intValue = ((Integer) view.getTag()).intValue();
                final Dialog dialog = new Dialog(Camera_Creation_Adapter.this.activity);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.dialog_delete);
                dialog.setCancelable(false);
                ((CustomTextView) dialog.findViewById(R.id.txt_no)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                ((CustomTextView) dialog.findViewById(R.id.txt_yes)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        new File(Camera_Creation_Adapter.this.filePath[intValue]).delete();
                        Camera_Creation_Adapter.this.notifyDataSetChanged();
                        CameraCreationFragment.refreshload();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}

package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;

public class C2775a extends BaseAdapter {
    private final String[] f12534a;
    private Context f12535b;

    public long getItemId(int i) {
        return 0;
    }

    public class C2765a {
        CustomTextView f12487a;
        final C2775a f12488b;

        public C2765a(C2775a c2775a) {
            this.f12488b = c2775a;
        }
    }

    public C2775a(Context context, String[] strArr) {
        this.f12535b = context;
        this.f12534a = strArr;
    }

    public int getCount() {
        return this.f12534a.length;
    }

    public Object getItem(int i) {
        return this.f12534a[i];
    }

    @SuppressLint({"WrongConstant"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        C2765a c2765a;
        if (view == null) {
            view = ((LayoutInflater) this.f12535b.getSystemService("layout_inflater")).inflate(R.layout.add_text_item, (ViewGroup) null);
            c2765a = new C2765a(this);
            c2765a.f12487a = (CustomTextView) view.findViewById(R.id.grid_text);
            view.setTag(c2765a);
        } else {
            c2765a = (C2765a) view.getTag();
        }
        CustomTextView customTextView = c2765a.f12487a;
        AssetManager assets = this.f12535b.getAssets();
        customTextView.setTypeface(Typeface.createFromAsset(assets, "fonts/" + this.f12534a[i]));
        return view;
    }
}

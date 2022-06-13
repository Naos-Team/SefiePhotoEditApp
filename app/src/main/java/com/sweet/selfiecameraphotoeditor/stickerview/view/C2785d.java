package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sweet.selfiecameraphotoeditor.R;

public class C2785d extends BaseAdapter {
    private final String[] f12587a;
    private Context f12588b;

    public long getItemId(int i) {
        return 0;
    }

    public class C2784a {
        ImageView f12585a;
        final C2785d f12586b;

        public C2784a(C2785d c2785d) {
            this.f12586b = c2785d;
        }
    }

    public C2785d(Context context, String[] strArr) {
        this.f12588b = context;
        this.f12587a = strArr;
    }

    public int getCount() {
        return this.f12587a.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(Color.parseColor(this.f12587a[i]));
    }

    @SuppressLint({"WrongConstant"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        C2784a c2784a;
        if (view == null) {
            view = ((LayoutInflater) this.f12588b.getSystemService("layout_inflater")).inflate(R.layout.color_item, viewGroup, false);
            c2784a = new C2784a(this);
            c2784a.f12585a = (ImageView) view.findViewById(R.id.grid_image);
            view.setTag(c2784a);
        } else {
            c2784a = (C2784a) view.getTag();
        }
        c2784a.f12585a.setBackgroundColor(Color.parseColor(this.f12587a[i]));
        return view;
    }
}

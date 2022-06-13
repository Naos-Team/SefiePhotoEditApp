package com.sweet.selfiecameraphotoeditor.stickerview.view;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.net.Uri;

public class C2794d {
    public static int m18425a(Context context, int i) {
        context.getResources();
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static Bitmap m18426a(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, options), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static BitmapFactory.Options m18427a(int i, int i2, int i3) {
        float f;
        float f2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (i > i2 || i2 <= i) {
            f = (float) i3;
            f2 = (float) i;
        } else {
            f = (float) i3;
            f2 = (float) i2;
        }
        float f3 = f / f2;
        options.outWidth = (int) ((((float) i) * f3) + 0.5f);
        options.outHeight = (int) ((f3 * ((float) i2)) + 0.5f);
        return options;
    }

    public static String m18429a(Uri uri, Context context) {
        try {
            Cursor query = context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
            if (query == null) {
                return uri.getPath();
            }
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex("_data"));
            query.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return uri.toString();
        }
    }

    public static int m18430b(int i, int i2, int i3) {
        int max = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= Integer.MAX_VALUE) {
                break;
            } else if (i4 * i3 > max) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 0) {
            return i4;
        }
        return 1;
    }
}

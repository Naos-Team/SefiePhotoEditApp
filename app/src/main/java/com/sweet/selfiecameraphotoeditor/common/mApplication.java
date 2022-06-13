package com.sweet.selfiecameraphotoeditor.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

public class mApplication extends Application {
    private static mApplication Instance;
    private static Context context;
    Bitmap image;

    public static Context getContext() {
        return context;
    }

    public mApplication() {
        Instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static synchronized mApplication getInstance() {
        mApplication mapplication;
        synchronized (mApplication.class) {
            synchronized (mApplication.class) {
                mapplication = Instance;
            }
        }
        return mapplication;
    }

    public void setImage(Bitmap bitmap) {
        this.image = bitmap;
    }
}

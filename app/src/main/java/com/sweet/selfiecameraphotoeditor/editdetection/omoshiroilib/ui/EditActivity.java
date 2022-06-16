package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.activities.PhotoEditorActivity;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.debug.removeit.GlobalConfig;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.helper.FilterType;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filtercommom;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.glessential.GLRootView;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.imgeditor.gl.GLWrapper;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.BitmapUtils;
import com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class EditActivity extends AppCompatActivity {
    private ImageView btndone;
    private RecyclerView filterListView;

    public GLRootView glRootView;

    public GLWrapper glWrapper;

    String path;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_filter);
        GlobalConfig.context = this;
        path = getIntent().getStringExtra("path");
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= 128;
        getWindow().setAttributes(attributes);
        setFilterList();
        setUpImage();
    }

    public void saveMemes(View view) {
        takePhoto();
    }

    Bitmap createBitmap;

    private void takePhoto() {

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(glRootView.getWidth(), glRootView.getHeight(),
                Bitmap.Config.ARGB_8888);


        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(glRootView, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {


                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                PhotoEditorActivity.current_img = new File(getRealPathFromURI(tempUri));
                Log.e("aaaaaaaaaaa", getRealPathFromURI(tempUri) + "");
                filtercommom.getInstance().bitmap = bitmap;
                Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("some_key", "String data");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();


            } else {
                Toast toast = Toast.makeText(this,
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }







/*    private void takePhoto() {


        try {
           createBitmap = Bitmap.createBitmap(this.glRootView.getWidth(), this.glRootView.getHeight(), Bitmap.Config.ARGB_8888);

        }catch (Exception e)
        {

            Log.e("ssssssssssss",e.getMessage());
        }


        Log.e("ssssssssssss","11111111111111");
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        if (Build.VERSION.SDK_INT >= 24) {
            PixelCopy.request(this.glRootView, createBitmap, new PixelCopy.OnPixelCopyFinishedListener() {
                public void onPixelCopyFinished(int i) {
                    if (i == 0) {
                        filtercommom.getInstance().bitmap = createBitmap;
                    } else {
                        EditActivity editActivity = EditActivity.this;
                        Toast.makeText(editActivity, "Failed to copyPixels: " + i, 1).show();
                    }
                    handlerThread.quitSafely();
                }
            }, new Handler(handlerThread.getLooper()));
        } else {
            this.glRootView.queueEvent(new Runnable() {
                public void run() {
                    EditActivity editActivity = EditActivity.this;
                    final Bitmap access$100 = editActivity.createBitmapFromGLSurface(0, 0, editActivity.glRootView.getWidth(), EditActivity.this.glRootView.getHeight(), (GL10) ((EGL10) EGLContext.getEGL()).eglGetCurrentContext().getGL());
                    EditActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            filtercommom.getInstance().bitmap = access$100;
                            EditActivity.this.onBackPressed();
                            if (createBitmap != null) {
                                Log.e("#DEBUG", "   getHeight:  " + createBitmap.getHeight());
                            }
                        }
                    });
                }
            });
        }
    }*/


    public Bitmap createBitmapFromGLSurface(int i, int i2, int i3, int i4, GL10 gl10) {
        int i5 = i3;
        int i6 = i4;
        int i7 = i5 * i6;
        int[] iArr = new int[i7];
        int[] iArr2 = new int[i7];
        IntBuffer wrap = IntBuffer.wrap(iArr);
        wrap.position(0);
        try {
            gl10.glReadPixels(i, i2, i3, i4, 6408, 5121, wrap);
            for (int i8 = 0; i8 < i6; i8++) {
                int i9 = i8 * i5;
                int i10 = ((i6 - i8) - 1) * i5;
                for (int i11 = 0; i11 < i5; i11++) {
                    int i12 = iArr[i9 + i11];
                    iArr2[i10 + i11] = (i12 & -16711936) | ((i12 << 16) & 16711680) | ((i12 >> 16) & 255);
                }
            }
            return Bitmap.createBitmap(iArr2, i5, i6, Bitmap.Config.ARGB_8888);
        } catch (GLException e) {
            Log.e("#DEBUG", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }
    }

    Bitmap loadBitmapFromFile;

    private void setUpImage() {
        try {
            Log.e("aaaaaaaa", "33333333333333" + path);

            loadBitmapFromFile = BitmapUtils.loadBitmapFromFile(path);


            Log.e("widthhhhh", loadBitmapFromFile.getWidth() + "");
            Log.e("widthhhhh", loadBitmapFromFile.getHeight() + "");


            this.glRootView.setAspectRatio(loadBitmapFromFile.getWidth(), loadBitmapFromFile.getHeight());
            loadBitmapFromFile.recycle();
            this.glWrapper.setFilePath(path);
        } catch (Exception e) {
            Log.e("aaaaaaaa", "eeeee");
            e.printStackTrace();
        }
    }

    private void setFilterList() {

        Log.e("aaaaaaaa", "1111111111111");


        this.glRootView = (GLRootView) findViewById(R.id.camera_view);
        this.btndone = (ImageView) findViewById(R.id.imgdone);
        this.btndone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditActivity editActivity = EditActivity.this;
                editActivity.saveMemes(editActivity.glRootView);
            }
        });
        this.glWrapper = GLWrapper.newInstance().setGlImageView(this.glRootView).setContext(this).init();
        FileUtils.upZipFile(this, "filter/thumbs/thumbs.zip", getFilesDir().getAbsolutePath());
        this.filterListView = (RecyclerView) findViewById(R.id.filter_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.filterListView.setLayoutManager(linearLayoutManager);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < FilterType.values().length; i++) {
            arrayList.add(FilterType.values()[i]);
            if (i == 0) {
                arrayList.add(FilterType.NONE);
            }
        }
        FilterAdapter filterAdapter = new FilterAdapter(this, arrayList);
        this.filterListView.setAdapter(filterAdapter);
        filterAdapter.setOnFilterChangeListener(new FilterAdapter.OnFilterChangeListener() {
            public void onFilterChanged(FilterType filterType) {
                EditActivity.this.glWrapper.switchLastFilterOfCustomizedFilters(filterType);
            }
        });
    }
}

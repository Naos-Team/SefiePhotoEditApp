package com.sweet.selfiecameraphotoeditor.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.adapter.AlbumAdapter;
import com.sweet.selfiecameraphotoeditor.adapter.ListAlbumAdapter;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;
import com.sweet.selfiecameraphotoeditor.common.Utils;
import com.sweet.selfiecameraphotoeditor.model.Constants;
import com.sweet.selfiecameraphotoeditor.model.ImageModel;
import com.sweet.selfiecameraphotoeditor.myinterface.OnAlbum;
import com.sweet.selfiecameraphotoeditor.myinterface.OnListAlbum;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhotoSelectActivity extends AppCompatActivity implements View.OnClickListener, OnAlbum, OnListAlbum {
    public static final String KEY_LIMIT_MAX_IMAGE = "KEY_LIMIT_MAX_IMAGE";
    public static final String KEY_LIMIT_MIN_IMAGE = "KEY_LIMIT_MIN_IMAGE";

    ItemAdapter adapter;
    AlbumAdapter albumAdapter;
    ArrayList<ImageModel> dataAlbum = new ArrayList<>();
    ArrayList<ImageModel> dataListPhoto = new ArrayList<>();
    RecyclerView gridViewAlbum;
    GridView gridViewListAlbum;
    HorizontalScrollView horizontalScrollView;
    ImageView imgBack;
    LinearLayout layoutListItemSelect;
    int limitImageMax = 10;
    int limitImageMin = 1;
    ListAlbumAdapter listAlbumAdapter;
    ArrayList<ImageModel> listItemSelect = new ArrayList<>();
    int pWHBtnDelete;
    int pWHItemSelected;
    ArrayList<String> pathList = new ArrayList<>();
    RecyclerView recycler;
    CustomTextView txtTotalImage;
    CustomTextView txtTitle;
    GridLayoutManager layoutManager;
    ProgressBar progressBar;

    private class GetItemAlbum extends AsyncTask<Void, Void, String> {

        @Override
        public void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            Log.d("", "");
        }

        @Override
        public void onProgressUpdate(Void... voidArr) {
            Log.d("", "");
        }


        private GetItemAlbum() {
            Log.d("", "");
        }


        public String doInBackground(Void... voidArr) {
            try{
                Uri contentUri = MediaStore.Files.getContentUri("external");
                Cursor query = PhotoSelectActivity.this.getContentResolver().query(contentUri, new String[]{"_id", "_data", "date_added", "media_type", "mime_type", "title"}, "media_type=1 OR media_type=3", (String[]) null, (String) null);
                if (query == null) {
                    return "";
                }
                int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
                while (query.moveToNext()) {
                    String string = query.getString(columnIndexOrThrow);
                    File file = new File(string);
                    if (file.exists()) {
                        boolean checkFile = PhotoSelectActivity.this.checkFile(file);
                        if (!PhotoSelectActivity.this.check(file.getParent(), PhotoSelectActivity.this.pathList) && checkFile) {
                            PhotoSelectActivity.this.pathList.add(file.getParent());
                            if (!file.getParentFile().getName().equalsIgnoreCase("Video") && !file.getParentFile().getName().equalsIgnoreCase("Image")) {
                                PhotoSelectActivity.this.dataAlbum.add(new ImageModel(file.getParentFile().getName(), string, file.getParent(), false, file.getParentFile().listFiles().length+""));
                            }
                        }
                    }
                }
                query.close();
                return "";
            }
            catch (Exception e){
                e.printStackTrace();
                //Log.e("TTT", "doInBackground: " + e);
            }
            return "";
        }


        @Override
        public void onPostExecute(String str) {
            progressBar.setVisibility(View.GONE);
            PhotoSelectActivity.this.gridViewAlbum.setLayoutManager(layoutManager);
            PhotoSelectActivity.this.gridViewAlbum.setAdapter(PhotoSelectActivity.this.albumAdapter);
            //PhotoSelectActivity.this.albumAdapter.notifyDataSetChanged();
        }
    }

    private class GetItemListAlbum extends AsyncTask<Void, Void, String> {
        String pathAlbum;


        @Override
        public void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            Log.d("", "");
        }


        @Override
        public void onProgressUpdate(Void... voidArr) {
            Log.d("", "");
        }


        GetItemListAlbum(String str) {
            this.pathAlbum = str;
        }


        public String doInBackground(Void... voidArr) {
            File file = new File(this.pathAlbum);
            if (!file.isDirectory()) {
                return "";
            }
            for (File file2 : file.listFiles()) {
                if (file2.exists()) {
                    boolean checkFile = PhotoSelectActivity.this.checkFile(file2);
                    if (!file2.isDirectory() && checkFile) {
                        PhotoSelectActivity.this.dataListPhoto.add(new ImageModel(file2.getName(), file2.getAbsolutePath(), file2.getAbsolutePath(), false, ""));
                        publishProgress(new Void[0]);
                    }
                }
            }
            return "";
        }

        @Override
        public void onPostExecute(String str) {
            try {
                Collections.sort(PhotoSelectActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                    public int compare(ImageModel imageModel, ImageModel imageModel2) {
                        File file = new File(imageModel.getPathFolder());
                        File file2 = new File(imageModel2.getPathFolder());
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
            PhotoSelectActivity.this.listAlbumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView((int) R.layout.photoselect_activity);
        getWindow().setFlags(1024, 1024);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.limitImageMax = extras.getInt(KEY_LIMIT_MAX_IMAGE, 10);
            this.limitImageMin = extras.getInt(KEY_LIMIT_MIN_IMAGE, 1);
            if (this.limitImageMin > this.limitImageMax) {
                finish();
            }
            if (this.limitImageMin < 1) {
                finish();
            }
        }
        this.pWHItemSelected = (((int) ((((float) getDisplayInfo(this).heightPixels) / 100.0f) * 25.0f)) / 100) * 80;
        this.pWHBtnDelete = (this.pWHItemSelected / 100) * 25;
        this.txtTitle = (CustomTextView) findViewById(R.id.txt_title);
        this.txtTitle.setText("Album");
        this.progressBar = findViewById(R.id.progress_wait);
        this.gridViewListAlbum = (GridView) findViewById(R.id.gridViewListAlbum);
        this.txtTotalImage = (CustomTextView) findViewById(R.id.txtTotalImage);
        ((ImageView) findViewById(R.id.img_done)).setOnClickListener(this);
        this.layoutListItemSelect = (LinearLayout) findViewById(R.id.layoutListItemSelect);
        this.horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        this.horizontalScrollView.getLayoutParams().height = this.pWHItemSelected;
        this.gridViewAlbum = (RecyclerView) findViewById(R.id.gridViewAlbum);
        this.recycler = (RecyclerView) findViewById(R.id.recycler);
        try {
            Collections.sort(this.dataAlbum, new Comparator<ImageModel>() {
                public int compare(ImageModel imageModel, ImageModel imageModel2) {
                    return imageModel.getName().compareToIgnoreCase(imageModel2.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.albumAdapter = new AlbumAdapter(PhotoSelectActivity.this,dataAlbum);
        this.albumAdapter.setOnItem(this);
        if (isPermissionGranted("android.permission.READ_EXTERNAL_STORAGE")) {
            new GetItemAlbum().execute(new Void[0]);
        } else {
            requestPermission("android.permission.READ_EXTERNAL_STORAGE", 1001);
        }
        updateTxtTotalImage();
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoSelectActivity.this.onBackPressed();
            }
        });
        this.layoutManager = new GridLayoutManager(this, 3);

    }

    private boolean isPermissionGranted(String str) {
        return ContextCompat.checkSelfPermission(this, str) == 0;
    }

    private void requestPermission(String str, int i) {
        ActivityCompat.shouldShowRequestPermissionRationale(this, str);
        ActivityCompat.requestPermissions(this, new String[]{str}, i);
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 1001) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                finish();
            } else {
                new GetItemAlbum().execute(new Void[0]);
            }
        } else if (i == 1002 && iArr.length > 0) {
            int i2 = iArr[0];
        }
    }


    public boolean check(String str, ArrayList<String> arrayList) {
        return !arrayList.isEmpty() && arrayList.contains(str);
    }


    public void addItemSelect(ImageModel imageModel) {
        imageModel.setId(this.listItemSelect.size());
        this.listItemSelect.add(imageModel);
        updateTxtTotalImage();
        this.adapter = new ItemAdapter(this.listItemSelect, this);
        this.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
        this.recycler.setAdapter(this.adapter);
    }


    public void updateTxtTotalImage() {
        this.txtTotalImage.setText(String.format(getResources().getString(R.string.text_images), new Object[]{Integer.valueOf(this.listItemSelect.size())}));
    }


    public void showListAlbum(String str) {
        this.txtTitle.setText(new File(str).getName());
        this.listAlbumAdapter = new ListAlbumAdapter(this, R.layout.piclist_row_list_album, this.dataListPhoto);
        this.listAlbumAdapter.setOnListAlbum(this);
         this.gridViewListAlbum.setAdapter(this.listAlbumAdapter);
        this.gridViewListAlbum.setVisibility(View.VISIBLE);
        new GetItemListAlbum(str).execute(new Void[0]);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.img_done) {
            ArrayList<String> listString = getListString(this.listItemSelect);
            if (listString.size() >= this.limitImageMin) {
                done(listString);
                return;
            }
            Toast.makeText(this, "Please select at lease " + this.limitImageMin + " item", Toast.LENGTH_SHORT).show();
        }
    }

    private void done(ArrayList<String> arrayList) {
        Intent intent = new Intent(this, PhotoCollageActivity.class);
        intent.putExtra("imageList", arrayList);
        startActivity(intent);
        finish();
    }


    public ArrayList<String> getListString(ArrayList<ImageModel> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(arrayList.get(i).getPathFile());
        }
        return arrayList2;
    }


    public boolean checkFile(File file) {
        if (file == null) {
            return false;
        }
        if (!file.isFile()) {
            return true;
        }
        String name = file.getName();
        if (name.startsWith(Utils.HIDDEN_PREFIX) || file.length() == 0) {
            return false;
        }
        for (int i = 0; i < Constants.FORMAT_IMAGE.size(); i++) {
            if (name.endsWith(Constants.FORMAT_IMAGE.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (this.gridViewListAlbum.getVisibility() == View.VISIBLE) {
            this.dataListPhoto.clear();
            this.listAlbumAdapter.notifyDataSetChanged();
            this.gridViewListAlbum.setVisibility(View.GONE);
            this.txtTitle.setText("Album");
            return;
        }
        super.onBackPressed();
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public void onitemalbumclick(int i) {
        showListAlbum(this.dataAlbum.get(i).getPathFolder());
    }

    public void onitemlistalbumclick(ImageModel imageModel) {
        int i = 0;
        while (i < this.listItemSelect.size()) {
            if (!this.listItemSelect.get(i).getName().equalsIgnoreCase(imageModel.getName())) {
                i++;
            } else {
                return;
            }
        }
        if (this.listItemSelect.size() < this.limitImageMax) {
            addItemSelect(imageModel);
        }
    }

    public void onlongalbumclicklist(int i, boolean z) {
        String pathFolder = this.dataAlbum.get(i).getPathFolder();
        if (z) {
            File file = new File(pathFolder);
            if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    if (file2.exists()) {
                        boolean checkFile = checkFile(file2);
                        if (!file2.isDirectory() && checkFile) {
                            if (this.listItemSelect.size() < this.limitImageMax) {
                                addItemSelect(new ImageModel(file2.getName(), file2.getAbsolutePath(), file2.getAbsolutePath(), false, String.valueOf(file.listFiles().length)));
                            } else {
                                Toast.makeText(this, "Limit " + this.limitImageMax + " item", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                return;
            }
            return;
        }
        File file3 = new File(pathFolder);
        if (file3.isDirectory()) {
            for (File file4 : file3.listFiles()) {
                if (file4.exists()) {
                    boolean checkFile2 = checkFile(file4);
                    if (!file4.isDirectory() && checkFile2) {
                        for (int i2 = 0; i2 < this.listItemSelect.size(); i2++) {
                            if (this.listItemSelect.get(i2).getName().equalsIgnoreCase(file4.getName())) {
                                this.listItemSelect.remove(i2);
                            }
                        }
                        updateTxtTotalImage();
                        this.adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
        Context context;

        public List<ImageModel> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView btnDelete;
            public ImageView imageItem;
            public ImageView ivPause;

            public MyViewHolder(View view) {
                super(view);
                this.imageItem = (ImageView) view.findViewById(R.id.imageItem);
                this.btnDelete = (ImageView) view.findViewById(R.id.btnDelete);
                this.ivPause = (ImageView) view.findViewById(R.id.ivPause);
            }
        }

        public ItemAdapter(List<ImageModel> list, Context context2) {
            this.moviesList = list;
            this.context = context2;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.piclist_item_selected, viewGroup, false));
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
            ImageModel imageModel = this.moviesList.get(i);
            if (imageModel.getPathFile().contains("mp4")) {
                myViewHolder.ivPause.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.ivPause.setVisibility(View.GONE);
            }
            ((RequestBuilder) Glide.with(this.context).load(imageModel.getPathFile()).placeholder((int) R.drawable.empty_photo)).into(myViewHolder.imageItem);
            myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ItemAdapter.this.moviesList.remove(i);
                    ItemAdapter.this.notifyDataSetChanged();
                    PhotoSelectActivity.this.txtTotalImage.setText(String.format(PhotoSelectActivity.this.getResources().getString(R.string.text_images), new Object[]{Integer.valueOf(ItemAdapter.this.moviesList.size())}));
                }
            });
        }

        public int getItemCount() {
            return this.moviesList.size();
        }
    }
}

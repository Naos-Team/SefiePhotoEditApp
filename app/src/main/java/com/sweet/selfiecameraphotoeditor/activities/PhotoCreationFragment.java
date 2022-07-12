package com.sweet.selfiecameraphotoeditor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.adapter.PhotoCreationAdapter;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class PhotoCreationFragment extends Fragment {
    private static String[] filename;
    public static String[] filepath;
    private static File[] filelist;
    static Activity activity;
    static PhotoCreationAdapter creationAdapter;
    static File destination;
    static GridView gridImage;
    static CustomTextView txtnodatafound;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.photocreation_fragment, viewGroup, false);
        txtnodatafound = (CustomTextView) inflate.findViewById(R.id.txtnodatafound);
        gridImage = (GridView) inflate.findViewById(R.id.gridview);
        activity = getActivity();
        if (!Environment.getExternalStorageState().equals("mounted")) {
            Toast.makeText(getContext(), "Error ! No SDCARD Found !", Toast.LENGTH_SHORT).show();
        } else {
            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
            new File(file + "/SelfieCameraPhotoEditor");
            destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/SelfieCameraPhotoEditor");
            destination.mkdirs();
        }
        refreshload();
        gridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                String str = PhotoCreationFragment.filepath[i];
                Intent intent = new Intent(PhotoCreationFragment.this.getContext(), PhotoShareActivity.class);
                intent.putExtra("FinalURI", str);
                PhotoCreationFragment.this.startActivity(intent);
            }
        });
        return inflate;
    }

    public static void refreshload() {
        filepath = null;
        filename = null;
        if (destination.isDirectory()) {
            filelist = destination.listFiles();
            Arrays.sort(filelist, new Comparator() {
                public int compare(Object obj, Object obj2) {
                    File file = (File) obj;
                    File file2 = (File) obj2;
                    if (file.lastModified() > file2.lastModified()) {
                        return -1;
                    }
                    return file.lastModified() < file2.lastModified() ? 1 : 0;
                }
            });
            File[] fileArr = filelist;
            filepath = new String[fileArr.length];
            filename = new String[fileArr.length];
            int i = 0;
            while (true) {
                File[] fileArr2 = filelist;
                if (i >= fileArr2.length) {
                    break;
                }
                filepath[i] = fileArr2[i].getAbsolutePath();
                filename[i] = filelist[i].getName();
                i++;
            }
        }
        creationAdapter = new PhotoCreationAdapter(activity, filepath, filename);
        gridImage.setAdapter(creationAdapter);
        if (filelist.length <= 0) {
            txtnodatafound.setVisibility(View.VISIBLE);
        }
    }
}

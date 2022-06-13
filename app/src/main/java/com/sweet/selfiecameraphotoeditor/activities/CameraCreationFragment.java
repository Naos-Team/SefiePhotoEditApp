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
import com.sweet.selfiecameraphotoeditor.adapter.Camera_Creation_Adapter;
import com.sweet.selfiecameraphotoeditor.common.CustomTextView;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class CameraCreationFragment extends Fragment {


    private static String[] filename;
    public static String[] filepath;
    private static File[] filelist;
    static File destination;
    static Activity activity;
    static Camera_Creation_Adapter cameraCreationAdapter;
    static GridView gridPhotoList;
    static CustomTextView txtnodatafound;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.cameracreation_fragment, viewGroup, false);
        this.gridPhotoList = (GridView) inflate.findViewById(R.id.gridPhotoList);

        activity = getActivity();
        if (!Environment.getExternalStorageState().equals("mounted")) {
            Toast.makeText(getContext(), "Error ! No SDCARD Found !", Toast.LENGTH_LONG).show();
        } else {
            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
            new File(file + "/Selfie Cam Photo Editor");
            destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/Selfie Cam Photo Editor");
            destination.mkdirs();
        }

        this.txtnodatafound = (CustomTextView) inflate.findViewById(R.id.txt_nodatafound);


        refreshload();
        gridPhotoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                String str = CameraCreationFragment.filepath[i];
                Intent intent = new Intent(CameraCreationFragment.this.getContext(), PhotoShareActivity.class);
                intent.putExtra("FinalURI", str);
                CameraCreationFragment.this.startActivity(intent);
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
        cameraCreationAdapter = new Camera_Creation_Adapter(activity, filepath, filename);
        gridPhotoList.setAdapter(cameraCreationAdapter);

        if (filelist.length <= 0) {
            txtnodatafound.setVisibility(View.VISIBLE);
        }
    }

}

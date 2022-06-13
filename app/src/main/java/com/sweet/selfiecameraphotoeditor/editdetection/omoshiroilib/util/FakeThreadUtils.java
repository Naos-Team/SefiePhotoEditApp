package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

public class FakeThreadUtils {
    private static final String TAG = "FakeThreadUtils";

    public static void postTask(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static class SaveFileTask extends AsyncTask<Void, Integer, Boolean> {
        private String fileName;
        private FileUtils.FileSavedCallback fileSavedCallback;
        private String inputPath;
        private String outputPath;

        public SaveFileTask(String str, String str2, String str3, FileUtils.FileSavedCallback fileSavedCallback2) {
            this.outputPath = str;
            this.fileName = str2;
            this.inputPath = str3;
            this.fileSavedCallback = fileSavedCallback2;
        }


        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }


        public Boolean doInBackground(Void... voidArr) {
            FileUtils.copyFileFromTo(this.outputPath, this.fileName, this.inputPath);
            return true;
        }


        @Override
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            Log.d(FakeThreadUtils.TAG, "onPostExecute: " + this.outputPath + " " + this.fileName + " " + this.inputPath);
            this.fileSavedCallback.onFileSaved(new File(this.outputPath, this.fileName).getAbsolutePath());
        }
    }
}

package com.sweet.selfiecameraphotoeditor.cameraview.json;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    private static final String TAG = "HttpHandler";

    public String makeServiceCall(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("GET");
            return convertStreamToString(new BufferedInputStream(httpURLConnection.getInputStream()));
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            return null;
        } catch (ProtocolException e2) {
            Log.e(TAG, "ProtocolException: " + e2.getMessage());
            return null;
        } catch (IOException e3) {
            Log.e(TAG, "IOException: " + e3.getMessage());
            return null;
        } catch (Exception e4) {
            Log.e(TAG, "Exception: " + e4.getMessage());
            return null;
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                    sb.append(10);
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            } catch (IOException e3) {
                e3.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

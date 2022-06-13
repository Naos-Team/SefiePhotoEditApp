package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.util;

import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class Logger {
    public static String TAG = "Logger";
    private static long currentTime;

    public static void logMatrix(float[] fArr) {
        Log.d(TAG, "Start Displaying Matrix");
        for (int i = 0; i < 4; i++) {
            String str = "";
            for (int i2 = i; i2 < 16; i2 += 4) {
                str = str + fArr[i2] + " ";
            }
            Log.d(TAG, str);
        }
    }

    public static void logTouchEvent(View view, MotionEvent motionEvent) {
        StringBuilder sb = new StringBuilder();
        sb.append(view.toString() + " \n");
        sb.append("Action: ");
        sb.append(motionEvent.getAction());
        sb.append("\n");
        sb.append("Location: ");
        sb.append(motionEvent.getX());
        sb.append(" x ");
        sb.append(motionEvent.getY());
        sb.append("\n");
        sb.append("Edge flags: ");
        sb.append(motionEvent.getEdgeFlags());
        sb.append("\n");
        sb.append("Pressure: ");
        sb.append(motionEvent.getPressure());
        sb.append("  ");
        sb.append("Size: ");
        sb.append(motionEvent.getSize());
        sb.append("\n");
        sb.append("Down time: ");
        sb.append(motionEvent.getDownTime());
        sb.append("ms\n");
        sb.append("Event time: ");
        sb.append(motionEvent.getEventTime());
        sb.append("ms");
        sb.append(" Elapsed:");
        sb.append(motionEvent.getEventTime() - motionEvent.getDownTime());
        sb.append(" ms\n");
        Log.d(TAG, sb.toString());
    }

    public static void updateCurrentTime() {
        currentTime = System.currentTimeMillis();
    }

    public static long logPassedTime(String str) {
        long currentTimeMillis = System.currentTimeMillis() - currentTime;
        String str2 = TAG;
        Log.d(str2, str + " is finished, timePassed: " + currentTimeMillis);
        return currentTimeMillis;
    }

    public static void logCameraSizes(List<Camera.Size> list) {
        if (list == null) {
            Log.d(TAG, "logCameraSizes: list is null");
            return;
        }
        for (Camera.Size next : list) {
            String str = TAG;
            Log.d(str, "logCameraSizes: " + next.width + " x " + next.height);
        }
    }
}

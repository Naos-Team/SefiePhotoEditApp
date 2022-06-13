package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.ui.module;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class TouchTimeHandler extends Handler {
    public static final int WHAT_233 = 0;
    private long delayTimeInMils;
    private boolean freeNow = true;
    private boolean shouldContinue = false;
    private Task task;

    public interface Task {
        void run();
    }

    public TouchTimeHandler(Looper looper, Task task2) {
        super(looper);
        this.task = task2;
    }

    public void clearMsg() {
        while (hasMessages(0)) {
            removeMessages(0);
        }
        this.shouldContinue = false;
        this.freeNow = true;
    }

    public void sendSingleMsg(long j) {
        clearMsg();
        this.freeNow = false;
        this.shouldContinue = false;
        sendEmptyMessageDelayed(0, j);
    }

    public void sendLoopMsg(long j, long j2) {
        clearMsg();
        this.freeNow = false;
        this.delayTimeInMils = j2;
        this.shouldContinue = true;
        sendEmptyMessageDelayed(0, j);
    }

    public void handleMessage(Message message) {
        Task task2 = this.task;
        if (task2 != null) {
            task2.run();
        }
        if (this.shouldContinue) {
            sendEmptyMessageDelayed(0, this.delayTimeInMils);
        }
    }

    public boolean isFreeNow() {
        return this.freeNow;
    }
}

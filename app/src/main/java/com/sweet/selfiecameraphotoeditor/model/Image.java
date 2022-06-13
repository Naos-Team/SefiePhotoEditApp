package com.sweet.selfiecameraphotoeditor.model;

public class Image {
    boolean isCheck;
    String path;

    public Image(String str, boolean z) {
        this.path = str;
        this.isCheck = z;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean z) {
        this.isCheck = z;
    }
}

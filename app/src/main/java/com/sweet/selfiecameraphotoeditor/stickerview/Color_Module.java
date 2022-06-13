package com.sweet.selfiecameraphotoeditor.stickerview;

public class Color_Module {
    String dirName;
    String fileName;
    String textColor;

    public Color_Module(String str) {
        this.dirName = str;
    }

    public Color_Module(String str, String str2) {
        this.dirName = str;
        this.fileName = str2;
    }

    public Color_Module(String str, String str2, String str3) {
        this.dirName = str;
        this.fileName = str2;
        this.textColor = str3;
    }

    public String getColor() {
        return this.textColor;
    }

    public void setColor(String str) {
        this.textColor = str;
    }

    public String getDirName() {
        return this.dirName;
    }

    public void setDirName(String str) {
        this.dirName = str;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }
}

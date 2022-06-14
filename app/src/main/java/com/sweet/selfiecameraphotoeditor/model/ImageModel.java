package com.sweet.selfiecameraphotoeditor.model;

public class ImageModel {
    int id;
    String name;
    String pathFile;
    String countFile;
    String pathFolder;
    boolean selected = false;

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public ImageModel(String str, String str2, String str3, Boolean bool, String countFile) {
        this.name = str;
        this.pathFile = str2;
        this.pathFolder = str3;
        this.selected = bool.booleanValue();
        this.countFile = countFile;
    }

    public String getCountFile() {
        return countFile;
    }

    public void setCountFile(String countFile) {
        this.countFile = countFile;
    }

    public String getPathFile() {
        return this.pathFile;
    }

    public void setPathFile(String str) {
        this.pathFile = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPathFolder() {
        return this.pathFolder;
    }

    public void setPathFolder(String str) {
        this.pathFolder = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }
}

package com.sweet.selfiecameraphotoeditor.model;

public class Drawables {
    public int image;
    boolean isSelected = false;
    String name;

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public Drawables(int i) {
        this.image = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int i) {
        this.image = i;
    }
}

package com.sweet.selfiecameraphotoeditor.model;

public class ScaleItem {
    private int id;
    private String title;
    private int scaleX;
    private int scaleY;
    private boolean isSelected;

    public ScaleItem(int id, String title, int scaleX, int scaleY, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScaleX() {
        return scaleX;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public int getScaleY() {
        return scaleY;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

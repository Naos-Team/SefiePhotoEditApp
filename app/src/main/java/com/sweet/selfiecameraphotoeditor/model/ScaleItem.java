package com.sweet.selfiecameraphotoeditor.model;

public class ScaleItem {
    private int id;
    private String title;
    private int scaleX;
    private int scaleY;
    private int thumbnail;
    private boolean isSelected;

    public ScaleItem(int id, String title, int scaleX, int scaleY, int thumbnail, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.thumbnail = thumbnail;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
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

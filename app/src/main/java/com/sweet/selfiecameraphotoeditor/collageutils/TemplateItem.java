package com.sweet.selfiecameraphotoeditor.collageutils;

import java.util.ArrayList;
import java.util.List;

public class TemplateItem extends ImageTemplate {
    private boolean mIsAds = false;
    private List<PhotoItem> mPhotoItemList = new ArrayList();

    public TemplateItem() {
    }

    public TemplateItem(ImageTemplate imageTemplate) {
        setLanguages(imageTemplate.getLanguages());
        setPackageId(imageTemplate.getPackageId());
        setPreview(imageTemplate.getPreview());
        setTemplate(imageTemplate.getTemplate());
        setChild(imageTemplate.getChild());
        setTitle(imageTemplate.getTitle());
        setThumbnail(imageTemplate.getThumbnail());
        setSelectedThumbnail(imageTemplate.getSelectedThumbnail());
        setSelected(imageTemplate.isSelected());
        setShowingType(imageTemplate.getShowingType());
        setLastModified(imageTemplate.getLastModified());
        setStatus(imageTemplate.getStatus());
        setId(imageTemplate.getId());
        this.mPhotoItemList = PhotoLayout.parseImageTemplate(imageTemplate);
    }

    public List<PhotoItem> getPhotoItemList() {
        return this.mPhotoItemList;
    }

    public boolean isAds() {
        return this.mIsAds;
    }
}

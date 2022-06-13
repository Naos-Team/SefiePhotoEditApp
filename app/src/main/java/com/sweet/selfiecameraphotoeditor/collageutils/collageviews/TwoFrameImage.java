package com.sweet.selfiecameraphotoeditor.collageutils.collageviews;

import android.graphics.PointF;

import com.sweet.selfiecameraphotoeditor.collageutils.PhotoItem;
import com.sweet.selfiecameraphotoeditor.collageutils.TemplateItem;

import java.util.ArrayList;

class TwoFrameImage {
    TwoFrameImage() {
    }

    static TemplateItem collage20() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_0.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 0.5f, 1.0f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.5f, 0.0f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage21() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_1.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 0.5f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.0f, 0.5f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage22() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_2.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 0.333f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.0f, 0.333f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage23() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_3.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 0.667f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.5f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.0f, 0.333f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.5f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage24() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_4.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 0.5714f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.8333f, 0.75f));
        photoItem.pointList.add(new PointF(0.6666f, 1.0f));
        photoItem.pointList.add(new PointF(0.5f, 0.75f));
        photoItem.pointList.add(new PointF(0.3333f, 1.0f));
        photoItem.pointList.add(new PointF(0.1666f, 0.75f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.0f, 0.4286f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.25f));
        photoItem2.pointList.add(new PointF(0.1666f, 0.0f));
        photoItem2.pointList.add(new PointF(0.3333f, 0.25f));
        photoItem2.pointList.add(new PointF(0.5f, 0.0f));
        photoItem2.pointList.add(new PointF(0.6666f, 0.25f));
        photoItem2.pointList.add(new PointF(0.8333f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.25f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage25() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_5.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 0.6667f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.0f, 0.6667f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage26() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_6.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 0.667f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 0.5f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.0f, 0.333f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.5f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage27() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_7.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 1.0f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        photoItem.clearAreaPoints = new ArrayList<>();
        photoItem.clearAreaPoints.add(new PointF(0.6f, 0.6f));
        photoItem.clearAreaPoints.add(new PointF(0.9f, 0.6f));
        photoItem.clearAreaPoints.add(new PointF(0.9f, 0.9f));
        photoItem.clearAreaPoints.add(new PointF(0.6f, 0.9f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.6f, 0.6f, 0.9f, 0.9f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage28() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_8.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 0.3333f, 1.0f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.3333f, 0.0f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage29() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_9.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 0.6667f, 1.0f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(0.5f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.3333f, 0.0f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.5f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage210() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_10.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 0.667f, 1.0f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.bound.set(0.667f, 0.0f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }

    static TemplateItem collage211() {
        TemplateItem collage = FrameImageUtils.collage("collage_2_11.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.bound.set(0.0f, 0.0f, 0.667f, 1.0f);
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(0.5f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem.index = 1;
        photoItem2.bound.set(0.333f, 0.0f, 1.0f, 1.0f);
        photoItem2.pointList.add(new PointF(0.5f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 0.0f));
        photoItem2.pointList.add(new PointF(1.0f, 1.0f));
        photoItem2.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem2);
        return collage;
    }
}

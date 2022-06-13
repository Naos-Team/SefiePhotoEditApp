package com.sweet.selfiecameraphotoeditor.collageutils.collageviews;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;

import com.sweet.selfiecameraphotoeditor.collageutils.PhotoItem;
import com.sweet.selfiecameraphotoeditor.collageutils.PhotoUtils;
import com.sweet.selfiecameraphotoeditor.collageutils.TemplateItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FrameImageUtils {
    public static final String FRAME_FOLDER = "frame";

    protected static TemplateItem collage(String str) {
        TemplateItem templateItem = new TemplateItem();
        templateItem.setPreview(PhotoUtils.ASSET_PREFIX.concat(FRAME_FOLDER).concat("/").concat(str));
        templateItem.setTitle(str);
        return templateItem;
    }

    private static TemplateItem collage10() {
        TemplateItem collage = collage("collage_1_0.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 1.0f);
        photoItem.index = 0;
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        return collage;
    }

    public static Path createHeartItem(float f, float f2) {
        Path path = new Path();
        float f3 = (f2 / 4.0f) + f;
        path.moveTo(f, f3);
        path.quadTo(f, f, f3, f);
        float f4 = (f2 / 2.0f) + f;
        path.quadTo(f4, f, f4, f3);
        float f5 = ((3.0f * f2) / 4.0f) + f;
        path.quadTo(f4, f, f5, f);
        float f6 = f2 + f;
        path.quadTo(f6, f, f6, f3);
        path.quadTo(f6, f4, f5, f5);
        path.lineTo(f4, f6);
        path.lineTo(f3, f5);
        path.quadTo(f, f4, f, f3);
        return path;
    }

    public static ArrayList<TemplateItem> loadFrameImages(Context context) {
        ArrayList<TemplateItem> arrayList = new ArrayList<>();
        try {
            String[] list = context.getAssets().list(FRAME_FOLDER);
            arrayList.clear();
            if (list != null && list.length > 0) {
                for (String createTemplateItems : list) {
                    TemplateItem createTemplateItems2 = createTemplateItems(createTemplateItems);
                    if (createTemplateItems2 != null) {
                        arrayList.add(createTemplateItems2);
                    }
                }
                Collections.sort(arrayList, new Comparator<TemplateItem>() {
                    public int compare(TemplateItem templateItem, TemplateItem templateItem2) {
                        return templateItem.getPhotoItemList().size() - templateItem2.getPhotoItemList().size();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private static TemplateItem createTemplateItems(String str) {
        if (str.equals("collage_1_0.png")) {
            return collage10();
        }
        if (str.equals("collage_2_0.png")) {
            return TwoFrameImage.collage20();
        }
        if (str.equals("collage_2_1.png")) {
            return TwoFrameImage.collage21();
        }
        if (str.equals("collage_2_2.png")) {
            return TwoFrameImage.collage22();
        }
        if (str.equals("collage_2_3.png")) {
            return TwoFrameImage.collage23();
        }
        if (str.equals("collage_2_4.png")) {
            return TwoFrameImage.collage24();
        }
        if (str.equals("collage_2_5.png")) {
            return TwoFrameImage.collage25();
        }
        if (str.equals("collage_2_6.png")) {
            return TwoFrameImage.collage26();
        }
        if (str.equals("collage_2_7.png")) {
            return TwoFrameImage.collage27();
        }
        if (str.equals("collage_2_8.png")) {
            return TwoFrameImage.collage28();
        }
        if (str.equals("collage_2_9.png")) {
            return TwoFrameImage.collage29();
        }
        if (str.equals("collage_2_10.png")) {
            return TwoFrameImage.collage210();
        }
        if (str.equals("collage_2_11.png")) {
            return TwoFrameImage.collage211();
        }
        if (str.equals("collage_3_0.png")) {
            return ThreeFrameImage.collage30();
        }
        if (str.equals("collage_3_1.png")) {
            return ThreeFrameImage.collage31();
        }
        if (str.equals("collage_3_2.png")) {
            return ThreeFrameImage.collage32();
        }
        if (str.equals("collage_3_3.png")) {
            return ThreeFrameImage.collage33();
        }
        if (str.equals("collage_3_4.png")) {
            return ThreeFrameImage.collage34();
        }
        if (str.equals("collage_3_5.png")) {
            return ThreeFrameImage.collage35();
        }
        if (str.equals("collage_3_6.png")) {
            return ThreeFrameImage.collage36();
        }
        if (str.equals("collage_3_7.png")) {
            return ThreeFrameImage.collage37();
        }
        if (str.equals("collage_3_8.png")) {
            return ThreeFrameImage.collage38();
        }
        if (str.equals("collage_3_9.png")) {
            return ThreeFrameImage.collage39();
        }
        if (str.equals("collage_3_10.png")) {
            return ThreeFrameImage.collage310();
        }
        if (str.equals("collage_3_11.png")) {
            return ThreeFrameImage.collage311();
        }
        if (str.equals("collage_3_12.png")) {
            return ThreeFrameImage.collage312();
        }
        if (str.equals("collage_3_13.png")) {
            return ThreeFrameImage.collage313();
        }
        if (str.equals("collage_3_14.png")) {
            return ThreeFrameImage.collage314();
        }
        if (str.equals("collage_3_15.png")) {
            return ThreeFrameImage.collage315();
        }
        if (str.equals("collage_3_16.png")) {
            return ThreeFrameImage.collage316();
        }
        if (str.equals("collage_3_17.png")) {
            return ThreeFrameImage.collage317();
        }
        if (str.equals("collage_3_18.png")) {
            return ThreeFrameImage.collage318();
        }
        if (str.equals("collage_3_19.png")) {
            return ThreeFrameImage.collage319();
        }
        if (str.equals("collage_3_20.png")) {
            return ThreeFrameImage.collage320();
        }
        if (str.equals("collage_3_21.png")) {
            return ThreeFrameImage.collage321();
        }
        if (str.equals("collage_3_22.png")) {
            return ThreeFrameImage.collage322();
        }
        if (str.equals("collage_3_23.png")) {
            return ThreeFrameImage.collage323();
        }
        if (str.equals("collage_3_24.png")) {
            return ThreeFrameImage.collage324();
        }
        if (str.equals("collage_3_25.png")) {
            return ThreeFrameImage.collage325();
        }
        if (str.equals("collage_3_26.png")) {
            return ThreeFrameImage.collage326();
        }
        if (str.equals("collage_3_27.png")) {
            return ThreeFrameImage.collage327();
        }
        if (str.equals("collage_3_28.png")) {
            return ThreeFrameImage.collage328();
        }
        if (str.equals("collage_3_29.png")) {
            return ThreeFrameImage.collage329();
        }
        if (str.equals("collage_3_30.png")) {
            return ThreeFrameImage.collage330();
        }
        if (str.equals("collage_3_31.png")) {
            return ThreeFrameImage.collage331();
        }
        if (str.equals("collage_3_32.png")) {
            return ThreeFrameImage.collage332();
        }
        if (str.equals("collage_3_33.png")) {
            return ThreeFrameImage.collage333();
        }
        if (str.equals("collage_3_34.png")) {
            return ThreeFrameImage.collage334();
        }
        if (str.equals("collage_3_35.png")) {
            return ThreeFrameImage.collage335();
        }
        if (str.equals("collage_3_36.png")) {
            return ThreeFrameImage.collage336();
        }
        if (str.equals("collage_3_37.png")) {
            return ThreeFrameImage.collage337();
        }
        if (str.equals("collage_3_38.png")) {
            return ThreeFrameImage.collage338();
        }
        if (str.equals("collage_3_39.png")) {
            return ThreeFrameImage.collage339();
        }
        if (str.equals("collage_3_40.png")) {
            return ThreeFrameImage.collage340();
        }
        if (str.equals("collage_3_41.png")) {
            return ThreeFrameImage.collage341();
        }
        if (str.equals("collage_3_42.png")) {
            return ThreeFrameImage.collage342();
        }
        if (str.equals("collage_3_43.png")) {
            return ThreeFrameImage.collage343();
        }
        if (str.equals("collage_3_44.png")) {
            return ThreeFrameImage.collage344();
        }
        if (str.equals("collage_3_45.png")) {
            return ThreeFrameImage.collage345();
        }
        if (str.equals("collage_3_46.png")) {
            return ThreeFrameImage.collage346();
        }
        if (str.equals("collage_3_47.png")) {
            return ThreeFrameImage.collage347();
        }
        if (str.equals("collage_4_0.png")) {
            return FourFrameImage.collage40();
        }
        if (str.equals("collage_4_1.png")) {
            return FourFrameImage.collage41();
        }
        if (str.equals("collage_4_2.png")) {
            return FourFrameImage.collage42();
        }
        if (str.equals("collage_4_4.png")) {
            return FourFrameImage.collage44();
        }
        if (str.equals("collage_4_5.png")) {
            return FourFrameImage.collage45();
        }
        if (str.equals("collage_4_6.png")) {
            return FourFrameImage.collage46();
        }
        if (str.equals("collage_4_7.png")) {
            return FourFrameImage.collage47();
        }
        if (str.equals("collage_4_8.png")) {
            return FourFrameImage.collage48();
        }
        if (str.equals("collage_4_9.png")) {
            return FourFrameImage.collage49();
        }
        if (str.equals("collage_4_10.png")) {
            return FourFrameImage.collage410();
        }
        if (str.equals("collage_4_11.png")) {
            return FourFrameImage.collage411();
        }
        if (str.equals("collage_4_12.png")) {
            return FourFrameImage.collage412();
        }
        if (str.equals("collage_4_13.png")) {
            return FourFrameImage.collage413();
        }
        if (str.equals("collage_4_14.png")) {
            return FourFrameImage.collage414();
        }
        if (str.equals("collage_4_15.png")) {
            return FourFrameImage.collage415();
        }
        if (str.equals("collage_4_16.png")) {
            return FourFrameImage.collage416();
        }
        if (str.equals("collage_4_17.png")) {
            return FourFrameImage.collage417();
        }
        if (str.equals("collage_4_18.png")) {
            return FourFrameImage.collage418();
        }
        if (str.equals("collage_4_19.png")) {
            return FourFrameImage.collage419();
        }
        if (str.equals("collage_4_20.png")) {
            return FourFrameImage.collage420();
        }
        if (str.equals("collage_4_21.png")) {
            return FourFrameImage.collage421();
        }
        if (str.equals("collage_4_22.png")) {
            return FourFrameImage.collage422();
        }
        if (str.equals("collage_4_23.png")) {
            return FourFrameImage.collage423();
        }
        if (str.equals("collage_4_24.png")) {
            return FourFrameImage.collage424();
        }
        if (str.equals("collage_4_25.png")) {
            return FourFrameImage.collage425();
        }
        if (str.equals("collage_5_0.png")) {
            return FiveFrameImage.collage50();
        }
        if (str.equals("collage_5_1.png")) {
            return FiveFrameImage.collage51();
        }
        if (str.equals("collage_5_2.png")) {
            return FiveFrameImage.collage52();
        }
        if (str.equals("collage_5_3.png")) {
            return FiveFrameImage.collage53();
        }
        if (str.equals("collage_5_4.png")) {
            return FiveFrameImage.collage54();
        }
        if (str.equals("collage_5_5.png")) {
            return FiveFrameImage.collage55();
        }
        if (str.equals("collage_5_6.png")) {
            return FiveFrameImage.collage56();
        }
        if (str.equals("collage_5_7.png")) {
            return FiveFrameImage.collage57();
        }
        if (str.equals("collage_5_8.png")) {
            return FiveFrameImage.collage58();
        }
        if (str.equals("collage_5_9.png")) {
            return FiveFrameImage.collage59();
        }
        if (str.equals("collage_5_10.png")) {
            return FiveFrameImage.collage510();
        }
        if (str.equals("collage_5_11.png")) {
            return FiveFrameImage.collage511();
        }
        if (str.equals("collage_5_12.png")) {
            return FiveFrameImage.collage512();
        }
        if (str.equals("collage_5_13.png")) {
            return FiveFrameImage.collage513();
        }
        if (str.equals("collage_5_14.png")) {
            return FiveFrameImage.collage514();
        }
        if (str.equals("collage_5_15.png")) {
            return FiveFrameImage.collage515();
        }
        if (str.equals("collage_5_16.png")) {
            return FiveFrameImage.collage516();
        }
        if (str.equals("collage_5_17.png")) {
            return FiveFrameImage.collage517();
        }
        if (str.equals("collage_5_18.png")) {
            return FiveFrameImage.collage518();
        }
        if (str.equals("collage_5_19.png")) {
            return FiveFrameImage.collage519();
        }
        if (str.equals("collage_5_20.png")) {
            return FiveFrameImage.collage520();
        }
        if (str.equals("collage_5_21.png")) {
            return FiveFrameImage.collage521();
        }
        if (str.equals("collage_5_22.png")) {
            return FiveFrameImage.collage522();
        }
        if (str.equals("collage_5_23.png")) {
            return FiveFrameImage.collage523();
        }
        if (str.equals("collage_5_24.png")) {
            return FiveFrameImage.collage524();
        }
        if (str.equals("collage_5_25.png")) {
            return FiveFrameImage.collage525();
        }
        if (str.equals("collage_5_26.png")) {
            return FiveFrameImage.collage526();
        }
        if (str.equals("collage_5_27.png")) {
            return FiveFrameImage.collage527();
        }
        if (str.equals("collage_5_28.png")) {
            return FiveFrameImage.collage528();
        }
        if (str.equals("collage_5_29.png")) {
            return FiveFrameImage.collage529();
        }
        if (str.equals("collage_5_30.png")) {
            return FiveFrameImage.collage530();
        }
        if (str.equals("collage_5_31.png")) {
            return FiveFrameImage.collage531();
        }
        if (str.equals("collage_6_0.png")) {
            return SixFrameImage.collage60();
        }
        if (str.equals("collage_6_1.png")) {
            return SixFrameImage.collage61();
        }
        if (str.equals("collage_6_2.png")) {
            return SixFrameImage.collage62();
        }
        if (str.equals("collage_6_3.png")) {
            return SixFrameImage.collage63();
        }
        if (str.equals("collage_6_4.png")) {
            return SixFrameImage.collage64();
        }
        if (str.equals("collage_6_5.png")) {
            return SixFrameImage.collage65();
        }
        if (str.equals("collage_6_6.png")) {
            return SixFrameImage.collage66();
        }
        if (str.equals("collage_6_7.png")) {
            return SixFrameImage.collage67();
        }
        if (str.equals("collage_6_8.png")) {
            return SixFrameImage.collage68();
        }
        if (str.equals("collage_6_9.png")) {
            return SixFrameImage.collage69();
        }
        if (str.equals("collage_6_10.png")) {
            return SixFrameImage.collage610();
        }
        if (str.equals("collage_6_11.png")) {
            return SixFrameImage.collage611();
        }
        if (str.equals("collage_6_12.png")) {
            return SixFrameImage.collage612();
        }
        if (str.equals("collage_6_13.png")) {
            return SixFrameImage.collage613();
        }
        if (str.equals("collage_6_14.png")) {
            return SixFrameImage.collage614();
        }
        if (str.equals("collage_7_0.png")) {
            return SevenFrameImage.collage70();
        }
        if (str.equals("collage_7_1.png")) {
            return SevenFrameImage.collage71();
        }
        if (str.equals("collage_7_2.png")) {
            return SevenFrameImage.collage72();
        }
        if (str.equals("collage_7_3.png")) {
            return SevenFrameImage.collage73();
        }
        if (str.equals("collage_7_4.png")) {
            return SevenFrameImage.collage74();
        }
        if (str.equals("collage_7_5.png")) {
            return SevenFrameImage.collage75();
        }
        if (str.equals("collage_7_6.png")) {
            return SevenFrameImage.collage76();
        }
        if (str.equals("collage_7_7.png")) {
            return SevenFrameImage.collage77();
        }
        if (str.equals("collage_7_8.png")) {
            return SevenFrameImage.collage78();
        }
        if (str.equals("collage_7_9.png")) {
            return SevenFrameImage.collage79();
        }
        if (str.equals("collage_7_10.png")) {
            return SevenFrameImage.collage710();
        }
        if (str.equals("collage_8_0.png")) {
            return EightFrameImage.collage80();
        }
        if (str.equals("collage_8_1.png")) {
            return EightFrameImage.collage81();
        }
        if (str.equals("collage_8_2.png")) {
            return EightFrameImage.collage82();
        }
        if (str.equals("collage_8_3.png")) {
            return EightFrameImage.collage83();
        }
        if (str.equals("collage_8_4.png")) {
            return EightFrameImage.collage84();
        }
        if (str.equals("collage_8_5.png")) {
            return EightFrameImage.collage85();
        }
        if (str.equals("collage_8_6.png")) {
            return EightFrameImage.collage86();
        }
        if (str.equals("collage_8_7.png")) {
            return EightFrameImage.collage87();
        }
        if (str.equals("collage_8_8.png")) {
            return EightFrameImage.collage88();
        }
        if (str.equals("collage_8_9.png")) {
            return EightFrameImage.collage89();
        }
        if (str.equals("collage_8_10.png")) {
            return EightFrameImage.collage810();
        }
        if (str.equals("collage_8_11.png")) {
            return EightFrameImage.collage811();
        }
        if (str.equals("collage_8_12.png")) {
            return EightFrameImage.collage812();
        }
        if (str.equals("collage_8_13.png")) {
            return EightFrameImage.collage813();
        }
        if (str.equals("collage_8_14.png")) {
            return EightFrameImage.collage814();
        }
        if (str.equals("collage_8_15.png")) {
            return EightFrameImage.collage815();
        }
        if (str.equals("collage_8_16.png")) {
            return EightFrameImage.collage816();
        }
        if (str.equals("collage_9_0.png")) {
            return NineFrameImage.collage90();
        }
        if (str.equals("collage_9_1.png")) {
            return NineFrameImage.collage91();
        }
        if (str.equals("collage_9_2.png")) {
            return NineFrameImage.collage92();
        }
        if (str.equals("collage_9_3.png")) {
            return NineFrameImage.collage93();
        }
        if (str.equals("collage_9_4.png")) {
            return NineFrameImage.collage94();
        }
        if (str.equals("collage_9_5.png")) {
            return NineFrameImage.collage95();
        }
        if (str.equals("collage_9_6.png")) {
            return NineFrameImage.collage96();
        }
        if (str.equals("collage_9_7.png")) {
            return NineFrameImage.collage97();
        }
        if (str.equals("collage_9_8.png")) {
            return NineFrameImage.collage98();
        }
        if (str.equals("collage_9_9.png")) {
            return NineFrameImage.collage99();
        }
        if (str.equals("collage_9_10.png")) {
            return NineFrameImage.collage910();
        }
        if (str.equals("collage_9_11.png")) {
            return NineFrameImage.collage911();
        }
        if (str.equals("collage_10_0.png")) {
            return TenFrameImage.collage100();
        }
        if (str.equals("collage_10_1.png")) {
            return TenFrameImage.collage101();
        }
        if (str.equals("collage_10_2.png")) {
            return TenFrameImage.collage102();
        }
        if (str.equals("collage_10_3.png")) {
            return TenFrameImage.collage103();
        }
        if (str.equals("collage_10_4.png")) {
            return TenFrameImage.collage104();
        }
        if (str.equals("collage_10_5.png")) {
            return TenFrameImage.collage105();
        }
        if (str.equals("collage_10_6.png")) {
            return TenFrameImage.collage106();
        }
        if (str.equals("collage_10_7.png")) {
            return TenFrameImage.collage107();
        }
        if (str.equals("collage_10_8.png")) {
            return TenFrameImage.collage108();
        }
        return null;
    }
}

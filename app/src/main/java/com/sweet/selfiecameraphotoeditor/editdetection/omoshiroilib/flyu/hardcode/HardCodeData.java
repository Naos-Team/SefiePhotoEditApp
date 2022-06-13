package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.flyu.hardcode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.SchedulerSupport;

public class HardCodeData {
    public static final String IMAGE_PATH = "image_path";
    public static List<EffectItem> itemList;

    public static class EffectItem {
        public String description;
        public String name;
        public int type;
        public String unzipPath;
        public String zipFilePath;

        public EffectItem(String str, int i, String str2, String str3) {
            this.name = str;
            this.zipFilePath = "faceu_effects/res/" + str + ".zip";
            this.type = i;
            this.unzipPath = str2;
            this.description = str3;
        }

        public String getThumbFilePath() {
            return "faceu_effects/thumbs/" + this.name + ".png";
        }
    }

    public static void initHardCodeData() {
        itemList = new ArrayList();
        itemList.add(new EffectItem(SchedulerSupport.NONE, -1, SchedulerSupport.NONE, "passthrough"));
        itemList.add(new EffectItem("10012_2", 0, "10012_2", "rainbow"));
        itemList.add(new EffectItem("50109_2", 1, "50109_2", "weisuo / xieyan"));
        itemList.add(new EffectItem("50291_3", 3, "50291_3", "fatface"));
        itemList.add(new EffectItem("20088_1_b", 3, "20088_1_b", "animal_catfoot_b"));
        itemList.add(new EffectItem("900029_5", 3, "900029_5", "smallmouth"));
        itemList.add(new EffectItem("50216_1", 1, "50216_1", "zhibo"));
        itemList.add(new EffectItem("170009_2", 2, "170009_2", "slim face"));
        itemList.add(new EffectItem("170010_1", 2, "170010_1", "mirrorface"));
        itemList.add(new EffectItem("10007_1_sb", 0, "10007_1_sb", "shabi"));
        itemList.add(new EffectItem("900317_1_tiger", 3, "900317_1_tiger", "tiger"));
    }
}

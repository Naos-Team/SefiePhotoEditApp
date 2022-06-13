package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.effect.mx;

import android.content.Context;

import androidx.recyclerview.widget.ItemTouchHelper;

public class BrightnessFilter extends MxOneHashBaseFilter {
    public  void init() {
        super.init();
    }

    public  void onDrawFrame(int i) {
        super.onDrawFrame(i);
    }

    public BrightnessFilter(Context context) {
        super(context, "filter/fsh/mx/mx_brightness.glsl");
        rgbMap = new int[]{0, 1, 3, 5, 7, 9, 11, 12, 14, 16, 18, 20, 21, 23, 25, 27, 29, 31, 32, 34, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 54, 55, 57, 59, 61, 62, 64, 66, 67, 69, 71, 73, 74, 76, 77, 79, 81, 82, 84, 86, 87, 89, 90, 92, 93, 95, 97, 98, 100, 101, 103, 104, 105, 107, 108, 110, 111, 113, 114, 115, 117, 118, 119, 121, 122, 123, 125, 126, 127, 128, 130, 131, 132, 133, 135, 136, 137, 138, 139, 141, 142, 143, 144, 145, 146, 147, 148, 149, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 168, 169, 170, 171, 172, 173, 174, 175, 176, 176, 177, 178, 179, 180, 181, 181, 182, 183, 184, 185, 185, 186, 187, 188, 188, 189, 190, 191, 191, 192, 193, 194, 194, 195, 196, 197, 197, 198, 199, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 201, 202, 203, 203, 204, 205, 205, 206, 207, 207, 208, 208, 209, 210, 210, 211, 212, 212, 213, 213, 214, 215, 215, 216, 216, 217, 218, 218, 219, 219, 220, 221, 221, 222, 222, 223, 223, 224, 225, 225, 226, 226, 227, 227, 228, 228, 229, 229, 230, 231, 231, 232, 232, 233, 233, 234, 234, 235, 235, 236, 236, 237, 237, 238, 238, 239, 239, 240, 240, 241, 241, 242, 242, 243, 243, 244, 244, 245, 245, 246, 246, 247, 247, 248, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 252, 252, 253, 253, 254, 254, 255};
    }
}

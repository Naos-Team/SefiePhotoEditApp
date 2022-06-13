package com.sweet.selfiecameraphotoeditor.stickerview;

import androidx.core.view.ViewCompat;

public class TextInfo {
    int bgAlpha = 255;
    int BG_COLOR = 0;
    String BG_DRAWABLE = "0";
    int CurveRotateProg;
    String FIELD_FOUR = "";
    int FIELD_ONE = 0;
    String FIELD_THREE = "";
    String FIELD_TWO = "";
    String FONT_NAME = "";
    int HEIGHT;
    int ORDER;
    float POS_X = 0.0f;
    float POS_Y = 0.0f;
    float ROTATION;
    int SHADOW_COLOR = 0;
    int SHADOW_PROG = 0;
    int TEMPLATE_ID;
    String TEXT = "";
    int TEXT_ALPHA = 100;
    int TEXT_COLOR = ViewCompat.MEASURED_STATE_MASK;
    int TEXT_ID;
    String TYPE = "";
    int WIDTH;
    int XRotateProg;
    int YRotateProg;
    int ZRotateProg;

    public int getWIDTH() {
        return this.WIDTH;
    }

    public void setWIDTH(int i) {
        this.WIDTH = i;
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }

    public void setHEIGHT(int i) {
        this.HEIGHT = i;
    }

    public String getFONT_NAME() {
        return this.FONT_NAME;
    }

    public void setFONT_NAME(String str) {
        this.FONT_NAME = str;
    }

    public String getTEXT() {
        return this.TEXT;
    }

    public void setTEXT(String str) {
        this.TEXT = str;
    }

    public int getTEXT_COLOR() {
        return this.TEXT_COLOR;
    }

    public void setTEXT_COLOR(int i) {
        this.TEXT_COLOR = i;
    }

    public int getTEXT_ALPHA() {
        return this.TEXT_ALPHA;
    }

    public void setTEXT_ALPHA(int i) {
        this.TEXT_ALPHA = i;
    }

    public int getSHADOW_PROG() {
        return this.SHADOW_PROG;
    }

    public void setSHADOW_PROG(int i) {
        this.SHADOW_PROG = i;
    }

    public int getSHADOW_COLOR() {
        return this.SHADOW_COLOR;
    }

    public void setSHADOW_COLOR(int i) {
        this.SHADOW_COLOR = i;
    }

    public String getBG_DRAWABLE() {
        return this.BG_DRAWABLE;
    }

    public void setBG_DRAWABLE(String str) {
        this.BG_DRAWABLE = str;
    }

    public int getBG_COLOR() {
        return this.BG_COLOR;
    }

    public void setBG_COLOR(int i) {
        this.BG_COLOR = i;
    }

    public int getBG_ALPHA() {
        return this.bgAlpha;
    }

    public void setBG_ALPHA(int i) {
        this.bgAlpha = i;
    }

    public float getPOS_X() {
        return this.POS_X;
    }

    public void setPOS_X(float f) {
        this.POS_X = f;
    }

    public float getPOS_Y() {
        return this.POS_Y;
    }

    public void setPOS_Y(float f) {
        this.POS_Y = f;
    }

    public float getROTATION() {
        return this.ROTATION;
    }

    public void setROTATION(float f) {
        this.ROTATION = f;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(String str) {
        this.TYPE = str;
    }

    public int getORDER() {
        return this.ORDER;
    }

    public void setORDER(int i) {
        this.ORDER = i;
    }

    public int getTEXT_ID() {
        return this.TEXT_ID;
    }

    public void setTEXT_ID(int i) {
        this.TEXT_ID = i;
    }

    public int getTEMPLATE_ID() {
        return this.TEMPLATE_ID;
    }

    public void setTEMPLATE_ID(int i) {
        this.TEMPLATE_ID = i;
    }

    public int getXRotateProg() {
        return this.XRotateProg;
    }

    public void setXRotateProg(int i) {
        this.XRotateProg = i;
    }

    public int getYRotateProg() {
        return this.YRotateProg;
    }

    public void setYRotateProg(int i) {
        this.YRotateProg = i;
    }

    public int getZRotateProg() {
        return this.ZRotateProg;
    }

    public void setZRotateProg(int i) {
        this.ZRotateProg = i;
    }

    public int getCurveRotateProg() {
        return this.CurveRotateProg;
    }

    public void setCurveRotateProg(int i) {
        this.CurveRotateProg = i;
    }

    public int getFIELD_ONE() {
        return this.FIELD_ONE;
    }

    public void setFIELD_ONE(int i) {
        this.FIELD_ONE = i;
    }

    public String getFIELD_TWO() {
        return this.FIELD_TWO;
    }

    public void setFIELD_TWO(String str) {
        this.FIELD_TWO = str;
    }

    public String getFIELD_THREE() {
        return this.FIELD_THREE;
    }

    public void setFIELD_THREE(String str) {
        this.FIELD_THREE = str;
    }

    public String getFIELD_FOUR() {
        return this.FIELD_FOUR;
    }

    public void setFIELD_FOUR(String str) {
        this.FIELD_FOUR = str;
    }
}

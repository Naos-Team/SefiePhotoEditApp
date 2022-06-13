package com.sweet.selfiecameraphotoeditor.stickerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.widget.EditText;

public class AutoEditText extends EditText {
    static final int NO_LINE_LIMIT = -1;
    boolean enablesize;
    int maxLines;
    float maxTextsize;
    Float minTextsize;
    SizeTester sizeTester;
    final RectF spaceavailablerect;
    public float spacingAdd;
    public float spacingMult;
    final SparseIntArray textCachedSizes;
    public TextPaint textPaint;
    public int widthLimit;
    boolean initiallize;

    interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    class C09861 implements SizeTester {
        final RectF textRect = new RectF();

        C09861() {
        }

        @TargetApi(16)
        public int onTestSize(int i, RectF rectF) {
            AutoEditText.this.textPaint.setTextSize((float) i);
            String obj = AutoEditText.this.getText().toString();
            if (AutoEditText.this.getMaxLines() == 1) {
                this.textRect.bottom = AutoEditText.this.textPaint.getFontSpacing();
                this.textRect.right = AutoEditText.this.textPaint.measureText(obj);
            } else {
                StaticLayout staticLayout = new StaticLayout(obj, AutoEditText.this.textPaint, AutoEditText.this.widthLimit, Layout.Alignment.ALIGN_NORMAL, AutoEditText.this.spacingMult, AutoEditText.this.spacingAdd, true);
                if (AutoEditText.this.getMaxLines() != -1 && staticLayout.getLineCount() > AutoEditText.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) staticLayout.getHeight();
                int i2 = -1;
                for (int i3 = 0; i3 < staticLayout.getLineCount(); i3++) {
                    if (((float) i2) < staticLayout.getLineWidth(i3)) {
                        i2 = (int) staticLayout.getLineWidth(i3);
                    }
                }
                this.textRect.right = (float) i2;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            if (rectF.contains(this.textRect)) {
                return -1;
            }
            return 1;
        }
    }

    public AutoEditText(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public AutoEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AutoEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.spaceavailablerect = new RectF();
        this.textCachedSizes = new SparseIntArray();
        this.spacingMult = 1.0f;
        this.spacingAdd = 0.0f;
        this.enablesize = true;
        this.initiallize = false;
        try {
            this.minTextsize = Float.valueOf(TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics()));
            this.maxTextsize = getTextSize();
            if (this.maxLines == 0) {
                this.maxLines = -1;
            }
            this.sizeTester = new C09861();
            this.initiallize = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTypeface(Typeface typeface) {
        if (this.textPaint == null) {
            this.textPaint = new TextPaint(getPaint());
        }
        this.textPaint.setTypeface(typeface);
        super.setTypeface(typeface);
    }

    public void setTextSize(float f) {
        this.maxTextsize = f;
        this.textCachedSizes.clear();
        adjustTextSize();
    }

    @Override
    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this.maxLines = i;
        reAdjust();
    }

    @Override
    public int getMaxLines() {
        return this.maxLines;
    }

    @Override
    public void setSingleLine() {
        super.setSingleLine();
        this.maxLines = 1;
        reAdjust();
    }
    @Override
    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this.maxLines = 1;
        } else {
            this.maxLines = -1;
        }
        reAdjust();
    }
    @Override
    public void setLines(int i) {
        super.setLines(i);
        this.maxLines = i;
        reAdjust();
    }
    @Override
    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this.maxTextsize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        this.textCachedSizes.clear();
        adjustTextSize();
    }

    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this.spacingMult = f2;
        this.spacingAdd = f;
    }

    public void setMinTextSize(Float f) {
        this.minTextsize = f;
        reAdjust();
    }

    public Float get_minTextSize() {
        return this.minTextsize;
    }

    void reAdjust() {
        adjustTextSize();
    }

    void adjustTextSize() {
        try {
            if (this.initiallize) {
                int round = Math.round(this.minTextsize.floatValue());
                int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
                this.widthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
                if (this.widthLimit > 0) {
                    this.spaceavailablerect.right = (float) this.widthLimit;
                    this.spaceavailablerect.bottom = (float) measuredHeight;
                    super.setTextSize(0, (float) efficientTextSizeSearch(round, (int) this.maxTextsize, this.sizeTester, this.spaceavailablerect));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEnableSizeCache(boolean z) {
        this.enablesize = z;
        this.textCachedSizes.clear();
        adjustTextSize();
    }

    int efficientTextSizeSearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3;
        if (!this.enablesize) {
            return binarySearch(i, i2, sizeTester, rectF);
        }
        String obj = getText().toString();
        if (obj == null) {
            i3 = 0;
        } else {
            i3 = obj.length();
        }
        int i4 = this.textCachedSizes.get(i3);
        if (i4 != 0) {
            return i4;
        }
        int binarySearch = binarySearch(i, i2, sizeTester, rectF);
        this.textCachedSizes.put(i3, binarySearch);
        return binarySearch;
    }

    int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            try {
                int onTestSize = sizeTester.onTestSize(i5, rectF);
                if (onTestSize < 0) {
                    i4 = i;
                    i = i5 + 1;
                } else if (onTestSize <= 0) {
                    return i5;
                } else {
                    i3 = i5 - 1;
                    i4 = i3;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i4;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        reAdjust();
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.textCachedSizes.clear();
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            reAdjust();
        }
    }
}

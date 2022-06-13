package com.sweet.selfiecameraphotoeditor.stickerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

public class AutoResizeTextView extends AppCompatTextView {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF availablespacerect;
    private boolean initialized;
    private int maxlines;
    private float maxtextsize;
    private float mintextsize;
    public TextPaint textPaint;
    private final SizeTester sizetester;
    public float spacingadd;
    public float spacingmult;
    public int widthlimit;

    private interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    public boolean isValidWordWrap(char c, char c2) {
        return c == ' ' || c == '-';
    }

    class C09871 implements SizeTester {
        final RectF textRect = new RectF();

        C09871() {
        }

        @TargetApi(16)
        public int onTestSize(int i, RectF rectF) {
            String str;
            AutoResizeTextView.this.textPaint.setTextSize((float) i);
            TransformationMethod transformationMethod = AutoResizeTextView.this.getTransformationMethod();
            if (transformationMethod != null) {
                str = transformationMethod.getTransformation(AutoResizeTextView.this.getText(), AutoResizeTextView.this).toString();
            } else {
                str = AutoResizeTextView.this.getText().toString();
            }
            if (AutoResizeTextView.this.getMaxLines() == 1) {
                this.textRect.bottom = AutoResizeTextView.this.textPaint.getFontSpacing();
                this.textRect.right = AutoResizeTextView.this.textPaint.measureText(str);
            } else {
                StaticLayout staticLayout = new StaticLayout(str, AutoResizeTextView.this.textPaint, AutoResizeTextView.this.widthlimit, Layout.Alignment.ALIGN_NORMAL, AutoResizeTextView.this.spacingmult, AutoResizeTextView.this.spacingadd, true);
                if (AutoResizeTextView.this.getMaxLines() != -1 && staticLayout.getLineCount() > AutoResizeTextView.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) staticLayout.getHeight();
                int lineCount = staticLayout.getLineCount();
                int i2 = -1;
                for (int i3 = 0; i3 < lineCount; i3++) {
                    int lineEnd = staticLayout.getLineEnd(i3);
                    if (i3 < lineCount - 1 && lineEnd > 0 && !AutoResizeTextView.this.isValidWordWrap(str.charAt(lineEnd - 1), str.charAt(lineEnd))) {
                        return 1;
                    }
                    if (((float) i2) < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                        i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
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

    public AutoResizeTextView(Context context) {
        this(context, (AttributeSet) null, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.availablespacerect = new RectF();
        this.spacingmult = 1.0f;
        this.spacingadd = 0.0f;
        this.initialized = false;
        this.mintextsize = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this.maxtextsize = getTextSize();
        this.textPaint = new TextPaint(getPaint());
        if (this.maxlines == 0) {
            this.maxlines = -1;
        }
        this.sizetester = new C09871();
        this.initialized = true;
    }

    @Override
    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        adjustTextSize();
    }

    @Override
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        adjustTextSize();
    }

    @Override
    public void setTextSize(float f) {
        this.maxtextsize = f;
        adjustTextSize();
    }

    @Override
    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this.maxlines = i;
        adjustTextSize();
    }

    @Override
    public int getMaxLines() {
        return this.maxlines;
    }

    @Override
    public void setSingleLine() {
        super.setSingleLine();
        this.maxlines = 1;
        adjustTextSize();
    }

    @Override
    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this.maxlines = 1;
        } else {
            this.maxlines = -1;
        }
        adjustTextSize();
    }
    @Override
    public void setLines(int i) {
        super.setLines(i);
        this.maxlines = i;
        adjustTextSize();
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
        this.maxtextsize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        adjustTextSize();
    }
    @Override
    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this.spacingmult = f2;
        this.spacingadd = f;
    }


    public void setMinTextSize(float f) {
        this.mintextsize = f;
        adjustTextSize();
    }

    private void adjustTextSize() {
        if (this.initialized) {
            int i = (int) this.mintextsize;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this.widthlimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this.widthlimit > 0) {
                this.textPaint = new TextPaint(getPaint());
                RectF rectF = this.availablespacerect;
                rectF.right = (float) this.widthlimit;
                rectF.bottom = (float) measuredHeight;
                superSetTextSize(i);
            }
        }
    }

    private void superSetTextSize(int i) {
        super.setTextSize(0, (float) binarySearch(i, (int) this.maxtextsize, this.sizetester, this.availablespacerect));
    }

    private int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            int onTestSize = sizeTester.onTestSize(i5, rectF);
            if (onTestSize < 0) {
                int i6 = i5 + 1;
                i4 = i;
                i = i6;
            } else if (onTestSize <= 0) {
                return i5;
            } else {
                i4 = i5 - 1;
                i3 = i4;
            }
        }
        return i4;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        adjustTextSize();
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            adjustTextSize();
        }
    }
}

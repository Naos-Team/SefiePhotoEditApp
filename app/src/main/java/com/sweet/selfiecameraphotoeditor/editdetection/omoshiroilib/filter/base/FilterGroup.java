package com.sweet.selfiecameraphotoeditor.editdetection.omoshiroilib.filter.base;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FilterGroup extends AbsFilter {
    private static final String TAG = "FilterGroup";
    protected FBO[] fboList;
    private FBO fboToRebind;
    protected List<AbsFilter> filters = new ArrayList();
    protected boolean isRunning;

    public void init() {
        this.fboToRebind = null;
        for (AbsFilter init : this.filters) {
            init.init();
        }
        this.isRunning = true;
    }

    public void destroy() {
        destroyFrameBuffers();
        for (AbsFilter destroy : this.filters) {
            destroy.destroy();
        }
        this.isRunning = false;
    }

    public void onDrawFrame(int i) {
        runPreDrawTasks();
        if (this.fboList != null) {
            int size = this.filters.size();
            for (int i2 = 0; i2 < size; i2++) {
                AbsFilter absFilter = this.filters.get(i2);
                Log.d(TAG, "onDrawFrame: " + i2 + " / " + size + " " + absFilter.getClass().getSimpleName() + " " + absFilter.surfaceWidth + " " + absFilter.surfaceHeight);
                if (i2 < size - 1) {
                    absFilter.setViewport();
                    this.fboList[i2].bind();
                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                    if (absFilter instanceof FilterGroup) {
                        ((FilterGroup) absFilter).setFboToRebind(this.fboList[i2]);
                    }
                    absFilter.onDrawFrame(i);
                    this.fboList[i2].unbind();
                    i = this.fboList[i2].getFrameBufferTextureId();
                } else {
                    FBO fbo = this.fboToRebind;
                    if (fbo != null) {
                        fbo.bind();
                    }
                    absFilter.setViewport();
                    absFilter.onDrawFrame(i);
                }
            }
            runPostDrawTasks();
        }
    }

    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
        int size = this.filters.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.filters.get(i3).onFilterChanged(i, i2);
        }
        if (this.fboList != null) {
            destroyFrameBuffers();
            this.fboList = null;
        }
        if (this.fboList == null) {
            int i4 = size - 1;
            this.fboList = new FBO[i4];
            for (int i5 = 0; i5 < i4; i5++) {
                AbsFilter absFilter = this.filters.get(i5);
                this.fboList[i5] = FBO.newInstance().create(absFilter.getSurfaceWidth(), absFilter.getSurfaceHeight());
            }
        }
    }

    private void destroyFrameBuffers() {
        this.fboToRebind = null;
        for (FBO destroy : this.fboList) {
            destroy.destroy();
        }
    }

    public void setFboToRebind(FBO fbo) {
        this.fboToRebind = fbo;
    }

    public void addFilter(final AbsFilter absFilter) {
        if (absFilter != null) {
            if (!this.isRunning) {
                this.filters.add(absFilter);
            } else {
                addPreDrawTask(new Runnable() {
                    public void run() {
                        absFilter.init();
                        FilterGroup.this.filters.add(absFilter);
                        FilterGroup filterGroup = FilterGroup.this;
                        filterGroup.onFilterChanged(filterGroup.surfaceWidth, FilterGroup.this.surfaceHeight);
                    }
                });
            }
        }
    }

    public void addFilterList(final List<AbsFilter> list) {
        if (list != null) {
            if (!this.isRunning) {
                for (AbsFilter add : list) {
                    this.filters.add(add);
                }
                return;
            }
            addPreDrawTask(new Runnable() {
                public void run() {
                    for (AbsFilter absFilter : list) {
                        absFilter.init();
                        FilterGroup.this.filters.add(absFilter);
                    }
                    FilterGroup filterGroup = FilterGroup.this;
                    filterGroup.onFilterChanged(filterGroup.surfaceWidth, FilterGroup.this.surfaceHeight);
                }
            });
        }
    }

    public void switchLastFilter(final AbsFilter absFilter) {
        if (absFilter != null) {
            Log.d(TAG, "onFilterChanged: " + absFilter.getClass().getSimpleName());
            if (!this.isRunning) {
                if (this.filters.size() > 0) {
                    List<AbsFilter> list = this.filters;
                    list.remove(list.size() - 1).destroy();
                }
                this.filters.add(absFilter);
                return;
            }
            addPreDrawTask(new Runnable() {
                public void run() {
                    if (FilterGroup.this.filters.size() > 0) {
                        FilterGroup.this.filters.remove(FilterGroup.this.filters.size() - 1).destroy();
                    }
                    absFilter.init();
                    FilterGroup.this.filters.add(absFilter);
                    FilterGroup filterGroup = FilterGroup.this;
                    filterGroup.onFilterChanged(filterGroup.surfaceWidth, FilterGroup.this.surfaceHeight);
                }
            });
        }
    }

    public void switchFilterAt(final AbsFilter absFilter, final int i) {
        if (absFilter != null && i < this.filters.size()) {
            Log.d(TAG, "onFilterChanged: " + absFilter.getClass().getSimpleName());
            addPreDrawTask(new Runnable() {
                public void run() {
                    absFilter.init();
                    ArrayList<AbsFilter> arrayList = new ArrayList<>();
                    for (int i = 0; i < FilterGroup.this.filters.size(); i++) {
                        AbsFilter absFilter = FilterGroup.this.filters.get(i);
                        if (i == i) {
                            arrayList.add(absFilter);
                            absFilter.destroy();
                        } else {
                            arrayList.add(absFilter);
                        }
                    }
                    FilterGroup.this.filters.clear();
                    for (AbsFilter add : arrayList) {
                        FilterGroup.this.filters.add(add);
                    }
                    FilterGroup filterGroup = FilterGroup.this;
                    filterGroup.onFilterChanged(filterGroup.surfaceWidth, FilterGroup.this.surfaceHeight);
                }
            });
        }
    }
}

package com.sweet.selfiecameraphotoeditor.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.Queue;

public class HorizontalListView extends AdapterView<ListAdapter> {
    protected ListAdapter mAdapter;
    public boolean mAlwaysOverrideTouch = true;
    protected int mCurrentX;
    public boolean mDataChanged = false;
    private DataSetObserver mDataObserver = new C06091();
    private int mDisplayOffset = 0;
    private GestureDetector mGesture;
    public int mLeftViewIndex = -1;
    private int mMaxX = Integer.MAX_VALUE;
    protected int mNextX;
    private GestureDetector.OnGestureListener mOnGesture = new C06113();
    public OnItemClickListener mOnItemClicked;
    public OnItemLongClickListener mOnItemLongClicked;
    public OnItemSelectedListener mOnItemSelected;
    private Queue<View> mRemovedViewQueue = new LinkedList();
    private int mRightViewIndex = 0;
    protected Scroller mScroller;

    public View getSelectedView() {
        return null;
    }

    public void setSelection(int i) {
    }

    class C06091 extends DataSetObserver {
        C06091() {
        }

        @Override
        public void onChanged() {
            synchronized (HorizontalListView.this) {
                HorizontalListView.this.mDataChanged = true;
            }
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }
        @Override
        public void onInvalidated() {
            HorizontalListView.this.reset();
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }
    }

    class C06102 implements Runnable {
        C06102() {
        }

        public void run() {
            HorizontalListView.this.requestLayout();
        }
    }

    class C06113 extends GestureDetector.SimpleOnGestureListener {
        C06113() {
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return HorizontalListView.this.onDown(motionEvent);
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return HorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
        }
        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            synchronized (HorizontalListView.this) {
                HorizontalListView.this.mNextX += (int) f;
            }
            HorizontalListView.this.requestLayout();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            for (int i = 0; i < HorizontalListView.this.getChildCount(); i++) {
                View childAt = HorizontalListView.this.getChildAt(i);
                if (isEventWithinView(motionEvent, childAt)) {
                    if (HorizontalListView.this.mOnItemClicked != null) {
                        OnItemClickListener onItemClickListener = HorizontalListView.this.mOnItemClicked;
                        HorizontalListView horizontalListView = HorizontalListView.this;
                        onItemClickListener.onItemClick(horizontalListView, childAt, horizontalListView.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                    }
                    if (HorizontalListView.this.mOnItemSelected != null) {
                        OnItemSelectedListener onItemSelectedListener = HorizontalListView.this.mOnItemSelected;
                        HorizontalListView horizontalListView2 = HorizontalListView.this;
                        onItemSelectedListener.onItemSelected(horizontalListView2, childAt, horizontalListView2.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                    }
                    return true;
                }
            }
            return true;
        }
        @Override
        public void onLongPress(MotionEvent motionEvent) {
            int childCount = HorizontalListView.this.getChildCount();
            int i = 0;
            while (i < childCount) {
                View childAt = HorizontalListView.this.getChildAt(i);
                if (!isEventWithinView(motionEvent, childAt)) {
                    i++;
                } else if (HorizontalListView.this.mOnItemLongClicked != null) {
                    OnItemLongClickListener onItemLongClickListener = HorizontalListView.this.mOnItemLongClicked;
                    HorizontalListView horizontalListView = HorizontalListView.this;
                    onItemLongClickListener.onItemLongClick(horizontalListView, childAt, horizontalListView.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                    return;
                } else {
                    return;
                }
            }
        }

        private boolean isEventWithinView(MotionEvent motionEvent, View view) {
            Rect rect = new Rect();
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            rect.set(i, i2, view.getWidth() + i, view.getHeight() + i2);
            return rect.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        }
    }

    public HorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private synchronized void initView() {
        this.mLeftViewIndex = -1;
        this.mRightViewIndex = 0;
        this.mDisplayOffset = 0;
        this.mCurrentX = 0;
        this.mNextX = 0;
        this.mMaxX = Integer.MAX_VALUE;
        this.mScroller = new Scroller(getContext());
        this.mGesture = new GestureDetector(getContext(), this.mOnGesture);
    }
    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelected = onItemSelectedListener;
    }
    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClicked = onItemClickListener;
    }
    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClicked = onItemLongClickListener;
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ListAdapter listAdapter) {
        ListAdapter listAdapter2 = this.mAdapter;
        if (listAdapter2 != null) {
            listAdapter2.unregisterDataSetObserver(this.mDataObserver);
        }
        this.mAdapter = listAdapter;
        this.mAdapter.registerDataSetObserver(this.mDataObserver);
        reset();
    }

    public synchronized void reset() {
        initView();
        removeAllViewsInLayout();
        requestLayout();
    }

    @SuppressLint({"WrongConstant"})
    private void addAndMeasureChild(View view, int i) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -1);
        }
        addViewInLayout(view, i, layoutParams, true);
        view.measure(MeasureSpec.makeMeasureSpec(getWidth(), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(getHeight(), Integer.MIN_VALUE));
    }

    @Override
    public synchronized void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mAdapter != null) {
            if (this.mDataChanged) {
                int i5 = this.mCurrentX;
                initView();
                removeAllViewsInLayout();
                this.mNextX = i5;
                this.mDataChanged = false;
            }
            if (this.mScroller.computeScrollOffset()) {
                this.mNextX = this.mScroller.getCurrX();
            }
            if (this.mNextX <= 0) {
                this.mNextX = 0;
                this.mScroller.forceFinished(true);
            }
            if (this.mNextX >= this.mMaxX) {
                this.mNextX = this.mMaxX;
                this.mScroller.forceFinished(true);
            }
            int i6 = this.mCurrentX - this.mNextX;
            removeNonVisibleItems(i6);
            fillList(i6);
            positionItems(i6);
            this.mCurrentX = this.mNextX;
            if (!this.mScroller.isFinished()) {
                post(new C06102());
            }
        }
    }

    private void fillList(int i) {
        View childAt = getChildAt(getChildCount() - 1);
        int i2 = 0;
        fillListRight(childAt != null ? childAt.getRight() : 0, i);
        View childAt2 = getChildAt(0);
        if (childAt2 != null) {
            i2 = childAt2.getLeft();
        }
        fillListLeft(i2, i);
    }

    private void fillListRight(int i, int i2) {
        while (i + i2 < getWidth() && this.mRightViewIndex < this.mAdapter.getCount()) {
            View view = this.mAdapter.getView(this.mRightViewIndex, this.mRemovedViewQueue.poll(), this);
            addAndMeasureChild(view, -1);
            i += view.getMeasuredWidth();
            if (this.mRightViewIndex == this.mAdapter.getCount() - 1) {
                this.mMaxX = (this.mCurrentX + i) - getWidth();
            }
            if (this.mMaxX < 0) {
                this.mMaxX = 0;
            }
            this.mRightViewIndex++;
        }
    }

    private void fillListLeft(int i, int i2) {
        int i3;
        while (i + i2 > 0 && (i3 = this.mLeftViewIndex) >= 0) {
            View view = this.mAdapter.getView(i3, this.mRemovedViewQueue.poll(), this);
            addAndMeasureChild(view, 0);
            i -= view.getMeasuredWidth();
            this.mLeftViewIndex--;
            this.mDisplayOffset -= view.getMeasuredWidth();
        }
    }

    private void removeNonVisibleItems(int i) {
        View childAt = getChildAt(0);
        while (childAt != null && childAt.getRight() + i <= 0) {
            this.mDisplayOffset += childAt.getMeasuredWidth();
            this.mRemovedViewQueue.offer(childAt);
            removeViewInLayout(childAt);
            this.mLeftViewIndex++;
            childAt = getChildAt(0);
        }
        View childAt2 = getChildAt(getChildCount() - 1);
        while (childAt2 != null && childAt2.getLeft() + i >= getWidth()) {
            this.mRemovedViewQueue.offer(childAt2);
            removeViewInLayout(childAt2);
            this.mRightViewIndex--;
            childAt2 = getChildAt(getChildCount() - 1);
        }
    }

    private void positionItems(int i) {
        if (getChildCount() > 0) {
            this.mDisplayOffset += i;
            int i2 = this.mDisplayOffset;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                int measuredWidth = childAt.getMeasuredWidth();
                childAt.layout(i2, 0, i2 + measuredWidth, childAt.getMeasuredHeight());
                i2 += childAt.getPaddingRight() + measuredWidth;
            }
        }
    }

    public synchronized void scrollTo(int i) {
        this.mScroller.startScroll(this.mNextX, 0, i - this.mNextX, 0);
        requestLayout();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent) | this.mGesture.onTouchEvent(motionEvent);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        synchronized (this) {
            this.mScroller.fling(this.mNextX, 0, (int) (-f), 0, 0, this.mMaxX, 0, 0);
        }
        requestLayout();
        return true;
    }

    public boolean onDown(MotionEvent motionEvent) {
        this.mScroller.forceFinished(true);
        return true;
    }
}

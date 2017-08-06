package com.syl.androidart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.syl.androidart.utils.LogUtil;

/**
 * Created by Bright on 2017/7/25.
 *
 * @Describe 粗糙的ViewPager(没有什么使用价值)
 * @Called
 */

public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = HorizontalScrollViewEx.class.getSimpleName();
    private int mChildrenSize;
    private int mChildIndex;
    private int mChildWidth;

    //记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    //记录上次滑动的坐标(onInterceptTouchEvent)
    private int mInterceptLastX = 0;
    private int mInterceptLastY = 0;

    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST & heightSpecMode == MeasureSpec.AT_MOST) {
            View childView = getChildAt(0);
            measureWidth = mChildIndex * childView.getMeasuredWidth();
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            View childView = getChildAt(0);
            measureWidth = mChildIndex * childView.getMeasuredWidth();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            View childView = getChildAt(0);
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft = 0;
        mChildrenSize = childCount;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mInterceptLastX;
                int deltaY = y - mInterceptLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }

        LogUtil.d(TAG, "intercept = " + intercept);
        mLastX = x;
        mLastY = y;
        mInterceptLastX = x;
        mInterceptLastY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (xVelocity > 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth + scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}

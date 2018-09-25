package com.hao.mywidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

public class GestureView extends View {

    private Context mContext;

    private VelocityTracker mVelocityTracker;
    private float mMaxVelocity;
    private float mMinVelocity;

    private OverScroller mOverScroller;

    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mVelocityTracker = VelocityTracker.obtain();
        mMaxVelocity = ViewConfiguration.get(mContext).getScaledMaximumFlingVelocity();
        mMinVelocity = ViewConfiguration.get(mContext).getScaledMinimumFlingVelocity();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        if (event.getPointerId(event.getActionIndex()) == 0) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;

                case MotionEvent.ACTION_MOVE:
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    //处理松手后的Fling
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                    int velocityX = (int) mVelocityTracker.getXVelocity();
                    Log.e("actionUp", "actionUp---" + velocityX);
                    if (Math.abs(velocityX) > mMinVelocity) {
                        fling(-velocityX);
                    }
                    //VelocityTracker回收
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    Log.e("actionCancel", "actionCancel");
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void computeScroll() {
        Log.e("computeScroll", "computeScroll");
        super.computeScroll();
    }

    private void fling(int vX) {
        Log.e("fling ", "fling");
    }

}

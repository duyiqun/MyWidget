package com.qun.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Qun on 2017/4/19.
 */

public class SlideMenu extends ViewGroup {

    private static final String TAG = "SlideMenu";
    private View mMenuView;
    private View mMainView;
    private int mMenuWidth;
    private int mMenuHeight;
    private int mMainWidth;
    private int mMainHeight;
    private float mStartX;
    private float mCurrentX;
    private Scroller mScroller;
    private float mStartX2;
    private float mStartY2;
    private GestureDetector mGestureDetector;

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        /**
         * 1. 创建手势识别器对象
         */
        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
//                Log.d(TAG, "onDown: " + e.getAction());
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
//                Log.d(TAG, "onShowPress: " + e.getAction());
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                Log.d(TAG, "onSingleTapUp: " + e.getAction());
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG, "onScroll: " + distanceX);
                float dx = mStartX - e2.getX();
                mStartX = e2.getX();

                if (getScrollX() + dx < -mMenuWidth) {
                    scrollTo(-mMenuWidth, 0);
                } else if (getScrollX() + dx > 0) {
                    scrollTo(0, 0);
                } else {
                    scrollBy((int) dx, 0);
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
//                Log.d(TAG, "onLongPress: " + e.getAction());
            }

            /**
             * 相当于Up事件
             * @param e1
             * @param e2
             * @param velocityX
             * @param velocityY
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                Log.d(TAG, "onFling: velocityX="+velocityX);
//                if (getScrollX() < -mMenuWidth / 2) {
//                    open();
//                } else {
//                    close();
//                }
                return false;
            }
        });
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
        measureChild(mMenuView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mMainView, widthMeasureSpec, heightMeasureSpec);
        mMenuWidth = mMenuView.getMeasuredWidth();
        mMenuHeight = mMenuView.getMeasuredHeight();
        mMainWidth = mMainView.getMeasuredWidth();
        mMainHeight = mMainView.getMeasuredHeight();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mMenuView.layout(-mMenuWidth, 0, 0, mMenuHeight);
        mMainView.layout(0, 0, mMainWidth, mMainHeight);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mStartX = event.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mCurrentX = event.getX();
//                float dx = mStartX - mCurrentX;
//                mStartX = mCurrentX;
//                float newScrollX = getScrollX() + dx;
//                if (newScrollX < -mMenuWidth) {
//                    scrollTo(-mMenuWidth, 0);
//                } else if (newScrollX > 0) {
//                    scrollTo(0, 0);
//                } else {
//                    scrollBy((int) dx, 0);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (getScrollX() < -mMenuWidth / 2) {
//                    open();
//                } else {
//                    close();
//                }
//                break;
//            default:
//                break;
//        }
//        return true;
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getScrollX() < -mMenuWidth / 2) {
                open();
            } else {
                close();
            }
        }
        return mGestureDetector.onTouchEvent(event);
    }

    private void open() {
        int dx = -mMenuWidth - getScrollX();
        mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx));
        invalidate();
    }

    private void close() {
        int dx = -getScrollX();
        mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx));
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            scrollTo(currX, 0);
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX2 = ev.getX();
                mStartY2 = ev.getY();
                //保存down事件时的x的位置
                mStartX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getX();
                float currentY = ev.getY();
                float dx = currentX - mStartX2;
                float dy = currentY - mStartY2;
                mStartX2 = currentX;
                mStartY2 = currentY;
                if (Math.abs(dx) > Math.abs(dy)) {//水平
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return false;
    }
}

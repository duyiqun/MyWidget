package com.qun.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Qun on 2017/4/19.
 */

public class SlideMenu extends ViewGroup {

    private View mMenuView;
    private View mMainView;
    private int mMenuWidth;
    private int mMenuHeight;
    private int mMainWidth;
    private int mMainHeight;

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);


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
}

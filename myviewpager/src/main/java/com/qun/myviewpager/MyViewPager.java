package com.qun.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Qun on 2017/4/16.
 */

public class MyViewPager extends ViewGroup {

    private int mMeasuredWidth;
    private int mMeasuredHeight;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    /**
     * 由于我们是ViewGroup，那么我们必须覆写该方法，在该方法中测量所有的子控件
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//用于给自己测量大小
        //测量自己的子控件
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        //获取自身的宽和高
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();

        //这两个方法必须等View完全展示到界面了才能获取到，本质的含义是View绘制好并展现后的大小
        //final int width = getWidth();
        //final int height = getHeight();
    }

    /**
     * 负责排版（Layout）子控件
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(0, 0, mMeasuredWidth, mMeasuredHeight);

        }
    }

    /**
     * 将图片放到ImageView上，然后经ImageView添加到当前ViewGroup中
     *
     * @param resIds
     */
    public void setData(int[] resIds) {
        for (int i = 0; i < resIds.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(resIds[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            //将ImageView添加到ViewGroup中
            addView(imageView);
        }
    }
}

package com.qun.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Qun on 2017/4/16.
 */

public class MyViewPager extends ViewGroup {

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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    /**
     * 将图片放到ImageView上，然后经ImageView添加到当前ViewGroup中
     * @param resIds
     */
    public void setData(int[] resIds) {
        
    }
}

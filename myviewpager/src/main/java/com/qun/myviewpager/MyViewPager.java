package com.qun.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by Qun on 2017/4/16.
 */

public class MyViewPager extends ViewGroup {

    private static final String TAG = "MyViewPager";
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private float mStartX;
    private Scroller mScroller;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化Scroller
        mScroller = new Scroller(getContext());
//        final float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
//        Log.d(TAG, "MyViewPager: " + ppi);
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
            child.layout(mMeasuredWidth * i, 0, mMeasuredWidth * (i + 1), mMeasuredHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float mCurrentX = event.getX();
                float dx = mStartX - mCurrentX;
                mStartX = mCurrentX;
                //获取上次累计的偏移量 + dx
                //getScrollX() ViewGroup已经发生的偏移量（往左滚动是正的）
                float newScrollX = getScrollX() + dx;
                if (newScrollX < 0) {
                    scrollTo(0, 0);
                } else if (newScrollX > mMeasuredWidth * (getChildCount() - 1)) {
                    scrollTo(mMeasuredWidth * (getChildCount() - 1), 0);
                } else {
                    //让ViewGroup整体移动dx距离
                    scrollBy((int) dx, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                //当ViewGroup正好滚动到一个完整的位置
                int scrollX = getScrollX();
                int position = (int) ((scrollX + 0.f) / mMeasuredWidth + 0.5f);
                //使用mScroller，将ViewGroup平滑的滚动到 mMeasuredWidth*position
                int dx2 = mMeasuredWidth * position - scrollX;
                /**
                 * 注意：该mScroller开始滚动其实是内部开始计算某时刻当前的ViewGroup应该滚动到哪里
                 *       需要不断的去mScroller中获取当前应该滚动到的位置，然后自己调用scrollTo（）实现滚动
                 */
                mScroller.startScroll(scrollX, 0, dx2, 0, Math.abs(dx2));
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当调用invalidate()方法重绘View时，该方法会被回调
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断mScroller有没有滚动完成
        if (mScroller.computeScrollOffset()) {
            //获取当前ViewGroup应该你滚动到哪里
            int currX = mScroller.getCurrX();
            //让当前ViewGroup移动到这个位置
            scrollTo(currX, 0);
            //重新让系统重绘
            invalidate();
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

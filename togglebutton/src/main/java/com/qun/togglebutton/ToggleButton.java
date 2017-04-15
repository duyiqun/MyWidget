package com.qun.togglebutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Qun on 2017/4/15.
 */

public class ToggleButton extends View {
    public ToggleButton(Context context) {
        this(context, null);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    /**
     * 第一个阶段：Android回调该方法用于确定当前View的尺寸信息
     *
     * @param widthMeasureSpec  该数值是当前View的父控件读取当前控件的布局参数后传递进来的，高两位代表测量模式，低30位代表测量出来的真正的值
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //MeasureSpec.EXACTLY;
        //MeasureSpec.AT_MOST;
        //MeasureSpec.UNSPECIFIED;
//        int width = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
//        setMeasuredDimension(width, 50);
        

    }

    /**
     * 第二个阶段：Android回调该方法用于给当前ViewGroup的子控件布局位置
     * @param canvas
     */
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//    }

    /**
     * 第三个阶段：用于绘制当前View的样子
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }
}

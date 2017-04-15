package com.qun.togglebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Qun on 2017/4/15.
 */

public class ToggleButton extends View {
    private static final String TAG = "ToggleButton";
    private Bitmap mBackBitmap;
    private Bitmap mUpBitmap;
    private int mBackBitmapWidth;
    private int mBackBitmapHeight;
    private int mUpBitmapWidth;
    private int mUpBitmapHeight;
    private boolean isOpen;
    private float mLeft;
    private boolean isFirstIn = true;

    public ToggleButton(Context context) {
        this(context, null);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //将图片转换为Bitmap对象
        mBackBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        mUpBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button);
        mBackBitmapWidth = mBackBitmap.getWidth();
        mBackBitmapHeight = mBackBitmap.getHeight();
        mUpBitmapWidth = mUpBitmap.getWidth();
        mUpBitmapHeight = mUpBitmap.getHeight();
        //从attrs中获取app:isOpen="true"属性
//        boolean isOpen = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "isOpen", false);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ToggleButton);
        isOpen = attributes.getBoolean(R.styleable.ToggleButton_isOpen, false);
        //释放资源
        attributes.recycle();
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
//
        //MeasureSpec.EXACTLY;
        //MeasureSpec.AT_MOST;
        //MeasureSpec.UNSPECIFIED;

//        int width = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
//        setMeasuredDimension(width, 50);

        //获取测量模式
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
//        int size = MeasureSpec.getSize(widthMeasureSpec);

//        if (mode == MeasureSpec.EXACTLY) {
//            Log.d(TAG, "onMeasure: EXACTLY");
//        } else if (size == MeasureSpec.AT_MOST) {
//            Log.d(TAG, "onMeasure: AT_MOST");
//        } else {
//            Log.d(TAG, "onMeasure: UNSPECIFIED");
//        }
//        Log.d(TAG, "onMeasure: " + size);

        //设置我们的View的宽和高
        setMeasuredDimension(mBackBitmapWidth, mBackBitmapHeight);
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
//        canvas.drawColor(Color.RED);
        //将Bitmap绘制到画布上
        //从当前View的(0,0)开始绘制背景图片
        canvas.drawBitmap(mBackBitmap, 0, 0, null);
        if (isFirstIn) {
            if (isOpen) {
                mLeft = mBackBitmapWidth - mUpBitmapWidth;
            } else {
                mLeft = 0;
            }
            isFirstIn = false;
        }
        canvas.drawBitmap(mUpBitmap, mLeft, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                mLeft = currentX - mUpBitmapWidth / 2;
                fixLeft();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                mLeft = upX - mUpBitmapWidth / 2;
                if (mLeft > (mBackBitmapWidth - mUpBitmapWidth) / 2) {
                    open();
                } else {
                    close();
                }
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private void open() {
        mLeft = mBackBitmapWidth - mUpBitmapWidth;
        isOpen = true;
        if (mOnOpenStateChangeListener != null) {
            mOnOpenStateChangeListener.onOpenChange(isOpen);
        }
    }

    private void close() {
        mLeft = 0;
        isOpen = false;
        if (mOnOpenStateChangeListener != null) {
            mOnOpenStateChangeListener.onOpenChange(isOpen);
        }
    }

    private void fixLeft() {
        if (mLeft < 0) {
            mLeft = 0;
        } else if (mLeft > mBackBitmapWidth - mUpBitmapWidth) {
            mLeft = mBackBitmapWidth - mUpBitmapWidth;
        }
    }

    public interface onOpenStateChangeListener {
        void onOpenChange(boolean isOpen);
    }

    private onOpenStateChangeListener mOnOpenStateChangeListener;

    public void setOnOpenStateChangeListener(onOpenStateChangeListener onOpenStateChangeListener) {
        this.mOnOpenStateChangeListener = onOpenStateChangeListener;
    }

    public boolean isOpen(){
        return isOpen;
    }
}

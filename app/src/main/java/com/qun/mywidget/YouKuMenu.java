package com.qun.mywidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Qun on 2017/4/14.
 */

public class YouKuMenu extends RelativeLayout implements View.OnClickListener {

    private RelativeLayout mRlLevel1;
    private RelativeLayout mRlLevel2;
    private RelativeLayout mRlLevel3;
    private ImageView mIvHome;
    private ImageView mIvMenu;

    private boolean isLevel3Show = true;
    private boolean isLevel2Show = true;

    /**
     * 该构造函数主要用于在代码中new View的时候使用
     *
     * @param context
     */
    public YouKuMenu(Context context) {
        this(context, null);
    }

    /**
     * 当该View声明在布局文件中时，在把这个布局转换为View的时候由系统会调用该构造函数创建对象
     *
     * @param context
     * @param attrs   布局文件中该控件的属性集合
     */
    public YouKuMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化控件：将编写的布局，添加到该ViewGroup中
        //使用布局填充器 将布局转换为View，然后在添加到该ViewGroup中
        LayoutInflater.from(context).inflate(R.layout.youku_layout, this, true);
        //找出各个子控件
        mRlLevel1 = (RelativeLayout) findViewById(R.id.rl_level1);
        mRlLevel2 = (RelativeLayout) findViewById(R.id.rl_level2);
        mRlLevel3 = (RelativeLayout) findViewById(R.id.rl_level3);
        mIvHome = (ImageView) findViewById(R.id.iv_home);
        mIvMenu = (ImageView) findViewById(R.id.iv_menu);
        //给控件绑定点击监听器
        mIvHome.setOnClickListener(this);
        mIvMenu.setOnClickListener(this);
    }

    /**
     * 也是在布局文件中被系统使用，当该控件声明style属性的时候
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public YouKuMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public YouKuMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home:
                if (isLevel3Show) {
                    hideView(mRlLevel3);
                    hideView(mRlLevel2, 300);
                    isLevel3Show = false;
                } else if (isLevel2Show) {//3级菜单是隐藏的，2级菜单时显示的
                    hideView(mRlLevel2);
                } else { //2级菜单隐藏时
                    showView(mRlLevel2);
                }
                isLevel2Show = !isLevel2Show;
                if (mOnMenuStateChangeListener != null) {
                    mOnMenuStateChangeListener.onLevel2Changed(isLevel2Show);
                    mOnMenuStateChangeListener.onLevel3Changed(isLevel3Show);
                }
                break;
            case R.id.iv_menu:
                if (isLevel3Show) {
                    hideView(mRlLevel3);
                } else {
                    showView(mRlLevel3);
                }
                isLevel3Show = !isLevel3Show;
                if (mOnMenuStateChangeListener != null) {
                    mOnMenuStateChangeListener.onLevel2Changed(isLevel2Show);
                    mOnMenuStateChangeListener.onLevel3Changed(isLevel3Show);
                }
                break;
            default:
                break;
        }
    }

    private void showView(RelativeLayout relativeLayout) {
        RotateAnimation hideAnimation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        hideAnimation.setDuration(1000);
        hideAnimation.setFillAfter(true);//维持动画执行完后的状态
        relativeLayout.startAnimation(hideAnimation);
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            relativeLayout.getChildAt(i).setEnabled(true);
        }
    }

    /**
     * 其实就是让传递过来的参数旋转180度（0--180）
     * 注意：当用补间动画隐藏一个ViewGroup的时候，一定要将该ViewGroup中的所有子控件的都不可点击(不可用)
     *
     * @param relativeLayout
     */
    private void hideView(RelativeLayout relativeLayout, int startOffset) {
        RotateAnimation hideAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        hideAnimation.setDuration(1000);
        hideAnimation.setFillAfter(true);//维持动画执行完后的状态
        hideAnimation.setStartOffset(startOffset);//延迟启动，等startOffset毫秒之后才会启动
        relativeLayout.startAnimation(hideAnimation);

        //下面的代码只能将该控件设置为不可用，但是不会影响其子控件的正常的使用
        //relativeLayout.setEnabled(false);
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            relativeLayout.getChildAt(i).setEnabled(false);
        }
    }

    private void hideView(RelativeLayout relativeLayout) {
        hideView(relativeLayout, 0);
    }

    public void openMenu() {
        if (!isLevel2Show) {
            showView(mRlLevel2);
        }
        if (!isLevel3Show) {
            showView(mRlLevel3);
        }
        isLevel2Show = true;
        isLevel3Show = true;
    }

    public void closeMenu() {
        if (isLevel3Show) {
            hideView(mRlLevel3);
            hideView(mRlLevel2, 300);
        } else if (isLevel2Show) {//3级菜单是隐藏的，2级菜单时显示的
            hideView(mRlLevel2);
        }
        isLevel2Show = false;
        isLevel3Show = false;
    }

    public interface onMenuStateChangeListener {
        void onLevel3Changed(boolean isOpen);

        void onLevel2Changed(boolean isOpen);
    }

    private onMenuStateChangeListener mOnMenuStateChangeListener;

    public void setOnMenuStateChangeListener(onMenuStateChangeListener onMenuStateChangeListener) {
        mOnMenuStateChangeListener = onMenuStateChangeListener;
    }
}

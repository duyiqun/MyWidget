package com.qun.advertisementbar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Qun on 2017/4/14.
 */

public class AdvertisementBar extends RelativeLayout {

    private ViewPager mViewPager;
    private LinearLayout mLlDots;
    private TextView mTextView;
    protected int currentIndex;

    public AdvertisementBar(Context context) {
        this(context, null);
    }

    public AdvertisementBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.advertisementbar, this, true);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mLlDots = (LinearLayout) findViewById(R.id.ll_dots);
        mTextView = (TextView) findViewById(R.id.tv_title);
    }

    public AdvertisementBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public AdvertisementBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    public void setData(final int[] imgIds, final String[] titles) {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            /**
             * 需要根据postion获取到对应的数据，然后将数据设置到View上，最后将View返回，同时将View必须添加到container上（addView）
             * @param container
             * @param position
             * @return
             */
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(imgIds[position]);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //注意：必须将ImageView添加到container上
                container.addView(imageView);
                //注意：必须将ImageView返回
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        //初始化标题
        mTextView.setText(titles[0]);
        //给ViewPager添加监听器
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTextView.setText(titles[position]);
                mLlDots.getChildAt(position).setSelected(true);
                mLlDots.getChildAt(currentIndex).setSelected(false);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //初始化指示器
        for (int i = 0; i < imgIds.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.dot_selector);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 30;//像素
            imageView.setLayoutParams(layoutParams);
            mLlDots.addView(imageView, layoutParams);
            if (i == 0) {
                //默认将第一个图标选中
                imageView.setSelected(true);
            }
        }
    }
}

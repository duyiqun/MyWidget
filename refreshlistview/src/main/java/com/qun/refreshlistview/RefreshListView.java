package com.qun.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Qun on 2017/4/18.
 */

public class RefreshListView extends ListView {

    private View mHeaderView;
    private int mMeasuredHeight;
    private float mStartY;
    private State currentState = State.pull2Refresh;
    private ProgressBar mPbHeader;
    private ImageView mIvArrow;
    private TextView mTvTime;
    private TextView mTvState;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_layout, this, false);
        mPbHeader = (ProgressBar) mHeaderView.findViewById(R.id.pb_header);
        mIvArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
        mTvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
        mTvState = (TextView) mHeaderView.findViewById(R.id.tv_state);
        //将headerView添加到ListView的头部（这是ListView本身支持的功能而RecyclerView没有这样的功能）
        addHeaderView(mHeaderView);
        //想获取headerView的高度，必须手动测量一下
        mHeaderView.measure(0, 0);//00  0000000000000011000
        mMeasuredHeight = mHeaderView.getMeasuredHeight();
        //默认隐藏HeaderView
        mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //获取每次move的偏移量
                float currentY = ev.getY();
                float dy = currentY - mStartY;
                mStartY = currentY;

                //如果是能看到的是第一个位置，则走自己的逻辑
                if (getFirstVisiblePosition() == 0) {
                    //将dy加给HeaderView的内边距，让其内边距变大
                    //在HeaderView原有内边距的基础上+dy
                    int paddingTop = mHeaderView.getPaddingTop();
                    int newPaddingTop = (int) (paddingTop + dy);
                    if (newPaddingTop < -mMeasuredHeight) {//就是上推到HeaderView正好完全隐藏，如果还往上推
                        mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);
                        //将headerView的padding固定死
                        //将move事件交给ListView处理
                        return super.onTouchEvent(ev);
                    } else {
                        if (newPaddingTop > 0) {//将状态改为松开刷新，提示用户松手
                            currentState = State.release2Refresh;
                        } else {//将状态改为下拉刷新
                            currentState = State.pull2Refresh;
                        }
                        updateArrow();
                        mTvState.setText(currentState.name);
                        mHeaderView.setPadding(0, newPaddingTop, 0, 0);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void updateArrow() {

    }

    enum State {
        pull2Refresh("下拉刷新"), release2Refresh("松开刷新"), refreshing("正在刷新");
        private String name;

        State(String name) {
            this.name = name;
        }
    }
}

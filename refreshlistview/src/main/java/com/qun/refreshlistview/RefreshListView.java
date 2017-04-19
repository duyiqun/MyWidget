package com.qun.refreshlistview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.qun.refreshlistview.RefreshListView.State.pull2Refresh;
import static com.qun.refreshlistview.RefreshListView.State.refreshing;
import static com.qun.refreshlistview.RefreshListView.State.release2Refresh;

/**
 * Created by Qun on 2017/4/18.
 */

public class RefreshListView extends ListView {

    private static final String TAG = "RefreshListView";
    private View mHeaderView;
    private int mHeaderViewHeight;
    private float mStartY;
    //记录当前HeaderView的状态
    private State currentState = pull2Refresh;
    //记录上一次改变后的状态
    private State preState = pull2Refresh;
    private ProgressBar mPbHeader;
    private ImageView mIvArrow;
    private TextView mTvTime;
    private TextView mTvState;
    private View mFooterView;
    private int mFooterViewHeight;
    private boolean isLoadMore;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
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
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
//        Log.d(TAG, "initHeaderView: "+mHeaderViewHeight);
        //默认隐藏HeaderView
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }

    private void initFooterView() {
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.footer_layout, this, false);
        addFooterView(mFooterView);
        //隐藏footerView
        mFooterView.measure(0, 0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
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

                if (currentState == refreshing) {
                    //当HeaderView正在刷新的时候，将触摸事件交给ListView本身
                    return super.onTouchEvent(ev);
                }

                //如果是能看到的是第一个位置，则走自己的逻辑
                if (getFirstVisiblePosition() == 0) {
                    mIvArrow.setVisibility(VISIBLE);
                    mPbHeader.setVisibility(GONE);
                    //将dy加给HeaderView的内边距，让其内边距变大
                    //在HeaderView原有内边距的基础上+dy
                    int paddingTop = mHeaderView.getPaddingTop();
                    int newPaddingTop = (int) (paddingTop + dy);
                    if (newPaddingTop < -mHeaderViewHeight) {//就是上推到HeaderView正好完全隐藏，如果还往上推
                        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                        //将headerView的padding固定死
                        //将move事件交给ListView处理
                        return super.onTouchEvent(ev);
                    } else {
                        if (newPaddingTop > 0) {//将状态改为松开刷新，提示用户松手
                            currentState = release2Refresh;
                        } else {//将状态改为下拉刷新
                            currentState = pull2Refresh;
                        }
                        updateArrow();
                        mTvState.setText(currentState.name);
                        mHeaderView.setPadding(0, newPaddingTop, 0, 0);
                        return true;
                    }
                } else if (getLastVisiblePosition() == getCount() - 1) {//已经到最低了
//                    Log.d(TAG, "getCount: "+getCount());
//                    Log.d(TAG, "getChildCount: "+getChildCount());//能看见的子控件个数
                    if (dy < 0) {//还往上拉
                        // 显示FooterView
                        mFooterView.setPadding(0, 0, 0, 0);
                        if (mOnRefreshingListener != null) {
                            //如果已经正在加载更多，则不要继续回调该方法
                            if (!isLoadMore) {
                                mOnRefreshingListener.onLoadMore();
                                isLoadMore = true;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentState == pull2Refresh) {//没有完全拉出来，松手了
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                } else if (currentState == release2Refresh) {
                    mHeaderView.setPadding(0, 0, 0, 0);
                    currentState = State.refreshing;
                    //隐藏箭头显示进度条
                    mIvArrow.setVisibility(GONE);
                    mPbHeader.setVisibility(VISIBLE);
                    mTvState.setText(currentState.name);
                    //回调接口对象
                    if (mOnRefreshingListener != null) {
                        mOnRefreshingListener.onRefreshing();
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 改变箭头的方向
     */
    private void updateArrow() {
        if (preState == State.pull2Refresh && currentState == State.release2Refresh) {
            beginAnimation(0, 180);
            preState = currentState;
        } else if (preState == State.release2Refresh && currentState == State.pull2Refresh) {
            beginAnimation(180, 360);
            preState = currentState;
        }
    }

    private void beginAnimation(int startDegree, int toDegree) {
//        RotateAnimation rotateAnimation = new RotateAnimation(startDegree, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(500);
//        rotateAnimation.setFillAfter(true);
//        mIvArrow.startAnimation(rotateAnimation);
        ObjectAnimator.ofFloat(mIvArrow, "rotation", startDegree, toDegree).setDuration(500).start();
    }

    public void setRefresh(boolean isRefresh) {
        if (!isRefresh) {//停止刷新动画
            //将状态恢复成最原始的状态
            currentState = pull2Refresh;
            preState = pull2Refresh;
            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
            //重新将箭头方向给转过来
            beginAnimation(180, 0);
            //更改最近修改时间
            String time = getTimeString();
            mTvTime.setText(time);
        } else {
            currentState = refreshing;
            mTvState.setText(currentState.name);
            mIvArrow.setVisibility(GONE);
            mPbHeader.setVisibility(VISIBLE);
            mHeaderView.setPadding(0, 0, 0, 0);
        }
    }

    private String getTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public void stopLoadMore() {
        isLoadMore = false;
        mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
    }

    enum State {
        pull2Refresh("下拉刷新"), release2Refresh("松开刷新"), refreshing("正在刷新");
        private String name;

        State(String name) {
            this.name = name;
        }
    }

    public interface onRefreshingListener {
        void onRefreshing();

        void onLoadMore();
    }

    private onRefreshingListener mOnRefreshingListener;

    public void setOnRefreshingListener(onRefreshingListener onRefreshingListener) {
        this.mOnRefreshingListener = onRefreshingListener;
    }
}

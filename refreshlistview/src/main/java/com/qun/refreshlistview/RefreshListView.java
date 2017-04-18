package com.qun.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Qun on 2017/4/18.
 */

public class RefreshListView extends ListView {

    private View mHeaderView;

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
        //将headerView添加到ListView的头部（这是ListView本身支持的功能而RecyclerView没有这样的功能）
        addHeaderView(mHeaderView);

    }
}

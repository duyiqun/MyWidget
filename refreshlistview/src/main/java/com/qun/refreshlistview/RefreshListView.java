package com.qun.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Qun on 2017/4/18.
 */

public class RefreshListView extends ListView {
    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }
}

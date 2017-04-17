package com.qun.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Qun on 2017/4/17.
 */

public class ViewGroupB extends RelativeLayout {
    private static final String TAG = "ViewGroupB";

    public ViewGroupB(Context context) {
        this(context, null);
    }

    public ViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public ViewGroupB(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: " + ev.getAction());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        return true;
    }
}

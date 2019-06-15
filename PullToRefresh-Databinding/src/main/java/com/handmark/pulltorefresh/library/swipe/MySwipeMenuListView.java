package com.handmark.pulltorefresh.library.swipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MySwipeMenuListView extends SwipeMenuListView {

    public MySwipeMenuListView(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, onGestureListener);
    }

    public MySwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetector(context, onGestureListener);
    }

    public MySwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, onGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    private GestureDetector mGestureDetector;

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceY != 0 && distanceX != 0) {
            }
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            //当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview 停住不能滚动
            setParentScrollAble(false);
            return false;
        }
    };

    /**
     * 是否把滚动事件交给父scrollview
     */
    private void setParentScrollAble(boolean flag) {
        //这里的parentScrollView就是listview外面的那个scrollview
        getParent().requestDisallowInterceptTouchEvent(!flag);
    }
}

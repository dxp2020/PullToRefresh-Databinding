package com.handmark.pulltorefresh.library;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Objects;

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    private View emptyView;

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs){

            final private AdapterDataObserver observer = new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    checkIfEmpty();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    checkIfEmpty();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    checkIfEmpty();
                }
            };

            private void checkIfEmpty() {
                if (emptyView != null && getAdapter() != null) {
                    final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
                    emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
                    setVisibility(emptyViewVisible ? GONE : VISIBLE);
                }
            }

            @Override
            public void setAdapter(Adapter adapter) {
                final Adapter oldAdapter = getAdapter();
                if (oldAdapter != null) {
                    oldAdapter.unregisterAdapterDataObserver(observer);
                }
                super.setAdapter(adapter);
                if (adapter != null) {
                    adapter.registerAdapterDataObserver(observer);
                }
                checkIfEmpty();
            }

        };
        return recyclerView;
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
        //解决设置了emptyView，无法下拉、上拉的问题
        emptyView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return true;
        });
        getRefreshableViewWrapper().addView(view,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    /**
     * 判断第一个条目是否完全可见
     */
    private boolean isFirstItemVisible() {
        final RecyclerView.Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView
                        .getTop();
            }
        }

        return false;
    }

    /**
     * 获取第一个可见子View的位置下标
     */
    private int getFirstVisiblePosition() {
        View firstVisibleChild = mRefreshableView.getChildAt(0);
        return firstVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(firstVisibleChild) : -1;
    }

    /**
     * 判断最后一个条目是否完全可见
     */
    private boolean isLastItemVisible() {
        final RecyclerView.Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;

        } else {
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition();
            if(lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount()-1) {
                return mRefreshableView.getChildAt(
                        mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView
                        .getBottom();
            }
        }

        return false;
    }

    /**
     * 获取最后一个可见子View的位置下标
     */
    private int getLastVisiblePosition() {
        View lastVisibleChild = mRefreshableView.getChildAt(mRefreshableView
                .getChildCount() - 1);
        return lastVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(lastVisibleChild) : -1;
    }

    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

}
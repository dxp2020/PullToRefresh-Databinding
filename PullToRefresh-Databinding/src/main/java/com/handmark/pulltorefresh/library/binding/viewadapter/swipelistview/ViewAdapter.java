package com.handmark.pulltorefresh.library.binding.viewadapter.swipelistview;

import androidx.annotation.LayoutRes;
import androidx.databinding.BindingAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshSwipeMenuListView;
import com.handmark.pulltorefresh.library.swipe.SwipeMenuCreator;
import com.handmark.pulltorefresh.library.swipe.SwipeMenuListView;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ViewAdapter {
    @BindingAdapter(value = {"itemBinding", "itemTypeCount", "items", "adapter", "itemDropDownLayout", "itemIds", "itemIsEnabled"}, requireAll = false)
    public static <T> void setAdapter(PullToRefreshSwipeMenuListView pullToRefreshListView, ItemBinding<T> itemBinding, Integer itemTypeCount, List items, BindingListViewAdapter<T> adapter, @LayoutRes int itemDropDownLayout, BindingListViewAdapter.ItemIds<? super T> itemIds, BindingListViewAdapter.ItemIsEnabled<? super T> itemIsEnabled) {
        BindingCollectionAdapters.setAdapter(pullToRefreshListView.getRefreshableView(),itemBinding,itemTypeCount,items,adapter,itemDropDownLayout,itemIds,itemIsEnabled);
    }

    @BindingAdapter(value = {"menuCreator","menuItemClickCommand"}, requireAll = true)
    public static void setListMenu(PullToRefreshSwipeMenuListView view, SwipeMenuCreator creator, SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener) {
        view.getRefreshableView().setMenuCreator(creator);
        view.getRefreshableView().setOnMenuItemClickListener(onMenuItemClickListener);
    }

}

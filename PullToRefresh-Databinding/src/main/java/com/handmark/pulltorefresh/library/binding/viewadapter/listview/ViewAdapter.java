package com.handmark.pulltorefresh.library.binding.viewadapter.listview;

import androidx.annotation.LayoutRes;
import androidx.databinding.BindingAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ViewAdapter {
    @BindingAdapter(value = {"itemBinding", "itemTypeCount", "items", "adapter", "itemDropDownLayout", "itemIds", "itemIsEnabled"}, requireAll = false)
    public static <T> void setAdapter(PullToRefreshListView pullToRefreshListView, ItemBinding<T> itemBinding, Integer itemTypeCount, List items, BindingListViewAdapter<T> adapter, @LayoutRes int itemDropDownLayout, BindingListViewAdapter.ItemIds<? super T> itemIds, BindingListViewAdapter.ItemIsEnabled<? super T> itemIsEnabled) {
        BindingCollectionAdapters.setAdapter(pullToRefreshListView.getRefreshableView(),itemBinding,itemTypeCount,items,adapter,itemDropDownLayout,itemIds,itemIsEnabled);
    }
}

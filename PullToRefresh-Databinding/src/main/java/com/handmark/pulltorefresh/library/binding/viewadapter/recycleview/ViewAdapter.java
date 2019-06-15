package com.handmark.pulltorefresh.library.binding.viewadapter.recycleview;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.LayoutManagers;

public class ViewAdapter {

    @BindingAdapter(value = {"itemBinding", "items", "adapter", "itemIds", "viewHolder"}, requireAll = false)
    public static <T> void setAdapter(PullToRefreshRecyclerView pullToRefreshRecyclerView, ItemBinding<T> itemBinding, List<T> items, BindingRecyclerViewAdapter<T> adapter, BindingRecyclerViewAdapter.ItemIds<? super T> itemIds, BindingRecyclerViewAdapter.ViewHolderFactory viewHolderFactory) {
        if (itemBinding == null) {
            throw new IllegalArgumentException("itemBinding must not be null");
        }
        RecyclerView recyclerView = pullToRefreshRecyclerView.getRefreshableView();
        BindingRecyclerViewAdapter oldAdapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new BindingRecyclerViewAdapter<>();
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setItemBinding(itemBinding);
        adapter.setItems(items);
        adapter.setItemIds(itemIds);
        adapter.setViewHolderFactory(viewHolderFactory);

        if (oldAdapter != adapter) {
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(PullToRefreshRecyclerView view, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        RecyclerView recyclerView = view.getRefreshableView();
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingAdapter("lineManager")
    public static void setLineManager(PullToRefreshRecyclerView view, LineManagers.LineManagerFactory lineManagerFactory) {
        RecyclerView recyclerView = view.getRefreshableView();
        recyclerView.addItemDecoration(lineManagerFactory.create(recyclerView));
    }

}

package com.bms.rabbit.binding;
// Created by Konstantin on 21.08.2018.


import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.bms.rabbit.adapters.BindingRecyclerViewAdapter;
import com.bms.rabbit.adapters.BindingViewPagerAdapter;
import com.bms.rabbit.adapters.ItemDecorationManager;
import com.bms.rabbit.adapters.LayoutManagers;
import com.bms.rabbit.features.ListViewModel;
import com.bms.rabbit.tools.recyclerAnim.ItemAnimatorManager;


public class RecyclerViewBinding {

    @BindingAdapter({"items", "itemLayout", "variable",})
    public static void setAdapter(RecyclerView recyclerView, ObservableList items, int layoutId, int brVarId) {
        BindingRecyclerViewAdapter adapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();

        if (adapter == null) {
            adapter = new BindingRecyclerViewAdapter(brVarId, layoutId);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
        adapter.setItems(items);

    }

    @BindingAdapter({"listViewModel"})
    public static void setPagerAdapter(RecyclerView recyclerView, final ListViewModel listViewModel) {
        BindingRecyclerViewAdapter adapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();

        if (adapter == null) {
            adapter = new BindingRecyclerViewAdapter(listViewModel.getBrVarId(), listViewModel.getLayoutId());
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
        adapter.setItems(listViewModel.getItems());
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingAdapter("itemDecorationManager")
    public static void setDecoratorManager(RecyclerView recyclerView, ItemDecorationManager.ItemDecorationFactory itemDecorationFactory) {
        recyclerView.addItemDecoration(itemDecorationFactory.create(recyclerView));
    }

    @BindingAdapter("itemAnimator")
    public static void setAminatorManager(RecyclerView recyclerView, ItemAnimatorManager.ItemAnimatorFactory itemAnimatorFactory) {
        recyclerView.setItemAnimator(itemAnimatorFactory.create(recyclerView));
    }

}

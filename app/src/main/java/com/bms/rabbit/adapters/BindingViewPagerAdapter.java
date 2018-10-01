package com.bms.rabbit.adapters;
// Created by Konstantin on 11.10.2017.

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class BindingViewPagerAdapter extends PagerAdapter {
    private int layoutId;
    private int brVarId;
    private ObservableList items;

    @NonNull
    private final WeakReferenceOnListChangedCallback callback = new WeakReferenceOnListChangedCallback(this);

    public BindingViewPagerAdapter(int layoutId, int brVarId, ObservableList items) {
        this.layoutId = layoutId;
        this.brVarId = brVarId;
        this.items = items;
        items.addOnListChangedCallback(callback);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);
        final Observable item = (Observable) items.get(position);
        binding.setVariable(brVarId,item);
        viewGroup.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private class WeakReferenceOnListChangedCallback extends ObservableList.OnListChangedCallback<ObservableList> {
        final WeakReference<BindingViewPagerAdapter> adapterRef;

        WeakReferenceOnListChangedCallback(BindingViewPagerAdapter adapter) {
            this.adapterRef = new WeakReference<>(adapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            BindingViewPagerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, final int positionStart, final int itemCount) {
            BindingViewPagerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, final int positionStart, final int itemCount) {
            BindingViewPagerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, final int fromPosition, final int toPosition, final int itemCount) {
            BindingViewPagerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, final int positionStart, final int itemCount) {
            BindingViewPagerAdapter adapter = adapterRef.get();
            if (adapter == null) {

                return;
            }
            adapter.notifyDataSetChanged();
        }


    }

}

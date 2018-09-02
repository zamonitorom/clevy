package com.bms.rabbit.adapters;
// Created by Konstantin on 11.10.2017.

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BindingViewPagerAdapter extends PagerAdapter {
    private int layoutId;
    private int brVarId;
    private ObservableList items;

    public BindingViewPagerAdapter(int layoutId, int brVarId, ObservableList items) {
        this.layoutId = layoutId;
        this.brVarId = brVarId;
        this.items = items;
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

}

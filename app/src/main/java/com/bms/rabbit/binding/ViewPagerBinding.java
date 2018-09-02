package com.bms.rabbit.binding;
// Created by Konstantin on 02.09.2018.

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v4.view.ViewPager;

import com.bms.rabbit.adapters.BindingViewPagerAdapter;
import com.bms.rabbit.tools.Callback;

public class ViewPagerBinding {

    @BindingAdapter({"items", "itemLayout", "variable"})
    public static void setPagerAdapter(ViewPager viewPager, final ObservableList items, int layoutId, int brVarId) {
        BindingViewPagerAdapter adapter = new BindingViewPagerAdapter(layoutId, brVarId, items);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @BindingAdapter({"position"})
    public static void setPosition(ViewPager viewPager, int position) {
        viewPager.setCurrentItem(position,true);
    }

    @BindingAdapter({"changeCallback"})
    public static void setPagerAdapter(ViewPager viewPager,final Callback<Integer> callback) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                callback.call(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

package com.bms.rabbit.binding;
// Created by Konstantin on 18.08.2018.


import android.databinding.BindingAdapter;
import android.view.View;

public class CommonBinding {
    @BindingAdapter("visibility")
    public static void setVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"onClick"})
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runnable.run();
            }
        });
    }

    @BindingAdapter("selected")
    public static void setSelected(View view, boolean value) {
        view.setSelected(value);
    }


}

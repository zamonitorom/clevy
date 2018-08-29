package com.bms.rabbit.adapters;
// Created by Konstantin on 04.10.2017.


import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

public class ItemDecorationManager {
    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;

    protected ItemDecorationManager() {

    }

    public static ItemDecorationFactory devider(final int orientation, final int resId) {
        return new ItemDecorationFactory() {
            @Override
            public RecyclerView.ItemDecoration create(RecyclerView recyclerView) {
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), orientation);
                if (recyclerView.getContext() != null) {
                    Drawable drawable = ContextCompat.getDrawable(recyclerView.getContext(), resId);
                    if(drawable!=null){
                        dividerItemDecoration.setDrawable(drawable);
                    }
                }
                return dividerItemDecoration;
            }
        };
    }

    public interface ItemDecorationFactory {
        RecyclerView.ItemDecoration create(RecyclerView recyclerView);
    }
}

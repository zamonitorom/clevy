package com.bms.rabbit.tools;
// Created by Konstantin on 30.08.2018.

import android.databinding.BaseObservable;

public class StringContainer extends BaseObservable {
    private String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }

    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }
}


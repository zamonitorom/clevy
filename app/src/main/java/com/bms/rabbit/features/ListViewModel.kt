package com.bms.rabbit.features

import android.databinding.ObservableList

// Created by Konstantin on 30.09.2018.

class ListViewModel<T>(val items:ObservableList<T>,var layoutId:Int,val brVarId:Int) {
    fun add(value:T){
        items.add(value)
    }
}
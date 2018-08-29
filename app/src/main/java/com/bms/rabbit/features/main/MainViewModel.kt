package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.bmsoftware.sense2beat.Router

// Created by Konstantin on 29.08.2018.

class MainViewModel(private val router: Router):BaseObservable() {
    val items = ObservableArrayList<LessonItemViewModel>()
    init {
        items.add(LessonItemViewModel(router,111))
        items.add(LessonItemViewModel(router,222))
        items.add(LessonItemViewModel(router,333))
    }
}
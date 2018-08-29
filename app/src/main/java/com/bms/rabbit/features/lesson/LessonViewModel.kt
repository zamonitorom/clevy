package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.bmsoftware.sense2beat.Router

// Created by Konstantin on 29.08.2018.

class LessonViewModel(private val router: Router,val id:Int):BaseObservable() {
    val title = "Урок№ " + id.toString()
    val items = ObservableArrayList<TaskViewModel>()

    init {
        items.add(TaskViewModel(router))
        items.add(TaskViewModel(router))
        items.add(TaskViewModel(router))
        items.add(TaskViewModel(router))
        items.add(TaskViewModel(router))
    }
}
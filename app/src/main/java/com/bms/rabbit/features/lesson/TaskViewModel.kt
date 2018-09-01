package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.bms.rabbit.Router

// Created by Konstantin on 29.08.2018.

class TaskViewModel(private val router: Router):BaseObservable() {
    val items = ObservableArrayList<TaskContentViewModel>()
    fun finish(){
        router.openFinish()
    }
}
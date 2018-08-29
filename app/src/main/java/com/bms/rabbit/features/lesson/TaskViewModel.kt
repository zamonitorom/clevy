package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import com.bmsoftware.sense2beat.Router

// Created by Konstantin on 29.08.2018.

class TaskViewModel(private val router: Router):BaseObservable() {
    fun click(){
        router.openTask()
    }

    fun finish(){
        router.openFinish()
    }
}
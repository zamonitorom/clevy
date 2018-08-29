package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import com.bmsoftware.sense2beat.Router

// Created by Konstantin on 29.08.2018.

class LessonItemViewModel(private val router: Router,val id:Int):BaseObservable() {
    fun click(){
        router.openLesson(id)
    }
}
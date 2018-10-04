package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import com.bms.rabbit.Router
import com.bms.rabbit.entities.LessonItem

// Created by Konstantin on 29.08.2018.

class LessonItemViewModel(private val router: Router, private val lessonItem: LessonItem):BaseObservable() {

    val title = lessonItem.topic
    val dateEnd = lessonItem.dateEnd


    fun click(){
        router.openLesson(lessonItem.id)
    }
}
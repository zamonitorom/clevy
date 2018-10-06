package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.R
import com.bms.rabbit.Router
import com.bms.rabbit.entities.LessonItem

// Created by Konstantin on 29.08.2018.

class LessonItemViewModel(private val router: Router, private val lessonItem: LessonItem):BaseObservable() {

    val title = lessonItem.topic
    val dateEnd = lessonItem.dateEnd
    val img = lessonItem.img
    val level = lessonItem.level

    @get:Bindable
    var background = 0
    @get:Bindable
    var typeText = ""

    init {
        defineType()
    }

    private fun defineType() {
        when (level) {
            "beginner" -> {
                typeText = "beginner"
                background = R.drawable.state3_back

            }
            "pre-Intermediate" -> {
                typeText = "beginner"
                background = R.drawable.state5_back

            }
            "intermediate" -> {
                typeText = "beginner"
                background = R.drawable.word_back

            }
            "upper-Intermediate" -> {
                typeText = "beginner"
                background = R.drawable.state4_back

            }
            "advanced" -> {
                typeText = "beginner"
                background = R.drawable.sentence_back

            }
            "proficiency" -> {
                typeText = "beginner"
                background = R.drawable.state6_back

            }
        }
        notifyPropertyChanged(BR.background)
        notifyPropertyChanged(BR.typeText)
    }


    fun click(){
        router.openLesson(lessonItem.id)
    }
}
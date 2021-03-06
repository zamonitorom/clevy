package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.R
import com.bms.rabbit.Router
import com.bms.rabbit.entities.TaskItem

// Created by Konstantin on 31.08.2018.
/**
 * {@R.layout.item_task}
 */
class TaskItemViewModel(private val router: Router, private val taskItem: TaskItem) : BaseObservable() {
    val title = taskItem.name
    val img = taskItem.img
    val passed = taskItem.passed
    private val type = taskItem.type

    @get:Bindable
    var background = 0
    @get:Bindable
    var typeText = ""

    init {
        defineType()
    }

    private fun defineType() {
        when (type) {
            0 -> {
                typeText = "words"
                background = R.drawable.word_back

            }
            1 -> {
                typeText = "grammar"
                background = R.drawable.sentence_back
            }
        }
        notifyPropertyChanged(BR.background)
        notifyPropertyChanged(BR.typeText)
    }

    fun click() {
        router.openTask(taskItem.id, taskItem.type)
    }
}
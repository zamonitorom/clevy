package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import com.bms.rabbit.Router
import com.bms.rabbit.entities.TaskItem

// Created by Konstantin on 31.08.2018.

class TaskItemViewModel(private val router: Router,private val taskItem:TaskItem):BaseObservable() {
    val title = taskItem.name
    fun click(){
        router.openTask(taskItem.id)
    }
}
package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.bms.rabbit.BR
import com.bms.rabbit.entities.TaskContent
import com.bms.rabbit.tools.Callback

// Created by Konstantin on 31.08.2018.

class TaskContentViewModel(private val taskContent: TaskContent, isTest:Boolean,private val callback: Callback<TaskContent>) : BaseObservable() {
    override fun hashCode(): Int {
        return taskContent.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is TaskContentViewModel) {
            this.taskContent == other.taskContent
        } else false
    }

    @get:Bindable
    var test = isTest
        set(value) {
            field = value
            notifyPropertyChanged(BR.test)
        }

    private val chooseCallback = Callback<TaskButtonViewModel> {

    }

    val variants = ObservableArrayList<TaskButtonViewModel>()

    val enWord = taskContent.correctWord.enWord
    val ruWord = taskContent.correctWord.ruWord
    val transcription = taskContent.correctWord.transcription
    val imgLink = taskContent.correctWord.imgLink

    fun setVatiants(){

    }

    fun complete(){
        callback.call(taskContent)
    }

    fun choose(){

    }
}
package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.bms.rabbit.BR
import com.bms.rabbit.entities.TaskContent
import com.bms.rabbit.tools.Callback
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

// Created by Konstantin on 31.08.2018.

class TaskContentViewModel(private val taskContent: TaskContent, isTest: Boolean, private val callback: Callback<TaskContent>) : BaseObservable() {
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
            if (value) {
                setVariants()
            }
            notifyPropertyChanged(BR.test)
        }

    @get:Bindable
    var correct = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.correct)
        }

    @get:Bindable
    var inCorrect = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.inCorrect)
        }

    private val chooseCallback = Callback<TaskButtonViewModel> {
        if (it.value == enWord) {
            correct = true
        } else inCorrect = true

        buttons.forEach { it.enabled = false }
        val exec = ScheduledThreadPoolExecutor(1)

        exec.schedule({ complete() }, 2, TimeUnit.SECONDS)
    }

    val buttons = ObservableArrayList<TaskButtonViewModel>()

    val enWord = taskContent.correctWord.enWord
    val ruWord = taskContent.correctWord.ruWord
    val transcription = taskContent.correctWord.transcription
    val imgLink = taskContent.correctWord.imgLink

    private fun setVariants() {
        val arr: ArrayList<String> = ArrayList()
        arr.addAll(taskContent.variants)
        arr.add(enWord)
        arr.shuffle()
        arr.forEach { buttons.add(TaskButtonViewModel(it, chooseCallback)) }
    }

    fun complete() {
        callback.call(taskContent)
    }
}
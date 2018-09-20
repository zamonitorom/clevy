package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.tools.Callback

// Created by Konstantin on 02.09.2018.

class TaskButtonViewModel(val value:String, private val clickCallback: Callback<TaskButtonViewModel>):BaseObservable() {
    @get:Bindable
    var enabled = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.enabled)
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

    fun click(){
        clickCallback.call(this)
    }
}
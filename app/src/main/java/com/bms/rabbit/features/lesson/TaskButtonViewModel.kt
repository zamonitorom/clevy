package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.tools.Callback

// Created by Konstantin on 02.09.2018.

class TaskButtonViewModel(val value:String, private val clockCallback: Callback<TaskButtonViewModel>):BaseObservable() {
    @get:Bindable
    var enabled = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.enabled)
        }

    fun click(){
        clockCallback.call(this)
    }
}
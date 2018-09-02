package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR

// Created by Konstantin on 02.09.2018.

class TaskButtonViewModel(val value:String):BaseObservable() {
    @get:Bindable
    var status = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }
}
package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR

// Created by Konstantin on 20.09.2018.

class AchievementViewModel(val picId:Int,val text:String):BaseObservable() {
    @get:Bindable
    var visible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.visible)
        }
}
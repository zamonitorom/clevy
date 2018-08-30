package com.bms.rabbit.features

// Created by Konstantin on 06.08.2018.

import com.bms.rabbit.BR
import android.databinding.BaseObservable
import android.databinding.Bindable

class LoaderViewModel(private val retryFunc: ()->Unit) : BaseObservable() {
    @get:Bindable
    var progress: Boolean = false
        private set(progress) {
            field = progress
            notifyPropertyChanged(BR.progress)
        }
    @get:Bindable
    var error: Boolean = false
        private set(error) {
            field = error
            notifyPropertyChanged(BR.error)
        }

    fun retry() {
        retryFunc.invoke()
    }

    fun startLoading() {
        error = false
        progress = true
    }

    fun finishLoading(isOk: Boolean) {
        error = !isOk
        progress = false
    }
}

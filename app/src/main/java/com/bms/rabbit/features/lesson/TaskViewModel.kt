package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.util.Log
import com.bms.rabbit.BR
import com.bms.rabbit.Router
import com.bms.rabbit.entities.TaskContent
import com.bms.rabbit.features.LoaderViewModel
import com.bms.rabbit.tools.Callback
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 29.08.2018.

class TaskViewModel(private val router: Router, private val lessonRepository: LessonRepository, val id: Int) : BaseObservable() {
    val loaderViewModel = LoaderViewModel({ loadTask() })
    @get:Bindable
    var title = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    @get:Bindable
    var position = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
    @get:Bindable
    var showMenu = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showMenu)
        }
    @get:Bindable
    var test = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.test)
        }
    val items = ObservableArrayList<TaskContentViewModel>()

    private val completeCallback = Callback<TaskContent> {
        val newPosition = position + 1
        if (newPosition < items.size) {
            position = newPosition
        } else {
            if (!test) {
                showMenu = true
            } else finish()
        }
    }

    init {
        loadTask()
    }

    private fun loadTask() {
        loaderViewModel.startLoading()
        lessonRepository.getTask(id)
                .doOnSuccess { title = it.name }
                .map { return@map it.content }
                .toObservable()
                .flatMap { return@flatMap Observable.fromIterable(it) }
                .map { return@map TaskContentViewModel(it, false, completeCallback) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    items.add(it)
                    loaderViewModel.finishLoading(true)
                }, {
                    Log.d("222", it.localizedMessage)
                    loaderViewModel.finishLoading(false)
                }, {})

    }

    fun repeat() {
        showMenu = false
        position = 0
    }

    fun test() {
        items.forEach { it.test = true }
        this.test = true
        repeat()
    }

    fun finish() {
        router.openFinish()
    }
}
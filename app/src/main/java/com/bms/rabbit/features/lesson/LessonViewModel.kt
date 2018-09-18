package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.bms.rabbit.BR
import com.bms.rabbit.Router
import com.bms.rabbit.features.LoaderViewModel
import com.bms.rabbit.features.task.TaskItemViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 29.08.2018.

class LessonViewModel(private val router: Router, private val lessonRepository: LessonRepository, val lessonId: Int) : BaseObservable() {
    val loaderViewModel = LoaderViewModel({ loadLesson() })
    @get:Bindable
    var title = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    val items = ObservableArrayList<TaskItemViewModel>()

    init {
        loadLesson()
    }

    private fun loadLesson() {
        loaderViewModel.startLoading()
        lessonRepository.getLesson(lessonId)
                .doOnSuccess {
                    title = it.topic
                    loaderViewModel.finishLoading(true)
                }
                .map { return@map it.tasks }
                .toObservable()
                .flatMap { return@flatMap Observable.fromIterable(it) }
                .map { return@map TaskItemViewModel(router, it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items.add(it) },{loaderViewModel.finishLoading(false)})
    }

}
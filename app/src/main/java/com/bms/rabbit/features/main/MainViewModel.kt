package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.bms.rabbit.Router
import com.bms.rabbit.features.lesson.LessonRepository
import com.bms.rabbit.features.LoaderViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 29.08.2018.

class MainViewModel(private val router: Router, private val lessonRepository: LessonRepository) : BaseObservable() {
    val loaderViewModel = LoaderViewModel({loadLessons()})
    val items = ObservableArrayList<LessonItemViewModel>()

    init {
        loadLessons()
    }

    fun loadLessons() {
        loaderViewModel.startLoading()
        lessonRepository.getLessons()
                .toObservable()
                .flatMap { return@flatMap Observable.fromIterable(it) }
                .map { return@map LessonItemViewModel(router, it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loaderViewModel.finishLoading(true)
                    items.add(it)
                }, {
                    loaderViewModel.finishLoading(false)
                })
    }
}
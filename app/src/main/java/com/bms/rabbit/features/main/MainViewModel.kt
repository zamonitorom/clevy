package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.bms.rabbit.features.LessonRepository
import com.bms.rabbit.features.LoaderViewModel
import com.bmsoftware.sense2beat.Router
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Date.from

// Created by Konstantin on 29.08.2018.

class MainViewModel(private val router: Router,private val lessonRepository: LessonRepository):BaseObservable() {
    val loaderViewModel = LoaderViewModel({})
    val items = ObservableArrayList<LessonItemViewModel>()

    init {
        loaderViewModel.startLoading()
        lessonRepository.getLessons()
                .toObservable()
                .flatMap { return@flatMap Observable.fromIterable(it) }
                .map {  return@map LessonItemViewModel(router,it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loaderViewModel.finishLoading(true)
                    items.add(it)
                },{
                    loaderViewModel.finishLoading(false)
                })
//        items.add(LessonItemViewModel(router,111))
//        items.add(LessonItemViewModel(router,222))
//        items.add(LessonItemViewModel(router,333))
    }
}
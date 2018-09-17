package com.bms.rabbit.features.lesson

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.util.Log
import com.bms.rabbit.BR
import com.bms.rabbit.Router
import com.bms.rabbit.entities.FinishResult
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
    var title = "Результаты теста"
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    var attempt = 0
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
                .doOnSuccess {
                    title = it.name
                    attempt = it.lastAttempt + 1
                }
                .map { return@map it.content }
                .toObservable()
                .flatMap { return@flatMap Observable.fromIterable(it) }
                .map { return@map TaskContentViewModel(lessonRepository, it, false, id, attempt, completeCallback) }
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

    //вынести в другую dm.vjltkm
    @get:Bindable
    var passed = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.passed)
        }

    @get:Bindable
    var correctCount = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.correctCount)
        }

    @get:Bindable
    var incorrectCount = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.incorrectCount)
        }

    @get:Bindable
    var percent = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.percent)
        }

    fun getTestResult(){
        lessonRepository.getFinishResult(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: FinishResult ->
                    correctCount = result.general.correctCount.toString()
                    incorrectCount = result.general.wrongCount.toString()
                    percent = result.general.correctPercent.toString()
                    passed = result.passed
                },{})
    }
}
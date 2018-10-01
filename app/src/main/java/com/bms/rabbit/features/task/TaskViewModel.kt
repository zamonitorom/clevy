package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.util.Log
import com.bms.rabbit.BR
import com.bms.rabbit.R
import com.bms.rabbit.Router
import com.bms.rabbit.adapters.ItemId
import com.bms.rabbit.entities.TaskSentenceContent
import com.bms.rabbit.entities.TaskWordContent
import com.bms.rabbit.features.ListViewModel
import com.bms.rabbit.features.LoaderViewModel
import com.bms.rabbit.features.lesson.LessonRepository
import com.bms.rabbit.tools.Callback
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 29.08.2018.

class TaskViewModel(private val router: Router, private val lessonRepository: LessonRepository, val id: Int,val type:Int) : BaseObservable() {
    val loaderViewModel = LoaderViewModel({ loadTask() })
    val listViewModel = ListViewModel(ObservableArrayList<BaseTaskContentViewModel>(), R.layout.fragment_word_content, ItemId.value)
    @get:Bindable
    var title = ""
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

    private val completeCallback = Callback<BaseTaskContentViewModel> {
        val newPosition = position + 1
        if (newPosition < listViewModel.items.size) {
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
        if(type ==0){
            listViewModel.layoutId = R.layout.fragment_word_content
            lessonRepository.getTask<TaskWordContent>(id,type)
                    .doOnSuccess {
                        title = it.name
                        attempt = it.lastAttempt + 1
                    }
                    .map { return@map it.content }
                    .toObservable()
                    .flatMap { return@flatMap Observable.fromIterable(it) }
                    .map { return@map TaskWordContentViewModel(lessonRepository, it, false, id, attempt, completeCallback) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        listViewModel.items.add(it)
                        loaderViewModel.finishLoading(true)
                    }, {
                        Log.d("222", it.localizedMessage)
                        loaderViewModel.finishLoading(false)
                    }, {})

        } else{
            listViewModel.layoutId = R.layout.fragment_sentence_content
            lessonRepository.getTask<TaskSentenceContent>(id,type)
                    .doOnSuccess {
                        title = it.name
                        attempt = it.lastAttempt + 1
                    }
                    .map { return@map it.content }
                    .toObservable()
                    .flatMap { return@flatMap Observable.fromIterable(it) }
                    .map { return@map TaskSentenceViewModel(lessonRepository, it, false, id, attempt, completeCallback) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        listViewModel.items.add(it)
                        loaderViewModel.finishLoading(true)
                    }, {
                        Log.d("222", it.localizedMessage)
                        loaderViewModel.finishLoading(false)
                    }, {test()})
        }


    }

    fun repeat() {
        showMenu = false
        position = 0
    }

    fun test() {
        listViewModel.items.forEach { it.test = true }
        this.test = true
        repeat()
    }

    fun finish() {
        synchronized(this) {
            router.openFinish(id)
        }
    }
}
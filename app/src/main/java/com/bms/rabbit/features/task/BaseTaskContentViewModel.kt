package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.entities.TaskWordContent
import com.bms.rabbit.entities.TestAnswer
import com.bms.rabbit.features.lesson.LessonRepository
import com.bms.rabbit.tools.Callback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

// Created by Konstantin on 30.09.2018.

open class BaseTaskContentViewModel(private val lessonRepository: LessonRepository,
                                    isTest: Boolean, private val taskId: Int, private val attempt: Int,
                                    private val callback: Callback<BaseTaskContentViewModel>):BaseObservable() {

    @get:Bindable
    open var test = isTest
        set(value) {
            field = value
            if (value) {
                setVariants()
            }
            notifyPropertyChanged(BR.test)
        }

    @get:Bindable
    open var progress: Boolean = false
        set(progress) {
            field = progress
            notifyPropertyChanged(BR.progress)
        }

    open fun setVariants(){

    }

    protected fun setResult(testAnswer: TestAnswer) {
        progress = true
        lessonRepository.setResult(testAnswer)
                .delaySubscription(850, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { t1, t2 ->
                    progress = false
                    complete()
                }
                .subscribe({}, {})

//        val exec = ScheduledThreadPoolExecutor(1)
//
//        exec.schedule({ complete() }, 2, TimeUnit.SECONDS)
    }

    open fun complete() {
//        val exec = ScheduledThreadPoolExecutor(1)
//        exec.schedule({ callback.call(taskWordContent) }, 500, TimeUnit.MILLISECONDS)
        callback.call(this)
    }

}
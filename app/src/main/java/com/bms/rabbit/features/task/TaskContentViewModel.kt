package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.bms.rabbit.BR
import com.bms.rabbit.entities.TaskContent
import com.bms.rabbit.entities.TestAnswer
import com.bms.rabbit.features.lesson.LessonRepository
import com.bms.rabbit.tools.Callback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

// Created by Konstantin on 31.08.2018.

class TaskContentViewModel(private val lessonRepository: LessonRepository, private val taskContent: TaskContent,
                           isTest: Boolean, private val taskId: Int, private val attempt: Int, private val callback: Callback<TaskContent>) : BaseObservable() {
    @get:Bindable
    var test = isTest
        set(value) {
            field = value
            if (value) {
                setVariants()
            }
            notifyPropertyChanged(BR.test)
        }

    @get:Bindable
    var correct = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.correct)
        }

    @get:Bindable
    var inCorrect = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.inCorrect)
        }

    @get:Bindable
    var progress: Boolean = false
        private set(progress) {
            field = progress
            notifyPropertyChanged(BR.progress)
        }
    private val chooseCallback = Callback<TaskButtonViewModel> {
        if (it.value == enWord) {
            correct = true
        } else {
            inCorrect = true
        }

        val testAnswer = TestAnswer(taskId, attempt, it.value == enWord, it.value, enWord)

        setResult(testAnswer)

        buttons.forEach { item -> item.enabled = false }
    }

    val buttons = ObservableArrayList<TaskButtonViewModel>()

    val enWord = taskContent.correctWord.enWord
    val ruWord = taskContent.correctWord.ruWord
    val transcription = taskContent.correctWord.transcription
    val imgLink = taskContent.correctWord.imgLink

    private fun setVariants() {
        val arr: ArrayList<String> = ArrayList()
        arr.addAll(taskContent.variants)
        arr.add(enWord)
        arr.shuffle()
        arr.forEach { buttons.add(TaskButtonViewModel(it, chooseCallback)) }
    }

    private fun setResult(testAnswer: TestAnswer) {
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

    fun complete() {
//        val exec = ScheduledThreadPoolExecutor(1)
//        exec.schedule({ callback.call(taskContent) }, 500, TimeUnit.MILLISECONDS)
        callback.call(taskContent)
    }
}
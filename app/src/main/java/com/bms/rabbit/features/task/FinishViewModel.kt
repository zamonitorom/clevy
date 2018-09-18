package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.Router
import com.bms.rabbit.entities.FinishResult
import com.bms.rabbit.features.lesson.LessonRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 18.09.2018.

class FinishViewModel(private val router: Router,private val lessonRepository: LessonRepository,private val taskId:Int):BaseObservable() {
    val title = "Результаты теста"
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

    @get:Bindable
    var faster = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.faster)
        }

    @get:Bindable
    var firstAttempt = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstAttempt)
        }

    @get:Bindable
    var firstInGroup = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstInGroup)
        }

    fun getTestResult(){
        lessonRepository.getFinishResult(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setData(it) },{})
    }

    private fun setData(finishResult: FinishResult){
        passed = finishResult.passed

        correctCount = finishResult.general.correctCount.toString()
        incorrectCount = finishResult.general.wrongCount.toString()
        percent = finishResult.general.correctPercent.toString()

        faster = finishResult.achievements.faster
        firstAttempt = finishResult.achievements.firstAttempt
        firstInGroup = finishResult.achievements.firstInGroup
    }

    fun continueLesson(){
        router.continueLesson()
    }
}
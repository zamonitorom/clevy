package com.bms.rabbit.features.task

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.R
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


    val faster = AchievementViewModel(R.drawable.faster,"Быстрее всех в группе!")
    val firstAttempt = AchievementViewModel(R.drawable.first_attempt,"С первой попытки!")
    val firstInGroup = AchievementViewModel(R.drawable.first,"Первый в группе!")

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

        faster.visible = /*true*/ finishResult.achievements.faster
        firstAttempt.visible = /*true*/ finishResult.achievements.firstAttempt
        firstInGroup.visible =/*true*/ finishResult.achievements.firstInGroup
    }

    fun continueLesson(){
        router.continueLesson()
    }
}
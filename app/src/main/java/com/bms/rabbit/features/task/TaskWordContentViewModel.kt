package com.bms.rabbit.features.task

import android.databinding.ObservableArrayList
import com.bms.rabbit.entities.TaskWordContent
import com.bms.rabbit.entities.TestAnswer
import com.bms.rabbit.features.lesson.LessonRepository
import com.bms.rabbit.tools.Callback
import com.bms.rabbit.tools.SoundPlayer

// Created by Konstantin on 31.08.2018.

class TaskWordContentViewModel(lessonRepository: LessonRepository,
                               private val taskWordContent: TaskWordContent,
                               isTest: Boolean,
                               val showRussian:Boolean,
                               private val taskId: Int,
                               private val attempt: Int,
                               callback: Callback<BaseTaskContentViewModel>,
                               private val soundPlayer: SoundPlayer) : BaseTaskContentViewModel(lessonRepository,isTest, taskId,attempt, callback) {


    private val chooseCallback = Callback<TaskButtonViewModel> {
        if (it.value == enWord) {
            it.correct = true
        } else {
            it.inCorrect = true
        }

        val testAnswer = TestAnswer(taskId, attempt, it.value == enWord, it.value, enWord)

        setResult(testAnswer)

        buttons.forEach { item -> item.enabled = false }
    }

    val buttons = ObservableArrayList<TaskButtonViewModel>()

    val enWord = taskWordContent.correctWord.enWord
    val ruWord = taskWordContent.correctWord.ruWord
    val transcription = taskWordContent.correctWord.transcription
    val imgLink = taskWordContent.correctWord.imgLink

    override fun setVariants() {
        val arr: ArrayList<String> = ArrayList()
        arr.addAll(taskWordContent.variants)
        arr.add(enWord)
        arr.shuffle()
        arr.forEach { buttons.add(TaskButtonViewModel(it, chooseCallback)) }
    }

    fun playSound(){
        soundPlayer.play(taskWordContent.correctWord.soundLink)
    }
}
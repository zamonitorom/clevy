package com.bms.rabbit.features.task

import android.databinding.ObservableArrayList
import com.bms.rabbit.entities.TaskSentenceContent
import com.bms.rabbit.entities.TaskWordContent
import com.bms.rabbit.entities.TestAnswer
import com.bms.rabbit.features.lesson.LessonRepository
import com.bms.rabbit.tools.Callback

// Created by Konstantin on 30.09.2018.

class TaskSentenceViewModel(private val lessonRepository: LessonRepository, private val taskSentenceContent: TaskSentenceContent,
                            isTest: Boolean, private val taskId: Int, private val attempt: Int,
                            private val callback: Callback<BaseTaskContentViewModel>) : BaseTaskContentViewModel(lessonRepository, isTest, taskId, attempt, callback) {

    val prefix = taskSentenceContent.prefix
    val suffix = taskSentenceContent.suffix

    val correctWord = if (taskSentenceContent.pair.first.isCorrect) taskSentenceContent.pair.first.value else taskSentenceContent.pair.second.value

    val buttons = ObservableArrayList<TaskButtonViewModel>()

    private val chooseCallback = Callback<TaskButtonViewModel> {
        if (it.value == correctWord) {
            it.correct = true
        } else {
            it.inCorrect = true
        }

        val testAnswer = TestAnswer(taskId, attempt, it.value == correctWord, it.value, correctWord)

        setResult(testAnswer)

        buttons.forEach { item -> item.enabled = false }
    }

    override fun setVariants() {
        val arr: ArrayList<String> = ArrayList()
        arr.add(taskSentenceContent.pair.first.value)
        arr.add(taskSentenceContent.pair.second.value)
        arr.shuffle()
        arr.forEach { buttons.add(TaskButtonViewModel(it, chooseCallback)) }
    }
}
package com.bms.rabbit.features.lesson

import com.bms.rabbit.entities.*
import io.reactivex.Single
import retrofit2.Retrofit
import java.util.HashMap

// Created by Konstantin on 30.08.2018.

class LessonRepository(retrofit: Retrofit) {
    private val lessonApi: LessonApi = retrofit.create<LessonApi>(LessonApi::class.java)

    fun getLessons(): Single<List<LessonItem>> {
        return lessonApi.lessons
    }

    fun getLesson(id: Int): Single<Lesson> {
        return lessonApi.getLessonWithTasks(id)
    }

    fun <T> getTask(id: Int, type: Int): Single<Task<T>> {
        if (type == 0) {
            return lessonApi.getTaskWord(id) as Single<Task<T>>
        }
        if (type == 1) {
            return lessonApi.getTaskSentence(id) as Single<Task<T>>
        }

        return Single.just(Task(0,0,"",false,0,ArrayList()))
    }

    fun setResult(textAnswer: TestAnswer): Single<Any> {
        val cc: Int = if (textAnswer.isCorrect) 1 else 0
        val fieldMap = HashMap<String, String>()
        fieldMap["task"] = textAnswer.taskId.toString()
        fieldMap["is_correct"] = cc.toString()
        fieldMap["given_value"] = textAnswer.givenValue
        fieldMap["correct_value"] = textAnswer.correctValue
        fieldMap["attempt"] = textAnswer.lastAttempt.toString()

        return lessonApi.sendResult(fieldMap)
    }

    fun getFinishResult(id: Int): Single<FinishResult> {
        return lessonApi.getFinishResult(id)
    }
}
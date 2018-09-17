package com.bms.rabbit.features.lesson

import com.bms.rabbit.entities.*
import io.reactivex.Single
import retrofit2.Retrofit
import java.util.HashMap

// Created by Konstantin on 30.08.2018.

class LessonRepository(retrofit: Retrofit) {
    private val lessonApi: LessonApi = retrofit.create<LessonApi>(LessonApi::class.java)

    fun getLessons(): Single<List<LessonItem>>{
        return lessonApi.lessons
    }

    fun getLesson(id:Int):Single<Lesson>{
        return lessonApi.getLessonWithTasks(id)
    }

    fun getTask(id:Int):Single<Task>{
        return lessonApi.getTask(id)
    }

    fun setResult(textAnswer: TestAnswer):Single<Any>{
        val cc:Int = if(textAnswer.isCorrect) 1 else 0
        val fieldMap = HashMap<String, String>()
        fieldMap["task"] = textAnswer.taskId.toString()
        fieldMap["is_correct"] = cc.toString()
        fieldMap["given_value"] = textAnswer.givenValue
        fieldMap["correct_value"] = textAnswer.correctValue
        fieldMap["attempt"] = textAnswer.lastAttempt.toString()

        return lessonApi.sendResult(fieldMap)
    }

    fun getFinishResult(id:Int):Single<FinishResult>{
        return lessonApi.getFinishResult(id)
    }
}
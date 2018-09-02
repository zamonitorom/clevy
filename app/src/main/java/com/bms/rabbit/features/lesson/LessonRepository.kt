package com.bms.rabbit.features.lesson

import com.bms.rabbit.entities.Lesson
import com.bms.rabbit.entities.LessonItem
import com.bms.rabbit.entities.Task
import io.reactivex.Single
import retrofit2.Retrofit

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
}
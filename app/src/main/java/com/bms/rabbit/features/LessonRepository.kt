package com.bms.rabbit.features

import com.bms.rabbit.features.auth.AuthApi
import retrofit2.Retrofit

// Created by Konstantin on 30.08.2018.

class LessonRepository(retrofit: Retrofit) {
    private val lessonApi:LessonApi = retrofit.create<LessonApi>(LessonApi::class.java)
}
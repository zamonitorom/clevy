package com.bms.rabbit.features.lesson;
// Created by Konstantin on 30.08.2018.

import com.bms.rabbit.entities.Lesson;
import com.bms.rabbit.entities.LessonItem;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LessonApi {
    @GET("get-lessons/")
    Single<List<LessonItem>> getLessons();
    @GET("get-lesson-detail/{id}/")
    Single<Lesson> getLessonWithTasks(@Path("id") int id);

}

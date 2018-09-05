package com.bms.rabbit.features.lesson;
// Created by Konstantin on 30.08.2018.

import com.bms.rabbit.entities.Lesson;
import com.bms.rabbit.entities.LessonItem;
import com.bms.rabbit.entities.Task;
import com.bms.rabbit.entities.UserResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LessonApi {
    @GET("get-lessons/")
    Single<List<LessonItem>> getLessons();

    @GET("get-lesson-detail/{id}/")
    Single<Lesson> getLessonWithTasks(@Path("id") int id);

    @GET("get-task-detail/{id}/")
    Single<Task> getTask(@Path("id") int id);

    @FormUrlEncoded
    @POST("make-answer/")
    Single<Object> sendResult(@FieldMap Map<String,String> fieldMap);

}

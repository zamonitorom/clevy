package com.bms.rabbit.features;
// Created by Konstantin on 30.08.2018.

import com.bms.rabbit.entities.LessonItem;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface LessonApi {
    @GET("get-lessons/")
    Single<List<LessonItem>> getLessons();
}

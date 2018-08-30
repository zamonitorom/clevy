package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName
import java.util.*

// Created by Konstantin on 30.08.2018.
//{"id":1,"topic":"Lesson 1. Africa","date_create":"2018-08-30T10:23:42.117849Z","date_start":"2018-08-30T10:24:05Z","date_end":"2018-08-31T10:24:09Z"}
data class LessonItem(val id:Int, val topic:String = "",
                      @SerializedName("date_create") val dateCreate:Date,
                      @SerializedName("date_start") val dateStart:Date,
                      @SerializedName("date_end") val dateEnd:Date) {
}
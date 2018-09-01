package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName
import java.util.*

// Created by Konstantin on 01.09.2018.

data class Lesson(val id:Int, val topic:String = "",
                      @SerializedName("date_create") val dateCreate: Date,
                      @SerializedName("date_start") val dateStart: Date,
                      @SerializedName("date_end") val dateEnd: Date,
                  val tasks:List<Task>)

data class Task(val type:Int,val id:Int,val name:String)

//{"id":1,"topic":"Lesson 1. Africa","date_create":"2018-08-30T10:23:42.117849Z","date_start":"2018-08-30T10:24:05Z","date_end":"2018-08-31T10:24:09Z","tasks":[{"id":3,"name":"Упражниение #1","type":0}]}
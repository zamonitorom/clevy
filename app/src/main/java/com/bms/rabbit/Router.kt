package com.bms.rabbit

import android.support.v4.app.FragmentActivity
import com.bms.rabbit.entities.LessonItem

// Created by Konstantin on 18.08.2018.


interface Router {
    fun openAuth()
    fun openMain()
    fun openLesson(id:Int)
    fun openTask()
    fun openProfile()
    fun openFinish()
    fun setActivity(activity: FragmentActivity)
    fun releaseActivity()
}

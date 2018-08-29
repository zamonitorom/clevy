package com.bmsoftware.sense2beat

import android.support.v4.app.FragmentActivity

// Created by Konstantin on 18.08.2018.


interface Router {
    fun openAuth()
    fun openMain()
    fun openLesson()
    fun openTask()
    fun openProfile()
    fun setActivity(activity: FragmentActivity)
    fun releaseActivity()
}

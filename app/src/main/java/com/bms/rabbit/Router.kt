package com.bms.rabbit

import android.support.v4.app.FragmentActivity

// Created by Konstantin on 18.08.2018.


interface Router {
    fun openAuth()
    fun openMain()
    fun openLesson(id:Int)
    fun openTask(id:Int,type:Int)
    fun openProfile()
    fun openFinish(taskId:Int)
    fun continueLesson()
    fun openPayment()
    fun setActivity(activity: FragmentActivity)
    fun releaseActivity()
    fun clearStack()
    fun startSession()
}

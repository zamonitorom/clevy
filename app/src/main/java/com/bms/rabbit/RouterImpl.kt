package com.bmsoftware.sense2beat

// Created by Konstantin on 18.08.2018.


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.bms.rabbit.R
import com.bms.rabbit.features.auth.AuthFragment
import com.bms.rabbit.features.lesson.LessonFragment
import com.bms.rabbit.features.lesson.TaskFragment
import com.bms.rabbit.features.main.MainActivity
import com.bms.rabbit.features.main.MainFragment

class RouterImpl : Router {
    private var activity: FragmentActivity? = null

    override fun setActivity(activity: FragmentActivity) {
        this.activity = activity
    }

    override fun releaseActivity() {
        this.activity = null
    }

    private fun changeFragment(fragment: Fragment) {
        if (activity is MainActivity) {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
    }

    override fun openProfile() {

    }

    override fun openAuth() {
        changeFragment(AuthFragment())
    }

    override fun openMain() {
        changeFragment(MainFragment())
    }

    override fun openLesson() {
        changeFragment(LessonFragment())
    }

    override fun openTask() {
        changeFragment(TaskFragment())
    }
}

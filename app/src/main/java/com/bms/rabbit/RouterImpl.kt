package com.bmsoftware.sense2beat

// Created by Konstantin on 18.08.2018.


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.bms.rabbit.R
import com.bms.rabbit.features.auth.AuthFragment
import com.bms.rabbit.features.lesson.FinishFragment
import com.bms.rabbit.features.lesson.LessonFragment
import com.bms.rabbit.features.lesson.TaskFragment
import com.bms.rabbit.features.main.MainActivity
import com.bms.rabbit.features.main.MainFragment

class RouterImpl : Router {
    companion object {
        @JvmField
        val lessonId = "lessonId"
    }
    private var activity: FragmentActivity? = null

    override fun setActivity(activity: FragmentActivity) {
        this.activity = activity
    }

    override fun releaseActivity() {
        this.activity = null
    }

    private fun changeFragment(fragment: Fragment,bundle: Bundle = Bundle()) {
        if (activity is MainActivity) {
            fragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
        }
    }

    override fun openProfile() {

    }

    override fun openFinish() {
        if (activity is MainActivity) {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FinishFragment()).commit()
        }
    }

    override fun openAuth() {
        changeFragment(AuthFragment())
    }

    override fun openMain() {
        changeFragment(MainFragment())
    }

    override fun openLesson(id:Int) {
        val bundle = Bundle()
        bundle.putInt(lessonId, id)
        changeFragment(LessonFragment(),bundle)
    }

    override fun openTask() {
        changeFragment(TaskFragment())
    }
}

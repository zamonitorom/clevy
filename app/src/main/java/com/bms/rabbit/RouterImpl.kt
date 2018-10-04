package com.bms.rabbit

// Created by Konstantin on 18.08.2018.


import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import com.bms.rabbit.features.auth.AuthFragment
import com.bms.rabbit.features.task.FinishFragment
import com.bms.rabbit.features.lesson.LessonFragment
import com.bms.rabbit.features.task.TaskFragment
import com.bms.rabbit.features.main.MainActivity
import com.bms.rabbit.features.main.MainFragment
import android.support.v4.content.ContextCompat
import android.view.WindowManager



class RouterImpl : Router {
    companion object {
        const val lessonId = "lessonId"
        const val taskId = "taskId"
        const val taskType = "taskType"
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
            activity!!.supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right,R.anim.slide_in_left, R.anim.slide_in_right)
                    .replace(R.id.fragment_container, fragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun openProfile() {

    }

    override fun openFinish(taskId:Int) {
        val bundle = Bundle()
        bundle.putInt(Companion.taskId, taskId)
        val fragment = FinishFragment()
        fragment.arguments = bundle
        if (activity is MainActivity) {
            activity!!.supportFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    override fun continueLesson() {
        if (activity is MainActivity) {
            activity!!.supportFragmentManager.beginTransaction()
                    .remove(activity!!.supportFragmentManager.findFragmentById(R.id.fragment_container))
                    .commit()
            activity!!.supportFragmentManager.popBackStack()
        }

    }

    override fun openAuth() {
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = activity!!.window

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

// finally change the color
            window.statusBarColor = ContextCompat.getColor(activity!!, R.color.transparent)
        }
*/
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

    override fun openTask(id:Int,type:Int) {
        val bundle = Bundle()
        bundle.putInt(taskId, id)
        bundle.putInt(taskType, type)
        changeFragment(TaskFragment(),bundle)
    }
}

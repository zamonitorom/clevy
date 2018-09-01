package com.bms.rabbit.features.lesson

import android.animation.Animator
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.databinding.FragmentTaskBinding
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class TaskFragment : Fragment() {

    lateinit var binding: FragmentTaskBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val router =(activity!!.applicationContext as RabbitApp).baseComponent.router
        binding.viewModel = TaskViewModel(router)
        super.onViewCreated(view, savedInstanceState)


        val listener = object:Animator.AnimatorListener {
            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                YoYo.with(Techniques.Tada)
                        .duration(600)
                        .repeat(4)
                        .playOn(binding.button2)
            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        }

        YoYo.with(Techniques.RollIn)
                .duration(700)
                .withListener(listener)
                .playOn(binding.button2)
    }

}

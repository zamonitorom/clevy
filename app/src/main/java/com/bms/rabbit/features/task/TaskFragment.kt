package com.bms.rabbit.features.task

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.RouterImpl
import com.bms.rabbit.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    lateinit var binding: FragmentTaskBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments!!.containsKey(RouterImpl.taskId)) {
            val key = arguments!!.getInt(RouterImpl.taskId)
            binding.viewModel = (activity!!.applicationContext as RabbitApp).baseComponent.getTaskViewModel(key)
        }
        super.onViewCreated(view, savedInstanceState)


//        val listener = object:Animator.AnimatorListener {
//            override fun onAnimationCancel(p0: Animator?) {
//
//            }
//
//            override fun onAnimationEnd(p0: Animator?) {
//                YoYo.with(Techniques.Tada)
//                        .duration(600)
//                        .repeat(4)
//                        .playOn(binding.button2)
//            }
//
//            override fun onAnimationRepeat(p0: Animator?) {
//
//            }
//
//            override fun onAnimationStart(p0: Animator?) {
//
//            }
//        }
//
//        YoYo.with(Techniques.RollIn)
//                .duration(700)
//                .withListener(listener)
//                .playOn(binding.button2)
    }

}

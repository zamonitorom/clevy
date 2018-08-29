package com.bms.rabbit.features.lesson


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.databinding.FragmentLessonBinding
import com.bmsoftware.sense2beat.RouterImpl

class LessonFragment : Fragment() {

    lateinit var binding: FragmentLessonBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments!!.containsKey(RouterImpl.lessonId)) {
            val key = arguments!!.getInt(RouterImpl.lessonId)
            val lessonViewModel: LessonViewModel = (activity!!.applicationContext as RabbitApp).baseComponent.getLessonViewModel(key)

            binding.viewModel = lessonViewModel
        }
        super.onViewCreated(view, savedInstanceState)
    }

}

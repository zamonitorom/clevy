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
            val id = arguments!!.getInt(RouterImpl.taskId)
            val type = arguments!!.getInt(RouterImpl.taskType)
            binding.viewModel = (activity!!.applicationContext as RabbitApp).baseComponent.getTaskViewModel(id,type)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        binding.viewModel?.stop()
        super.onPause()
    }

}

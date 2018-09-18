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
import com.bms.rabbit.databinding.FragmentFinishBinding
import com.bms.rabbit.features.task.TaskViewModel

class FinishFragment : Fragment() {
    lateinit var binding: FragmentFinishBinding
    lateinit var finishViewModel: FinishViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_finish, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments!!.containsKey(RouterImpl.taskId)) {
            val key = arguments!!.getInt(RouterImpl.taskId)
            finishViewModel = (activity!!.applicationContext as RabbitApp).baseComponent.getFinishViewModel(key)
            binding.viewModel = finishViewModel
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        finishViewModel.getTestResult()
        super.onStart()
    }

}

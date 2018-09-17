package com.bms.rabbit.features.lesson

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.databinding.FragmentFinishBinding

class FinishFragment : Fragment() {
    lateinit var binding: FragmentFinishBinding
    lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_finish, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskViewModel = (activity!!.applicationContext as RabbitApp).baseComponent.taskViewModel
        binding.viewModel = (activity!!.applicationContext as RabbitApp).baseComponent.taskViewModel
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        taskViewModel.getTestResult()
        super.onStart()
    }

}

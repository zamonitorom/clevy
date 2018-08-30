package com.bms.rabbit.features.main


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val router =(activity!!.applicationContext as RabbitApp).baseComponent.router
        val repo =(activity!!.applicationContext as RabbitApp).baseComponent.lessonRepository
        binding.viewModel = MainViewModel(router,repo)
        super.onViewCreated(view, savedInstanceState)
    }

}

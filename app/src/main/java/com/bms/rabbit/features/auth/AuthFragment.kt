package com.bms.rabbit.features.auth


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    lateinit var binding: FragmentAuthBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val router =(activity!!.applicationContext as RabbitApp).baseComponent.router
        val authRepository = (activity!!.applicationContext as RabbitApp).baseComponent.authRepository
        val messenger = (activity!!.applicationContext as RabbitApp).baseComponent.messenger
        binding.viewModel = AuthViewModel(router,authRepository,messenger)
        super.onViewCreated(view, savedInstanceState)
    }
}

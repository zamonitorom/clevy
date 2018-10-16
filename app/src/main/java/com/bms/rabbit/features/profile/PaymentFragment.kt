package com.bms.rabbit.features.profile


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {
    lateinit var binding: FragmentPaymentBinding
    lateinit var viewModel: PaymentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val paymentService = PaymentService(activity!!)
        val router = (activity!!.applicationContext as RabbitApp).baseComponent.router
        val authDbDataSource = (activity!!.applicationContext as RabbitApp).baseComponent.authDbDataSource
        val messenger = (activity!!.applicationContext as RabbitApp).baseComponent.messenger
        viewModel = PaymentViewModel(paymentService, router, authDbDataSource,messenger)
        binding.viewModel = viewModel
//        paymentService.requestSubs()
    }

    override fun onResume() {
        super.onResume()
        if (::viewModel.isInitialized){
            viewModel.navScreen()
        }
    }
}

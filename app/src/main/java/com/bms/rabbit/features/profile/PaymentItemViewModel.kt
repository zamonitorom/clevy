package com.bms.rabbit.features.profile

import android.databinding.BaseObservable
import com.android.billingclient.api.SkuDetails

// Created by Konstantin on 16.10.2018.

class PaymentItemViewModel(private val skuDetails: SkuDetails,
                           private val paymentService: PaymentService) : BaseObservable() {
    val title = if (skuDetails.title != null) skuDetails.title!! else ""
    val price = if (skuDetails.price != null) skuDetails.price!! else ""

    fun click() {
        paymentService.buySku(skuDetails)
    }
}
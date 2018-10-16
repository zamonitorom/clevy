package com.bms.rabbit.features.profile

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.util.Log
import com.android.billingclient.api.Purchase
import com.bms.rabbit.R
import com.bms.rabbit.Router
import com.bms.rabbit.adapters.ItemId
import com.bms.rabbit.entities.User
import com.bms.rabbit.features.ListViewModel
import com.bms.rabbit.features.LoaderViewModel
import com.bms.rabbit.features.auth.AuthDbDataSource
import com.bms.rabbit.tools.Messenger
import com.crashlytics.android.Crashlytics
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 16.10.2018.

class PaymentViewModel(private val paymentService: PaymentService,
                       private val router: Router,
                       private val authDbDataSource: AuthDbDataSource,
                       private val messenger: Messenger
) : BaseObservable() {

    val loaderViewModel = LoaderViewModel { setupPurchaseList() }

    val listViewModel = ListViewModel(ObservableArrayList<PaymentItemViewModel>(), R.layout.item_subs, ItemId.value)

    private lateinit var user: User

    private var state = 0

    init {
        setupPurchaseList()
    }

    private fun setupPurchaseList() {

        authDbDataSource.user
                .subscribeOn(Schedulers.io())
                .subscribe({
                    user = it!!
                }, {})

        loaderViewModel.startLoading()
        paymentService.setupConnection()
                .flatMap { return@flatMap if (it) paymentService.requestSubs() else Single.error(Throwable("notConnected")) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.forEach {
                        listViewModel.add(PaymentItemViewModel(it, paymentService))
                    }
                    loaderViewModel.finishLoading(true)
                }, {
                    Crashlytics.logException(it)
                    loaderViewModel.finishLoading(false)
                })


        paymentService.purchaseUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorReturn({})
                .subscribe({ purchase ->
                    Log.d("Payment", purchase.sku)
                    user.needPayment = false
                    authDbDataSource.updateUser(user)
                    state = 1
                }, {
                    messenger.showSystemMessage("Не удалось обработаь покупку. Попробуйте позднее")
                    state = 2

                    Crashlytics.logException(it)
                })
    }

    fun navScreen() {
        if (state == 1) {
            router.openMain()
        }
        if (state == 2) {
            router.openAuth()
        }
    }
}
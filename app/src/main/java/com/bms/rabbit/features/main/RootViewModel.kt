package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.bms.rabbit.BR
import com.bms.rabbit.Router
import com.bms.rabbit.entities.ScreenData
import com.bms.rabbit.features.auth.AuthDbDataSource
import com.bms.rabbit.features.profile.PaymentService
import com.bms.rabbit.tools.Messenger
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 29.08.2018.

class RootViewModel(private val router: Router,
                    private val authDbDataSource: AuthDbDataSource,
                    private val paymentService: PaymentService,
                    private val messenger: Messenger) : BaseObservable() {

    init {

    }

    @get:Bindable
    var progress: Boolean = false
        private set(progress) {
            field = progress
            notifyPropertyChanged(BR.progress)
        }

    fun resolveScreen(screenData: ScreenData = ScreenData("",0)) {
        var sku = ""
        progress = true
        if (authDbDataSource.registerFlag) {
            paymentService.setupConnection()
                    .flatMap { return@flatMap if (it) authDbDataSource.user else Single.error(Throwable("notConnected")) }
                    .flatMap {
                        if (it.sku != null) sku = it.sku
                        return@flatMap Single.just(it.needPayment)
                    }
                    .flatMap {
                        if (sku.isEmpty()) return@flatMap Single.just(false)
                        return@flatMap if (it) paymentService.checkPurchase(sku) else Single.just(true)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it) {
                            if (screenData.screenKey.isEmpty()) {
                                router.openMain()
                            } else if (screenData.screenKey == "lesson") {
                                router.openLesson(screenData.id)
                            }
                        } else {
                            router.openPayment()
                        }
                        progress = false
                    }, {
                        progress = false
                        if (it.message!!.contains("notConnected")) {
                            messenger.showSystemMessage("Не удалось загрузить данные! Проверьте подключение к интернету!")
                        }
                        if (it.message!!.contains("Not Found")) {
                            router.openPayment()
                        }
                    })
        } else {
            router.openAuth()
        }
//        Log.d("onMessageReceived",FirebaseInstanceId.getInstance().token)
    }

}
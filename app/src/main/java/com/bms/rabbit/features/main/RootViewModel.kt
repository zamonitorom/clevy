package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import com.bms.rabbit.Router
import com.bms.rabbit.features.auth.AuthDbDataSource

// Created by Konstantin on 29.08.2018.

class RootViewModel(private val router: Router, private val authDbDataSource: AuthDbDataSource) : BaseObservable() {

    init {


    }

    fun resolveScreen() {
        if (authDbDataSource.registerFlag) {
            router.openMain()
        } else {
            router.openAuth()
        }

    }
}
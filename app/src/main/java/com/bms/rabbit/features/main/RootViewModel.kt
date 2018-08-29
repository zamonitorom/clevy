package com.bms.rabbit.features.main

import android.databinding.BaseObservable
import com.bmsoftware.sense2beat.Router

// Created by Konstantin on 29.08.2018.

class RootViewModel(private val router: Router):BaseObservable(){

    init {
        router.openMain()
    }

    fun resolveScreen(){

    }
}
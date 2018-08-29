package com.bms.rabbit

// Created by Konstantin on 29.08.2018.

import android.app.Application

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore

import io.fabric.sdk.android.Fabric

class RabbitApp : Application() {
    val baseComponent:BaseComponent by lazy { BaseComponent.create(this) }
    override fun onCreate() {
        Fabric.with(this, Crashlytics.Builder().core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build())
        super.onCreate()
    }
}

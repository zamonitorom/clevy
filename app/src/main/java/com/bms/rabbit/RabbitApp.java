package com.bms.rabbit;
// Created by Konstantin on 29.08.2018.

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;

public class RabbitApp extends Application {
    @Override
    public void onCreate() {
        Fabric.with(this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build());
        super.onCreate();
    }
}

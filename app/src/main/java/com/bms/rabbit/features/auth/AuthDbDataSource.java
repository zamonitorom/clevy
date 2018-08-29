package com.bms.rabbit.features.auth;
// Created by Konstantin on 13.08.2018.


import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class AuthDbDataSource {
    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_REGISTER = "register";
    public static final String APP_PREFERENCES_TOKEN = "token";
    public static final String APP_PURCHASED = "purchased";

    private Context context;

    private PublishSubject<Boolean> authSubject = PublishSubject.create();

    private String token;

    public AuthDbDataSource(Context context) {
        this.context = context;
    }

    public Observable<Boolean> isAuthorized() {
        return authSubject.startWith(getRegisterFlag());
    }

    public String getToken() {
        if (token != null) {
            return token;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(APP_PREFERENCES_TOKEN, null);
        return token;
    }

    public void saveToken(String token) {
        this.token = token;
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES_TOKEN, token);
        editor.putBoolean(APP_PREFERENCES_REGISTER, true);
        editor.apply();
        authSubject.onNext(true);
    }

    public void removeToken() {
        this.token = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(APP_PREFERENCES_TOKEN);
        editor.remove(APP_PREFERENCES_REGISTER);
        editor.apply();
        authSubject.onNext(false);
    }

    public Boolean getRegisterFlag() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.contains(APP_PREFERENCES_REGISTER);
    }

    public void setHasPurchased() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(APP_PURCHASED, true);
        editor.apply();
    }

    public Boolean hasPurchased() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.contains(APP_PURCHASED);
    }
}

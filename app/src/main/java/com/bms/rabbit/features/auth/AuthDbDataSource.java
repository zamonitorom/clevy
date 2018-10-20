package com.bms.rabbit.features.auth;
// Created by Konstantin on 13.08.2018.


import android.content.Context;
import android.content.SharedPreferences;

import com.bms.rabbit.entities.User;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class AuthDbDataSource {
    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_REGISTER = "register";
    public static final String APP_PREFERENCES_TOKEN = "token";
    public static final String APP_PREFERENCES_USER = "user";
    public static final String APP_PURCHASED = "purchased";

    private Context context;

    private PublishSubject<Boolean> authSubject = PublishSubject.create();

    private User user;

    public AuthDbDataSource(Context context) {
        this.context = context;
    }

    public Observable<Boolean> isAuthorized() {
        return authSubject.startWith(getRegisterFlag());
    }

    public String getToken() {
        if (user != null) {
            return user.getToken();
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(APP_PREFERENCES_USER, null);
        if (jsonString != null) {
            user = new Gson().fromJson(jsonString, User.class);
            return user.getToken();
        } else return "";
    }

    public Single<User> getUser() {
        if (user != null) {
            return Single.just(user);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(APP_PREFERENCES_USER, null);
        if (jsonString != null) {
            user = new Gson().fromJson(jsonString, User.class);
            return Single.just(user);
        } else return Single.error(new NullPointerException("user not found"));
    }

//    public void saveToken(String token) {
//        this.token = token;
//        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(APP_PREFERENCES_TOKEN, token);
//        editor.putBoolean(APP_PREFERENCES_REGISTER, true);
//        editor.apply();
//        authSubject.onNext(true);
//    }

    public void updateUser(User user) {
        this.user = user;
        removeUser();
        saveUser(user);
    }

    public void saveUser(User user) {
        this.user = user;
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES_USER, new Gson().toJson(user));
        editor.putBoolean(APP_PREFERENCES_REGISTER, true);
        editor.putBoolean(APP_PURCHASED, false);
        editor.apply();
        authSubject.onNext(true);
    }

    public void removeUser() {
        this.user = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(APP_PREFERENCES_USER);
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

//    public Boolean hasPurchased() {
////        boolean res = false;
////        getUser().doOnSuccess(new Consumer<User>() {
////            @Override
////            public void accept(User user) throws Exception {
////                if (!user.getSku().isEmpty()){
////                    res = true;
////                }
////            }
////        }).toObservable().blockingSubscribe();
//    }
}

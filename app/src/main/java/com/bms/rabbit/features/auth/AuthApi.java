package com.bms.rabbit.features.auth;
// Created by Konstantin on 13.08.2018.


import com.bms.rabbit.entities.User;
import com.bms.rabbit.entities.UserResponse;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {
    @FormUrlEncoded
    @POST("auth/")
    Single<User> createUser(@FieldMap Map<String,String> fieldMap);
}

package com.bms.rabbit.features.auth;
// Created by Konstantin on 13.08.2018.


import com.bms.rabbit.entities.NewUser;
import com.bms.rabbit.entities.UserResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class AuthNetworkDataSource {
    private AuthApi authApi;

    public AuthNetworkDataSource(Retrofit retrofit) {
        authApi = retrofit.create(AuthApi.class);
    }

    public Single<UserResponse> createUser(NewUser newUser) {
        Map<String,String> fieldMap = new HashMap<>();
        fieldMap.put("name",newUser.getName());
        fieldMap.put("email",newUser.getMail());
        fieldMap.put("group_code",newUser.getCode());
        return authApi.createUser(fieldMap);
    }
}

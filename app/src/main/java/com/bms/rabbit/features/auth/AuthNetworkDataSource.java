package com.bms.rabbit.features.auth;
// Created by Konstantin on 13.08.2018.


import retrofit2.Retrofit;

public class AuthNetworkDataSource {
    private AuthApi authApi;

    public AuthNetworkDataSource(Retrofit retrofit) {
        authApi = retrofit.create(AuthApi.class);
    }

//    public Single<UserResponse> createUser(NewUser newUser) {
//        return authApi.createUser(newUser);
//    }
}

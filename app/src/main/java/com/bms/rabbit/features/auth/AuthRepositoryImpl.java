package com.bms.rabbit.features.auth;
// Created by Konstantin on 30.08.2018.

import com.bms.rabbit.entities.NewUser;
import com.bms.rabbit.entities.UserResponse;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class AuthRepositoryImpl implements AuthRepository {
    private AuthDbDataSource authDbDataSource;
    private AuthNetworkDataSource authNetworkDataSource;

    public AuthRepositoryImpl(AuthDbDataSource authDbDataSource, AuthNetworkDataSource authNetworkDataSource) {
        this.authDbDataSource = authDbDataSource;
        this.authNetworkDataSource = authNetworkDataSource;
    }

    @Override
    public Single<UserResponse> createUser(NewUser newUser) {
        return authNetworkDataSource.createUser(newUser).doOnSuccess(new Consumer<UserResponse>() {
            @Override
            public void accept(UserResponse userResponse) throws Exception {
                authDbDataSource.saveToken(userResponse.getToken());
            }
        });
    }
}

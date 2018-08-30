package com.bms.rabbit.features.auth;
// Created by Konstantin on 13.08.2018.


import com.bms.rabbit.entities.NewUser;
import com.bms.rabbit.entities.UserResponse;

import io.reactivex.Single;

public interface AuthRepository {
    Single<UserResponse> createUser(NewUser newUser);
}

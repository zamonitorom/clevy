package com.bms.rabbit.features.auth

// Created by Konstantin on 30.08.2018.

import com.bms.rabbit.entities.NewUser
import com.bms.rabbit.entities.User
import com.bms.rabbit.entities.UserResponse

import io.reactivex.Single
import io.reactivex.functions.Consumer

class AuthRepositoryImpl(private val authDbDataSource: AuthDbDataSource, private val authNetworkDataSource: AuthNetworkDataSource) : AuthRepository {

    override fun createUser(newUser: NewUser): Single<User> {
        return authNetworkDataSource.createUser(newUser)
                .doOnSuccess { authDbDataSource.saveUser(it) }
    }
}

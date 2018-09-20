package com.bms.rabbit.features.auth

// Created by Konstantin on 13.08.2018.


import com.bms.rabbit.entities.NewUser
import com.bms.rabbit.entities.UserResponse

import java.util.HashMap

import io.reactivex.Single
import retrofit2.Retrofit

class AuthNetworkDataSource(retrofit: Retrofit) {
    private val authApi: AuthApi = retrofit.create(AuthApi::class.java)

    fun createUser(newUser: NewUser): Single<UserResponse> {
        val fieldMap = HashMap<String, String>()
        fieldMap["name"] = newUser.name
        fieldMap["email"] = newUser.mail
        fieldMap["group_code"] = newUser.code
        fieldMap["client_key"] = newUser.clientKey
        return authApi.createUser(fieldMap)
    }
}

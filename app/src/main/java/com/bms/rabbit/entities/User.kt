package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName

// Created by Konstantin on 30.08.2018.

data class NewUser(val name: String, val mail: String, val code: String, val clientKey: String = "")

data class User(val name: String, val mail: String, val code: String, val clientKey: String = "",
                val needPayment: Boolean = false)

data class UserResponse(@SerializedName("token") val token: String = "")
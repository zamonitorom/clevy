package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName

// Created by Konstantin on 30.08.2018.

data class NewUser(val name: String,
                   val mail: String,
                   val code: String,
                   val clientKey: String = "")

//todo replaace var to val after tests
data class User(val name: String,
                @SerializedName("email") val mail: String,
                @SerializedName("group_code") val code: String,
                val token: String = "",
                @SerializedName("is_need_to_pay") var needPayment: Boolean = false,
                var sku: String = "")

data class UserResponse(@SerializedName("token") val token: String = "")
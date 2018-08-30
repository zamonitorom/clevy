package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName

// Created by Konstantin on 30.08.2018.

data class NewUser(@SerializedName("id") val name:String, val mail:String, val code:Int) {
}

data class UserResponse(val token:String = ""){

}
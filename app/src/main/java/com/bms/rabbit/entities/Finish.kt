package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName

// Created by Konstantin on 17.09.2018.

data class FinishResult(val achievements: Achievements, val general: General,
                        @SerializedName("is_passed") val passed:Boolean = false)

data class General(@SerializedName("correct_count")val correctCount:Int = 0,
                   @SerializedName("correct_percent") val correctPercent:Double = 0.0,
                       @SerializedName("wrong_count") val wrongCount:Int = 0)

data class Achievements(val faster:Boolean = false, @SerializedName("first_attempt") val firstAttempt:Boolean = false,
                        @SerializedName("first_in_group") val firstInGroup:Boolean = false)
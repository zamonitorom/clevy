package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName

// Created by Konstantin on 16.10.2018.
//{"productId":"com.bms.rabbit.subscribe.monthly",
// "type":"subs",
// "price":"299,00 ₽",
// "price_amount_micros":299000000,
// "price_currency_code":"RUB",
// "subscriptionPeriod":"P1M",
// "title":"Подписка на месяц (LearnRabbit)",
// "description":"Используйте приложения для запоминания слов!"}
data class InAppSubscription(@SerializedName("productId") val id: String,
                             val type: String,
                             val price: String,
                             @SerializedName("productId") val currency: String,
                             val title: String,
                             val description: String
)
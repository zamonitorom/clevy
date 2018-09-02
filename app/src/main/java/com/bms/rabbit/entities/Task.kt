package com.bms.rabbit.entities

import com.google.gson.annotations.SerializedName

// Created by Konstantin on 02.09.2018.

data class TaskItem(val type: Int, val id: Int, val name: String)

data class Task(val type: Int, val id: Int, val name: String,@SerializedName("data")  val content: List<TaskContent>)

data class TaskContent(@SerializedName("correct_word") val correctWord: CorrectWord, val variants: List<String>)

data class CorrectWord(val id: Int, @SerializedName("en_word") val enWord: String,
                       @SerializedName("ru_word") val ruWord: String, val transcription: String,
                       @SerializedName("src") val imgLink: String = "",@SerializedName("sound") val soundLink: String = "")

//{"id":4,"name":"Упражниение #2","type":0,"data":[
// {"correct_word":{"id":2,"en_word":"dog","ru_word":"собака","imgLink":"/uploads/dog.png","transcription":"[dôg]","soundLink":""},"variants":["cat","hamster","parrot"]}
// ,{"correct_word":{"id":1,"en_word":"cat","ru_word":"кошка","imgLink":"/uploads/grey-cat-sitting-transparent-background-image.png","transcription":"[kat]","soundLink":""},"variants":["hamster","dog","parrot"]},
// {"correct_word":{"id":3,"en_word":"parrot","ru_word":"попугай","imgLink":"/uploads/parrot.png","transcription":"[ˈparət]","soundLink":""},"variants":["hamster","cat","dog"]},{"correct_word":{"id":4,"en_word":"hamster","ru_word":"хомячек","imgLink":"/uploads/hamster.png","transcription":"[ˈhamstər]","soundLink":""},"variants":["parrot","dog","cat"]}]}
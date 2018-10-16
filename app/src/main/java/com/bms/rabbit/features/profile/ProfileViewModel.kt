package com.bms.rabbit.features.profile

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bms.rabbit.BR
import com.bms.rabbit.Router
import com.bms.rabbit.entities.User
import com.bms.rabbit.features.auth.AuthDbDataSource
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 11.10.2018.

class ProfileViewModel(private val authDbDataSource: AuthDbDataSource,
                       private val router: Router) : BaseObservable() {

    @get:Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var mail = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mail)
        }

    @get:Bindable
    var groupName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.groupName)
        }

    private lateinit var user: User

    init {
        loadProfile()
    }

    private fun loadProfile() {
        authDbDataSource.user
                .subscribeOn(Schedulers.io())
                .subscribe({
                    user = it!!
                    setupContent(user)
                }, {})

    }

    private fun setupContent(user: User) {
        name = user.name
        mail = user.mail
        groupName = user.code
    }

    fun logout(){
        authDbDataSource.removeUser()
        router.clearStack()
        router.openAuth()
    }
}
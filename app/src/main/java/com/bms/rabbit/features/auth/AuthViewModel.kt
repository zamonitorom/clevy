package com.bms.rabbit.features.auth

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.bms.rabbit.BR
import com.bms.rabbit.entities.NewUser
import com.bms.rabbit.entities.UserResponse
import com.bms.rabbit.features.LoaderViewModel
import com.bms.rabbit.tools.Messenger
import com.bms.rabbit.tools.StringContainer
import com.bmsoftware.sense2beat.Router
import com.crashlytics.android.Crashlytics
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Created by Konstantin on 29.08.2018.

class AuthViewModel(private val router: Router, private val authRepository: AuthRepository, private val messenger: Messenger) : BaseObservable() {
    val loaderViewModel = LoaderViewModel({})
    val descError = "Не является адресом электронной почты"
    @get:Bindable
    var errorMail = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMail)
        }
    val name = StringContainer()
    val mail = object : StringContainer() {
        override fun set(value: String) {
            errorMail = if (value != "") {
                !isValidEmailAddress(value)
            } else {
                false
            }
            super.set(value)
        }
    }
    val code = StringContainer()

    private fun isValidEmailAddress(email: String): Boolean {
        val ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = java.util.regex.Pattern.compile(ePattern)
        val m = p.matcher(email)
        return m.matches()
    }

    init {
//        mail.set("223sdf@mail.ru")
//        name.set("akhmed228")
//        code.set("qwe")
    }

    fun sendData() {
        if (!errorMail) {
            loaderViewModel.startLoading()
            authRepository.createUser(NewUser(name.get(), mail.get(), code.get()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        loaderViewModel.finishLoading(true)
                        router.openMain()
                    }, {
                        loaderViewModel.finishLoading(false)
                        if (it.localizedMessage != null) {
                            messenger.showSystemMessage(it.localizedMessage)
                        } else {
                            messenger.showSystemMessage("Не удалось")
                        }
                    })
        } else {
            messenger.showSystemMessage("Введите правильный адрес электронной почты")
        }

    }

}
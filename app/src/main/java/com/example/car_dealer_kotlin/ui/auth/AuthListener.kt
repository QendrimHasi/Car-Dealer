package com.example.car_dealer_kotlin.ui.auth

import com.example.car_dealer_kotlin.data.db.entities.User
import com.example.car_dealer_kotlin.data.network.responses.Authresponse

interface AuthListener {
    fun  onSuccess(user: User?)
    fun onFailure(message:String)
    fun  onSuccessSignup(authresponse: Authresponse?)


}
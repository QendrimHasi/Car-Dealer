package com.example.car_dealer_kotlin.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.car_dealer_kotlin.data.repositories.UserRepository
@Suppress("UNCHACKED_CAST")
class AuthViewModelFactory(
    private val userRepository: UserRepository
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(userRepository) as T
    }
}
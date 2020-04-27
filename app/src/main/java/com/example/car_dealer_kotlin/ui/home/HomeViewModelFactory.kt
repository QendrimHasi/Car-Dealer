package com.example.car_dealer_kotlin.ui.home

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.car_dealer_kotlin.Application
import com.example.car_dealer_kotlin.data.repositories.ClientRepository
import com.example.car_dealer_kotlin.data.repositories.UserRepository
@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: ClientRepository,
    private val userRepository: UserRepository,
    private val context: Context
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository,userRepository,context) as T
    }
}
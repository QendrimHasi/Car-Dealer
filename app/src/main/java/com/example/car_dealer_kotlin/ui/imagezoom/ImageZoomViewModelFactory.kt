package com.example.car_dealer_kotlin.ui.imagezoom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.car_dealer_kotlin.data.repositories.ClientRepository
import com.example.car_dealer_kotlin.data.repositories.UserRepository
@Suppress("UNCHECKED_CAST","UNCHECKED_CAST")
class ImageZoomViewModelFactory(
    private val repository: ClientRepository,
    private val repositoryuser: UserRepository
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageZoomViewModel(repository,repositoryuser) as T
    }
}
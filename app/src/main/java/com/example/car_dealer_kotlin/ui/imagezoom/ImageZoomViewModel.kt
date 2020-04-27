package com.example.car_dealer_kotlin.ui.imagezoom

import android.content.Context
import androidx.lifecycle.*
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.data.db.entities.User
import com.example.car_dealer_kotlin.data.repositories.ClientRepository
import com.example.car_dealer_kotlin.data.repositories.UserRepository
import com.example.car_dealer_kotlin.util.lazyDeferres
import kotlinx.coroutines.Deferred

class ImageZoomViewModel(
    private  val repository:ClientRepository,
    private val repositoryuser: UserRepository
):ViewModel() {

    fun getLogedInUser()=repositoryuser.getUser()

    fun deleteimage(clientid:Int,imageid:Int)=repository.deleteimage(clientid,imageid)

}
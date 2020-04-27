package com.example.car_dealer_kotlin.data.repositories

import com.example.car_dealer_kotlin.data.db.AppDatabase
import com.example.car_dealer_kotlin.data.db.entities.User
import com.example.car_dealer_kotlin.data.network.SafeApiRequest
import com.example.car_dealer_kotlin.data.network.UserserviceAuth
import com.example.car_dealer_kotlin.data.network.responses.Authresponse
import com.example.car_dealer_kotlin.data.preferences.PreferencProvider
import com.example.car_dealer_kotlin.util.Coroutines

class UserRepository(
    private  val userserviceAuth: UserserviceAuth,
    private val db: AppDatabase,
    private val preferenc: PreferencProvider
) :SafeApiRequest(){

    suspend fun userLogin(email:String, password:String): User {
        return apiRequest{userserviceAuth.userLogin(email,password)}
    }

    suspend fun userSignup(email: String,password: String,name: String): Authresponse {
        return apiRequest{userserviceAuth.userSignup(email,password,name)}
    }
    suspend fun saveUser(user: User)=db.getUserDao().upsert(user)

    fun getUser()=db.getUserDao().getuser()

    fun logoutUser(){
        Coroutines.io{
            db.getUserDao().deleteUser()
        }
        preferenc.deleteToken()

    }
}
package com.example.car_dealer_kotlin.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.car_dealer_kotlin.data.db.AppDatabase
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.data.network.SafeApiRequest
import com.example.car_dealer_kotlin.data.network.UserserviceAuth
import com.example.car_dealer_kotlin.data.preferences.PreferencProvider
import com.example.car_dealer_kotlin.util.ApiException
import com.example.car_dealer_kotlin.util.Coroutines
import com.example.car_dealer_kotlin.util.NoInternetException
import com.example.car_dealer_kotlin.util.NoSuchFileOrDirectoryException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class ClientRepository (
    private val usersevice: UserserviceAuth,
    private val db: AppDatabase,
    private val preferences: PreferencProvider
):SafeApiRequest(){
    private val clients= MutableLiveData<List<Client>>()
    init {
        clients.observeForever{
            saveClients(it)
        }
    }

    private suspend fun fetchClients(){
        val response = apiRequest{
            usersevice.getClient(preferences.getToken()!!)
        }
        clients.postValue(response.clients)
    }
    suspend fun getClient():LiveData<List<Client>>{
        return withContext(Dispatchers.IO){
            try{
                fetchClients()
            }catch (e:NoInternetException){
            }catch (e:ApiException){}

            db.getClienDao().getclientes()
        }
    }

    fun getClientById(id: Int):LiveData<Client>{
        Coroutines.io{
            try{
                val response=apiRequest{
                    usersevice.getClientById(preferences.getToken()!!,id)
                }
                db.getClienDao().saveClient(response.client)
            }catch (e:NoInternetException){
            }catch (e:ApiException){}
        }
        return db.getClienDao().getclientById(id)
    }

    fun getClientWithFilter(startDate:String,endaDate:String){
        Coroutines.io{
            try {
                val response=apiRequest {
                    usersevice.getClientWithFilter(preferences.getToken()!!,startDate,endaDate)
                }
                db.getClienDao().deleteAllClients()
                response.clients?.let {
                    db.getClienDao().saveallClients(it)
                }

            }catch (e:NoInternetException){
            }catch (e:ApiException){}
        }
    }

    fun deleteimage(clientId:Int,imageId:Int):Boolean{
        var flag=false
        Coroutines.io{
            try {
                val response=apiRequest{
                    usersevice.deleteImage(preferences.getToken()!!,clientId,imageId)
                }
                db.getClienDao().saveClient(response.client)
                flag=true
            }catch (e:NoInternetException){
            }catch (e: NoSuchFileOrDirectoryException){
            }catch (e:ApiException){}
        }
        return flag
    }

    fun uploadImage(clientId:Int,immagine: MultipartBody.Part){
        Coroutines.io{
            try {
                val response=apiRequest{
                    usersevice.uploadImage(preferences.getToken()!!,clientId,immagine)
                }
                db.getClienDao().saveClient(response.client)

            }catch (e:NoInternetException){
            }catch (e:ApiException){}
        }
    }
    private fun saveClients(clients: List<Client>){
        Coroutines.io{
            db.getClienDao().saveallClients(clients)
        }
    }
}
package com.example.car_dealer_kotlin.data.network

import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.data.db.entities.User
import com.example.car_dealer_kotlin.data.network.responses.Authresponse
import com.example.car_dealer_kotlin.data.network.responses.Clientresponse
import com.example.car_dealer_kotlin.data.network.responses.SingleClientresponse
import okhttp3.OkHttpClient
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import okhttp3.MultipartBody


interface UserserviceAuth {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(
        @Field("email") email:String,
        @Field("password") pasword:String
    ): Response<User>

    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun userSignup(
        @Field("email") email: String,
        @Field("password") pasword: String,
        @Field("name") name: String
    ):Response<Authresponse>

    //@Headers("Content-Type: application/json")
    @GET("clienti")
    suspend fun getClient(
        @Header("Authorization") token: String
    ):Response<Clientresponse>

    @GET("clienti/{clientid}")
    suspend fun getClientById(
        @Header("Authorization") token: String,
        @Path("clientid") clientid: Int
    ):Response<SingleClientresponse>

    @GET("clienti")
    suspend fun getClientWithFilter(
        @Header("Authorization") token: String,
        @Query("startDate")  startDate:String,
        @Query("endDate")  endDate:String
    ):Response<Clientresponse>


    @DELETE("remove/{client_id}/{image_id}")
    suspend fun deleteImage(
        @Header("Authorization") token: String,
        @Path("client_id") clientid: Int,
        @Path("image_id") imageid: Int
    ):Response<SingleClientresponse>

    @Multipart
    @POST("upload/{clientid}")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("clientid") clientid: Int,
        @Part  immagine: MultipartBody.Part
    ):Response<SingleClientresponse>


    companion object{
        operator fun  invoke(networkConnectionInteerception: NetworkConnectionInteerception):UserserviceAuth{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInteerception)
                .build()
            return Retrofit.Builder().baseUrl("http://api-task.draft2017.com/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserserviceAuth::class.java)
        }
    }
}
package com.example.car_dealer_kotlin.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.car_dealer_kotlin.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInteerception(
    context: Context
) : Interceptor{

    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInterneteAvailable()){
            throw NoInternetException("No internet")
        }
        return chain.proceed(chain.request())
    }

    private fun isInterneteAvailable():Boolean{
        val connectivityManager= applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it !=null && it.isConnected
        }
    }

}
package com.example.car_dealer_kotlin.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
    private const val KEY="token"
class PreferencProvider(
    context:Context
) {
    private val appContext=context.applicationContext

    private val preferences:SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun saveToken(token:String){
        preferences.edit().putString(KEY,token).apply()
    }

    fun getToken():String?{
        return preferences.getString(KEY,null)
    }
    fun deleteToken(){
        preferences.edit().remove(KEY).apply()
    }

}
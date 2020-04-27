package com.example.car_dealer_kotlin.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.car_dealer_kotlin.data.repositories.UserRepository
import com.example.car_dealer_kotlin.util.ApiException
import com.example.car_dealer_kotlin.util.Coroutines
import com.example.car_dealer_kotlin.util.NoInternetException

class AuthViewModel(
     private val repository: UserRepository
): ViewModel(){
    var email : String? =null
    var password : String?= null
    var name: String?=null
    var confirmpass :String?=null

    var authListener: AuthListener?= null

    fun getLogedInUser()=repository.getUser()

    fun onButtonClick(view : View){
        if (name.isNullOrEmpty()){
            authListener?.onFailure("Name is required")
            return
        }
        if (email.isNullOrEmpty()){
            authListener?.onFailure("Email is required")
            return
        }
        if (password.isNullOrEmpty()){
            authListener?.onFailure("Password is required")
            return
        }
        if (confirmpass.isNullOrEmpty()){
            authListener?.onFailure("Confrim Password is required")
            return
        }
        if (!password.equals(confirmpass)){
            authListener?.onFailure("Password and Confirm Password dont matches")
            return
        }
        Coroutines.main{
            try {
                val authresponse= repository.userSignup(email!!,password!!,name!!)
                authresponse.let {
                    authListener?.onSuccessSignup(it)
                    return@main
                }
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }

        }

    }

    fun onSignup(view: View){
        Intent(view.context,SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLoginButtonClick(view : View){
        if (email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Email and Password")
            return
        }
        Coroutines.main{
            try {
                val authresponse= repository.userLogin(email!!,password!!)
                authresponse.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }

        }

    }
}
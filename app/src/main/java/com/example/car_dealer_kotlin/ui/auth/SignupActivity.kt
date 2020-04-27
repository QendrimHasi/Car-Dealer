package com.example.car_dealer_kotlin.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.data.db.entities.User
import com.example.car_dealer_kotlin.data.network.responses.Authresponse
import com.example.car_dealer_kotlin.databinding.ActivityLoginBinding
import com.example.car_dealer_kotlin.databinding.ActivitySignupBinding
import com.example.car_dealer_kotlin.ui.home.HomeActivity
import com.example.car_dealer_kotlin.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(),AuthListener, KodeinAware {


    override val kodein by kodein()

    private  val factory : AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindig: ActivitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        bindig.viewmodel=viewModel

        viewModel.authListener=this
        viewModel.getLogedInUser().observe(this, Observer {authlistener ->
            if (authlistener !=null){
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                onBackPressed()
            }

        })
    }

    override fun onSuccess(user: User?) {}

    override fun onFailure(message: String) {
        toast(message)
    }

    override fun onSuccessSignup(authresponse: Authresponse?) {
        toast(authresponse?.message.toString())
    }
}

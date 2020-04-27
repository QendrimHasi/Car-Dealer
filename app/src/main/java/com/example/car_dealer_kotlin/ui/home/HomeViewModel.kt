package com.example.car_dealer_kotlin.ui.home

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.data.repositories.ClientRepository
import com.example.car_dealer_kotlin.data.repositories.UserRepository
import com.example.car_dealer_kotlin.ui.auth.LoginActivity
import com.example.car_dealer_kotlin.ui.auth.SignupActivity
import com.example.car_dealer_kotlin.util.lazyDeferres
import kotlinx.coroutines.Deferred
import java.util.*

class HomeViewModel(
    private  val repository:ClientRepository,
    private val userRepository: UserRepository,
    private var context: Context
):ViewModel() {
    private val data:String=context.resources.getString(R.string.date)
    val dateone: ObservableField<String> = ObservableField<String>(data)
    val datetwo: ObservableField<String> = ObservableField<String>(data)

    fun getClieant(): Deferred<LiveData<List<Client>>> {
        val clients by lazyDeferres(){
            repository.getClient()
        }
        return clients
    }

    fun getClientWithFilter(startDate:String,endaDate:String){
        repository.getClientWithFilter(startDate,endaDate)
    }

    fun onclickdateone(viewt: View){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(viewt.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateone.set(""" $year-${monthOfYear + 1}-$dayOfMonth """)
            check()
        }, year, month, day)
        dpd.show()
    }
    fun onclickdatetwo(view: View){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(view.context,DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            datetwo.set(""" $year-${monthOfYear + 1}-$dayOfMonth """)
            check()
        }, year, month, day)
        dpd.show()
    }

    fun check(){
        if (dateone.get()!=data && datetwo.get()!=data){
          getClientWithFilter(dateone.get().toString(),datetwo.get().toString())
        }
    }

    fun logout(view: View){
        userRepository.logoutUser()
        Intent(view.context, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            view.context.startActivity(it)
        }
    }
}
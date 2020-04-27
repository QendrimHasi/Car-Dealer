package com.example.car_dealer_kotlin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.databinding.ActivityHomeBinding
import com.example.car_dealer_kotlin.ui.adapter.HomeAdapter
import com.example.car_dealer_kotlin.ui.client.ClientInfoActivity
import com.example.car_dealer_kotlin.util.Coroutines
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindig: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        bindig.homeviewmodel = viewModel
        Coroutines.main {
            viewModel.getClieant().await().observe(this, Observer {
                initRecyclerView(it)
            })
        }
    }

    private fun initRecyclerView(clients: List<Client>) {
        val homeAdapter = HomeAdapter(clients)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
        recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Intent(applicationContext, ClientInfoActivity::class.java).also {
                    it.putExtra("Clientid", clients[position].id)
                    startActivity(it)
                }
            }
        })
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)
            }
            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener({
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                })
            }
        })
    }
}
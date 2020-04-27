package com.example.car_dealer_kotlin.ui.imagezoom

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.databinding.ActivityImageZoomBinding
import kotlinx.android.synthetic.main.activity_image_zoom.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ImageZoomActivity : AppCompatActivity(),KodeinAware {
    override val kodein by kodein()
    private  val factory : ImageZoomViewModelFactory by instance()
    var id=-1
    var url=""
    var clientid=-1
    private lateinit var viewModel:ImageZoomViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind:ActivityImageZoomBinding= DataBindingUtil.setContentView(this,R.layout.activity_image_zoom)
         viewModel = ViewModelProviders.of(this,factory).get(ImageZoomViewModel::class.java)
        bind.viewmodel=viewModel

         id= intent.getIntExtra("id",0)
         url= intent.getStringExtra("url")
         clientid= intent.getIntExtra("clientid",0)

        Glide.with(applicationContext).load(url).into(imagezoom)
        btn2.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                viewModel.deleteimage(clientid,id)
                onBackPressed()
            }
        })
    }

    fun onback(view: View){
        onBackPressed()
    }
}

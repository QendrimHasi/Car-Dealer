package com.example.car_dealer_kotlin.ui.client

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.GridLayoutManager
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.databinding.ActivityClientInfoBinding
import com.example.car_dealer_kotlin.ui.adapter.ClientInfoAdapter
import com.example.car_dealer_kotlin.ui.adapter.imagedata
import com.example.car_dealer_kotlin.util.Coroutines
import kotlinx.android.synthetic.main.activity_client_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File

class ClientInfoActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: ClientInfoViewModelFactory by instance()
    private lateinit var viewModel: ClientInfoViewModel

    private var clientid = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bind: ActivityClientInfoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_client_info)
        viewModel = ViewModelProviders.of(this, factory).get(ClientInfoViewModel::class.java)
        bind.viewmodel = viewModel
        clientid = getIntent().getIntExtra("Clientid", 0)

        Coroutines.main{
            viewModel.getClient(clientid).observe(this, Observer {
                val imageadapter = ClientInfoAdapter(it, this, viewModel)
                recyclerView.apply {
                    layoutManager = GridLayoutManager(applicationContext, 3)
                    setHasFixedSize(true)
                    adapter = imageadapter
                }
            })
        }
    }

    fun btnupload(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        } else {
            val arr = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, arr, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            viewModel.uploadImage(data?.data!!)
        }
    }
}




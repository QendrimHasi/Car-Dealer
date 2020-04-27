package com.example.car_dealer_kotlin.ui.client

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.loader.content.CursorLoader
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.data.repositories.ClientRepository
import com.example.car_dealer_kotlin.data.repositories.UserRepository
import com.example.car_dealer_kotlin.ui.adapter.imagedata
import com.example.car_dealer_kotlin.ui.auth.LoginActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ClientInfoViewModel(
    private  val repository:ClientRepository,
    private val userRepository: UserRepository,
    private val context: Context
):ViewModel() {
    lateinit var mutableImageList:MutableLiveData<ArrayList<imagedata>>
    var imageurls: ArrayList<imagedata> = ArrayList()

    var client: ObservableField<Client> = ObservableField<Client>(
        Client(-1,"","",""
            ,"","","","",""))


    fun getClient(id: Int):LiveData<ArrayList<imagedata>> {
        mutableImageList= MutableLiveData()
        repository.getClientById(id).observeForever{
            it.data_consegna=it.data_consegna.substring(0,10)
            it.data_rientro=it.data_rientro.substring(0,10)
            client.set(it)
            imageurls.clear()
            if (it?.immagini != null && it?.immagini!!.length > 6) {
                val length: Int = it?.immagini!!.length
                val images: String = it.immagini.substring(1, length - 1)
                val imgarr = images.split(",")
                val url: String = context.resources.getString(R.string.imgpath)
                for (i in imgarr.indices) {
                    var path: String = imgarr[i]
                    if (images.contains(":".toRegex())) {
                        var pa = ""
                        val arr =
                            path.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val ids = arr[0].substring(1, arr[0].length - 1)
                        val id = Integer.parseInt(ids)
                        pa = arr[1]
                        pa = pa.substring(1, pa.length - 1)
                        pa = pa.replace("\\/", "///")
                        val imageData = imagedata(id, url + pa, it.id)
                        imageurls.add(imageData)
                    } else {
                        path = imgarr[i].substring(2, imgarr[i].length - 1)
                        path = path.replace("\\/", "///")
                        val imageData = imagedata(i, url + path, it.id)
                        imageurls.add(imageData)
                    }
                }
            }
            mutableImageList.postValue(imageurls)

        }
        return mutableImageList
    }

    fun deleteimage(clientid:Int,imageid:Int)=repository.deleteimage(clientid,imageid)

    fun uploadImage(imageuri: Uri){
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val loader= CursorLoader(context, imageuri, projection, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        var ImagePath= cursor.getString(column_index)
        val file = File(ImagePath)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("immagine", file.getName(), requestBody)
        if (client.get()!!.id != -1){
            repository.uploadImage(client.get()!!.id,body)
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
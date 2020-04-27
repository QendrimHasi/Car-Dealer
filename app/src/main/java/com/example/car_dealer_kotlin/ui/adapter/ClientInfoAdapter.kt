package com.example.car_dealer_kotlin.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.ui.client.ClientInfoViewModel
import com.example.car_dealer_kotlin.ui.imagezoom.ImageZoomActivity
import kotlinx.android.synthetic.main.popup.*

class ClientInfoAdapter(val imageDatas:ArrayList<imagedata>,val context: Context,val viewmodel:ClientInfoViewModel):RecyclerView.Adapter<ClientInfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.imagecard,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  imageDatas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url:String=imageDatas[position].path
        val id:Int=imageDatas[position].index
        val clientid:Int=imageDatas[position].clientid
        Glide.with(context).load(url)
            .into(holder.image)

        holder.image.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                Intent(context, ImageZoomActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    it.putExtra("id",id)
                    it.putExtra("url",url)
                    it.putExtra("clientid",clientid)
                    startActivity(context,it,null)
                }
            }

        })
        holder.btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                showdialog(clientid,id)
            }

        })
    }

    private fun showdialog( clientid: Int, imageid: Int) {
        val  dialogview : View=LayoutInflater.from(context).inflate(R.layout.popup,null)
        val builder=AlertDialog.Builder(context).setView(dialogview)
        val dialog=builder.show()
        dialog.btnno.setOnClickListener{
            dialog.dismiss()
        }
        dialog.btnsi.setOnClickListener{
            viewmodel.deleteimage(clientid,imageid)
            dialog.dismiss()
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image=itemView.findViewById(R.id.imageView) as ImageView
        val btn=itemView.findViewById(R.id.txtimg) as TextView

    }
}
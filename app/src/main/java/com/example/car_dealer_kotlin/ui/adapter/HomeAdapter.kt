package com.example.car_dealer_kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.car_dealer_kotlin.R
import com.example.car_dealer_kotlin.data.db.entities.Client

class HomeAdapter(val clients:List<Client>):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_client,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  clients.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val client:Client=clients[position]
        holder.textViewCode.text=client.code
        holder.textViewdelivery_date.text=client.data_consegna.substring(0,10)
        holder.textViewreturn_date.text=client.data_rientro.substring(0,10)
        holder.textViewname.text=client.nome_cliente
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCode=itemView.findViewById(R.id.code) as TextView
        val textViewdelivery_date=itemView.findViewById(R.id.delivery_date) as TextView
        val textViewreturn_date=itemView.findViewById(R.id.return_date) as TextView
        val textViewname=itemView.findViewById(R.id.name) as TextView

    }
}
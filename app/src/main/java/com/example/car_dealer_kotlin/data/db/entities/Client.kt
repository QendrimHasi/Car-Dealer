package com.example.car_dealer_kotlin.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client (
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val code: String,
    var data_consegna: String,
    var data_rientro: String,
    val nome_cliente: String,
    val cognome_cliente: String,
    val km: String,
    val targa: String,
    val immagini: String
)
package com.example.car_dealer_kotlin.data.network.responses

import com.example.car_dealer_kotlin.data.db.entities.Client

class Clientresponse (
    val message : String?=null,
    val clients: List<Client>?=null
)


package com.example.car_dealer_kotlin.data.network.responses
data class Authresponse(
 var access_token: String?=null,
 var  token_type: String?=null,
 var  expires_at: Int?= null,
 var message:String?=null
)

package com.example.car_dealer_kotlin.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID=0
@Entity
data class User (
    var access_token: String?=null,
    var  token_type: String?=null,
    var  expires_at: Int?= null,
    var  user_id: Int?= null,
    var user_email:String?=null
){
    @PrimaryKey(autoGenerate = false)
    var uid : Int= CURRENT_USER_ID
}
package com.example.car_dealer_kotlin.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.car_dealer_kotlin.data.db.entities.CURRENT_USER_ID
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.data.db.entities.User
@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveallClients(client: List<Client>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveallClients2(client: List<Client>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveClient(client: Client?)

    @Query("SELECT * FROM Client")
    fun getclientes(): LiveData<List<Client>>

    @Query("DELETE FROM Client")
    fun deleteAllClients()

    @Query("SELECT * FROM Client WHERE ID= :idc")
    fun getclientById(idc:Int): LiveData<Client>
}
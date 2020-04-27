package com.example.car_dealer_kotlin.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.car_dealer_kotlin.data.db.entities.Client
import com.example.car_dealer_kotlin.data.db.entities.User

@Database(entities = [User::class,Client::class],version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getUserDao():UserDao
    abstract fun getClienDao():ClientDao

    companion object{
        @Volatile
        private var instance : AppDatabase?=null
        private  val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance=it
            }
        }
        private  fun buildDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}
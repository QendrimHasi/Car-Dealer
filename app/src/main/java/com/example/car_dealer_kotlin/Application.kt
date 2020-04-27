package com.example.car_dealer_kotlin

import android.app.Application
import com.example.car_dealer_kotlin.data.db.AppDatabase
import com.example.car_dealer_kotlin.data.network.NetworkConnectionInteerception
import com.example.car_dealer_kotlin.data.network.UserserviceAuth
import com.example.car_dealer_kotlin.data.preferences.PreferencProvider
import com.example.car_dealer_kotlin.data.repositories.ClientRepository
import com.example.car_dealer_kotlin.data.repositories.UserRepository
import com.example.car_dealer_kotlin.ui.auth.AuthViewModelFactory
import com.example.car_dealer_kotlin.ui.client.ClientInfoViewModelFactory
import com.example.car_dealer_kotlin.ui.home.HomeViewModelFactory
import com.example.car_dealer_kotlin.ui.imagezoom.ImageZoomViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class Application : Application(),KodeinAware{
    override val kodein= Kodein.lazy {
        import(androidXModule(this@Application))
        bind() from singleton { NetworkConnectionInteerception(instance()) }
        bind() from  singleton { UserserviceAuth(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferencProvider(instance()) }
        bind() from singleton { UserRepository(instance(),instance(),instance()) }
        bind() from singleton { ClientRepository(instance(),instance(),instance()) }
        bind() from provider {AuthViewModelFactory(instance())}
        bind() from provider {HomeViewModelFactory(instance(),instance(),instance()) }
        bind() from provider {ClientInfoViewModelFactory(instance(),instance(),instance()) }
        bind() from provider { ImageZoomViewModelFactory(instance(),instance()) }
    }

}
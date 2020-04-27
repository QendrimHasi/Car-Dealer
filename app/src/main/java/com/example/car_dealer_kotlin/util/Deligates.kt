package com.example.car_dealer_kotlin.util

import kotlinx.coroutines.*

fun<T> lazyDeferres(block :suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async ( start= CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}
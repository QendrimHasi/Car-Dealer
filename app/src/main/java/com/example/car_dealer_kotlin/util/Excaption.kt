package com.example.car_dealer_kotlin.util

import java.io.IOException

class ApiException(message:String):IOException(message)
class NoInternetException(message:String):IOException(message)
class NoSuchFileOrDirectoryException(message: String):IOException(message)
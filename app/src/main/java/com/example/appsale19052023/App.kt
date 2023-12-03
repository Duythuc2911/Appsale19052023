package com.example.appsale19052023

import android.app.Application
import com.example.appsale19052023.data.model.Product

class App : Application(){
    val cartItem = mutableListOf<Product>()

    companion object{
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
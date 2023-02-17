package com.example.cryptoapp.app

import android.app.Application
import android.content.Context
import com.example.cryptoapp.data.database.AppDatabase

class CoinApplication: Application() {
    val database by lazy {
        AppDatabase.getInstance(instance)
    }
    lateinit var instance: Context

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}

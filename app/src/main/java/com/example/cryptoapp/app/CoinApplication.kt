package com.example.cryptoapp.app

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.di.DaggerAppComponent
import com.example.cryptoapp.workers.RefreshCoinsInfoWorkerFactory

class CoinApplication : Application(), Configuration.Provider {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshCoinsInfoWorkerFactory(
                    ApiFactory.apiService,
                    AppDatabase.getInstance(this).coinPriceInfoDao(),
                    CoinsMapper()
                )
            )
            .build()
    }
}

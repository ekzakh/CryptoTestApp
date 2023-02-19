package com.example.cryptoapp.app

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.di.DaggerAppComponent
import com.example.cryptoapp.workers.RefreshCoinsInfoWorkerFactory
import javax.inject.Inject

class CoinApplication : Application(), Configuration.Provider {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    @Inject
    lateinit var workerFactory: RefreshCoinsInfoWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}

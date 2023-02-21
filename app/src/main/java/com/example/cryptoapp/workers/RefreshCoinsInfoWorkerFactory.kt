package com.example.cryptoapp.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.network.ApiService
import javax.inject.Inject
import javax.inject.Provider

class RefreshCoinsInfoWorkerFactory @Inject constructor(
   private val workerFactoryProviders: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = when (workerClassName) {
        RefreshCoinsInfoWorker::class.qualifiedName -> {
            val factory = workerFactoryProviders[RefreshCoinsInfoWorker::class.java]?.get()
            factory?.create(appContext, workerParameters)
        }
        else -> throw IllegalArgumentException("Unknown worker class $workerClassName")
    }
}

package com.example.cryptoapp.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.network.ApiService
import javax.inject.Inject

class RefreshCoinsInfoWorkerFactory @Inject constructor(
    private val apiService: ApiService,
    private val coinsDao: CoinPriceInfoDao,
    private val mapper: CoinsMapper
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshCoinsInfoWorker(apiService, coinsDao, mapper, appContext, workerParameters)
    }
}

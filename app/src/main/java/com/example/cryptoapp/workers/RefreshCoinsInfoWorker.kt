package com.example.cryptoapp.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay

class RefreshCoinsInfoWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val apiService: ApiService = ApiFactory.apiService
    private val coinDao: CoinPriceInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val mapper: CoinsMapper = CoinsMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val data = apiService.getTopCoinsInfo(limit = 50)
                val coinsName = mapper.mapRawDataToNames(data)
                coinsName.forEach { name ->
                    val priceData = ApiFactory.apiService.getFullPriceList(fSyms = name)
                    val coinInfoDto = mapper.mapPriceInfoRawDataToDto(priceData)
                    val dbModels = mapper.mapDtoToDbModelList(coinInfoDto)
                    coinDao.insertPriceList(dbModels)
                }
            } catch (e: Exception) {
                Log.e(NAME, "${e.message}")
            }
            delay(FREQUENCY_UPDATE_MS)
        }
    }

    companion object {
        private const val FREQUENCY_UPDATE_MS = 10_000L
        const val NAME = "RefreshCoinsInfoWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshCoinsInfoWorker>().build()
        }
    }
}

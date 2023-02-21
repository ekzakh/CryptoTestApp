package com.example.cryptoapp.workers

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshCoinsInfoWorker(
    private val apiService: ApiService,
    private val coinsDao: CoinPriceInfoDao,
    private val mapper: CoinsMapper,
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val data = apiService.getTopCoinsInfo(limit = 50)
                val coinsName = mapper.mapRawDataToNames(data)
                coinsName.forEach { name ->
                    val priceData = ApiFactory.apiService.getFullPriceList(fSyms = name)
                    val coinInfoDto = mapper.mapPriceInfoRawDataToDto(priceData)
                    val dbModels = mapper.mapDtoToDbModelList(coinInfoDto)
                    coinsDao.insertPriceList(dbModels)
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

    class Factory @Inject constructor(
        private val apiService: ApiService,
        private val coinsDao: CoinPriceInfoDao,
        private val mapper: CoinsMapper,
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshCoinsInfoWorker(
                apiService,
                coinsDao,
                mapper,
                context,
                workerParameters
            )
        }

    }
}

package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.domain.CoinsRepository
import com.example.cryptoapp.domain.entity.CoinInfo
import com.example.cryptoapp.workers.RefreshCoinsInfoWorker
import javax.inject.Inject

class CoinsRepositoryImpl @Inject constructor(
    private val coinDao: CoinPriceInfoDao,
    private val mapper: CoinsMapper,
    private val application: Application
) : CoinsRepository {

    override fun getCoinsInfoList(): LiveData<List<CoinInfo>> {
        val dbModelLiveData = coinDao.getPriceList()
        return Transformations.map(dbModelLiveData) { dbModel ->
            return@map mapper.mapDbModelToEntityList(dbModel)
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        val dbModelLiveData = coinDao.getPriceInfoAboutCoin(fromSymbol)
        return Transformations.map(dbModelLiveData) { dbModel ->
            return@map mapper.mapDbModelToEntity(dbModel)
        }
    }

    override suspend fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshCoinsInfoWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshCoinsInfoWorker.makeRequest()
        )
    }

}

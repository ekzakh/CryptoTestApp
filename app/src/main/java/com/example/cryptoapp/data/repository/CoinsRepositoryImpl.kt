package com.example.cryptoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinsRepository
import com.example.cryptoapp.domain.entity.CoinInfo

class CoinsRepositoryImpl(
    private val coinDao: CoinPriceInfoDao,
    private val mapper: CoinsMapper
) :
    CoinsRepository {
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
        val data = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
        val coinsName = mapper.mapRawDataToNames(data)
        coinsName.forEach { name ->
            val priceData = ApiFactory.apiService.getFullPriceList(fSyms = name)
            val coinInfoDto = mapper.mapPriceInfoRawDataToDto(priceData)
            val dbModels = mapper.mapDtoToDbModelList(coinInfoDto)
            coinDao.insertPriceList(dbModels)
        }
    }

}

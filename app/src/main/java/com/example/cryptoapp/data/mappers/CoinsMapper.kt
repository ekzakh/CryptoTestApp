package com.example.cryptoapp.data.mappers

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.models.CoinInfoDto
import com.example.cryptoapp.data.network.models.CoinInfoListOfData
import com.example.cryptoapp.data.network.models.CoinPriceInfoRawData
import com.example.cryptoapp.domain.entity.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinsMapper {

    fun mapRawDataToNames(source: CoinInfoListOfData): List<String> {
        val namesList = mutableListOf<String>()
        source.data?.map { it.coinName?.name?.let { name -> namesList.add(name) } }
        return namesList
    }

    fun mapPriceInfoRawDataToDto(source: CoinPriceInfoRawData): List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>()
        val jsonObject = source.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapDtoToDbModelList(source: List<CoinInfoDto>): List<CoinInfoDbModel> {
        return source.map { coinDto ->
            CoinInfoDbModel(
                price = coinDto.price ?: "",
                lastUpdate = coinDto.lastUpdate,
                lowDay = coinDto.lowDay ?: "",
                highDay = coinDto.highDay ?: "",
                lastMarket = coinDto.lastMarket ?: "",
                fromSymbol = coinDto.fromSymbol,
                toSymbol = coinDto.toSymbol ?: "",
                imageUrl = getFullImageUrl(coinDto.imageUrl ?: "")
            )
        }
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            price = dbModel.price,
            lastUpdate = getFormattedTime(dbModel.lastUpdate),
            lowDay = dbModel.lowDay,
            hightDay = dbModel.highDay,
            lastMarket = dbModel.lastMarket,
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            imageUrl = getFullImageUrl(dbModel.imageUrl)
        )
    }

    fun mapDbModelToEntityList(dbModelList: List<CoinInfoDbModel>): List<CoinInfo> {
        return dbModelList.map { dbModel ->
            CoinInfo(
                price = dbModel.price,
                lastUpdate = getFormattedTime(dbModel.lastUpdate),
                lowDay = dbModel.lowDay,
                hightDay = dbModel.highDay,
                lastMarket = dbModel.lastMarket,
                fromSymbol = dbModel.fromSymbol,
                toSymbol = dbModel.toSymbol,
                imageUrl = getFullImageUrl(dbModel.imageUrl)
            )
        }
    }

    private fun getFormattedTime(lastUpdate: Long): String {
        return convertTimestampToTime(lastUpdate)
    }

    private fun getFullImageUrl(imageUrl: String): String {
        return ApiFactory.BASE_IMAGE_URL + imageUrl
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}




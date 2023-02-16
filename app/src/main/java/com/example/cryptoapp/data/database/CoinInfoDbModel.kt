package com.example.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromSymbol: String,
    val price: String,
    val lastUpdate: Long,
    val lowDay: String,
    val highDay: String,
    val lastMarket: String,
    val toSymbol: String,
    val imageUrl: String
) {
}

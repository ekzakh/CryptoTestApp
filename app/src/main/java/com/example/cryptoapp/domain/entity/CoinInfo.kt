package com.example.cryptoapp.domain.entity

data class CoinInfo(
    val name: String,
    val price: Float,
    val lastUpdate: Long,
    val lowDay: Float,
    val hightDay: Float,
    val lastMarket: String,
    val fromSymbol: String,
    val toSymbol: String,
    val imageUrl: String
)

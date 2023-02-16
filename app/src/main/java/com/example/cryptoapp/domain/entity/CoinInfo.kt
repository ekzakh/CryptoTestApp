package com.example.cryptoapp.domain.entity

data class CoinInfo(
    val price: String,
    val lastUpdate: String,
    val lowDay: String,
    val hightDay: String,
    val lastMarket: String,
    val fromSymbol: String,
    val toSymbol: String,
    val imageUrl: String
)

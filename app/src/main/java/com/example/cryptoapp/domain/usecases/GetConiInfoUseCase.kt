package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinsRepository
import com.example.cryptoapp.domain.entity.CoinInfo

class GetConiInfoUseCase(
    private val fromSymbol: String,
    private val repository: CoinsRepository
) {
    operator fun invoke(): CoinInfo = repository.getCoinInfo(fromSymbol)
}

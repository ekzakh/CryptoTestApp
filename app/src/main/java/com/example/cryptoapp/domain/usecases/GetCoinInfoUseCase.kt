package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinsRepository

class GetCoinInfoUseCase(
    private val repository: CoinsRepository
) {
    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}

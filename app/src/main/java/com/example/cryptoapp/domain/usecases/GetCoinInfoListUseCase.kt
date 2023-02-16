package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinsRepository

class GetCoinInfoListUseCase(private val repository: CoinsRepository) {
    operator fun invoke() = repository.getCoinsInfoList()
}

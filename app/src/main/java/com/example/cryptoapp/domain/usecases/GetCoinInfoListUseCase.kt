package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinsRepository
import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(private val repository: CoinsRepository) {
    operator fun invoke() = repository.getCoinsInfoList()
}

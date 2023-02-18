package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinsRepository
import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(
    private val repository: CoinsRepository
) {
    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}

package com.example.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.CoinsRepository
import com.example.cryptoapp.domain.entity.CoinInfo

class GetCoinInfoListUseCase(private val repository: CoinsRepository) {
    operator fun invoke(): LiveData<List<CoinInfo>> = repository.getCoinsInfoList()
}

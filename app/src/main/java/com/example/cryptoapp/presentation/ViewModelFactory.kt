package com.example.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase

class ViewModelFactory(
    private val loadDataUseCase: LoadDataUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            CoinViewModel::class.java -> CoinViewModel(loadDataUseCase, getCoinInfoListUseCase, getCoinInfoUseCase) as T
            else -> throw IllegalArgumentException("$modelClass not found")
        }
}

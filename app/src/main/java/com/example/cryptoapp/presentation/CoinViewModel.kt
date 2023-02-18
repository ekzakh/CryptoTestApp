package com.example.cryptoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.entity.CoinInfo
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase

) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String): LiveData<CoinInfo> {
        return getCoinInfoUseCase.invoke(fSym)
    }

    init {
        viewModelScope.launch { loadDataUseCase.invoke() }
    }

}

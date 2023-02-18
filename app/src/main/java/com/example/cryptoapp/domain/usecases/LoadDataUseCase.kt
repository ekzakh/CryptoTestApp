package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinsRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: CoinsRepository
) {
    suspend operator fun invoke() = repository.loadData()
}

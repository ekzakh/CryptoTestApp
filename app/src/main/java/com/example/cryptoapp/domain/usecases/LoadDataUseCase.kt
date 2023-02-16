package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinsRepository

class LoadDataUseCase(private val repository: CoinsRepository) {
    suspend operator fun invoke() = repository.loadData()
}

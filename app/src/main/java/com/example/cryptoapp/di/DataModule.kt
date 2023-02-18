package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.repository.CoinsRepositoryImpl
import com.example.cryptoapp.domain.CoinsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindCoinsRepository(impl: CoinsRepositoryImpl): CoinsRepository

    companion object {
        @Provides
        fun provideCoinDao(application: Application): CoinPriceInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }
    }
}

package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.data.repository.CoinsRepositoryImpl
import com.example.cryptoapp.domain.CoinsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindCoinsRepository(impl: CoinsRepositoryImpl): CoinsRepository

    companion object {
        @Provides
        @AppScope
        fun provideCoinDao(application: Application): CoinPriceInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @AppScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}

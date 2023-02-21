package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.app.CoinApplication
import com.example.cryptoapp.presentation.CoinDetailActivity
import com.example.cryptoapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [
    DataModule::class,
    ViewModelModule::class,
    WorkersModule::class
])
interface AppComponent {

    fun inject(activity: CoinPriceListActivity)
    fun inject(activity: CoinDetailActivity)
    fun inject(application: CoinApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}

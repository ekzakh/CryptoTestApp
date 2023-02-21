package com.example.cryptoapp.di

import com.example.cryptoapp.workers.ChildWorkerFactory
import com.example.cryptoapp.workers.RefreshCoinsInfoWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkersModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshCoinsInfoWorker::class)
    fun bindCoinWorkerFactory(impl: RefreshCoinsInfoWorker.Factory): ChildWorkerFactory
}

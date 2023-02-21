package com.example.cryptoapp.di

import com.example.cryptoapp.workers.ChildWorkerFactory
import com.example.cryptoapp.workers.RefreshCoinsInfoWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshCoinsInfoWorker::class)
    fun bindRefreshCoinsWorkerFactory(imp: RefreshCoinsInfoWorker.Factory): ChildWorkerFactory
}

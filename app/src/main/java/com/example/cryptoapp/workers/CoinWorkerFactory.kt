package com.example.cryptoapp.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class CoinWorkerFactory @Inject constructor(
    private val workerFactoryProviders: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            RefreshCoinsInfoWorker::class.qualifiedName -> {
                val factory = workerFactoryProviders[RefreshCoinsInfoWorker::class.java]?.get()
                factory?.create(appContext, workerParameters)
            }
            else -> null
        }
    }
}

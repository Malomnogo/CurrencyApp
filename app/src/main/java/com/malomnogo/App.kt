package com.malomnogo

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    init {
        Log.d("dLog", "init block")
    }

    override val workManagerConfiguration = Configuration.Builder()
        .setWorkerFactory(
            EntryPoints.get(
                this,
                HiltWorkerFactoryEntryPoint::class.java
            ).workerFactory()
        ).build()
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HiltWorkerFactoryEntryPoint {
    fun workerFactory(): HiltWorkerFactory
}
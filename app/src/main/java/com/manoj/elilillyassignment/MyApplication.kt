package com.manoj.elilillyassignment

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application(), Configuration.Provider {

    companion object {
        lateinit var appContext: Context
    }

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
       Configuration.Builder()
           .setWorkerFactory(workerFactory)
           .build()


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}
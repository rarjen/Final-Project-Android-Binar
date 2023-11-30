package com.example.finalprojectbinar

import android.app.Application
import com.example.finalprojectbinar.di.KoinModule.dataModule
import com.example.finalprojectbinar.di.KoinModule.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    uiModule
                )
            )
        }
    }
}
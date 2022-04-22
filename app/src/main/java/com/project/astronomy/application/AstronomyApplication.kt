package com.project.astronomy.application

import android.app.Application
import com.project.apod.di.apodModule
import com.project.astronomy.di.mainModule
import com.project.core.di.androidNetworkStatusModule
import com.project.core.di.coreRepositoriesModule
import com.project.core.di.retrofitModule
import com.project.donki.di.solarFlareModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AstronomyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AstronomyApplication)
            modules(listOf(
                retrofitModule,
                coreRepositoriesModule,
                androidNetworkStatusModule,
                apodModule,
                solarFlareModule,
                mainModule
            ))
        }
    }
}
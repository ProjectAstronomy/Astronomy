package com.project.astronomy.application

import android.app.Application
import com.project.apod.di.apodModule
import com.project.core.di.androidNetworkStatusModule
import com.project.core.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AstronomyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AstronomyApplication)
            modules(listOf(retrofitModule, androidNetworkStatusModule, apodModule))
        }
    }
}
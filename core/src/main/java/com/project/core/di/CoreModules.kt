package com.project.core.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.core.domain.BaseInterceptor
import com.project.core.domain.CalendarRepository
import com.project.core.net.AndroidNetworkStatus
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val retrofitModule = module {
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(OkHttpClient.Builder().apply {
            addInterceptor(BaseInterceptor.interceptor)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build())
        .build()

    single { provideRetrofit() }
}

val coreRepositoriesModule = module {
    factory { Calendar.getInstance() }

    factory { CalendarRepository(calendar = get()) }
}

val androidNetworkStatusModule = module {
    single { AndroidNetworkStatus(context = androidContext()) }
}
package com.project.apod.di

import com.project.apod.domain.APODApiService
import com.project.apod.domain.APODRepository
import com.project.apod.usecases.APODUseCase
import com.project.apod.viewmodels.APODViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_APOD_LIST_MODULE = "SCOPE_APOD_MODULE"

val apodModule = module {
    scope(named(SCOPE_APOD_LIST_MODULE)) {
        scoped<APODApiService> { get<Retrofit>().create(APODApiService::class.java) }

        scoped { APODRepository(apodApiService = get()) }

        scoped { APODUseCase(calendarRepository = get(), apodRepository = get()) }

        scoped { APODViewModelFactory(apodUseCase = get()) }
    }
}
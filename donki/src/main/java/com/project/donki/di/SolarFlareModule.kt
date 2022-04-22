package com.project.donki.di

import com.project.donki.domain.SolarFlareApiService
import com.project.donki.domain.SolarFlareRepository
import com.project.donki.usecase.SolarFlareUseCase
import com.project.donki.viewmodel.SolarFlareViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_SOLAR_FLARE_MODULE = "SCOPE_SOLAR_FLARE_MODULE"

val solarFlareModule = module {
    scope(named(SCOPE_SOLAR_FLARE_MODULE)) {
        scoped<SolarFlareApiService> { get<Retrofit>().create(SolarFlareApiService::class.java) }

        scoped { SolarFlareRepository(solarFlareApiService = get()) }

        scoped { SolarFlareUseCase(calendarRepository = get(), solarFlareRepository = get()) }

        scoped { SolarFlareViewModelFactory(solarFlareUseCase = get()) }
    }
}
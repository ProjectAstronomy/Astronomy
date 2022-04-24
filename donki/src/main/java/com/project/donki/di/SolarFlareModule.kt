package com.project.donki.di

import com.project.donki.domain.GSTApiService
import com.project.donki.domain.GSTRepository
import com.project.donki.domain.FLRApiService
import com.project.donki.domain.FLRRepository
import com.project.donki.usecases.GSTUseCase
import com.project.donki.usecases.FLRUseCase
import com.project.donki.viewmodels.GSTViewModelFactory
import com.project.donki.viewmodels.FLRViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_FLR_MODULE = "SCOPE_FLR_MODULE"
internal const val SCOPE_GST_MODULE = "SCOPE_GST_MODULE"

val flrModule = module {
    scope(named(SCOPE_FLR_MODULE)) {
        scoped<FLRApiService> { get<Retrofit>().create(FLRApiService::class.java) }

        scoped { FLRRepository(flrApiService = get()) }

        scoped { FLRUseCase(calendarRepository = get(), flrRepository = get()) }

        scoped { FLRViewModelFactory(flrUseCase = get()) }
    }
}

val gstModule = module {
    scope(named(SCOPE_GST_MODULE)) {
        scoped<GSTApiService> { get<Retrofit>().create(GSTApiService::class.java) }

        scoped { GSTRepository(gstApiService = get()) }

        scoped { GSTUseCase(calendarRepository = get(), gstRepository = get()) }

        scoped { GSTViewModelFactory(gstUseCase = get()) }
    }
}
package com.project.astronomy.di

import com.project.astronomy.repository.MainRepository
import com.project.astronomy.viewmodel.MainViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val SCOPE_MAIN_MODULE = "SCOPE_MAIN_MODULE"

val mainModule = module {
    scope(named(SCOPE_MAIN_MODULE)) {
        scoped { MainRepository(apodApiService = get()) }

        scoped { MainViewModelFactory(mainRepository = get()) }
    }
}
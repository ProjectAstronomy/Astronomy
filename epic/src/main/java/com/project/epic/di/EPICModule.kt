package com.project.epic.di

import com.project.epic.domain.EPICApiKey
import com.project.epic.domain.EPICRepository
import com.project.epic.viewmodels.EPICViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_EPIC_MODULE = "SCOPE_EPIC_MODULE"

val epicModule = module {
    scope(named(SCOPE_EPIC_MODULE)) {
        scoped<EPICApiKey> { get<Retrofit>().create(EPICApiKey::class.java) }

        scoped { EPICRepository(epicApiKey = get()) }

        scoped { EPICViewModelFactory(epicRepository = get()) }
    }
}
package com.project.astronomy.di

import androidx.lifecycle.SavedStateHandle
import com.project.astronomy.repository.MainRepository
import com.project.astronomy.utils.ResourceProvider
import com.project.astronomy.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val SCOPE_MAIN_MODULE = "SCOPE_MAIN_MODULE"

val mainModule = module {
    scope(named(SCOPE_MAIN_MODULE)) {
        scoped { ResourceProvider(androidContext()) }

        scoped { MainRepository(resourceProvider = get()) }

        viewModel { (savedStateHandle: SavedStateHandle) ->
            MainViewModel(savedStateHandle = savedStateHandle, mainRepository = get())
        }
    }
}
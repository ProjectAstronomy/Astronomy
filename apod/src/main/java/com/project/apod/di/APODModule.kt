package com.project.apod.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.project.apod.database.APODDatabase
import com.project.apod.database.DATABASE_NAME
import com.project.apod.domain.local.APODDao
import com.project.apod.domain.local.APODRepositoryLocal
import com.project.apod.domain.remote.APODApiService
import com.project.apod.domain.remote.APODRepository
import com.project.apod.usecases.APODUseCase
import com.project.apod.viewmodels.APODViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_APOD_LIST_MODULE = "SCOPE_APOD_MODULE"

val apodModule = module {
    fun provideDatabase(context: Context): APODDatabase =
        Room.databaseBuilder(context, APODDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(database: APODDatabase): APODDao = database.apodDao()

    single { provideDatabase(androidContext()) }

    single { provideDao(get()) }

    scope(named(SCOPE_APOD_LIST_MODULE)) {
        scoped<APODApiService> { get<Retrofit>().create(APODApiService::class.java) }

        scoped { APODRepositoryLocal(dao = get()) }

        scoped { APODRepository(apodApiService = get()) }

        scoped { APODUseCase(calendarRepository = get(), remoteRepository = get(), localRepository = get()) }

        viewModel { (savedStateHandle: SavedStateHandle) ->
            APODViewModel(savedStateHandle = savedStateHandle, apodUseCase = get())
        }
    }
}
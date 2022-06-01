package com.project.donki.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.project.donki.database.DONKIDatabase
import com.project.donki.database.DONKI_DATABASE_NAME
import com.project.donki.domain.local.*
import com.project.donki.domain.local.dao.*
import com.project.donki.domain.remote.*
import com.project.donki.domain.remote.api.FLRApiService
import com.project.donki.domain.remote.api.GSTApiService
import com.project.donki.usecases.FLRUseCase
import com.project.donki.usecases.GSTUseCase
import com.project.donki.viewmodels.FLRViewModel
import com.project.donki.viewmodels.GSTViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_FLR_MODULE = "SCOPE_FLR_MODULE"
internal const val SCOPE_GST_MODULE = "SCOPE_GST_MODULE"

val donkiDatabaseModule = module {
    fun provideDatabase(context: Context): DONKIDatabase =
        Room.databaseBuilder(context, DONKIDatabase::class.java, DONKI_DATABASE_NAME).build()

    fun provideAllKpIndexDao(database: DONKIDatabase): AllKpIndexDao =
        database.allKpIndexDao()

    fun provideGeomagneticStormDao(database: DONKIDatabase): GeomagneticStormDao =
        database.geomagneticStormDao()

    fun provideInstrumentDao(database: DONKIDatabase): InstrumentDao =
        database.instrumentDao()

    fun provideLinkedEventDao(database: DONKIDatabase): LinkedEventDao =
        database.linkedEventDao()

    fun provideSolarFlareDao(database: DONKIDatabase): SolarFlareDao =
        database.solarFlareDao()

    single { provideDatabase(androidContext()) }

    single { provideAllKpIndexDao(get()) }

    single { provideInstrumentDao(get()) }

    single { provideGeomagneticStormDao(get()) }

    single { provideLinkedEventDao(get()) }

    single { provideSolarFlareDao(get()) }
}

val flrModule = module {
    scope(named(SCOPE_FLR_MODULE)) {
        scoped<FLRApiService> { get<Retrofit>().create(FLRApiService::class.java) }

        scoped { FLRRepository(flrApiService = get()) }

        scoped { FLRRepositoryLocal(solarFlareDao = get(), instrumentDao = get(), linkedEventDao = get()) }

        scoped { FLRUseCase(calendarRepository = get(), remoteRepository = get(), localRepository = get()) }

        viewModel { (savedStateHandle: SavedStateHandle) ->
            FLRViewModel(savedStateHandle = savedStateHandle, flrUseCase = get())
        }
    }
}

val gstModule = module {
    scope(named(SCOPE_GST_MODULE)) {
        scoped<GSTApiService> { get<Retrofit>().create(GSTApiService::class.java) }

        scoped { GSTRepository(gstApiService = get()) }

        scoped { GSTRepositoryLocal(geomagneticStormDao = get(), allKpIndexDao = get(), linkedEventDao = get()) }

        scoped { GSTUseCase(calendarRepository = get(), remoteRepository = get(), localRepository = get()) }

        viewModel { (savedStateHandle: SavedStateHandle) ->
            GSTViewModel(savedStateHandle = savedStateHandle, gstUseCase = get())
        }
    }
}
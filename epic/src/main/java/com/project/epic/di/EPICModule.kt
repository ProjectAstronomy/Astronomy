package com.project.epic.di

import android.content.Context
import androidx.room.Room
import com.project.epic.BuildConfig
import com.project.epic.database.EPICDatabase
import com.project.epic.database.EPICDatabase.Companion.EPIC_DATABASE
import com.project.epic.domain.local.EPICDao
import com.project.epic.domain.local.EPICRepositoryLocal
import com.project.epic.domain.remote.EPICApiService
import com.project.epic.domain.remote.EPICRepository
import com.project.epic.domain.remote.EPICRepositoryFake
import com.project.epic.usecases.EPICUseCase
import com.project.epic.viewmodels.EPICViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_EPIC_MODULE = "SCOPE_EPIC_MODULE"

val epicModule = module {
    fun provideDatabase(context: Context): EPICDatabase =
        Room.databaseBuilder(context, EPICDatabase::class.java, EPIC_DATABASE).build()

    fun provideDao(database: EPICDatabase): EPICDao = database.epicDao()

    single { provideDatabase(androidContext()) }

    single { provideDao(get()) }

    scope(named(SCOPE_EPIC_MODULE)) {
        scoped<EPICApiService> { get<Retrofit>().create(EPICApiService::class.java) }

        scoped {
            if (BuildConfig.FLAVOR == "FAKE") {
                EPICRepositoryFake()
            } else {
                EPICRepository(epicApiService = get())
            }
        }

        scoped { EPICRepositoryLocal(dao = get()) }

        scoped { EPICUseCase(remoteRepository = get(), localRepository = get()) }

        scoped { EPICViewModelFactory(epicUseCase = get()) }
    }
}
package com.project.apod.di

import android.content.Context
import androidx.room.Room
import com.project.apod.BuildConfig
import com.project.apod.database.APODDatabase
import com.project.apod.domain.*
import com.project.apod.usecases.APODUseCase
import com.project.apod.viewmodels.APODViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_APOD_LIST_MODULE = "SCOPE_APOD_MODULE"
internal const val DATABASE_NAME = "a_picture_of_the_day_database"
internal const val TABLE_NAME = "a_picture_of_the_day_table"

val apodModule = module {
    fun provideDatabase(context: Context): APODDatabase =
        Room.databaseBuilder(context, APODDatabase::class.java, DATABASE_NAME).build()

    fun provideDao(database: APODDatabase): APODDao = database.apodDao()

    single { provideDatabase(androidContext()) }

    single { provideDao(get()) }

    scope(named(SCOPE_APOD_LIST_MODULE)) {
        scoped<APODApiService> { get<Retrofit>().create(APODApiService::class.java) }

        scoped { APODRepositoryLocal(dao = get()) }

        scoped {
            if (BuildConfig.FLAVOR == "FAKE") {
                APODRepositoryFake()
            } else {
                APODRepository(apodApiService = get())
            }
        }

        scoped { APODUseCase(calendarRepository = get(), remoteRepository = get(), localRepository = get()) }

        scoped { APODViewModelFactory(apodUseCase = get()) }
    }
}
package com.project.mrp.di

import com.project.mrp.domain.remote.*
import com.project.mrp.viewmodel.MissionManifestViewModelFactory
import com.project.mrp.viewmodel.PhotosViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_MISSION_MANIFEST_MODULE = "SCOPE_MISSION_MANIFEST_MODULE"
internal const val SCOPE_PHOTOS_MODULE = "SCOPE_PHOTOS_MODULE"

val missionManifestModule = module {
    scope(named(SCOPE_MISSION_MANIFEST_MODULE)) {
        scoped<MissionManifestApiService> { get<Retrofit>().create(MissionManifestApiService::class.java) }

        scoped { MissionManifestRepository(missionManifestApiService = get()) }

        scoped { MissionManifestViewModelFactory(repository = get()) }
    }
}

val photosModule = module {
    scope(named(SCOPE_PHOTOS_MODULE)) {
        scoped<PhotosApiService> { get<Retrofit>().create(PhotosApiService::class.java) }

        scoped { PhotosRepository(photosApiService = get()) }

        scoped { PhotosViewModelFactory(repository = get()) }
    }
}
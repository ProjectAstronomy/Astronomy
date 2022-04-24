package com.project.mrp.di

import com.project.mrp.domain.MissionManifestApiService
import com.project.mrp.domain.MissionManifestRepository
import com.project.mrp.viewmodel.MissionManifestViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val SCOPE_MISSION_MANIFEST_MODULE = "SCOPE_MISSION_MANIFEST_MODULE"

val missionManifestModule = module {
    scope(named(SCOPE_MISSION_MANIFEST_MODULE)) {
        scoped<MissionManifestApiService> { get<Retrofit>().create(MissionManifestApiService::class.java) }

        scoped { MissionManifestRepository(missionManifestApiService = get()) }

        scoped { MissionManifestViewModelFactory(missionManifestRepository = get()) }
    }
}
package com.project.mrp.domain

import com.project.mrp.BuildConfig
import com.project.mrp.entities.MissionManifest

class MissionManifestRepository(private val missionManifestApiService: MissionManifestApiService) {
    suspend fun loadMissionManifest(roverName: String): MissionManifest =
        missionManifestApiService.loadMissionManifest(roverName, BuildConfig.NASA_API_KEY)
}
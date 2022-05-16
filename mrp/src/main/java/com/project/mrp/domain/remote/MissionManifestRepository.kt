package com.project.mrp.domain.remote

import com.project.mrp.entities.remote.MissionManifest

class MissionManifestRepository(private val missionManifestApiService: MissionManifestApiService) {
    suspend fun loadMissionManifest(roverName: String): MissionManifest =
        missionManifestApiService.loadMissionManifest(roverName)
}
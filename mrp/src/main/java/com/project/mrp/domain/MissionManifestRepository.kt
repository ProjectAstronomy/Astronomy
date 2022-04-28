package com.project.mrp.domain

import com.project.mrp.entities.MissionManifest

class MissionManifestRepository(private val missionManifestApiService: MissionManifestApiService) {
    suspend fun loadMissionManifest(roverName: String): MissionManifest =
        missionManifestApiService.loadMissionManifest(roverName)
}
package com.project.mrp.domain.remote

import com.project.mrp.entities.remote.MissionManifest

interface BaseMissionManifestRepository {
    suspend fun loadMissionManifest(roverName: String): MissionManifest
}
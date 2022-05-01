package com.project.mrp.domain

import com.project.mrp.entities.MissionManifest

interface BaseMissionManifestRepository {
    suspend fun loadMissionManifest(roverName: String): MissionManifest
}
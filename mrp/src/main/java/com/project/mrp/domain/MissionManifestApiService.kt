package com.project.mrp.domain

import com.project.mrp.entities.remote.MissionManifest
import retrofit2.http.GET
import retrofit2.http.Path

interface MissionManifestApiService {
    @GET("/mars-photos/api/v1/manifests/{rover_name}")
    suspend fun loadMissionManifest(
        @Path("rover_name") roverName: String
    ): List<MissionManifest>
}
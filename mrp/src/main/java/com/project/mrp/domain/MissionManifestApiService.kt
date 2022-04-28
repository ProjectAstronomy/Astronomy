package com.project.mrp.domain

import com.project.mrp.entities.MissionManifest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MissionManifestApiService {
    @GET("/mars-photos/api/v1/manifests/{rover_name}")
    suspend fun loadMissionManifest(
        @Path("rover_name") roverName: String
    ): MissionManifest
}
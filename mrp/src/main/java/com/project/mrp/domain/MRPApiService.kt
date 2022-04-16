package com.project.mrp.domain

import com.project.mrp.entities.MissionManifest
import com.project.mrp.entities.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MRPApiService {
    @GET("/mars-photos/api/v1/manifests/{rover_name}")
    suspend fun loadMissionManifest(
        @Path("rover_name") roverName: String,
        @Query("api_key") apiKey: String
    ): Response<MissionManifest>

    @GET("/mars-photos/api/v1/rovers/{rover_name}")
    suspend fun loadPhotosByMartianSol(
        @Path("rover_name") roverName: String,
        @Query("sol") sol: Long,
        @Query("api_key") apiKey: String
    ): Response<Photos>

    @GET("/mars-photos/api/v1/rovers/{rover_name}")
    suspend fun loadPhotosByMartianSolAndCamera(
        @Path("rover_name") roverName: String,
        @Query("sol") sol: Long,
        @Query("camera") camera: String,
        @Query("api_key") apiKey: String
    ): Response<Photos>

    @GET("/mars-photos/api/v1/rovers/{rover_name}")
    suspend fun loadPhotosByEarthDate(
        @Path("rover_name") roverName: String,
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String
    ): Response<Photos>

    @GET("/mars-photos/api/v1/rovers/{rover_name}")
    suspend fun loadPhotosByEarthDateAndCamera(
        @Path("rover_name") roverName: String,
        @Query("earth_date") earthDate: String,
        @Query("camera") camera: String,
        @Query("api_key") apiKey: String
    ): Response<Photos>
}
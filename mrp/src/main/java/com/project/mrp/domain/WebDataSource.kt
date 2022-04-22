package com.project.mrp.domain

import com.project.core.domain.BaseWebDataSource
import com.project.mrp.BuildConfig
import com.project.mrp.entities.MissionManifest
import com.project.mrp.entities.Photos
import retrofit2.Response

class WebDataSource : BaseWebDataSource() {
    private val mrpApiService = retrofit.create(MRPApiService::class.java)

    suspend fun loadMissionManifest(roverName: String): Response<MissionManifest> =
        mrpApiService.loadMissionManifest(roverName, BuildConfig.NASA_API_KEY)

    suspend fun loadPhotosByMartianSol(roverName: String, sol: Long): Response<Photos> =
        mrpApiService.loadPhotosByMartianSol(roverName, sol, BuildConfig.NASA_API_KEY)

    suspend fun loadPhotosByMartianSolAndCamera(roverName: String, sol: Long, camera: String): Response<Photos> =
        mrpApiService.loadPhotosByMartianSolAndCamera(roverName, sol, camera, BuildConfig.NASA_API_KEY)

    suspend fun loadPhotosByEarthDate(roverName: String, earthDate: String): Response<Photos> =
        mrpApiService.loadPhotosByEarthDate(roverName, earthDate, BuildConfig.NASA_API_KEY)

    suspend fun loadPhotosByEarthDateAndCamera(roverName: String, earthDate: String, camera: String): Response<Photos> =
        mrpApiService.loadPhotosByEarthDateAndCamera(roverName, earthDate, camera, BuildConfig.NASA_API_KEY)
}
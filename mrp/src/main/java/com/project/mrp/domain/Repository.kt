package com.project.mrp.domain

import com.project.mrp.entities.MissionManifest
import com.project.mrp.entities.Photos
import retrofit2.Response

class Repository(private val webDataSource: WebDataSource) {
    suspend fun loadMissionManifest(roverName: String): Response<MissionManifest> =
        webDataSource.loadMissionManifest(roverName)

    suspend fun loadPhotosByMarianSol(roverName: String, sol: Long): Response<Photos> =
        webDataSource.loadPhotosByMartianSol(roverName, sol)

    suspend fun loadPhotosByMarianSolAndCamera(roverName: String, sol: Long, camera: String): Response<Photos> =
        webDataSource.loadPhotosByMartianSolAndCamera(roverName, sol, camera)

    suspend fun loadPhotosByEarthDate(roverName: String, earthDate: String): Response<Photos> =
        webDataSource.loadPhotosByEarthDate(roverName, earthDate)

    suspend fun loadPhotosByEarthDateAndCamera(roverName: String, earthDate: String, camera: String): Response<Photos> =
        webDataSource.loadPhotosByEarthDateAndCamera(roverName, earthDate, camera)
}
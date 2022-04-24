package com.project.mrp.domain

import com.project.mrp.BuildConfig
import com.project.mrp.entities.Photos

class PhotosRepository(private val photosApiService: PhotosApiService) {
    suspend fun loadPhotosByMarianSol(roverName: String, sol: Long): Photos =
        photosApiService.loadPhotosByMartianSol(roverName, sol, BuildConfig.NASA_API_KEY)
}
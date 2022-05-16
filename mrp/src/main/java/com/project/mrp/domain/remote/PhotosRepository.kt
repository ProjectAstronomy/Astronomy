package com.project.mrp.domain.remote

import com.project.mrp.entities.remote.Photos

class PhotosRepository(private val photosApiService: PhotosApiService) {
    suspend fun loadPhotosByMartianSol(roverName: String, sol: Long): Photos =
        photosApiService.loadPhotosByMartianSol(roverName, sol)
}
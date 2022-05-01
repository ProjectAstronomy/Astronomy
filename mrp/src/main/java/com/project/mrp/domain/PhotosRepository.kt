package com.project.mrp.domain

import com.project.mrp.entities.Photos

class PhotosRepository(private val photosApiService: PhotosApiService) : BasePhotosRepository {
    override suspend fun loadPhotosByMartianSol(roverName: String, sol: Long): Photos =
        photosApiService.loadPhotosByMartianSol(roverName, sol)
}